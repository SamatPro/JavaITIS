package ru.kpfu.itis;

import java.sql.*;

public class App {
    public static void main(String[] args) {

        try {
            //This will load the driver, and while loading, the driver will automatically register itself with JDBC
            //Подгружаем драйвер, оно автоматически регистрирует JDBC
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

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                System.out.print(resultSet.getLong("id") + "\t");
                System.out.print(resultSet.getString("first_name") + "\t");
                System.out.print(resultSet.getString("last_name") + "\t");
                System.out.print(resultSet.getString("email") + "\t");
                System.out.print("\n");
            }
        } catch (SQLException throwables) {
            //ignore
        }
    }
}
