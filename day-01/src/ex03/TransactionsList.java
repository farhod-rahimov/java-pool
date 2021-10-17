package ex03;

import java.util.UUID;

public interface TransactionsList {
    void             addTransaction(Transaction newTransaction);
    void             removeTransactionByID(UUID id);
    Transaction[]    toArray();
}