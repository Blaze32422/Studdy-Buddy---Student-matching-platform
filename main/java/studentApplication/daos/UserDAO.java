package studentApplication.daos;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import studentApplication.classes.User;

public class UserDAO {
    
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;   
    }
    // CHECK IF EMAIL EXISTS
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ? LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            System.out.println("Error checking email: " + e.getMessage());
            return true; // safer to block registration if error
        }
    }

    // CHECK IF USERNAME EXISTS
    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ? LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            System.out.println("Error checking username: " + e.getMessage());
            return true; // safer to block registration if error
        }
    }

    // REGISTER A NEW USER
    public boolean registerUser(User user) {

        if (emailExists(user.getEmail())) {
            System.out.println("Email already taken.");
            return false;
        } else if (usernameExists(user.getUsername())) {
            System.out.println("Username already taken.");
            return false;
        }

        String sql = "INSERT INTO Users (first_name, last_name, email, username, password, major, school_year) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getMajor());
            stmt.setInt(7, user.getSchoolYear());

            
            int rows = stmt.executeUpdate();

            if (rows == 1) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    if(generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                } catch (SQLException e) { 
                    System.out.println("Error retrieving user ID: " + e.getMessage());
                }
            }
            return rows == 1;

        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    // GET USER GIVEN EMAIL AND PASSWORD
    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT user_id, first_name, last_name, email, username, password, major, school_year FROM Users WHERE email = ? AND password = ? LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user= new User(rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getString("major"),
                                    rs.getInt("school_year"));
                    user.setUserId(rs.getInt("user_id"));
                    return user;
                }
            }

        } catch (Exception e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }

    // GET USER GIVEN USERNAME
    public User getUserByUsername(String username) {
        String sql = "SELECT user_id, first_name, last_name, email, username, password, major, school_year FROM Users WHERE username = ? LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user= new User(rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getString("major"),
                                    rs.getInt("school_year"));
                    user.setUserId(rs.getInt("user_id"));
                    return user;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }

    // GET USER GIVEN USER ID
    public User getUserByUserId(int userId) {
        String sql = "SELECT user_id, first_name, last_name, email, username, password, major, school_year FROM Users WHERE user_id = ? LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user= new User(rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getString("major"),
                                    rs.getInt("school_year"));
                    user.setUserId(rs.getInt("user_id"));
                    return user;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }

    // GET USER GIVEN POST ID
    public User getUserByPostId(int postId) {
        String sql = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.username, u.password, u.major, u.school_year " + 
        "FROM Users u " +
        "JOIN Posts p ON u.user_id = p.user_id " +
        "WHERE post_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user= new User(rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getString("major"),
                                    rs.getInt("school_year"));
                    user.setUserId(rs.getInt("user_id"));
                    return user;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }

    // DELETE ALL USERS
    public boolean clearAllUsers() {
        String sql = "DELETE FROM Users";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error clearing users: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteUser(int userId) {
        String deleteAcceptancesSQL = "DELETE FROM Acceptances WHERE user_id = ? ";
        String deleteAcceptancesForUserPostsSQL = "DELETE FROM Acceptances WHERE post_id IN (SELECT post_id FROM Posts WHERE user_id = ?)";
        String deletePostsSQL = "DELETE FROM Posts WHERE user_id = ?";
        String deleteMessagesSQL = "DELETE FROM Messages WHERE (sender_id = ? OR receiver_id = ?)";
        String deleteUsersSQL = "DELETE FROM Users WHERE user_id = ?";
        String updatePostSQL = "UPDATE Posts SET status = FALSE, accepter_id = NULL WHERE accepter_id = ?";
        try {
            try (PreparedStatement stmt = conn.prepareStatement(updatePostSQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(deleteAcceptancesSQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(deleteMessagesSQL)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }   
            try (PreparedStatement stmt = conn.prepareStatement(deleteAcceptancesForUserPostsSQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(deletePostsSQL)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            
            
            try (PreparedStatement stmt = conn.prepareStatement(deleteUsersSQL)) {
                stmt.setInt(1, userId);
                int rows = stmt.executeUpdate();
                return rows > 0;
            }
        }  catch (SQLException e) {
            System.out.println("Error clearing users: " + e.getMessage());
            return false;
        }
    }

    // EDIT
        public boolean editUser(User user, User editedUser ) {
            String sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, username = ?, password = ?, major = ?, school_year = ? WHERE user_id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, editedUser.getFirstName());
                stmt.setString(2, editedUser.getLastName());
                stmt.setString(3, editedUser.getEmail());
                stmt.setString(4, editedUser.getUsername());
                stmt.setString(5, editedUser.getPassword());
                stmt.setString(6, editedUser.getMajor());
                stmt.setInt(7, editedUser.getSchoolYear());
                stmt.setInt(8, user.getUserId());

                int rows = stmt.executeUpdate();
                return rows == 1;

            } catch (Exception e) {
                System.out.println("Error editing user: " + e.getMessage());
                return false;
            }
        }
    
}
