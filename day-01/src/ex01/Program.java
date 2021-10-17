package ex01;

public class Program {

    public static void main(String[] args) {
        User    user1 = new User("Alex", 100);
        User    user2 = new User("Nicola", 200);

        System.out.printf("User1 name %s\n", user1.getName());
        System.out.printf("User1 id %s\n", user1.getId());
        System.out.printf("User1 balance %s\n", user1.getBalance());

        System.out.printf("\nUser2 name %s\n", user2.getName());
        System.out.printf("User2 id %s\n", user2.getId());
        System.out.printf("User2 balance %s\n", user2.getBalance());

        UserIdsGenerator userIdsGenerator1 = new UserIdsGenerator();
        System.out.printf("\nuserIdsGenerator1.generateId() %d\n", userIdsGenerator1.generateId());
        UserIdsGenerator userIdsGenerator2 = new UserIdsGenerator();
        System.out.printf("\nuserIdsGenerator2.generateId() %d\n", userIdsGenerator2.generateId());

    }
}
