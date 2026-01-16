package studentApplication.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import studentApplication.classes.Message;

public class MessageDAO {
    private final Connection conn;

    public MessageDAO(Connection conn) {
        this.conn = conn;   
    }

    // INSERT
    public boolean insertMessage(Message message) {
        String sql = "INSERT INTO Messages (message, time_sent, sender_id, receiver_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, message.getMessage());
            ps.setTimestamp(2, message.getTimeSent());
            ps.setInt(3, message.getSenderId());
            ps.setInt(4, message.getReceiverId());
            

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (Exception e) {
            System.out.println("Error inserting Message: " + e.getMessage());
            return false;
        }
    }

    // GET ALL MESSAGES BETWEEN TWO USERS
    public List<Message> getConversation(int userA, int userB) {
        List<Message> list = new ArrayList<>();

        String sql = """
            SELECT m.message, m.time_sent, m.sender_id, m.receiver_id, u.username
            FROM Messages m
            JOIN Users u ON u.user_id = m.sender_id
            WHERE (m.sender_id = ? AND m.receiver_id = ?)
                OR (m.sender_id = ? AND m.receiver_id = ?)
            ORDER BY m.time_sent ASC
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userA);
            ps.setInt(2, userB);
            ps.setInt(3, userB);
            ps.setInt(4, userA);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getString("message"), rs.getTimestamp("time_sent"), rs.getInt("sender_id"), rs.getInt("receiver_id"));
                message.setSenderUsername(rs.getString("username"));
                list.add(message);
            }

        } catch (Exception e) {
            System.out.println("Error getting conversation: " + e.getMessage());
        }

        return list;
    }

    // DELETE ALL MESSAGES BETWEEN TWO USERS
    public void deleteConversation(int userA, int userB) {
        String sql = """
            DELETE FROM Messages 
            WHERE (sender_id = ? AND receiver_id = ?) 
               OR (sender_id = ? AND receiver_id = ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userA);
            ps.setInt(2, userB);
            ps.setInt(3, userB);
            ps.setInt(4, userA);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error deleting conversation: " + e.getMessage());
        }
    }

    // GET USERS THAT RECEIVED OR SENT MESSAGE TO SPECIFIED USER ID
    public List<String> getMessageReceiversFromUserId(int userId) {
        List<String> receivers = new ArrayList<>();

        String sql = """
            SELECT DISTINCT u.username
            FROM Users u
            JOIN Messages m
            ON (u.user_id = m.sender_id AND m.receiver_id = ?) OR (u.user_id = m.receiver_id AND m.sender_id = ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                receivers.add(rs.getString("username"));
            }

        } catch (Exception e) {
            System.out.println("Error getting receivers: " + e.getMessage());
        }

        return receivers;
    }
}

