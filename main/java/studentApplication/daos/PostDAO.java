package studentApplication.daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import studentApplication.classes.Post;
import studentApplication.classes.User;

public class PostDAO {
    private final Connection conn;

    public PostDAO(Connection conn) {
        this.conn = conn;   
    }
    // INSERT
    public boolean createPost(Post post, User user) {

        String sql = "INSERT INTO Posts (description, date_posted, class, status, user_id, accepter_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, post.getDescription());
            stmt.setDate(2, post.getDatePosted());
            stmt.setString(3, post.getClassName());
            stmt.setBoolean(4, post.getStatus());
            stmt.setInt(5, user.getUserId());
            stmt.setObject(6, null);

            
            int rows = stmt.executeUpdate();

            if (rows == 1) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    if(generatedKeys.next()) {
                        post.setPostId(generatedKeys.getInt(1));
                    }
                } catch (SQLException e) { 
                    System.out.println("Error retrieving post ID: " + e.getMessage());
                }
            }
            return rows == 1;

        } catch (Exception e) {
            System.out.println("Error registering post: " + e.getMessage());
            return false;
        }
    }
   
    // DELETE
    public boolean deletePost(int postId) {
        String sql = "DELETE FROM Posts WHERE post_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            int rows = stmt.executeUpdate();
            return rows == 1;

        } catch (Exception e) {
            System.out.println("Error clearing users: " + e.getMessage());
            return false;
        }
    }
        

    // DELETE POST GIVEN A POST ID THAT HAS BEEN ACCEPTED BY ANOTHER USER
    public boolean deleteAcceptedPost(int postId) {
        String deleteAcceptanceSQL = "DELETE FROM Acceptances WHERE post_id = ?";
        String deletePostSQL = "DELETE FROM Posts WHERE post_id = ?";
        try {
            try (PreparedStatement stmt = conn.prepareStatement(deleteAcceptanceSQL)) {
                stmt.setInt(1, postId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(deletePostSQL)) {
                stmt.setInt(1, postId);
                int rows = stmt.executeUpdate();
                return rows == 1;
            } 

            
        }   catch (SQLException e) {
            System.out.println("Error clearing users: " + e.getMessage());
            return false;
        }
    }

    // DELETE ALL OF A USER'S POSTS
    public boolean deletePostsFromUserId(int userId) {
        String sql = "DELETE FROM Posts WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("Error clearing users: " + e.getMessage());
            return false;
        }
    }

    // GET POST GIVEN POST ID
    public Post getPostByPostId(int postId) {
        String sql = "SELECT description, date_posted, class, status, user_id, accepter_id, post_id FROM Posts WHERE post_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Post post = new Post(rs.getString("description"),
                                    rs.getDate("date_posted"),
                                    rs.getString("class"),
                                    rs.getBoolean("status"),
                                    rs.getInt("user_id"),
                                  rs.getObject("accepter_id", Integer.class));
                    post.setPostId(rs.getInt("post_id"));
                    return post;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding post: " + e.getMessage());
        }
        return null;
    }

    // GET ALL POSTS 
    public List<Post> getAllPosts() {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT p.*, u.username FROM Posts p JOIN Users u ON p.user_id = u.user_id";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getString("description"),
                                  rs.getDate("date_posted"),
                                  rs.getString("class"),
                                  rs.getBoolean("status"),
                                  rs.getInt("user_id"),
                                  rs.getObject("accepter_id", Integer.class));
                post.setPostId(rs.getInt("post_id"));
                post.setUsername(rs.getString("username"));
                list.add(post);
                      
            }

        } catch (Exception e) {
            System.out.println("Error getting Acceptances: " + e.getMessage());
        }

        return list;
    }

    // GET ALL POSTS THAT HAVE NOT BEEN ACCEPTED BY ANOTHER USER
    public List<Post> getAllUnacceptedPosts() {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT p.*, u.username FROM Posts p JOIN Users u ON p.user_id = u.user_id WHERE p.status = false";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getString("description"),
                                  rs.getDate("date_posted"),
                                  rs.getString("class"),
                                  rs.getBoolean("status"),
                                  rs.getInt("user_id"),
                                  rs.getObject("accepter_id", Integer.class));
                post.setPostId(rs.getInt("post_id"));
                post.setUsername(rs.getString("username"));
                list.add(post);
                      
            }

        } catch (Exception e) {
            System.out.println("Error getting Acceptances: " + e.getMessage());
        }

        return list;
    }

    // GET ALL POSTS FROM A USER
    public List<Post> getAllPostsFromUser(int userId) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT p.*, u.username FROM Posts p JOIN Users u ON p.user_id = u.user_id WHERE p.user_id = ?" ;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getString("description"),
                                  rs.getDate("date_posted"),
                                  rs.getString("class"),
                                  rs.getBoolean("status"),
                                  rs.getInt("user_id"),
                                  rs.getObject("accepter_id", Integer.class));
                post.setPostId(rs.getInt("post_id"));
                post.setUsername(rs.getString("username"));
                list.add(post);
                      
            }

        } catch (Exception e) {
            System.out.println("Error getting Acceptances: " + e.getMessage());
        }

        return list;
    }

    // GET ALL ACCEPTED POSTS FROM A USER
    public List<Post> getAllAcceptedPostsFromUser(int userId) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT p.*, u.username FROM Posts p JOIN Users u ON p.user_id = u.user_id WHERE p.user_id = ? AND p.status = true";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getString("description"),
                                  rs.getDate("date_posted"),
                                  rs.getString("class"),
                                  rs.getBoolean("status"),
                                  rs.getInt("user_id"),
                                  rs.getObject("accepter_id", Integer.class));
                post.setPostId(rs.getInt("post_id"));
                post.setUsername(rs.getString("username"));
                list.add(post);
                      
            }

        } catch (Exception e) {
            System.out.println("Error getting Acceptances: " + e.getMessage());
        }

        return list;
    }

    // GET ALL POSTS THAT HAVE NOT BEEN ACCEPTED BY ANOTHER USER
    public List<Post> getAllUnacceptedPostsFromUser(int userId) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT p.*, u.username FROM Posts p JOIN Users u ON p.user_id = u.user_id WHERE p.user_id = ? AND p.status = false"; 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getString("description"),
                                  rs.getDate("date_posted"),
                                  rs.getString("class"),
                                  rs.getBoolean("status"),
                                  rs.getInt("user_id"),
                                  rs.getObject("accepter_id", Integer.class));
                post.setPostId(rs.getInt("post_id"));
                post.setUsername(rs.getString("username"));
                list.add(post);
                      
            }

        } catch (Exception e) {
            System.out.println("Error getting Acceptances: " + e.getMessage());
        }

        return list;
    }

    // GET POSTS THAT HAVE A USER HAS ACCEPTED GIVEN USER ID
    public List<Post> getPostsAcceptedByUser(int userId) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT p.*, u.username FROM Posts p JOIN Acceptances a ON a.post_id = p.post_id JOIN Users u ON p.user_id = u.user_id WHERE a.user_id = ?"; 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getString("description"),
                                  rs.getDate("date_posted"),
                                  rs.getString("class"),
                                  rs.getBoolean("status"),
                                  rs.getInt("user_id"),
                                  rs.getObject("accepter_id", Integer.class));
                post.setPostId(rs.getInt("post_id"));
                post.setUsername(rs.getString("username"));
                list.add(post);
                      
            }

        } catch (Exception e) {
            System.out.println("Error getting Acceptances: " + e.getMessage());
        }

        return list;
    }
    // CHECK IF A POST EXISTS
    public boolean exists(int postId) {
        String sql = "SELECT * FROM Posts WHERE post_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error checking Acceptance: " + e.getMessage());
        }
        return false;
    }

    // EDIT AND UPDATE POSTS TABLE
    public boolean editPost(Post post, Post editedPost ) {
            String sql = "UPDATE Posts SET description = ?, date_posted = ?, class = ?, status = ?, user_id = ? WHERE post_id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, editedPost.getDescription());
            stmt.setDate(2, editedPost.getDatePosted());
            stmt.setString(3, editedPost.getClassName());
            stmt.setBoolean(4, editedPost.getStatus());
            stmt.setInt(5, editedPost.getUserId());
            stmt.setInt(6, post.getPostId());
            int rows =    stmt.executeUpdate();
            return rows == 1;

            } catch (Exception e) {
                System.out.println("Error editing post: " + e.getMessage());
                return false;
            }
        }
    
    // CHECK IF A POST HAS BEEN ACCEPTED
    public boolean checkStatus(int postId) {
        String sql = "SELECT status FROM Posts WHERE post_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("status");
            }

        } catch (Exception e) {
            System.out.println("Error checking post status: " + e.getMessage());
        }
        return false;
    
    }
}
