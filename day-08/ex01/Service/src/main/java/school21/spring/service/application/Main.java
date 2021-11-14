package school21.spring.service.application;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.*;
import school21.spring.service.repositories.*;
import com.zaxxer.hikari.*;
import java.util.*;
import javax.sql.*;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "context.xml"
        );
        UsersRepository usersRepository1 =
                context.getBean("jdbcBean", UsersRepositoryJdbcImpl.class);
        UsersRepository usersRepository2 =
                context.getBean("jdbcTemplateBean", UsersRepositoryJdbcTemplateImpl.class);

        demonstration(usersRepository1);
        System.out.println("********************************************");
        demonstration(usersRepository2);
    }

    private static void deleteAllUsersFromTable(UsersRepository usersRepository) {
        List<User> allUsers = usersRepository.findAll();

        for (User user : allUsers) {
            usersRepository.delete(user.getId());
        }
    }

    private static void printAllUsers(UsersRepository usersRepository) {
        List<User> allUsers = usersRepository.findAll();

        for (User user : allUsers) {
            System.out.println(user);
        }
    }

    private static void demonstration(UsersRepository usersRepository) {
        deleteAllUsersFromTable(usersRepository);

        usersRepository.save(new User(1L, "user-1-email"));
        usersRepository.save(new User(2L, "user-2-email"));
        usersRepository.save(new User(3L, "user-3-email"));
        printAllUsers(usersRepository);
        System.out.println("---------------------------------");

        System.out.println(usersRepository.findById(1L));
        System.out.println(usersRepository.findById(4L));
        System.out.println(usersRepository.findByEmail("user-1-email"));
        System.out.println(usersRepository.findByEmail("user-1-email-not-exists"));
        System.out.println("---------------------------------");

        usersRepository.update(new User(1L, "user-1-email-updated"));
        System.out.println(usersRepository.findById(1L));
        System.out.println("---------------------------------");
        usersRepository.delete(1L);
        printAllUsers(usersRepository);
    }
}