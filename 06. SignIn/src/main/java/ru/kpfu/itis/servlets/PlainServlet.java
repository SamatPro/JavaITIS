package ru.kpfu.itis.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class PlainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String query = request.getParameter("query");
        if (query != null) {
            System.out.println(query);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/home.jsp");
        requestDispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");

        String status = "";
        if (!password.equals(repassword)) {
            status = "Password does not match!!!";
        }
        request.setAttribute("validation", status);
        request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
    }
}
