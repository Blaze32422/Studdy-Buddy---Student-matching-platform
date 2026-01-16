package studentApplication.createtables;

import java.sql.Connection;
import java.sql.Statement;

import util.DBConnection;

public class CreatePostsTable {
	 public static void main(String[] args) {
	        try (Connection conn = DBConnection.getConnection();
	             Statement stmt = conn.createStatement()) {

	            String sql = """
	                CREATE TABLE IF NOT EXISTS Posts (
	                    post_id INT AUTO_INCREMENT PRIMARY KEY,
	                    description VARCHAR(300) NOT NULL,
	                    date_posted DATE,
	                    class VARCHAR(100),
	                    status BOOLEAN,
	                    user_id INT NOT NULL,
						accepter_id INT,
						FOREIGN KEY (accepter_id) REFERENCES Users(user_id)
	                    FOREIGN KEY (user_id) REFERENCES Users(user_id)
	                );
	            """;

	            stmt.execute(sql);
	            System.out.println("Posts table created.");

	        } catch (Exception e) {
	            System.out.println("Error creating Posts table: " + e.getMessage());
	        }
	    }
}
