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

/** Реализация интерфейса MeterReadingRepository и
 * предоставляет методы для взаимодействия с таблицей meters_readings в БД */
public class MeterReadingRepositoryImpl implements MeterReadingRepository {
    private static MeterReadingRepository instance;
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private MeterReadingRepositoryImpl() {}

    public static MeterReadingRepository getInstance(){
        if (instance == null){
            instance = new MeterReadingRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void add(MeterReading meterReading) {
        try {
            connection = ConnectionManager.createConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement (ConstantsSQL.CREATE_METERS_READINGS);

            pst.setObject(1, meterReading.getUserId());
            pst.setObject(2, meterReading.getMeterTypeId());
            pst.setInt(3, meterReading.getReadings());
            pst.setTimestamp(4, Timestamp.valueOf(meterReading.getDate()));

            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollBack(connection, e);
        }finally {
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }
    }

    @Override
    public Optional<List<MeterReading>> getLastMetersReadingsByUserId(BigInteger userId) {
        List <MeterReading> readings = null;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(ConstantsSQL.SELECT_LAST_METERS_READINGS_BY_USER_ID);
            pst.setObject(1, userId);
            rs = pst.executeQuery();
            readings = new ArrayList<>();

            while (rs.next()){
                BigInteger meterReadingId = rs.getBigDecimal(ConstantsSQL.METER_READING_ID_LABEL).toBigInteger();
                BigInteger meterTypeId = rs.getBigDecimal(ConstantsSQL.METER_TYPE_ID_LABEL).toBigInteger();
                Integer reading = rs.getInt(ConstantsSQL.METER_READING_READING_LABEL);
                Timestamp timestamp = rs.getTimestamp(ConstantsSQL.METER_READING_DATE_LABEL);
                LocalDateTime date = timestamp.toLocalDateTime();

                readings.add(new MeterReading(meterReadingId, userId, meterTypeId, reading, date));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return Optional.ofNullable(readings);
    }

    @Override
    public boolean hasMeterReading(MeterReading meterReading) {
        boolean hasMeterReading = false;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(ConstantsSQL.HAS_METER_READING);
            pst.setObject(1, meterReading.getUserId());
            pst.setObject(2, meterReading.getMeterTypeId());
            pst.setInt(3, meterReading.getDate().getMonthValue());
            pst.setInt(4, meterReading.getDate().getYear());
            rs = pst.executeQuery();

            while (rs.next()){
                hasMeterReading = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return hasMeterReading;
    }

    @Override
    public Optional<List<MeterReading>> getMetersReadingsBySpecificDate(BigInteger userId, int month, int year) {
        List <MeterReading> readings = null;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(ConstantsSQL.SELECT_METERS_READINGS_BY_DATE);
            pst.setObject(1, userId);
            pst.setInt(2, month);
            pst.setInt(3, year);

            rs = pst.executeQuery();
            readings = new ArrayList<>();

            while (rs.next()){
                BigInteger meterReadingId = rs.getBigDecimal(ConstantsSQL.METER_READING_ID_LABEL).toBigInteger();
                BigInteger meterTypeId = rs.getBigDecimal(ConstantsSQL.METER_TYPE_ID_LABEL).toBigInteger();
                Integer reading = rs.getInt(ConstantsSQL.METER_READING_READING_LABEL);
                Timestamp timestamp = rs.getTimestamp(ConstantsSQL.METER_READING_DATE_LABEL);
                LocalDateTime date = timestamp.toLocalDateTime();

                readings.add(new MeterReading(meterReadingId, userId, meterTypeId, reading, date));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return Optional.ofNullable(readings);
    }

    @Override
    public Optional<List<MeterReading>> getMetersReadingsUser(BigInteger userId) {
        List <MeterReading> readings = null;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(ConstantsSQL.SELECT_ALL_METERS_READINGS_USER);
            pst.setObject(1, userId);
            rs = pst.executeQuery();
            readings = new ArrayList<>();

            while (rs.next()){
                BigInteger meterReadingId = rs.getBigDecimal(ConstantsSQL.METER_READING_ID_LABEL).toBigInteger();
                BigInteger meterTypeId = rs.getBigDecimal(ConstantsSQL.METER_TYPE_ID_LABEL).toBigInteger();
                Integer reading = rs.getInt(ConstantsSQL.METER_READING_READING_LABEL);
                Timestamp timestamp = rs.getTimestamp(ConstantsSQL.METER_READING_DATE_LABEL);
                LocalDateTime date = timestamp.toLocalDateTime();

                readings.add(new MeterReading(meterReadingId, userId, meterTypeId, reading, date));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return Optional.ofNullable(readings);
    }


    /**
     * Метод для отмены транзакции
     * */
    private void rollBack(Connection connection, SQLException e){
        System.err.println("Произошла ошибка: " + e.getMessage());
        if (connection != null) {
            try {
                connection.rollback();
                System.err.println("Транзакция отменена.");
            } catch (SQLException rollbackException) {
                System.err.println("Ошибка при откате транзакции: " + rollbackException.getMessage());
            }
        }
    }
}
