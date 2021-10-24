package ex04;

import java.util.UUID;

public class Transaction {
    private UUID        id;
    private User recipient;
    private User sender;
    private int         transferAmount;
    private Category    category;
    private Transaction nextTransaction;

    public enum Category {
        DEBIT, CREDIT;
    }

    public Transaction() {
        this.id = UUID.randomUUID();
        this.category = null;
        this.transferAmount = 0;
        this.nextTransaction = null;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getSender() {
        return this.sender;
    }

    public void setTransferAmount(int transferAmount) {
        if ((this.category == Category.DEBIT) && (transferAmount < 0)) {
            this.transferAmount = 0;
        } else if ((this.category == Category.CREDIT) && (transferAmount > 0)) {
            this.transferAmount = 0;
        } else {
            this.transferAmount = transferAmount;
        }
    }

    public int getTransferAmount() {
        return this.transferAmount;
    }

    public void setCategory(Category category) {
        if ((category == Category.DEBIT) && (this.transferAmount < 0)) {
            this.transferAmount = 0;
        } else if ((category == Category.CREDIT) && (this.transferAmount > 0)) {
            this.transferAmount = 0;
        }
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    public Transaction getNextTransaction() {
        return this.nextTransaction;
    }

    public void setNextTransaction(Transaction next) {
        this.nextTransaction = next;
    }
}
