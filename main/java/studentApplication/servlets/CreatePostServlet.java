
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
import java.sql.Date;

// Servlet for creating posts
// Mapped to /createpost url
@WebServlet("/createpost")
public class CreatePostServlet extends HttpServlet {
    private PostDAO postDAO;

    // Initializes a PostDAO and connects to the MySQL database
    @Override
    public void init() throws ServletException{
        try {    
            postDAO = new PostDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize PostDAO", e);
        }
        
    }

    // Handles GET requests when a user attempts to enter the createpost page
    // Acquires the current user
    // Forwards user to the createpost page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");  
            if (user != null) {
                req.setAttribute("user", user);  
                req.getRequestDispatcher("/createpost.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("login?error=true");
            }
        } else {
            resp.sendRedirect("login?error=true");
        }
        
    }

    // Handles POST requests when a user creates a post
    // Acquires the current user
    // Grabs the user's inputted information for the post and creates a Post object that is inserted into the Posts table
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
        
        String description= req.getParameter("description");
        String className = req.getParameter("className");

        Post post = new Post(description, new Date(System.currentTimeMillis()), className, false, user.getUserId(), null);
        boolean success = postDAO.createPost(post, user);

        if (success) {
            resp.sendRedirect("createpost");
        } else {
            System.out.println("POST CREATION FAILED");
            resp.sendRedirect("home?error=true");
        }
        
    }
}

