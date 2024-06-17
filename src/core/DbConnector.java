package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/tourismagency";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "PtYzxV123.!";
    private Connection connection = null;
    private static  DbConnector instance = null;

    public Connection getConnection() {
        return connection;
    }

    private DbConnector() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DbConnector();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();

    }
}
