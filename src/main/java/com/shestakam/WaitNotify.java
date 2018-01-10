package com.shestakam;

import java.util.concurrent.atomic.AtomicInteger;

public class WaitNotify {

    public static void main(String[] args) {
        Object lock = new Object();
        AtomicInteger atomicInteger = new AtomicInteger(100);

        for (int  i = 0 ; i < atomicInteger.get() ; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    try {
                        lock.wait();
                        System.out.println(Thread.currentThread().getName());
                        atomicInteger.decrementAndGet();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // Restore the interrupted status
                        Thread.currentThread().interrupt();
                    }
                }

            }).start();
        }


        while (atomicInteger.get() != 0) {
            System.out.println("Main thread");
            synchronized (lock) {
                lock.notify();
                lock.notify();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Restore the interrupted status
                Thread.currentThread().interrupt();
            }
        }
    }
}
