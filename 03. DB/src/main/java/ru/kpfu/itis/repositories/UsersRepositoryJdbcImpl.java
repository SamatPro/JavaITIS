package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private Statement statement;
    private Connection connection;

    private final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE users.id = ?";
    private final String SQL_FIND_ALL = "SELECT * FROM users";

    public UsersRepositoryJdbcImpl(Statement statement) {
        this.statement = statement;
    }

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(SQL_FIND_ALL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));

                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
//            resultSet = statement.executeQuery("SELECT * FROM users WHERE users.id=" + id);
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
        }
        return null;
    }

    @Override
    public User save(User user) {
        String sql_insert_user = "INSERT INTO users (first_name, last_name, email) " +
                "VALUES ('"+ user.getFirstName() +"', '" + user.getLastName() + "', '" + user.getEmail() +"');";
        try {
            statement.execute(sql_insert_user, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
            }
        } catch (SQLException throwables) {
            //ignore
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {

    }
}
