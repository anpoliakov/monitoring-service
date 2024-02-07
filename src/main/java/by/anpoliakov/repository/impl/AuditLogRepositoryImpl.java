package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.AuditLog;
import by.anpoliakov.domain.enums.ActionType;
import by.anpoliakov.infrastructure.constant.ConstantsSQL;
import by.anpoliakov.repository.AuditLogRepository;
import by.anpoliakov.util.ConnectionManager;

import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса AuditLogRepository и
 * предоставляет методы для взаимодействия с таблицей audit_logs в БД
 */
public class AuditLogRepositoryImpl implements AuditLogRepository {
    private static AuditLogRepository instance;

    private AuditLogRepositoryImpl() {
    }

    public static AuditLogRepository getInstance() {
        if (instance == null) {
            instance = new AuditLogRepositoryImpl();
        }
        return instance;
    }

    /**
     * Метод для добавления сущности AuditLog в базу данных
     */
    @Override
    public void create(AuditLog auditLog) {
        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.CREATE_AUDIT_LOG)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, auditLog.getLoginUser());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(auditLog.getDate()));
            preparedStatement.setString(3, auditLog.getActionType().name());
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<List<AuditLog>> findAuditLogsByLogin(String loginUser) {
        List<AuditLog> auditLogs = null;
        ResultSet resultSet = null;

        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.SELECT_AUDIT_LOGS_BY_LOGIN)) {

            preparedStatement.setString(1, loginUser);
            resultSet = preparedStatement.executeQuery();
            auditLogs = new ArrayList<>();

            while (resultSet.next()) {
                BigInteger auditLogId = resultSet.getBigDecimal(ConstantsSQL.AUDIT_LOG_ID_LABEL).toBigInteger();
                Timestamp timestamp = resultSet.getTimestamp(ConstantsSQL.AUDIT_LOG_DATE_LABEL);
                LocalDateTime date = timestamp.toLocalDateTime();
                String login = resultSet.getString(ConstantsSQL.USER_LOGIN_LABEL);
                ActionType action = ActionType.valueOf(resultSet.getString(ConstantsSQL.ACTION_TYPE_NAME_LABEL));

                auditLogs.add(new AuditLog(auditLogId, login, date, action));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return Optional.ofNullable(auditLogs);
    }
}
