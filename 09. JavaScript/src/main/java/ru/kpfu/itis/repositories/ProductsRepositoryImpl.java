package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.models.Product;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryImpl implements ProductsRepository {

    private Connection connection;

    //language=sql
    private final String INSERT_PRODUCT = "INSERT INTO products(title, cost) VALUES (?, ?)";

    public ProductsRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setDouble(2, product.getCost());
            //preparedStatement.setDate(3, Date.from(product.getCreatedAt()));
            resultSet = preparedStatement.executeQuery();
            product = rowMapper.rowMap(resultSet);
            return product;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private RowMapper<Product> rowMapper = ((resultSet) -> {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setTitle(resultSet.getString("title"));
        product.setCost(resultSet.getDouble("cost"));
//        product.setCreatedAt();
        return product;
    });
}
