package ua.java.conferences.controller;

import ua.java.conferences.actions.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.slf4j.*;

import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(process(request)).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(process(request));
    }

    private String process(HttpServletRequest request) {
        Action action = ACTION_FACTORY.createAction(request.getParameter(ACTION));
        String path = ERROR_PAGE;
        try {
            path = action.execute(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return path;
    }
}