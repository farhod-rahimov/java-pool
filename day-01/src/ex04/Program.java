package ex04;

class Program {
    private static void printAllTransactions(Transaction[] array) {
        if (array == null) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i].getId());
        }
    }

    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();
        User                alex = new User("Alex", 10000);
        User                nicolas = new User("Nicolas", 20000);
        Transaction[]       array;

        service.addUser(alex);
        service.addUser(nicolas);

        System.out.printf("Alex's balance = %d\n", service.retrieveUserBalance(alex.getId()));
        System.out.printf("Nicolas's balance = %d\n\n", service.retrieveUserBalance(nicolas.getId()));
        System.out.println("Nicolas tries to send Alex 2000$");
        System.out.println("Nicolas tries to send Alex 3000$");
        System.out.println("Nicolas tries to send Alex 4000$");

        try {
            service.performTransaction(alex.getId(), nicolas.getId(), 2000);
            service.performTransaction(alex.getId(), nicolas.getId(), 3000);
            service.performTransaction(alex.getId(), nicolas.getId(), 4000);
        } catch (IllegalTransactionException e) {
            System.err.println(e.getMessage());
            System.out.print("\n");
        }
        System.out.printf("Alex's balance = %d\n", service.retrieveUserBalance(alex.getId()));
        System.out.printf("Nicolas's balance = %d\n\n", service.retrieveUserBalance(nicolas.getId()));
        System.out.println("Nicolas tries to send Alex 14000$");

        try {
            service.performTransaction(alex.getId(), nicolas.getId(), 14000);
        } catch (IllegalTransactionException e) {
            System.out.print("\n");
            System.err.println(e.getMessage());

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.print("\n");
        }
        System.out.printf("Alex's balance = %d\n", service.retrieveUserBalance(alex.getId()));
        System.out.printf("Nicolas's balance = %d\n\n", service.retrieveUserBalance(nicolas.getId()));

        array = service.retrieveTransactionsOfUser(alex.getId());
        System.out.print("Retrieving all transactions of Alex\n");
        printAllTransactions(array);

        array = service.retrieveTransactionsOfUser(nicolas.getId());
        System.out.print("\nRetrieving all transactions of Nicolas\n");
        printAllTransactions(array);

        System.out.print("\nRemoving first transaction of Nicolas\n\n");
        service.removeTransactionOfUser(nicolas.getId(), array[0].getId());

        array = service.retrieveTransactionsOfUser(alex.getId());
        System.out.print("Retrieving all transactions of Alex\n");
        printAllTransactions(array);

        array = service.retrieveTransactionsOfUser(nicolas.getId());
        System.out.print("\nRetrieving all transactions of Nicolas\n");
        printAllTransactions(array);

        System.out.print("\nPrinting all unpaired transactions\n");
        array = service.checkValidity();
        printAllTransactions(array);
    }
}