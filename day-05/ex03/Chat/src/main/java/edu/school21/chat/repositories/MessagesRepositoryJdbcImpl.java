package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void update(Message message) {
        Connection connection;
        PreparedStatement preparedStatement;

        if (message == null) {
            return ;
        }
        String statement = "UPDATE messages SET " +
                "author = '" + message.getAuthor().getId() + "', " +
                "room = '" + message.getRoom().getId() + "', " +
                "text = '" + message.getText() + "', ";

        if (message.getDate() == null) {
            statement += "date = NULL ";
        } else {
            statement += "date = '" + message.getDate() + "' ";
        }
        statement += "WHERE id = " + message.getId();

        try {
            connection = this.dataSource.getConnection();
            checkIfSuchIDExistsInTable(message.getAuthor().getId(), "users", connection);
            checkIfSuchIDExistsInTable(message.getRoom().getId(), "chat_rooms", connection);
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Message message) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        if (message == null) {
            return ;
        }
        String statement = "INSERT INTO messages(author, room, text, date) VALUES ('" +
                + message.getAuthor().getId() + "', '"
                + message.getRoom().getId() + "', '"
                + message.getText() + "', '"
                + message.getDate() + "') RETURNING id";

        try {
            connection = this.dataSource.getConnection();
            checkIfSuchIDExistsInTable(message.getAuthor().getId(), "users", connection);
            checkIfSuchIDExistsInTable(message.getRoom().getId(), "chat_rooms", connection);
            preparedStatement = connection.prepareStatement(statement);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            message.setId(resultSet.getLong(1));
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkIfSuchIDExistsInTable(Long id, String tableName,
                                            Connection connection) throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = connection.prepareStatement(
                "SELECT id FROM " + tableName + " WHERE id = " + id
        );
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next() == false) {
            resultSet.close();
            preparedStatement.close();
            throw new NotSavedSubEntityException(
                    "Id = " + id + " in table " + tableName + " doesn't exist"
            );
        }
        resultSet.close();
        preparedStatement.close();
    }

    @Override
    public Optional<Message> findById(Long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Message message;

        if (id == null) {
            return Optional.empty();
        }

        try {
            connection = this.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT id, author, room, text, date FROM messages WHERE id = " + id
            );
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return Optional.empty();
            }
            message = new Message(
                    getAuthorOfMessage(resultSet.getLong(2)),
                    getChatRoomOfMessage(resultSet.getLong(3)),
                    resultSet.getString(4)
            );
            message.setId(resultSet.getLong(1));
            message.setDate(resultSet.getTimestamp(5).toLocalDateTime());
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return Optional.of(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User getAuthorOfMessage(Long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        User user = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT login, password FROM users WHERE id = " + id
            );
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return null;
            }
            String login = resultSet.getString(1);
            String password = resultSet.getString(2);
            user = new User(login, password);
            user.setId(id);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private Chatroom getChatRoomOfMessage(Long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Chatroom chatroom = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT name FROM chat_rooms WHERE id = " + id
            );
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return null;
            }
            String name = resultSet.getString(1);
            chatroom = new Chatroom(name, null);
            chatroom.setId(id);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatroom;
    }
}

class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException(String message) {
        super(message);
    }
}
