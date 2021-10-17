package ex00;

import java.util.UUID;

public class Transaction {
    private UUID        id;
    private User        recipient;
    private User        sender;
    private Category    category;
    private int         transferAmount;

    public enum Category {
        DEBIT, CREDIT;
    }

    public Transaction() {
        this.id = UUID.randomUUID();
        this.transferAmount = 0;
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
}
