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
/*
* Servlet that handles user login. 
* Mapped to the /login URL.
*/

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
    * Handles GET requests to show the login form.
    * Forwards request to login.jsp page
    */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }


    /*
    * Handles POST requests to process the login form submission.
    * Searches for an existing user with the specified email and password.
    * If the user is found, redirects to main page; otherwise, redirects back to login page.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Get form values
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userDAO.getUserByEmailAndPassword(email, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            resp.sendRedirect("home");
        } else {
            System.out.println("LOGIN FAILED");
            resp.sendRedirect("login?error=true");
        }
    }
}