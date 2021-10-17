package ex03;

import java.util.UUID;

class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String msg) {
        super(msg);
    }
}
  
public class TransactionsLinkedList implements TransactionsList {
    private Transaction firstTransaction;
    private Transaction lastTransaction;

    public TransactionsLinkedList() {
        this.firstTransaction = null;
    }

    @Override
    public void addTransaction(Transaction newTransaction) {
        if (this.firstTransaction == null) {
            this.firstTransaction = newTransaction;
            this.lastTransaction = this.firstTransaction;
        } else {
            this.lastTransaction.setNextTransaction(newTransaction);
            this.lastTransaction = this.lastTransaction.getNextTransaction();
        }
    };

    @Override
    public void removeTransactionByID(UUID id) {
        Transaction tmp;
        Transaction prev = null;

        for (tmp = this.firstTransaction; tmp != null; tmp = tmp.getNextTransaction()) {
            if (tmp.getId().equals(id)) {
                if (prev == null) {
                    this.firstTransaction = tmp.getNextTransaction();
                } else {
                    prev.setNextTransaction(tmp.getNextTransaction());
                }
                return;
            }
            prev = tmp;
        }
        throw new TransactionNotFoundException(String.format("Transaction by id %s not found", id));
    };

    @Override
    public Transaction[] toArray() {
        Transaction array[];
        int         size = 0;
        int         i = 0;

        for (Transaction tmp = this.firstTransaction; tmp != null; tmp = tmp.getNextTransaction()) {
            size++;
        }
        array = new Transaction[size];

        for (Transaction tmp = this.firstTransaction; tmp != null; tmp = tmp.getNextTransaction()) {
            array[i++] = tmp;            
        }
        return array;
    };
}