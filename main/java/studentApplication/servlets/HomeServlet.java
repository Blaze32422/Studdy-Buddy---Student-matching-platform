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
import studentApplication.classes.Post;
import studentApplication.classes.User;
import studentApplication.daos.PostDAO;
import util.DBConnection;


// Servlet that handles home page. 
// Mapped to the /home URL.


@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private PostDAO postDAO;

    @Override
    public void init() throws ServletException{
        try {
            Connection conn = DBConnection.getConnection();
            postDAO = new PostDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize DAOs", e);
        }
        
    }

    
    // Handles GET requests to show the home page.
    // Forwards request to home page
    // Grabs all unaccepted requests
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
        req.setAttribute("user", user);  
        

        try {
            List<Post> posts = postDAO.getAllUnacceptedPosts();
            req.setAttribute("posts", posts);
        } catch (Exception e) {
            System.out.println("Error retrieving posts: " + e.getMessage());
        }
        req.getRequestDispatcher("/home.jsp").forward(req, resp);  
        
    }

    

   
}