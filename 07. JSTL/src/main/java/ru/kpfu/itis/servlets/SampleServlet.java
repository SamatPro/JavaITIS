package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Post;
import ru.kpfu.itis.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users")
public class SampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setFirstName("Даниль");
        user1.setLastName("Галеев");
        user1.setId(1L);
        Post post1 = new Post(1L, "Пост№1");
        user1.setPost(post1);

        User user2 = new User();
        user2.setFirstName("Алина");
        user2.setLastName("Биккинина");
        user2.setId(2L);
        Post post2 = new Post(2L, "Пост№2");
        user2.setPost(post2);

        User user3 = new User();
        user3.setFirstName("Самат");
        user3.setLastName("Зайдуллин");
        user3.setId(3L);
        Post post3 = new Post(3L, "Пост№3");
        user3.setPost(post3);

        users.add(user1);
        users.add(user2);
        users.add(user3);

        request.setAttribute("users", users);

        request.getRequestDispatcher("/jsp/userList.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
