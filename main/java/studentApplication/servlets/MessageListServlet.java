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
    import studentApplication.daos.UserDAO;
    import studentApplication.classes.User;
    import studentApplication.daos.MessageDAO;
    import util.DBConnection;
    import java.util.List;

    // Servlet for displaying people that a user has messaged
    // Mapped to /messagelist url
    @WebServlet("/messagelist")
    public class MessageListServlet extends HttpServlet {
        private MessageDAO messageDAO;
        
        @Override
        public void init() throws ServletException{
            try {    
                Connection conn = DBConnection.getConnection();
                messageDAO = new MessageDAO(conn);
            } catch (SQLException e) {
                throw new ServletException("Unable to initialize DAOs", e);
            }
            
        }

        // Handles GET requests when a user opens the messagelist page
        // Acquires current user and the people they have messaged
        // Forwards to /messagelist page
        
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
        
            List<String> receivers = messageDAO.getMessageReceiversFromUserId(user.getUserId());
            req.setAttribute("receivers", receivers);

            req.getRequestDispatcher("/messagelist.jsp").forward(req, resp);
            
            
        }
            

        
    }
