package ex03;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String  str;
        int     total;
        int     minNumber;
        int     number = 0;
        long    dataRating = 0;
        long    dataWeek = 2;
        int     currentWeek = 0;
        int     lastWeek = 0;

        for (total = 1; total <= 18; total++) {
            str = scanner.nextLine();

            if (str.equals("42")) {
                break ;
            }
            number = getCurrentWeek(str);
            currentWeek = checkInputError(currentWeek, str, number);
            minNumber = getMinNumber(scanner);
            dataRating += minNumber;
            dataRating *= 10;

            for (; lastWeek < currentWeek; lastWeek++) {
                dataWeek *= 10;
            }
            dataWeek += 1;
            str = scanner.nextLine();

            if (!str.equals("")) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            lastWeek = currentWeek;
        }

        for (; lastWeek < 18; lastWeek++) {
            dataWeek *= 10;
        }
        printResult(dataRating, dataWeek);
        scanner.close();
    }
    
    public static int checkInputError(int lastWeek, String str, int currentWeek) {

        if ((str.equals("Week " + currentWeek) == false) || (currentWeek <= lastWeek)) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        return currentWeek;
    }

    public static void printResult(long dataRating, long dataWeek) {
        int     n;
        int     i;
        long    tmp;
        long    currentWeek;
        long    currentRating;

        n = 0;
        for (long k = dataRating; k != 0; k /= 10) {
            n++;
        }

        while (--n > 0) {
            tmp = 0;
            currentWeek = dataWeek;
            currentRating = dataRating;

            for (i = 0; ; i++) {
                if (currentWeek % 10 == 1) {
                    tmp++;

                    if (tmp == n) {
                        break;
                    }
                }
                currentWeek /= 10;
            }
            System.out.printf("Week %2d ", 18 - i);

            for (i = 0; i <= n; i++) {
                tmp = currentRating % 10;
                currentRating /= 10;
            }

            while (tmp > 0) {
                System.out.print("=");
                tmp--;
            }
            System.out.println(">");
        }
    }

    public static int getCurrentWeek(String str) {
        Scanner scanner = new Scanner(str);
        int     currentWeek = 0;

        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                currentWeek = scanner.nextInt();

                if ((currentWeek < 1) || (currentWeek > 18)) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                break ;
            }
            scanner.next();
        }
        scanner.close();
        return currentWeek;
    }

    public static int getMinNumber(Scanner scanner) {
        int minNumber;
        int number;

        minNumber = 9;
        for (int i = 0; i < 5; i++) {
            number = scanner.nextInt();

            if ((number < 1) || (number > 9)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            if (number < minNumber) {
                minNumber = number;
            }
        }
        return minNumber;
    }
}
