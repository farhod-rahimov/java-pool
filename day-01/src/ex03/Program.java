package ex03;

import java.util.UUID;

class Program {
    private static void printAllTransactions(Transaction[] array) {
        if (array == null) {
            return;
        }

        for (Transaction tmp : array) {
            System.out.println(tmp.getId());
        }
    }

    public static Transaction createDebitTransaction(int amount, User sender, User recipient) {
        Transaction transactionDebit = new Transaction();

        transactionDebit.setCategory(Transaction.Category.DEBIT);
        transactionDebit.setSender(sender);
        transactionDebit.setRecipient(recipient);
        transactionDebit.setTransferAmount(amount);
        sender.setBalance(sender.getBalance() - amount);

        return transactionDebit;
    }

    public static Transaction createCreditTransaction(UUID id, int amount, User sender, User recipient) {
        Transaction transactionCredit = new Transaction();

        transactionCredit.setCategory(Transaction.Category.CREDIT);
        transactionCredit.setSender(sender);
        transactionCredit.setRecipient(recipient);
        transactionCredit.setTransferAmount(amount);
        transactionCredit.setId(id);
        recipient.setBalance(recipient.getBalance() + amount);

        return transactionCredit;
    }

    public static void main(String[] args) {
        User alex = new User("Alex", 1000);
        User john = new User("John", 2000);

        System.out.println("Alex has " + alex.getBalance() + "$");
        System.out.println("John has " + john.getBalance() + "$");

        System.out.println("\n1) Alex sends John 500$");
        Transaction debitTransaction1 = createDebitTransaction(500, alex, john);
        Transaction creditTransaction1 = createCreditTransaction(
                debitTransaction1.getId(),500, alex, john);
        alex.getTransactionsList().addTransaction(debitTransaction1);
        john.getTransactionsList().addTransaction(creditTransaction1);

        System.out.println("2) Alex sends John 300$");
        Transaction debitTransaction2 = createDebitTransaction(300, alex, john);
        Transaction creditTransaction2 = createCreditTransaction(
                debitTransaction2.getId(),300, alex, john);
        alex.getTransactionsList().addTransaction(debitTransaction2);
        john.getTransactionsList().addTransaction(creditTransaction2);

        System.out.println("The balance of each users");
        System.out.println(alex.getBalance());
        System.out.println(john.getBalance());

        System.out.println("\nPrinting all transactions of Alex");
        printAllTransactions(alex.getTransactionsList().toArray());

        System.out.println("\nPrinting all transactions of John");
        printAllTransactions(john.getTransactionsList().toArray());

        System.out.println("\nRemoving first transaction from both users");
        alex.getTransactionsList().removeTransactionByID(creditTransaction1.getId());
        john.getTransactionsList().removeTransactionByID(debitTransaction1.getId());

        System.out.println("\nPrinting all transactions of Alex");
        printAllTransactions(alex.getTransactionsList().toArray());

        System.out.println("\nPrinting all transactions of John");
        printAllTransactions(john.getTransactionsList().toArray());

        System.out.println("\nRemoving non existing transaction");

        try {
            alex.getTransactionsList().removeTransactionByID(UUID.randomUUID());
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
    }
}