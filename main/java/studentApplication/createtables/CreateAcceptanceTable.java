package studentApplication.createtables;

import java.sql.Connection;
import java.sql.Statement;

import util.DBConnection;

public class CreateAcceptanceTable {
	  public static void main(String[] args) {
	        try (Connection conn = DBConnection.getConnection();
	             Statement stmt = conn.createStatement()) {

	            String sql = """
	                CREATE TABLE IF NOT EXISTS Acceptances (
						accetpance_id INT AUTO_INCREMENT PRIMARY KEY,
	                    date_accepted DATE,
	                    user_id INT NOT NULL,
	                    post_id INT NOT NULL,
	                    FOREIGN KEY (user_id) REFERENCES Users(user_id),
	                    FOREIGN KEY (post_id) REFERENCES Posts(post_id)
	                );
	            """;

	            stmt.execute(sql);
	            System.out.println("Acceptance table created.");

	        } catch (Exception e) {
	            System.out.println("Error creating Acceptance table: " + e.getMessage());
	        }
	    }
}