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

/** Реализация интерфейса AuditLogRepository и
 * предоставляет методы для взаимодействия с таблицей audit_logs в БД */
public class AuditLogRepositoryImpl implements AuditLogRepository {
    private static AuditLogRepository instance;
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private AuditLogRepositoryImpl() {}

    public static AuditLogRepository getInstance(){
        if (instance == null){
            instance = new AuditLogRepositoryImpl();
        }
        return instance;
    }

    /** Метод для добавления сущности AuditLog в базу данных */
    @Override
    public void add(AuditLog auditLog) {
        try {
            connection = ConnectionManager.createConnection();
            connection.setAutoCommit(false);

            pst = connection.prepareStatement (ConstantsSQL.CREATE_AUDIT_LOG);
            pst.setString(1, auditLog.getLoginUser());
            pst.setTimestamp(2, Timestamp.valueOf(auditLog.getDate()));
            pst.setString(3, auditLog.getActionType().name());
            pst.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            ConnectionManager.rollBack(connection);
        }finally {
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }
    }

    @Override
    public Optional<List<AuditLog>> getAuditLogsByLogin(String loginUser) {
        List <AuditLog> auditLogs = null;

        try {
            connection = ConnectionManager.createConnection();

            pst = connection.prepareStatement(ConstantsSQL.SELECT_AUDIT_LOGS_BY_LOGIN);
            pst.setString(1, loginUser);
            rs = pst.executeQuery();
            auditLogs = new ArrayList<>();

            while (rs.next()){
                BigInteger auditLogId = rs.getBigDecimal(ConstantsSQL.AUDIT_LOG_ID_LABEL).toBigInteger();
                Timestamp timestamp = rs.getTimestamp(ConstantsSQL.AUDIT_LOG_DATE_LABEL);
                LocalDateTime date = timestamp.toLocalDateTime();
                String login = rs.getString(ConstantsSQL.USER_LOGIN_LABEL);
                ActionType action = ActionType.valueOf(rs.getString(ConstantsSQL.ACTION_TYPE_NAME_LABEL));

                auditLogs.add(new AuditLog(auditLogId, login, date, action));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return Optional.ofNullable(auditLogs);
    }
}
