package ua.com.juja.sqlcmd.controller.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MainServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURL = request.getRequestURI();
        String action = requestURL.substring(request.getContextPath().length(), requestURL.length());

        if(action.startsWith("/menu")){
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        }
        else if(action.startsWith("/connect")){
            request.getRequestDispatcher("connect.jsp").forward(request, response);
        }
        else if(action.startsWith("/connect")){
            request.getRequestDispatcher("clear.jsp").forward(request, response);
        }
        else{
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
