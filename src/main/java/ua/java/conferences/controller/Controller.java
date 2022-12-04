package ua.java.conferences.controller;

import ua.java.conferences.actions.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.slf4j.*;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processPost(request, response);
    }

    private void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = getActionName(request);
        Action action = ACTION_FACTORY.createAction(url);
        String path = null;
        try {
            path = action.executeGet(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        request.getRequestDispatcher(path).forward(request, response);
    }

    private void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = getActionName(request);
        ActionPost actionPost = ACTION_FACTORY.createActionPost(url);
        String path = null;
        try {
            path = actionPost.executePost(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        response.sendRedirect(path);
    }

    private String getActionName(HttpServletRequest request) {
        return request.getParameter("action");
    }
}