package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ex01";
        HikariDataSource hikariDataSource = new HikariDataSource();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(hikariDataSource);
        hikariDataSource.setJdbcUrl(url);

        checkIdOfMessage(messagesRepository);
        checkException(messagesRepository);

        hikariDataSource.close();
    }

    private static void checkIdOfMessage(MessagesRepository messagesRepository) {
        User creator = new User(3L, "user", "user_pass",
                new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom chatroom = new Chatroom(2L, "room_name", creator, new ArrayList<>());
        Message message = new Message(null, author, chatroom, "Hello!", LocalDateTime.now());

        try {
            messagesRepository.save(message);
            System.out.println(message.getId());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void checkException(MessagesRepository messagesRepository) {
        User creator = new User(100L, "user", "user_pass",
                new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom chatroom = new Chatroom(200L, "room_name", creator, new ArrayList<>());
        Message message = new Message(null, author, chatroom, "Hello!", LocalDateTime.now());

        try {
            messagesRepository.save(message);
            System.out.println(message.getId());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
