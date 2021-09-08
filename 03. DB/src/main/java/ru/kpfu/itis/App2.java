package ru.kpfu.itis;

import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.repositories.UsersRepositoryJdbcImpl;

import java.sql.*;

public class App2 {
    public static void main(String[] args) {
        try {
            //This will load the driver, and while loading, the driver will automatically register itself with JDBC
            //Подгружаем драйвер, он автоматически регистрирует JDBC
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            //ignore
        }
        //Интерфейс позволяет устанавливать соединения с БД
        Connection connection = null;

        //Предназначен для выполнения простых SQL-запросов
        Statement statement = null;

        try {
            // Идет подключение к БД
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5435/postgres", "postgres", "postgres");

            //Далее используется для выполения SQL запросов
            statement = connection.createStatement();
            UsersRepository usersRepository = new UsersRepositoryJdbcImpl(statement);
            UsersRepository usersRepositoryOnlyWithConnection = new UsersRepositoryJdbcImpl(connection);

            // Вытаскиваем всех пользователей
            //System.out.println(usersRepository.findAll());


            // Вытаскиваем пользоватля с определенным id
//            System.out.println(usersRepository.findById(3L));
            System.out.println(usersRepositoryOnlyWithConnection.findById(3L));

            /*User user = new User();
            user.setFirstName("Hidden");
            user.setLastName("Hidden");
            user.setEmail("hidden@gmail.com");

            System.out.println(usersRepository.save(user));


            User user2 = new User();
            user2.setFirstName("Hacker");
            user2.setLastName("hacker");
            user2.setEmail("email'); drop table temp; select * from users");

            System.out.println(usersRepository.save(user2));*/


        } catch (SQLException throwables) {
            //ignore
        }
    }
}
