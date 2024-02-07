package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.MeterReading;
import by.anpoliakov.infrastructure.constant.ConstantsSQL;
import by.anpoliakov.repository.MeterReadingRepository;
import by.anpoliakov.util.ConnectionManager;

import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса MeterReadingRepository и
 * предоставляет методы для взаимодействия с таблицей meters_readings в БД
 */
public class MeterReadingRepositoryImpl implements MeterReadingRepository {
    private static MeterReadingRepository instance;

    private MeterReadingRepositoryImpl() {
    }

    public static MeterReadingRepository getInstance() {
        if (instance == null) {
            instance = new MeterReadingRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void create(MeterReading meterReading) {
        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.CREATE_METERS_READINGS)) {

            connection.setAutoCommit(false);
            preparedStatement.setObject(1, meterReading.getUserId());
            preparedStatement.setObject(2, meterReading.getMeterTypeId());
            preparedStatement.setInt(3, meterReading.getReadings());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(meterReading.getDate()));
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<List<MeterReading>> findLastMetersReadingsByUserId(BigInteger userId) {
        ResultSet resultSet = null;
        List<MeterReading> readings = null;

        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.SELECT_LAST_METERS_READINGS_BY_USER_ID)) {

            preparedStatement.setObject(1, userId);
            resultSet = preparedStatement.executeQuery();
            readings = new ArrayList<>();

            while (resultSet.next()) {
                BigInteger meterReadingId = resultSet.getBigDecimal(ConstantsSQL.METER_READING_ID_LABEL).toBigInteger();
                BigInteger meterTypeId = resultSet.getBigDecimal(ConstantsSQL.METER_TYPE_ID_LABEL).toBigInteger();
                Integer reading = resultSet.getInt(ConstantsSQL.METER_READING_READING_LABEL);
                Timestamp timestamp = resultSet.getTimestamp(ConstantsSQL.METER_READING_DATE_LABEL);
                LocalDateTime date = timestamp.toLocalDateTime();

                readings.add(new MeterReading(meterReadingId, userId, meterTypeId, reading, date));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return Optional.ofNullable(readings);
    }

    @Override
    public boolean hasMeterReading(MeterReading meterReading) {
        ResultSet resultSet = null;
        boolean hasMeterReading = false;

        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.HAS_METER_READING)) {

            preparedStatement.setObject(1, meterReading.getUserId());
            preparedStatement.setObject(2, meterReading.getMeterTypeId());
            preparedStatement.setInt(3, meterReading.getDate().getMonthValue());
            preparedStatement.setInt(4, meterReading.getDate().getYear());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                hasMeterReading = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return hasMeterReading;
    }

    @Override
    public Optional<List<MeterReading>> findMetersReadingsBySpecificDate(BigInteger userId, int month, int year) {
        ResultSet resultSet = null;
        List<MeterReading> readings = null;

        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.SELECT_METERS_READINGS_BY_DATE)) {

            preparedStatement.setObject(1, userId);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, year);

            resultSet = preparedStatement.executeQuery();
            readings = new ArrayList<>();

            while (resultSet.next()) {
                BigInteger meterReadingId = resultSet.getBigDecimal(ConstantsSQL.METER_READING_ID_LABEL).toBigInteger();
                BigInteger meterTypeId = resultSet.getBigDecimal(ConstantsSQL.METER_TYPE_ID_LABEL).toBigInteger();
                Integer reading = resultSet.getInt(ConstantsSQL.METER_READING_READING_LABEL);
                Timestamp timestamp = resultSet.getTimestamp(ConstantsSQL.METER_READING_DATE_LABEL);
                LocalDateTime date = timestamp.toLocalDateTime();

                readings.add(new MeterReading(meterReadingId, userId, meterTypeId, reading, date));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return Optional.ofNullable(readings);
    }

    @Override
    public Optional<List<MeterReading>> findMetersReadingsByUserId(BigInteger userId) {
        ResultSet resultSet = null;
        List<MeterReading> readings = null;

        try (Connection connection = ConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ConstantsSQL.SELECT_ALL_METERS_READINGS_USER)) {

            preparedStatement.setObject(1, userId);
            resultSet = preparedStatement.executeQuery();
            readings = new ArrayList<>();

            while (resultSet.next()) {
                BigInteger meterReadingId = resultSet.getBigDecimal(ConstantsSQL.METER_READING_ID_LABEL).toBigInteger();
                BigInteger meterTypeId = resultSet.getBigDecimal(ConstantsSQL.METER_TYPE_ID_LABEL).toBigInteger();
                Integer reading = resultSet.getInt(ConstantsSQL.METER_READING_READING_LABEL);
                Timestamp timestamp = resultSet.getTimestamp(ConstantsSQL.METER_READING_DATE_LABEL);
                LocalDateTime date = timestamp.toLocalDateTime();

                readings.add(new MeterReading(meterReadingId, userId, meterTypeId, reading, date));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(resultSet);
        }

        return Optional.ofNullable(readings);
    }
}
