package ex00;

public class User {
    private static long lastId;
    private long        id;
    private String      name;
    private int         balance = 0;

    public User(String name, int balance) {
        if (balance < 0) {
            this.balance = 0;
        } else {
            this.balance = balance;
        }
        this.name = name;
        this.id = ++this.lastId;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
