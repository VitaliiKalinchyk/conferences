package ua.java.conferences.dao.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ua.java.conferences.dao.*;
import ua.java.conferences.entity.User;
import ua.java.conferences.entity.builder.UserBuilder;
import ua.java.conferences.exception.DBException;

import java.io.*;

@WebServlet({"/getUser"})
public class GetUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new UserBuilder().setEmail(req.getParameter("email")).get();
        DAOFactory daoFactory = DAOFactory.getInstance("MySql");
        UserDAO userDAO = daoFactory.getUserDAO();

        String result;
        try {
            user = userDAO.get(user);
            result = user.toString();
        } catch (DBException e) {
            e.printStackTrace();
            result = "No such user";
        } catch (Throwable e) {
            e.printStackTrace();
            result = "SHit!!!";
        }

        PrintWriter writer = resp.getWriter();
        writer.append("<html>\n").append("<head>\n").append("<title>Title</title>\n").append("</head>\n").append("<body>\n").append("<h4>Hello, ").append(result).append("</h4>").append("</body>\n").append("</html>");
    }
}