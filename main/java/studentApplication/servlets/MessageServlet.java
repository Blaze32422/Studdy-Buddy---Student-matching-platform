package studentApplication.servlets;


import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import studentApplication.daos.UserDAO;
import studentApplication.classes.Message;
import studentApplication.classes.User;
import studentApplication.daos.MessageDAO;
import util.DBConnection;
import java.sql.Timestamp;

// Servlet for messaging users
// Mapped to /message url
@WebServlet("/message")
public class MessageServlet extends HttpServlet {
    private MessageDAO messageDAO;
    private UserDAO userDAO;
    
    //Initializes the MessageDAO and UserDAO and connects to MySQL database
    @Override
    public void init() throws ServletException{
        try {    
            Connection conn = DBConnection.getConnection();
            messageDAO = new MessageDAO(conn);
            userDAO = new UserDAO(conn);
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize DAOs", e);
        }
        
    }

    // Handles GET requests when opening up the message page
    // Acquires the current user, the user receiving the message, and the previous messages between the two
    // Forwards to the /message page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("login?error=true");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login?error=true");
            return;
        }
       
        String receiverName = req.getParameter("receiverName");
        if (receiverName == null || receiverName.isEmpty()) {
            System.out.println("RECEIVER NAME UNKNOWN");
            resp.sendRedirect("messagelist?error=true");
            return;
        }

        User receiver = userDAO.getUserByUsername(receiverName);
        if (receiver == null) {
            System.out.println("GET: RECEIVER UNKNOWN");
            resp.sendRedirect("messagelist?error=true");
            return;
        } else if (receiverName.equals(user.getUsername())) {
            System.out.println("CAN'T SEND MESSAGES TO SELF");
            resp.sendRedirect("messagelist?error=true");
            return;
        }

        req.setAttribute("receiverName", receiver.getUsername());
        req.setAttribute("receiverId", receiver.getUserId());
        req.setAttribute("messages",
                messageDAO.getConversation(user.getUserId(), receiver.getUserId()));

        req.getRequestDispatcher("/message.jsp").forward(req, resp);
        
        
    }
        

    // Handles POST requests when a user attempts to send a message
    // Acquires the current user, receiver, and message information
    // Creates a Message object and inserts the message in the Messages table
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


        String messageString= req.getParameter("message");
        int receiverId = Integer.parseInt(req.getParameter("receiverId"));
        User  receiver = userDAO.getUserByUserId(receiverId);
        if(receiver == null) {
            System.out.println("POST: RECEIVER UNKNOWN");
            resp.sendRedirect("messagelist?error=true");
            return;
        }


        Message message = new Message(messageString, new Timestamp(System.currentTimeMillis()), user.getUserId(), receiverId);
        boolean success = messageDAO.insertMessage(message); 
        
        if (success) {
            resp.sendRedirect("message?receiverName=" + URLEncoder.encode(receiver.getUsername(), "UTF-8"));
        } else {
            System.out.println("MESSAGE FAILED");
            resp.sendRedirect("message?error=true");
        }

    }
}
