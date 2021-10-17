package ex04;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String  str;
        int     array[] = new int[65536];

        for (int i = 0; i < 65536; i++) {
            array[i] = 0;
        }
        str = scanner.nextLine();

        if (str.isEmpty()) {
            System.exit(0);
        }
        fillArray(array, str);
        makeResult(array);
        scanner.close();
    }

    public static void fillArray(int array[], String str) {
        char    charArray[] = str.toCharArray();
        int     index;

        for (int i = 0; i < str.length(); i++) {
            index = (int)charArray[i];

            if (array[index] < 999) {
                array[index] += 1;
            }
        }
    }

    public static void selectionSort(int[] arr){
        int min;
        int min_i;
        int tmp;

        for (int i = 0; i < arr.length; i++) {
            min = arr[i];
            min_i = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > min) {
                    min = arr[j];
                    min_i = j;
                }
            }

            if (i != min_i) {
                tmp = arr[i];
                arr[i] = arr[min_i];
                arr[min_i] = tmp;
            }
        }
    }

    public static void makeResult(int array[]) {
        int result[][] = new int[65536][2];
        int sortArray[];
        int k = 0;

        for (int i = 0; i < 65536; i++) {
            if (array[i] > 0) {
                result[k][0] = i;
                result[k][1] = array[i];
                k++;
            }
        }
        sortArray = new int[k];

        for (int i = 0; i < k; i++) {
            sortArray[i] = result[i][1];
        }
        selectionSort(sortArray);
        printResult(k, sortArray, result);
    }

    public static void printResult(int k, int sortArray[], int result[][]) {
        int maxMatch = sortArray[0];
        int printArray[][] = new int[k][12];
        int l;

        for (int i = 0; i < k; i++) {
            for (int m = 0; m < 65536; m++) {
                if (sortArray[i] == result[m][1]) {
                    l = sortArray[i] * 10 / maxMatch;
                    printArray[i][l + 1] = sortArray[i];

                    for (int j = 11; j > l + 1; j--) {
                        printArray[i][j] = 0;
                    }

                    for (; l > 0; --l) {
                        printArray[i][l] = -1;
                    }
                    printArray[i][l] = result[m][0];
                    result[m][1] = 0;
                    break;
                }
            }
        }
        l = 11;

        while (l >= 0) {
            for (int j = 0; j < k; j++) {
                if (printArray[j][l] == -1) {
                    System.out.print("# ");
                } else if (l == 0) {
                    System.out.printf("%c ", (char)printArray[j][l]);
                } else if (printArray[j][l] > 0) {
                    System.out.printf("%d ", printArray[j][l]);
                }
            }
            System.out.print("\n");
            l--;
        }
    }
}
