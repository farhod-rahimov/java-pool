package ex01;

class Program {
    private static int getNumPrint(String str) {
        String  array[] = array = str.split("=");
        int     n = 0;

        if ((array.length != 2) || (!array[0].equals("--count"))) {
            System.err.println("Error. Wrong argument. Example: java Program --count=50");
            System.exit(1);
        }

        try {
            n = Integer.parseInt(array[1]);
        } catch (NumberFormatException e) {
            System.err.print("Error. ");
            System.err.println(e.getMessage());
            System.err.println("Example: java Program --count=50");
            System.exit(1);
        }

        if (n < 0) {
            System.err.println("Error. Count number cannot be negative");
            System.exit(1);
        }
        return n;
    }

    public static void main(String[] args) {
        Object  lock = new Object();
        Thread  thread1;
        Thread  thread2;
        int     n;

        if (args.length != 1) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }
        n = getNumPrint(args[0]);
        thread1 = new Thread(new Hen(n, lock));
        thread2 = new Thread(new Eggs(n, lock));
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        for (int i = 0; i < n; i++) {
            System.out.println("Human");
        }
    }
}