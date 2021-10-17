package ex00;

public class Program {

    public static void main(String[] args) {
        int num = 479598;
        int res = 0;

        res += num % 10;
        num /= 10;
        res += num % 10;
        num /= 10;
        res += num % 10;
        num /= 10;
        res += num % 10;
        num /= 10;
        res += num % 10;
        num /= 10;
        res += num % 10;
        System.out.println(res);
    }
}
