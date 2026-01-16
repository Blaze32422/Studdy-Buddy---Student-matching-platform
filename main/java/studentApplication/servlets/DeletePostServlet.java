package studentApplication.servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import studentApplication.classes.User;
import studentApplication.daos.PostDAO;
import util.DBConnection;

// Servlet for deleting posts
// Mapped to /deletepost url

@WebServlet("/deletepost")
public class DeletePostServlet extends HttpServlet{
    private PostDAO postDAO;
    
    // Initializing a PostDAO and connecting to MySQL database
    @Override
    public void init() throws ServletException{
        try {    
            postDAO = new PostDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize PostDAO", e);
        }
        
    }

    // Handles POST requests when a user deletes a post
    // Acquires the current user and the post id of the post they are attempting to delete
    // Deletes the chosen post from the Posts table
    // If it is an accepted post, the acceptance is also deleted.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login?error=true");
            return;
        }
        User user = (User) session.getAttribute("user");
        if(user == null) {
            resp.sendRedirect("login?error=true");
            return;
        }

        boolean success;
        int postId = Integer.parseInt(req.getParameter("postId"));
        if (postDAO.checkStatus(postId)) {
            success = postDAO.deleteAcceptedPost(postId);
        } else {
            success = postDAO.deletePost(postId);
        }


        if (success) {
            resp.sendRedirect("profile");
        } else {
            System.out.println("POST DELETION FAILED");
            resp.sendRedirect("profile?error=true");
        }
    }
}
