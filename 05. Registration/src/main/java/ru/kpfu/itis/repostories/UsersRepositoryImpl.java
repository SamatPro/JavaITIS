package ru.kpfu.itis.repostories;

import ru.kpfu.itis.models.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {

    private Connection connection;

    //language=sql
    private final String SQL_INSERT_USER = "INSERT INTO users(first_name, last_name, login, password_hash) VALUES (?, ?, ?, ?)";

    public UsersRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPasswordHash());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {

    }
}
