package ex02;

public class Program {

    public static void main(String[] args) {
        UsersArrayList  list = new UsersArrayList();
        User            user1 = new User("Alex", 1000);
        User            user2 = new User("Nicola", 2000);

        for (int i = 0; i < 10; i++) {
            list.addUser(user1);
            list.addUser(user2);
        }
        System.out.printf("retrieveNumberOfUsers -> %d\n", list.retrieveNumberOfUsers());

        try {
            System.out.printf("retrieveUserById(1) - should be Alex -> %s\n", list.retrieveUserById(1).getName());
            System.out.printf("retrieveUserById(2) - should be Nicola -> %s\n", list.retrieveUserById(2).getName());
            System.out.printf("retrieveUserById(9) - should be UserNotFoundException -> %s\n", list.retrieveUserById(9).getName());
        } catch (UserNotFoundException e) {
            System.out.print("\n");
            e.printStackTrace();
            System.out.print("\n");
        }

        try {
            System.out.printf("retrieveUserByIndex(1) - should be Nicola -> %s\n", list.retrieveUserByIndex(1).getName());
            System.out.printf("retrieveUserByIndex(2) - should be Alex -> %s\n", list.retrieveUserByIndex(2).getName());
            System.out.printf("retrieveUserByIndex(14) - should be Alex -> %s\n", list.retrieveUserByIndex(14).getName());
            System.out.printf("retrieveUserByIndex(25) - should be Exception -> %s\n", list.retrieveUserByIndex(25).getName());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("\n");
            e.printStackTrace();
            System.out.print("\n");
        }

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("THE END");
    }
}
