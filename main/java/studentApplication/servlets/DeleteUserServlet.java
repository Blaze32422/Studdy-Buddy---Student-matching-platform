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
import studentApplication.daos.UserDAO;
import util.DBConnection;

// Servlet for deleting a user
// Mapped to /deleteuser url

@WebServlet("/deleteuser")
public class DeleteUserServlet extends HttpServlet{
    private UserDAO userDAO;
    
    // Initializes a UserDAO and connects to MySQL database
    @Override
    public void init() throws ServletException{
        try {    
            userDAO = new UserDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize UserDAO", e);
        }
        
    }

    // Handles POST requests when a user deletes their account
    // Acquires the current user
    // Deletes the current user  
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

        boolean success = userDAO.deleteUser(user.getUserId());

        if (success) {
            session.invalidate();
            System.out.println("USER DELETED SUCCESSFULLY");
            resp.sendRedirect("login");
        } else {
            System.out.println("USER DELETION FAILED");
            resp.sendRedirect("profile?error=true");
        }
    }
}
