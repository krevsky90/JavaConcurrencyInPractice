package com.company.CountDownLatch_5_5_1.internet;

import java.util.concurrent.CountDownLatch;

/**
 * https://coderoad.ru/17827022/%D0%9A%D0%B0%D0%BA-CountDownLatch-%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D1%83%D0%B5%D1%82%D1%81%D1%8F-%D0%B2-%D0%BC%D0%BD%D0%BE%D0%B3%D0%BE%D0%BF%D0%BE%D1%82%D0%BE%D1%87%D0%BD%D0%BE%D1%81%D1%82%D0%B8-Java
 */
public class CountDownLatchExample {
    public static void main(String[] args) {
        // Parent thread creating a latch object
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(new ProcessThread("Worker1", latch, 2000)).start(); // time in millis.. 2 secs
        new Thread(new ProcessThread("Worker2", latch, 6000)).start();//6 secs
        new Thread(new ProcessThread("Worker3", latch, 4000)).start();//4 secs

        System.out.println("waiting for Children processes to complete....");
        long start = System.nanoTime();

        try {
            //current thread will get notified if all chidren's are done
            // and thread will resume from wait() mode.
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All Process Completed in " + (System.nanoTime() - start));

        System.out.println("Parent Thread Resuming work....");
    }
}