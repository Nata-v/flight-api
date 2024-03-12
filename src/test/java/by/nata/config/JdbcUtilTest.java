package by.nata.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtilTest extends JdbcUtil{
    private static JdbcUtil jdbcUtil;
    protected JdbcUtilTest() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");

    }
    protected Connection getJdbcConnection() throws SQLException {
        return   DriverManager.getConnection("jdbc:mysql://localhost:3306/flight_api_test?createDatabaseIfNotExist=true",
                "user",
                "user");
    }
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (jdbcUtil == null){
            jdbcUtil = new JdbcUtil();
        }
        return jdbcUtil.getJdbcConnection();
    }
}
