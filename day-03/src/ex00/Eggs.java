package ex00;

import java.lang.Runnable;

public class Eggs implements Runnable {
    int n;

    Eggs(int n) {
        this.n = n;
    }
    
    public void run () {
        for (int i = 0; i < n; i++) {
            System.out.println("Eggs");
        }
    }
}