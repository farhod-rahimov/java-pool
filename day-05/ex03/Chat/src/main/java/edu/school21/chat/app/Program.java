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
        hikariDataSource.setJdbcUrl(url);
        Scanner scanner = new Scanner(System.in);
        Long messageID;
        Optional<Message> message;

        System.out.println("Type ID of message");
        messageID = Long.parseLong(scanner.nextLine());
        message = messagesRepository.findById(messageID);

        if (message.isPresent()) {
            System.out.println("Current Text of Message: " + message.get().getText());
            System.out.println("Type new text");
            message.get().setText(scanner.nextLine());
            message.get().setDate(null);
            messagesRepository.update(message.get());
            System.out.println("And now it is: " + message.get().getText());
        } else {
            System.err.println("Message with ID = " + messageID + " doesn't exist");
        }
        hikariDataSource.close();
        scanner.close();
    }
}
