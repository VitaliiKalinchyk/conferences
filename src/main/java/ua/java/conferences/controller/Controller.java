package ua.java.conferences.controller;

import ua.java.conferences.actions.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {


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
        Action action = ACTION_FACTORY.createAction(request);

        //TRY
        String address = action.execute(request);
        //CATCH

        request.getRequestDispatcher(address).forward(request, response);
    }

    private void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = ACTION_FACTORY.createAction(request);
        String address = null;
        try {
            address = action.execute(request);
        } catch (Exception e) {
            //Log the error
            request.setAttribute("error", e.getMessage());
        }
        response.sendRedirect(address);
    }
}