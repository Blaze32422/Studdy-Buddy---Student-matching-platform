package studentApplication.classes;

import java.sql.Timestamp;

public class Message {
    private int messageId;
    private String message;
    private Timestamp timeSent;
    private int senderId;
    private int receiverId;
    private String senderUsername; 
    
    public Message() {}

    public Message(String message, Timestamp timeSent, int senderId, int receiverId) {
        this.message = message;
        this.timeSent = timeSent;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
    

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }   
    

}
