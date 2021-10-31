package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ex01";
        HikariDataSource hikariDataSource = new HikariDataSource();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(hikariDataSource);
        Scanner scanner = new Scanner(System.in);

        hikariDataSource.setJdbcUrl(url);
        System.out.println("Enter message ID");

        Long messageId = scanner.nextLong();
        Optional<Message> message = messagesRepository.findById(messageId);

        if (message.isPresent()) {
            System.out.println(message.get());
        } else {
            System.out.println("There is no message with ID = " + messageId);
        }
        hikariDataSource.close();
    }
}
