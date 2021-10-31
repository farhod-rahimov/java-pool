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
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Message message) {
        if (message == null) {
            return ;
        }
        String statement = "INSERT INTO messages(author, room, text, date) VALUES ('" +
                + message.getAuthor().getId() + "', '"
                + message.getRoom().getId() + "', '"
                + message.getText() + "', '"
                + message.getDate() + "') RETURNING id";

        try {
            this.connection = this.dataSource.getConnection();
            checkIfSuchIDExistsInTable(message.getAuthor().getId(), "users");
            checkIfSuchIDExistsInTable(message.getRoom().getId(), "chat_rooms");
            this.preparedStatement = connection.prepareStatement(statement);
            this.resultSet = preparedStatement.executeQuery();
            this.resultSet.next();
            message.setId(resultSet.getLong(1));
            this.resultSet.close();
            this.preparedStatement.close();
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkIfSuchIDExistsInTable(Long id, String tableName) throws SQLException {
        this.preparedStatement = this.connection.prepareStatement(
                "SELECT id FROM " + tableName + " WHERE id = " + id
        );
        this.resultSet = preparedStatement.executeQuery();

        if (this.resultSet.next() == false) {
            this.resultSet.close();
            this.preparedStatement.close();
            throw new NotSavedSubEntityException(
                    "Id = " + id + " in table " + tableName + " doesn't exist"
            );
        }
        this.resultSet.close();
        this.preparedStatement.close();
    }

    @Override
    public Optional<Message> findById(Long id) {
        Message message;

        if (id == null) {
            return Optional.empty();
        }

        try {
            this.connection = this.dataSource.getConnection();
            this.preparedStatement = this.connection.prepareStatement(
                    "SELECT id, author, room, text, date FROM messages WHERE id = " + id
            );
            this.resultSet = this.preparedStatement.executeQuery();

            if (this.resultSet.next() == false) {
                return Optional.empty();
            }
            message = new Message(
                    getAuthorOfMessage(this.resultSet.getLong(2)),
                    getChatRoomOfMessage(this.resultSet.getLong(3)),
                    this.resultSet.getString(4)
            );
            message.setId(this.resultSet.getLong(1));
            message.setDate(this.resultSet.getTimestamp(5).toLocalDateTime());
            this.resultSet.close();
            this.preparedStatement.close();
            this.connection.close();
            return Optional.of(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User getAuthorOfMessage(Long id) {
        User user = null;

        try {
            this.preparedStatement = this.connection.prepareStatement(
                    "SELECT login, password FROM users WHERE id = " + id
            );
            this.resultSet = this.preparedStatement.executeQuery();

            if (this.resultSet.next() == false) {
                return null;
            }
            String login = this.resultSet.getString(1);
            String password = this.resultSet.getString(2);
            user = new User(login, password);
            user.setId(id);
            this.resultSet.close();
            this.preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private Chatroom getChatRoomOfMessage(Long id) {
        Chatroom chatroom = null;

        try {
            this.preparedStatement = this.connection.prepareStatement(
                    "SELECT name FROM chat_rooms WHERE id = " + id
            );
            this.resultSet = this.preparedStatement.executeQuery();

            if (this.resultSet.next() == false) {
                return null;
            }
            String name = this.resultSet.getString(1);
            chatroom = new Chatroom(name, null);
            chatroom.setId(id);
            this.resultSet.close();
            this.preparedStatement.close();
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
