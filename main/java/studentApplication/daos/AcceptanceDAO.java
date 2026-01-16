package studentApplication.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import studentApplication.classes.Acceptance;

public class AcceptanceDAO {
    private final Connection conn;

    public AcceptanceDAO(Connection conn) {
        this.conn = conn;   
    }
    // INSERT
    public boolean insertAcceptance(Date dateAccepted, int userId, int postId) {
        String sql = "INSERT INTO Acceptances (date_accepted, user_id, post_id) VALUES (?, ?, ?)";
        String updatePostSQL = "UPDATE Posts SET status = TRUE, accepter_id = ? WHERE post_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql); 
        PreparedStatement psUpdatePost = conn.prepareStatement(updatePostSQL)) {
            conn.setAutoCommit(false);

            ps.setDate(1, dateAccepted);
            ps.setInt(2, userId);
            ps.setInt(3, postId);
            int rowsInserted = ps.executeUpdate();

            psUpdatePost.setObject(1,userId, java.sql.Types.INTEGER);
            psUpdatePost.setInt(2, postId);
            
            int rowsUpdated = psUpdatePost.executeUpdate();
            
            conn.commit();
            return rowsInserted == 1 && rowsUpdated == 1; 
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("Error updating Post: " + e.getMessage());            }
            System.out.println("Error inserting Acceptance: " + e.getMessage());
            return false;
        }
        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }

    // GET one record
    public boolean exists(int userId, int postId) {
        String sql = "SELECT * FROM Acceptances WHERE user_id = ? AND post_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, postId);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error checking Acceptance: " + e.getMessage());
        }
        return false;
    }

    // DELETE
    public boolean deleteAcceptance(int userId, int postId) {
        String deleteAcceptanceSQL = "DELETE FROM Acceptances WHERE user_id = ? AND post_id = ?";
        String updatePostSQL = "UPDATE Posts SET status = FALSE, accepter_id = NULL WHERE post_id = ?";
        try {
            try (PreparedStatement ps = conn.prepareStatement(deleteAcceptanceSQL)) {

            ps.setInt(1, userId);
            ps.setInt(2, postId);
            ps.executeUpdate();

            }
        try (PreparedStatement ps = conn.prepareStatement(updatePostSQL)) {

            ps.setInt(1, postId);
            int rows = ps.executeUpdate();
            return rows == 1;
        }  
    }   catch (SQLException e) {
            System.out.println("Error deleting Acceptance: " + e.getMessage());
            return false;
        }
    }

    // GET ALL
    public List<String> getAllAcceptance() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT * FROM Acceptances";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add("User " + rs.getInt("user_id") +
                         " accepted Post " + rs.getInt("post_id") +
                         " on " + rs.getDate("date_accepted"));
            }

        } catch (Exception e) {
            System.out.println("Error getting Acceptances: " + e.getMessage());
        }

        return list;
    }

    // GET ACCEPTANCE GIVEN POST ID
    public Acceptance getAcceptanceByPostId(int postId) {
        String sql = "SELECT * FROM Acceptances WHERE post_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Acceptance acceptance = new Acceptance(
                        rs.getDate("date_accepted"),
                        rs.getInt("user_id"),
                        rs.getInt("post_id")
                );
                return acceptance;
                }
                
            }

        } catch (Exception e) {
            System.out.println("Error getting Acceptances: " + e.getMessage());
        }
        return null;
    }
}



