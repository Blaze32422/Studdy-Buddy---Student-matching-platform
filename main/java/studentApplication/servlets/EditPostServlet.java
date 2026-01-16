package studentApplication.servlets;


import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import studentApplication.classes.Post;
import studentApplication.classes.User;
import studentApplication.daos.PostDAO;
import util.DBConnection;


// Servlet for editing posts
// Mapped to /editpost url

@WebServlet("/editpost")
public class EditPostServlet extends HttpServlet {
    private PostDAO postDAO;
    
    // Initialize a PostDAO and a connection to the MySQL database
    @Override
    public void init() throws ServletException{
        try {
            postDAO = new PostDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize PostDAO", e);
        }
        
    }

    // Handles GET requests when a user attempts to open an editpost page
    // Acquires current user and post id
    // Forwards user to the editpost page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
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

        String postIdStr = req.getParameter("postId");
        if(postIdStr == null) {
            resp.sendRedirect("profile?error=true");
            return;
        }

        int postId = Integer.parseInt(postIdStr);
        Post post = postDAO.getPostByPostId(postId);

        if(post == null || post.getUserId() != user.getUserId()) {
            resp.sendRedirect("profile?error=true");
            return;
        }

        session.setAttribute("post", post); 
        req.getRequestDispatcher("/editpost.jsp").forward(req, resp);
    }


    // Handles POST requests when a user edits a post
    // Acquires user and the id of the post that will be edited
    // Creates a Post object with the retrieved information
    // Updates edited post in Posts table
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
        int postId = Integer.parseInt(req.getParameter("postId"));
        Post post = postDAO.getPostByPostId(postId);

        if (post == null || post.getUserId() != user.getUserId()) {
            resp.sendRedirect("profile?error=true");
            return;
        }

        String description = req.getParameter("description");
        String className = req.getParameter("className");

        Post editedPost = new Post(description, new java.sql.Date(System.currentTimeMillis()), className,post.getStatus(), user.getUserId(), post.getAccepterId());
        editedPost.setPostId(postId);
        boolean success = postDAO.editPost(post, editedPost);

        if (success) {
            session.setAttribute("post", editedPost);
            resp.sendRedirect("profile");
        } else {
            System.out.println("EDIT FAILED");
            resp.sendRedirect("profile?error=true");
        }
    }
}