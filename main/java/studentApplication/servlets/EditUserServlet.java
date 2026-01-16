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


// Servlet that handles user registration. 
// Mapped to the /register URL.


@WebServlet("/edituser")
public class EditUserServlet extends HttpServlet {
    private UserDAO userDAO;
    
    // Initializes the UserDAO and connects to MySQL database
    
    @Override
    public void init() throws ServletException{
        try {
            userDAO = new UserDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize UserDAO", e);
        }
        
    }



    
    // Handles POST requests to process the registration form submission.
    // Acquires current user
    // Creates a new User object edited information and attempts to register/insert them into the Users table.
    
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

        // Get form values
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String major = req.getParameter("major");
        int schoolYear = Integer.parseInt(req.getParameter("year"));
        if (schoolYear < 1 || schoolYear > 4) {
            resp.sendRedirect("register?error=true");
            return;
        }

        User editedUser = new User(firstName, lastName, email, username, password, major, schoolYear);
        editedUser.setUserId(user.getUserId());
        boolean success = userDAO.editUser(user, editedUser);

        if (success) {
            session.setAttribute("user", editedUser);
            resp.sendRedirect("profile");
        } else {
            System.out.println("EDIT FAILED");
            resp.sendRedirect("profile?error=true");
        }
    }
}