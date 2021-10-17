package ex01;

import java.util.Scanner;

class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long    inputNumber = scanner.nextLong();
        int     totalSteps = 0;

        if (inputNumber <= 1) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        totalSteps += 1;

        if (inputNumber == 2 || inputNumber == 3 || inputNumber % 2 == 0) {

            if (inputNumber > 3) {
                printResult(false, 1);
            } else {
                printResult(true, 1);
            }
            System.exit(0);
        }

        for (int i = 3; i * i <= inputNumber; i++)  {
            totalSteps += 1;

            if (inputNumber % i == 0) {
                printResult(false, totalSteps);
                System.exit(0);
            }
        }
        printResult(true, ++totalSteps);
        scanner.close();
    }

    static void printResult(boolean bool, int totalSteps) {
        System.out.println(bool + " " + totalSteps);
    }
}
