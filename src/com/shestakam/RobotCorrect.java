package com.shestakam;

public class RobotCorrect {

    private static final int stepsCount = 2;

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread thread1 = new Thread(new Foot(lock,"foot1"));
        Thread thread2 = new Thread(new Foot(lock,"foot2"));

        thread1.start();
        thread2.start();

        thread1.join();
        System.out.println("End");
        thread2.join();
        System.out.println("End");
    }

    private static class Foot implements Runnable {
        private Object lock;
        private String name;

        Foot(Object lock, String name) {
            this.lock = lock;
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < stepsCount; i++) {
                try {
                    synchronized (lock) {
                        System.out.println(name);
                        lock.notify();
                        if (i != stepsCount - 1) {
                            lock.wait();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }
}
