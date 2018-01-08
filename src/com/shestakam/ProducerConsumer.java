package com.shestakam;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {

    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        Lock lock = new ReentrantLock();
        Condition emptyCondVar = lock.newCondition();
        Condition fullCondVar = lock.newCondition();
        int maxSize = 10;
        int iterationsCount = 100;

        Thread producer = new Thread(() -> {
            for (int i = 0 ; i < iterationsCount ; i++) {
                try {
                    lock.lock();
                    if (queue.size() == maxSize) {
                        fullCondVar.await();
                    }
                    queue.add(i);
                    emptyCondVar.signalAll();
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
                finally {
                    lock.unlock();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            int processed = 0;
            while (processed != iterationsCount - 1) {
                try {
                    lock.lock();
                    if (queue.size() == 0) {
                        emptyCondVar.await();
                    }
                    processed = queue.poll();
                    System.out.println(processed);
                    fullCondVar.signalAll();
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                } finally {
                    lock.unlock();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}