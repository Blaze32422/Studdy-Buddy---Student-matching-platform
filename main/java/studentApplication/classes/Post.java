    package studentApplication.classes;

import java.sql.Date;
import java.lang.Integer;    

public class Post {
    private int postId;
    private String description;
    private Date datePosted;
    private String className;
    private boolean status;
    private int userId;
    private String username;
    private String accepterName;
    private Integer accepterId;
    public Post() {}

    public Post(String description, Date datePosted, String className, boolean status, int userId, Integer accepterId ) {
        this.description = description;
        this.datePosted = datePosted;
        this.className = className;
        this.status = status;
        this.userId = userId;
        this.accepterId = accepterId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

    public String getAccepterName() {
        return accepterName;
    }

    public void setAccepterName(String accepterName) {
        this.accepterName = accepterName;
    }

    public Integer getAccepterId() {
        return accepterId;
    }

    public void setAccepterId(Integer accepterId) {
        this.accepterId = accepterId;
    }
    

}
