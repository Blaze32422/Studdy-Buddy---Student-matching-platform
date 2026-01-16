package studentApplication.servlets;


import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import studentApplication.classes.User;
import studentApplication.daos.UserDAO;
import util.DBConnection;

/*
* Servlet that handles user registration. 
* Mapped to the /register URL.
*/

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;
    /*
    * Initializes the servlet and sets up the UserDAO instance.
    */
    @Override
    public void init() throws ServletException{
        try {
            userDAO = new UserDAO(DBConnection.getConnection());
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize UserDAO", e);
        }
        
    }

    /*
    * Handles GET requests to show the registration form.
    * Forwards request to register.jsp page
    */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }


    /*
    * Handles POST requests to process the registration form submission.
    * Creates a new user and attempts to register them using UserDAO.
    * If registration is successful, redirects to main page; otherwise, redirects back to registration page
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

        User user = new User(firstName, lastName, email, username, password, major, schoolYear);

        boolean success = userDAO.registerUser(user);

        if (success) {
            resp.sendRedirect("login");
        } else {
            System.out.println("REGISTRATION FAILED");
            resp.sendRedirect("register?error=true");
        }
    }
}