package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    
    private static final String DB_URL = "";
    private static final String USER = "";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static Connection getConnection(String db_url, String user, String pass) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
        return DriverManager.getConnection(db_url, user, pass);
    }

    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()){
            if(conn != null) {
                System.out.println("Connected to MySQL database");
            } else {
                System.out.println("Failed to connect to MySQL database");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}