package studentApplication.createtables;

import java.sql.Connection;
import java.sql.Statement;

import util.DBConnection;

public class CreateMessagesTable {
	 public static void main(String[] args) {
	        try (Connection conn = DBConnection.getConnection();
	             Statement stmt = conn.createStatement()) {

	            String sql = """
	                CREATE TABLE IF NOT EXISTS Messages (
						message_id INT AUTO_INCREMENT PRIMARY KEY,
	                    message VARCHAR(300),
	                    time_sent TIMESTAMP,
	                    sender_id INT NOT NULL,
	                    receiver_id INT NOT NULL,
	                    FOREIGN KEY (sender_id) REFERENCES Users(user_id),
	                    FOREIGN KEY (receiver_id) REFERENCES Users(user_id)
	                );
	            """;

	            stmt.execute(sql);
	            System.out.println("Messages table created.");

	        } catch (Exception e) {
	            System.out.println("Error creating Messages table: " + e.getMessage());
	        }
	    }
}
