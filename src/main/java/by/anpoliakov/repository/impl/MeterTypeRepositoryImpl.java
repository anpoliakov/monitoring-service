package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.infrastructure.constant.ConstantsSQL;
import by.anpoliakov.repository.MeterTypeRepository;
import by.anpoliakov.util.ConnectionManager;

import java.math.BigInteger;
import java.sql.*;
import java.util.*;

/** Реализация интерфейса MeterTypeRepository и
 * предоставляет методы для взаимодействия с таблицей meters_types в БД */
public class MeterTypeRepositoryImpl implements MeterTypeRepository {
    private static MeterTypeRepository instance;
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private Statement st = null;

    public MeterTypeRepositoryImpl() {}

    public static MeterTypeRepository getInstance(){
        if (instance == null){
            instance = new MeterTypeRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void add(String typeName) {
        try {
            connection = ConnectionManager.createConnection();
            connection.setAutoCommit(false);

            pst = connection.prepareStatement (ConstantsSQL.CREATE_METER_TYPE);
            pst.setString(1, typeName);
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
    public Optional<MeterType> getMeterType(String typeName) {
        MeterType meterType = null;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(ConstantsSQL.SELECT_METER_TYPE);
            pst.setString(1, typeName);
            rs = pst.executeQuery();

            while (rs.next()){
                BigInteger meterTypeId = rs.getBigDecimal(ConstantsSQL.METER_TYPE_ID_LABEL).toBigInteger();
                String meterTypeName = rs.getString(ConstantsSQL.METER_TYPE_NAME_LABEL);

                meterType = new MeterType(meterTypeId, meterTypeName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return Optional.ofNullable(meterType);
    }

    @Override
    public Optional<List<String>> getNamesMetersTypes(){
        List <String> names = null;

        try {
            connection = ConnectionManager.createConnection();
            st = connection.createStatement();
            rs = st.executeQuery(ConstantsSQL.SELECT_ALL_METERS_TYPES);
            names = new ArrayList<>();

            while (rs.next()){
                String meterTypeName = rs.getString(ConstantsSQL.METER_TYPE_NAME_LABEL);
                names.add(meterTypeName);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(st);
            ConnectionManager.closeConnection();
        }

        return Optional.ofNullable(names);
    }
}
