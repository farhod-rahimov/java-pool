package ex00;

import java.lang.Runnable;

public class Hen implements Runnable {
    int n;

    Hen(int n) {
        this.n = n;
    }
    
    public void run () {
        for (int i = 0; i < n; i++) {
            System.out.println("Hen");
        }
    }
}