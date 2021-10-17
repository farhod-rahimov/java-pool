package ex03;

import ex02.UserIdsGenerator;

public class User {
    private long                id;
    private String              name;
    private int                 balance = 0;
    private TransactionsList    transactionsList;

    public User(String name, int balance) {
        if (balance < 0) {
            this.balance = 0;
        } else {
            this.balance = balance;
        }
        this.name = name;
        this.id = UserIdsGenerator.getInstance().generateId();
        this.transactionsList = new TransactionsLinkedList();
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

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(TransactionsList transactionsList) {
        this.transactionsList = transactionsList;
    }
}
