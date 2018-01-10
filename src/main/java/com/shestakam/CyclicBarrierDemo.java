package com.shestakam;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
   private static final int threadsCount = 3;
   public static void main(String[] args) {
      Runnable action = () -> {
         String name = Thread.currentThread().getName();
         System.out.printf("End of step \n", name);
      };
      final CyclicBarrier barrier = new CyclicBarrier(threadsCount, action);
      Runnable task = () -> {
         for (int i = 0 ; i < 5 ; i ++) {
            System.out.printf("%s \n", Thread.currentThread().getName());
            try {
               barrier.await();
            } catch (BrokenBarrierException bbe) {
               System.out.println("barrier is broken");
               return;
            } catch (InterruptedException ie) {
               System.out.println("thread interrupted");
               return;
            }
         }
      };

      for (int i = 0 ; i < threadsCount ; i++) {
         new Thread(task).start();
      }
   }
}