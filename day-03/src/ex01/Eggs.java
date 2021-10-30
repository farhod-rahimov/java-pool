package ex01;

import java.lang.Runnable;

public class Eggs implements Runnable {
    Object  lock;
    int     n;

    Eggs(int n, Object lock) {
        this.n = n;
        this.lock = lock;
    }
    
    public void run () {
        synchronized (lock) {
            try {
                for (int i = 0; i < n; i++) {
                    System.out.println("Eggs");
                    lock.notify();
                    lock.wait();
                }
            } catch (InterruptedException equals) {
                System.err.println("interrupted");
                System.exit(1);
            }
        }
    }
}