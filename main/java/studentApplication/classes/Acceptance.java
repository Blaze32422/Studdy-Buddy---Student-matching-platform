package studentApplication.classes;

import java.sql.Date;

public class Acceptance {
    private Date dateAccepted;
    private int userId;
    private int postId;
    
    public Acceptance() {}

    public Acceptance(Date dateAccepted, int userId, int postId) {
        this.dateAccepted = dateAccepted;
        this.userId = userId;
        this.postId = postId;

    }

    public Date getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(Date dateAccepted) {
        this.dateAccepted = dateAccepted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
