package ex02;

import java.util.Scanner;

class Program {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long    inputNumber;
        long    sum = 0;
        int     totalRequests = 0;

        do {
            inputNumber = scanner.nextInt();
            sum = getSumOfDigits(inputNumber);

            if (isPrime(sum)) {
                totalRequests++;
            }
        } while (inputNumber != 42);
        System.out.printf("Count of coffee-request - %d\n", totalRequests);
        scanner.close();
    }

    public static boolean isPrime(long number) {
        if (number == 2 || number == 3 || number % 2 == 0) {

            if (number > 3) {
                return false;
            } else {
                return true;
            }
        }

        for (int i = 3; i * i <= number; i++)  {

            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


    public static long getSumOfDigits(long number) {
        long res = 0;

        if (number < 2) {
            return res;
        }

        while (number != 0) {
            res += number % 10;
            number = number / 10;
        }
        return res;
    }
}
