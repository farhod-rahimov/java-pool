package school21.spring.service.repositories; 

import school21.spring.service.models.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {
   private DataSource dataSource;

   public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    };

    @Override
    public User findById(Long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        User user = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT id, email FROM users WHERE id = " + id
            );
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return null;
            }
            user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2)
            );
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return user;
    };

    @Override
    public List<User> findAll() {
        List<User>          list = new LinkedList<>();
        Connection          connection;
        PreparedStatement   preparedStatement;
        ResultSet           resultSet;

        try {
            connection = this.dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT id, email FROM users");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                list.add(new User(resultSet.getLong(1), resultSet.getString(2)));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.print(ex.getMessage());
            System.exit(1);
        }
        return list;
    };

    @Override
    public void save(User entity) {
        Connection          connection;
        PreparedStatement   preparedStatement;
        String              command;

        command = "INSERT INTO users (id, email) VALUES(";
        command += entity.getId() + ", '";
        command += entity.getEmail() + "')";

        try {
            connection = this.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.print(ex.getMessage());
            System.exit(1);
        }
    };

    @Override
    public void update(User entity) {
        Connection          connection;
        PreparedStatement   preparedStatement;
        String              command;

        command = "UPDATE users SET email = '" + entity.getEmail() + "' ";
        command += "WHERE id = " + entity.getId();

        try {
            connection = this.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.print(ex.getMessage());
            System.exit(1);
        }
    };

    @Override
    public void delete(Long id) {
        Connection          connection;
        PreparedStatement   preparedStatement;
        String              command;

        command = "DELETE FROM users WHERE id = " + id;

        try {
            connection = this.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.print(ex.getMessage());
            System.exit(1);
        }
    };

    @Override
    public Optional<User> findByEmail(String email) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        User user;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT id FROM users WHERE email = '" + email + "'"
            );
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return Optional.empty();
            }
            user = new User(
                    resultSet.getLong(1),
                    email
            );
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return Optional.empty();
    };
}
