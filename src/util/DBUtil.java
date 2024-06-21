package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*

public class DBUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Ch11072003$";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
*/



public class DBUtil {

    // Replace with your actual JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "HONG123";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

