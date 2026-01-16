package studentApplication.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import studentApplication.classes.Acceptance;
import studentApplication.classes.User;
import studentApplication.daos.AcceptanceDAO;
import studentApplication.daos.UserDAO;
import util.DBConnection;

// Servlet for accepting posts
// Mapped to /acceptance url
@WebServlet("/acceptance")
public class AcceptanceServlet extends HttpServlet{
    private AcceptanceDAO acceptanceDAO;
    private UserDAO userDAO;
    
    // initializes UserDAO and AcceptanceDAO
    // Connects to MySQL database
    @Override
    public void init() throws ServletException{
        try {    
            Connection conn = DBConnection.getConnection();
            userDAO = new UserDAO(conn);
            acceptanceDAO = new AcceptanceDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize DAOs", e);
        }
        
    }

    // Handles POST requests when user accepts a request/post
    // Acquires current user and the accepted post id
    // Creates and inserts an acceptance into the database
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        if(session == null) {
            System.out.println("ACCEPTANCE POST REQUEST ERROR: SESSION NULL");
            resp.sendRedirect("login?error=true");
            return;
        }
        User user = (User) session.getAttribute("user");
        int postId = Integer.parseInt(req.getParameter("postId"));
        if(user == null) {
            System.out.println( "ACCEPTANCE POST REQUEST ERROR: USER NULL");
            resp.sendRedirect("login?error=true");
            return;
        } else if(user.getUserId() == userDAO.getUserByPostId(postId).getUserId() ){
            System.out.println("ACCEPTANCE POST REQUEST ERROR : CANNOT ACCEPT OWN POST");
            resp.sendRedirect("home?error=true");
            return;
        }

        Acceptance acceptance = new Acceptance(new java.sql.Date(System.currentTimeMillis()), user.getUserId(), postId);
        
        if(req.getParameter("acceptance").equals("true")) {
            boolean success = acceptanceDAO.insertAcceptance(acceptance.getDateAccepted(), acceptance.getUserId(), acceptance.getPostId());
            if (success) {
                resp.sendRedirect("home");
            } else {
                System.out.println("ACCEPTANCE POST REQUEST ERROR : ACCEPTANCE FAILED");
                resp.sendRedirect("home?error=true");
            }
        }
    }
}
