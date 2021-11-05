package edu.school21.numbers;

import java.util.Scanner;

class IllegalNumberException extends RuntimeException {
    public IllegalNumberException(String msg) {
        super(msg);
    }
}

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number <= 1) {
            throw new IllegalNumberException("Illegal number");
        }

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

    public long digitsSum(int number) {
        long res = 0;

        number = Math.abs(number);

        if (number <= 10) {
            return number;
        }

        while (number != 0) {
            res += number % 10;
            number = number / 10;
        }
        return res;
    }
}
