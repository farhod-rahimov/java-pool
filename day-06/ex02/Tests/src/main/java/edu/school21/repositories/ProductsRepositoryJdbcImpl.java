package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new LinkedList<>();
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM products");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return list;
            }

            do {
                Product product = new Product(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getFloat(3)
                );
                list.add(product);
            } while (resultSet.next());
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM products WHERE id=" + id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return Optional.empty();
            }
            Product product = new Product(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getFloat(3)
            );
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return Optional.of(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        Connection connection;
        PreparedStatement preparedStatement;

        if (findById(product.getId()).isPresent() == false) {
            return;
        }

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE products SET " +
                            "id = '" + product.getId() + "', " +
                            "name = '" + product.getName() + "', " +
                            "price = '" + product.getPrice() + "'" +
                            "WHERE id = " + product.getId()
            );
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {
        Connection connection;
        PreparedStatement preparedStatement;

        if (findById(product.getId()).isPresent() == true) {
            return;
        }

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO products (id, name, price) VALUES (" +
                            "'" + product.getId() + "', " +
                            "'" + product.getName() + "', " +
                            "'" + product.getPrice() + "')"
            );
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection;
        PreparedStatement preparedStatement;

        if (findById(id).isPresent() == false) {
            return;
        }

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM products WHERE id = " + id
            );
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
