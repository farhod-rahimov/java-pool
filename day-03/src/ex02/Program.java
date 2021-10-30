package ex02;

import java.util.Random;

class Program {
    private static void getData(String args[], int data[]) {

        if (args.length != 2) {
            System.err.println("Error. Wrong arguments. Example: java Program --arraySize=13 --threadsCount=3");
            System.exit(1);
        }

        for (int i = 0; i < 2; i++) {
            String tmp[] = args[i].split("=");
            
            if ((tmp.length == 2) && tmp[0].equals("--arraySize") && (data[0] == -1)) {
                data[0] = Integer.parseInt(tmp[1]);

                if (data[0] < 0 || data[0] > 2000000) {
                    System.err.println("Error. The value of 'arraySize' cannot be negative " +
                            "and greater than 2 000 000");
                    System.exit(1);
                }
            } else if ((tmp.length == 2) && tmp[0].equals("--threadsCount") && (data[1] == -1)) {
                data[1] = Integer.parseInt(tmp[1]);

                if (data[1] < 0 || data[1] > data[0]) {
                    System.err.println("Error. The value of 'threadsCount' cannot be negative " +
                            "and greater than arraySize. ArraySize should be given before ThreadsCount. " +
                            "Example: java Program --arraySize=13 --threadsCount=3");
                    System.exit(1);
                }
            } else {
                System.err.println("Error. Wrong arguments. Example: java Program --arraySize=13 --threadsCount=3");
                System.exit(1);
            }
        }
    }

    private static int[] getRandomArray(int arraySize) {
        int[]   array = new int[arraySize];
        Random  rand = new Random();
        
        for (int i = 0; i < arraySize; i++) {
            array[i] = rand.nextInt() % 1000;
        }
        return array;
    }

    private static void printSumWithoutThread(int[] array) {
        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        System.out.printf("Sum: %d\n", sum);
    }

    public static void main(String[] args) {
        Thread  thread;
        int[]   data = new int[2];
        int[]   sum = new int[1];
        int[]   array;
        int     step;
        Object  lock = new Object();

        if (args.length != 2) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }
        data[0] = -1;
        data[1] = -1;
        getData(args, data);

        array = getRandomArray(data[0]);
        step = data[0] / data[1];
        printSumWithoutThread(array);

        for (int i = 0; i < data[1]; i++) {
            if (i != data[1] - 1) {
                thread = new Thread(new CountSum(array, step * i, step * (i + 1), sum, i, lock));
            } else {
                thread = new Thread(new CountSum(array, step * i, data[0], sum, i, lock));
            }

            try {
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        System.out.printf("Sum by threads: %d\n", sum[0]);
    }
}