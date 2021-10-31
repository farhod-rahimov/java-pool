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
    public Optional<Message> findById(Long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Message message;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT id, author, room, text, date FROM messages WHERE id = " + id
            );
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                return Optional.empty();
            }
            message = new Message(
                    getAuthorOfMessage(connection, resultSet.getLong(2)),
                    getChatRoomOfMessage(connection, resultSet.getLong(3)),
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

    private User getAuthorOfMessage(Connection connection, Long id) {
        User user = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT login, password FROM users WHERE id = " + id
            );
            ResultSet resultSet = preparedStatement.executeQuery();

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

    private Chatroom getChatRoomOfMessage(Connection connection, Long id) {
        Chatroom chatroom = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT name FROM chat_rooms WHERE id = " + id
            );
            ResultSet resultSet = preparedStatement.executeQuery();

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
