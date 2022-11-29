package ua.java.conferences.controller;

import jakarta.servlet.annotation.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.actions.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet({"/controller"})
public class Controller extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processPost(request, response);
    }

    private void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = getActionName(request);
        Action action = ACTION_FACTORY.createAction(url);
        String address = null;
        try {
            address = action.execute(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        request.getRequestDispatcher(address).forward(request, response);
    }

    private void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String getActionName(HttpServletRequest request) {
        return request.getParameter("action");
    }
}