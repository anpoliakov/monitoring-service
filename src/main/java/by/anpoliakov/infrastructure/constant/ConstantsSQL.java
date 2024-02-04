package by.anpoliakov.infrastructure.constant;

public class ConstantsSQL {
    public static final String NAME_SYSTEM_SCHEMA_LIQUIBASE = "system_tables_liquibase";

    public static final String USER_ID_LABEL = "user_id";
    public static final String USER_LOGIN_LABEL = "login";
    public static final String USER_PASSWORD_LABEL = "password";
    public static final String ROLE_TYPE_NAME_LABEL = "name";
    public static final String METER_TYPE_ID_LABEL = "meter_type_id";
    public static final String METER_TYPE_NAME_LABEL = "name";
    public static final String AUDIT_LOG_ID_LABEL = "audit_log_id";
    public static final String AUDIT_LOG_DATE_LABEL = "date";
    public static final String ACTION_TYPE_NAME_LABEL = "name";
    public static final String METER_READING_ID_LABEL = "meter_reading_id";
    public static final String METER_READING_DATE_LABEL = "date";
    public static final String METER_READING_READING_LABEL = "reading";

    public static final String CREATE_USER = "INSERT INTO entities.users (login, password, role_type_id) " +
            "VALUES (?, ?, (SELECT role_type_id FROM entities.roles_types WHERE entities.roles_types.name = ?))";

    public static final String CREATE_METER_TYPE = "INSERT INTO entities.meters_types (name) VALUES (?)";

    public static final String CREATE_AUDIT_LOG = "INSERT INTO entities.audit_logs (user_id, date, action_type_id) " +
            "VALUES ((SELECT user_id FROM entities.users WHERE entities.users.login = ?), " +
            "?, " +
            "(SELECT action_type_id FROM entities.actions_types WHERE entities.actions_types.name = ?))";

    public static final String CREATE_METERS_READINGS = "INSERT INTO entities.meters_readings (user_id, meter_type_id, reading, date) " +
            "VALUES (?,?,?,?)";

    public static final String HAS_METER_READING = "SELECT * FROM entities.meters_readings " +
            "WHERE entities.meters_readings.user_id = ? " +
            "AND entities.meters_readings.meter_type_id = ? " +
            "AND EXTRACT(MONTH FROM entities.meters_readings.date) = ? " +
            "AND EXTRACT(YEAR FROM entities.meters_readings.date) = ?";

    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM entities.users " +
            "INNER JOIN entities.roles_types ON entities.users.role_type_id = entities.roles_types.role_type_id " +
            "WHERE entities.users.login = ?";

    public static final String SELECT_ALL_USERS = "SELECT * FROM entities.users " +
            "INNER JOIN entities.roles_types ON entities.users.role_type_id = entities.roles_types.role_type_id";

    public static final String SELECT_METER_TYPE = "SELECT * FROM entities.meters_types " +
            "WHERE entities.meters_types.name = ?";

    public static final String SELECT_ALL_METERS_TYPES = "SELECT * FROM entities.meters_types";

    public static final String SELECT_AUDIT_LOGS_BY_LOGIN = "SELECT * FROM entities.audit_logs " +
            "INNER JOIN entities.users ON entities.audit_logs.user_id = entities.users.user_id " +
            "INNER JOIN entities.actions_types ON entities.audit_logs.action_type_id = entities.actions_types.action_type_id " +
            "WHERE entities.users.login = ?";

    public static final String SELECT_METERS_READINGS_BY_DATE = "SELECT * FROM entities.meters_readings " +
            "INNER JOIN entities.users ON entities.meters_readings.user_id = entities.users.user_id " +
            "INNER JOIN entities.meters_types ON entities.meters_readings.meter_type_id = entities.meters_types.meter_type_id " +
            "WHERE entities.meters_readings.user_id = ? AND EXTRACT(MONTH FROM entities.meters_readings.date) = ? " +
            "AND EXTRACT(YEAR FROM entities.meters_readings.date) = ? ";

    public static final String SELECT_ALL_METERS_READINGS_USER = "SELECT * FROM entities.meters_readings " +
            "INNER JOIN entities.users ON entities.meters_readings.user_id = entities.users.user_id " +
            "INNER JOIN entities.meters_types ON entities.meters_readings.meter_type_id = entities.meters_types.meter_type_id " +
            "WHERE entities.users.user_id = ?";

    public static final String SELECT_LAST_METERS_READINGS_BY_USER_ID = "SELECT * FROM entities.meters_readings " +
            "INNER JOIN entities.users ON entities.meters_readings.user_id = entities.users.user_id " +
            "INNER JOIN entities.meters_types ON entities.meters_readings.meter_type_id = entities.meters_types.meter_type_id " +
            "WHERE EXTRACT(MONTH FROM entities.meters_readings.date) = (SELECT MAX(EXTRACT(MONTH FROM entities.meters_readings.date)) " +
            "FROM entities.meters_readings) " +
            "AND entities.meters_readings.user_id = ?";
}
