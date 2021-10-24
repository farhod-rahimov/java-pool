package ex04;

import java.util.UUID;

class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String msg) {
        super(msg);
    }
}

public class TransactionsService {
    UsersArrayList  userList = new UsersArrayList();

    public void addUser(User newUser) {
        userList.addUser(newUser);
    }

    public int retrieveUserBalance(long id) {
        return userList.retrieveUserById(id).getBalance();
    }

    public void performTransaction(long recipientId, long senderId, int amount) {
        Transaction debit = new Transaction();
        Transaction credit = new Transaction();
        User        recipient;
        User        sender;

        recipient = userList.retrieveUserById(recipientId);
        sender = userList.retrieveUserById(senderId);

        if (sender.getBalance() < amount) {
            String msg = String.format("Sender by id %d has not enough balance to make transaction", senderId);

            throw new IllegalTransactionException(msg);
        }
        debit.setRecipient(userList.retrieveUserById(recipientId));
        debit.setSender(userList.retrieveUserById(senderId));
        debit.setTransferAmount(amount);
        debit.setCategory(Transaction.Category.DEBIT);
        
        credit.setRecipient(userList.retrieveUserById(recipientId));
        credit.setSender(userList.retrieveUserById(senderId));
        credit.setTransferAmount(amount);
        credit.setCategory(Transaction.Category.CREDIT);
        credit.setId(debit.getId());

        recipient.getTransactionsList().addTransaction(debit);
        recipient.setBalance(recipient.getBalance() + amount);

        sender.getTransactionsList().addTransaction(credit);
        sender.setBalance(sender.getBalance() - amount);
    }

    public Transaction[] retrieveTransactionsOfUser(long id) {
        return this.userList.retrieveUserById(id).getTransactionsList().toArray();
    }

    public void removeTransactionOfUser(long userId, UUID transId) {
        this.userList.retrieveUserById(userId).getTransactionsList().removeTransactionByID(transId);
    }

    public Transaction[] checkValidity() {
        int                     arraySize = 0;
        Transaction[]           array;
        TransactionsLinkedList  unpaired = new TransactionsLinkedList();

        for (int i = 0; i < userList.retrieveNumberOfUsers(); i++) {
            array = userList.retrieveUserByIndex(i).getTransactionsList().toArray();
            
            for (int k = 0; k < array.length; k++) {
                unpaired.addTransaction(array[k]);
                arraySize++;
            }
        }
        array = unpaired.toArray();

        for (int i = 0; i < arraySize; i++) {
            if (array[i] != null) {

                for (int k = 0; k < arraySize; k++) {
                    if (k != i && array[k] != null && array[i].getId().equals(array[k].getId())) {
                        unpaired.removeTransactionByID(array[i].getId());
                        unpaired.removeTransactionByID(array[i].getId());
                        array[i] = null;
                        array[k] = null;
                        break ;
                    }
                }
            }
        }
        return (unpaired.toArray());
    }
}
