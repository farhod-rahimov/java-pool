package ex01;

import java.lang.Runnable;

public class Hen implements Runnable {
    Object  lock;
    int     n;

    Hen(int n, Object lock) {
        this.n = n;
        this.lock = lock;
    }
    
    public void run () {
        synchronized (lock) {
            try {
                for (int i = 0; i < n; i++) {
                    lock.wait();
                    System.out.println("Hen");
                    lock.notify();
                }
            } catch (InterruptedException equals) {
                System.err.println("interrupted");
                System.exit(1);
            }
        }
    }
}