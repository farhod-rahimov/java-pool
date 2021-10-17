package ex00;

class Program {
    
    public static void main(String[] args) {
        User        user1 = new User("Alex", 100);
        User        user2 = new User("Nicola", 200);
        Transaction transaction = new Transaction();

        transaction.setSender(user1);
        transaction.setRecipient(user2);
        transaction.setCategory(Transaction.Category.DEBIT);
        transaction.setTransferAmount(100);

        System.out.printf("Transfer id %s\n", transaction.getId());
        System.out.printf("Sender id %s\n", transaction.getSender().getId());
        System.out.printf("Sender name %s\n", transaction.getSender().getName());
        System.out.printf("Sender balance %s\n", transaction.getSender().getBalance());
        System.out.printf("Recipient id %s\n", transaction.getRecipient().getId());
        System.out.printf("Recipient name %s\n", transaction.getRecipient().getName());
        System.out.printf("Recipient balance %s\n", transaction.getRecipient().getBalance());
        System.out.printf("Transfer amount %d\n", transaction.getTransferAmount());
        System.out.printf("Transfer category %s\n", transaction.getCategory());
    }
}
