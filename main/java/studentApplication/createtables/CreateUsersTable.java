package studentApplication.createtables;

import java.sql.Connection;
import java.sql.Statement;

import util.DBConnection;


public class CreateUsersTable {
	public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS Users (
                    user_id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(100) NOT NULL,
                    password VARCHAR(100),
                    first_name VARCHAR(100),
                    last_name VARCHAR(100),
                    email VARCHAR(100),
                    major VARCHAR(50),
                    school_year INT
                );
            """;

            stmt.execute(sql);
            System.out.println("Users table created.");

        } catch (Exception e) {
            System.out.println("Error creating Users table: " + e.getMessage());
        }
    }
}
