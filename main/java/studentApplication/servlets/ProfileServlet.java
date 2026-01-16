package studentApplication.servlets;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import studentApplication.classes.Acceptance;
import studentApplication.classes.Post;
import studentApplication.classes.User;
import studentApplication.daos.AcceptanceDAO;
import studentApplication.daos.PostDAO;
import studentApplication.daos.UserDAO;
import util.DBConnection;


// Servlet for the profile page
// Mapped to /profile url
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private PostDAO postDAO;
    private AcceptanceDAO acceptanceDAO;
    private UserDAO userDAO;

    //Initialize DAOs and connect to the MySQL database
    @Override
    public void init() throws ServletException{
        try {
            Connection conn = DBConnection.getConnection();
            postDAO = new PostDAO(conn);
            acceptanceDAO = new AcceptanceDAO(conn);
            userDAO = new UserDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize DAOs", e);
        }
        
    }

    // Handles GET requests for users opening their profile
    // Acquires the current user, their posts that have been accepted, posts that haven't been accepted, and the posts they have accepted
    // Forwards to the /profile page 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
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
        

        try {
            List<Post> acceptedPosts = postDAO.getAllAcceptedPostsFromUser(user.getUserId());

        for (Post p : acceptedPosts) {
            Acceptance acceptance = acceptanceDAO.getAcceptanceByPostId(p.getPostId());

            if (acceptance != null) {
                User accepter = userDAO.getUserByUserId(acceptance.getUserId());
                if (accepter != null) {
                    p.setAccepterName(accepter.getUsername());
                    p.setAccepterId(accepter.getUserId()); // add this field to Post for messaging
                } else {
                    p.setAccepterName("Unknown");
                    p.setAccepterId(-1);
                    System.out.println("ACCEPTER NULL");
                }
            } else {
                p.setAccepterName("Unknown");
                p.setAccepterId(-1);
                System.out.println("ACCEPTANCE NULL");
            }
        }

            List<Post> unacceptedPosts = postDAO.getAllUnacceptedPostsFromUser(user.getUserId());
            req.setAttribute("acceptedposts", acceptedPosts);
            req.setAttribute("unacceptedposts", unacceptedPosts);

        List<Post> postsAcceptedByUser = postDAO.getPostsAcceptedByUser(user.getUserId());
        req.setAttribute("postsacceptedbyuser", postsAcceptedByUser);

        } catch (Exception e) {
            System.out.println("Error retrieving posts: " + e.getMessage());
        }
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);  
        
    }

    

   
}