package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.infrastructure.constant.ConstantsSQL;
import by.anpoliakov.repository.MeterTypeRepository;
import by.anpoliakov.util.ConnectionManager;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса MeterTypeRepository и
 * предоставляет методы для взаимодействия с таблицей meters_types в БД
 */
public class MeterTypeRepositoryImpl implements MeterTypeRepository {
    private static MeterTypeRepository instance;

    public MeterTypeRepositoryImpl() {
    }

    public static MeterTypeRepository getInstance() {
        if (instance == null) {
            instance = new MeterTypeRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Optional<MeterType> create(String typeName) {
        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.CREATE_METER_TYPE)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, typeName);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return findMeterType(typeName);
    }

    @Override
    public Optional<MeterType> findMeterType(String typeName) {
        ResultSet resultSet = null;
        MeterType meterType = null;

        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.SELECT_METER_TYPE)) {

            preparedStatement.setString(1, typeName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BigInteger meterTypeId = resultSet.getBigDecimal(ConstantsSQL.METER_TYPE_ID_LABEL).toBigInteger();
                String meterTypeName = resultSet.getString(ConstantsSQL.METER_TYPE_NAME_LABEL);

                meterType = new MeterType(meterTypeId, meterTypeName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return Optional.ofNullable(meterType);
    }

    @Override
    public Optional<List<String>> getNamesMetersTypes() {
        ResultSet resultSet = null;
        List<String> names = null;

        try (Connection connection = ConnectionManager.createConnection();
             Statement statement = connection.createStatement()) {

            resultSet = statement.executeQuery(ConstantsSQL.SELECT_ALL_METERS_TYPES);
            names = new ArrayList<>();

            while (resultSet.next()) {
                String meterTypeName = resultSet.getString(ConstantsSQL.METER_TYPE_NAME_LABEL);
                names.add(meterTypeName);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return Optional.ofNullable(names);
    }
}
