package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Photo;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/file-download")
public class FileDownloadServlet extends HttpServlet {

    private final String PATH = "C:/Users/Samat/Desktop/JavaITIS/08. File Uploading/%s";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        todo Ищем в БД соответсвующую модельку по названию файла
//        String fileName = request.getParameter("fileName");

        Photo photo = new Photo(1L, "6033802041.jpg");


        ServletOutputStream out;
        out = response.getOutputStream();
        FileInputStream fin = new FileInputStream(String.format(PATH, photo.getTitle()));
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        int ch =0; ;
        while((ch=bin.read())!=-1)
        {
            bout.write(ch);
        }

        bin.close();
        fin.close();
        bout.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
