package ua.com.juja.sqlcmd.controller.web;

import ua.com.juja.sqlcmd.service.Service;
import ua.com.juja.sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service = new ServiceImpl();
        String action = getAction(request);

        if(action.startsWith("/menu")){
            request.setAttribute("items", service.commandList());
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        }
        else if(action.startsWith("/connect")){
            request.getRequestDispatcher("connect.jsp").forward(request, response);
        }
        else if(action.startsWith("/help")){
            request.getRequestDispatcher("help.jsp").forward(request, response);
        }
        else{
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.substring(request.getContextPath().length(), requestURI.length());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service = new ServiceImpl();
        String action = getAction(request);

        if (action.startsWith("/connect")) {
            String dbName = (String) request.getAttribute("dbName");
            String username = (String) request.getAttribute("username");
            String password = (String) request.getAttribute("password");

            try {
                service.connect(dbName, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
