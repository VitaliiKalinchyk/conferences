package ua.java.conferences.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.slf4j.*;
import ua.java.conferences.controller.actions.*;

import java.io.IOException;

import static ua.java.conferences.controller.actions.constants.Pages.ERROR_PAGE;
import static ua.java.conferences.controller.actions.constants.Parameters.ACTION;

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