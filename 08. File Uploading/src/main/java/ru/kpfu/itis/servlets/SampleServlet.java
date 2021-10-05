package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Photo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/photo")
public class SampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo получаем какую нибудь сущность и записываем в request attribute
        request.getRequestDispatcher("/jsp/file-download.jsp").forward(request, response);
    }

}
