package ua.java.conferences.controller;

import jakarta.servlet.annotation.WebServlet;
import ua.java.conferences.actions.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/action")
public class Controller extends HttpServlet {

    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get");
        processGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processPost(request, response);
    }

    private void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = getActionName(request);
        Action action = ACTION_FACTORY.createAction(actionName);

        //TRY
        String address = action.execute(request);
        //CATCH
//        response.sendRedirect(address);

        request.getRequestDispatcher(address).forward(request, response);
    }

    private void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = getActionName(request);
        Action action = ACTION_FACTORY.createAction(url);
        String address = null;
        try {
            address = action.execute(request);
        } catch (Exception e) {
            //Log the error
            request.setAttribute("error", e.getMessage());
        }
        response.sendRedirect(address);
    }

    private String getActionName(HttpServletRequest request) {
        return request.getParameter("action");
    }
}