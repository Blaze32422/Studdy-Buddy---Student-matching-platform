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
import studentApplication.daos.AcceptanceDAO;
import util.DBConnection;

// Servlet for unaccepting a post
// Mapped to /unaccept url

@WebServlet("/unaccept")
public class DeleteAcceptance extends HttpServlet{
    private AcceptanceDAO acceptanceDAO;
    
    // initializing an AcceptanceDAO and connecting to the MySQL database
    @Override
    public void init() throws ServletException{
        try {    
            acceptanceDAO = new AcceptanceDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize UserDAO", e);
        }
        
    }


    // Handles POST requests when a user unaccepts a request post
    // Acquires current user
    // Deletes the acceptance from the Acceptances table
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

        boolean success = acceptanceDAO.deleteAcceptance(user.getUserId(), Integer.parseInt(req.getParameter("postId")));

        if (success) {
            resp.sendRedirect("profile");
        } else {
            System.out.println("ACCEPTANCE DELETION FAILED");
            resp.sendRedirect("profile?error=true");
        }
    }
}
