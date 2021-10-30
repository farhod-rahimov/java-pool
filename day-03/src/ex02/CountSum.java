package ex02;

import java.lang.Runnable;

public class CountSum implements Runnable {
    int     sum[];
    int     threadNumber;
    int     array[];
    int     start;
    int     end;
    Object  lock;

    CountSum(int array[], int start, int end, int sum[], int threadNumber, Object lock) {
        this.array = array;
        this.sum = sum;
        this.start = start;
        this.end = end;
        this.threadNumber = threadNumber;
        this.lock = lock;
    }
    
    public void run () {
        int initialSum = this.sum[0];
        int initialStart = this.start;

            for (; this.start < this.end; this.start++) {
                synchronized (lock) {
                    this.sum[0] += this.array[this.start];
                }
            }
            System.out.printf("Thread %d: from %d to %d sum is %d\n", this.threadNumber, initialStart, this.end - 1, this.sum[0] - initialSum);
    }
}
