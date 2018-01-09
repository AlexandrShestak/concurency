package com.shestakam;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RobotIncorrect {

    private static final int stepsCount = 2;

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread1 = new Thread(new Foot(lock, condition,"foot1"));
        Thread thread2 = new Thread(new Foot(lock, condition,"foot2"));

        thread1.start();
        thread2.start();

        thread1.join();
        System.out.println("End");
        thread2.join();
        System.out.println("End");
    }

    private static class Foot implements Runnable {
        private Lock lock;
        private Condition condition;
        private String name;

        Foot(Lock lock, Condition condition, String name) {
            this.lock = lock;
            this.name = name;
            this.condition = condition;
        }

        @Override
        public void run() {
            for (int i = 0; i < stepsCount; i++) {
                try {
                    lock.lock();
                    System.out.println(name);
                    condition.signal();
                    if (i != stepsCount -1) {
                        System.out.println("await");
                        // program will not terminated and wait for this lock forever
                        condition.await();
                        System.out.println("awaitEnd");
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }
}
