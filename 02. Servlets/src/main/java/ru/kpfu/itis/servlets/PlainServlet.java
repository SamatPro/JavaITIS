package ru.kpfu.itis.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PlainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        try {
            String htmlForm =
                "<!DOCTYPE HTML>" +
                    " <html>" +
                    " <head> " +
                    "  <meta charset=\"utf-8\">" +
                    "  <title>Название сайта</title>" +
                    " </head>" +
                    " <body>" +
                    "  <form action=\"/home\" method=\"POST\">" +
                    "  <p><b>Введите название вашего любимого фильма</b></p>" +
                    "  <p><input type=\"text\" name=\"film\" value=\"\"/></p>" +
                    "  <p><input type=\"submit\"></p>" +
                    " </form>" +
                    " </body>" +
                    " </html>";
            writer.println(htmlForm);
        } finally {
            writer.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println(request.getParameter("film"));
    }
}
