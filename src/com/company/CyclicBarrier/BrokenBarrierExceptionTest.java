package com.company.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Thread 3 is waiting on barrier
 * Thread 1 is waiting on barrier
 * Thread 2 is waiting on barrier
 * Thread 1: java.util.concurrent.TimeoutException
 * 1625402836792
 * Thread 2: java.util.concurrent.BrokenBarrierException
 * 1625402837793
 * Thread 3: java.util.concurrent.BrokenBarrierException
 * 1625402838783
 *
 * Thread 1: sleep - 1s. after that - await method - 1s. After that - throws java.util.concurrent.TimeoutException
 * Thread 2: sleep - 3s. after that method await throws java.util.concurrent.BrokenBarrierException because Thread 1 has already broken the Barriei and other threads won't wait
 * Thread 3: see about Thread 2
 *
 */
public class BrokenBarrierExceptionTest {
    //Runnable task for each thread
    private static class Task implements Runnable {

        private CyclicBarrier barrier;
        private int taskNumber;

        public Task(CyclicBarrier barrier, int taskNumber) {
            this.barrier = barrier;
            this.taskNumber = taskNumber;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting on barrier");
                Thread.sleep(1000*taskNumber);
                barrier.await(taskNumber, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
            } catch (InterruptedException ex) {
                System.out.println(Thread.currentThread().getName() + ": " + ex);
                System.out.println(System.currentTimeMillis());
            } catch (BrokenBarrierException ex) {
                System.out.println(Thread.currentThread().getName() + ": " + ex);
                System.out.println(System.currentTimeMillis());
            } catch (TimeoutException ex) {
                System.out.println(Thread.currentThread().getName() + ": " + ex);
                System.out.println(System.currentTimeMillis());
            }
        }
    }

    public static void main(String args[]) {
        //creating CyclicBarrier with 3 parties i.e. 3 Threads needs to call await()
        final CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                //This task will be executed once all thread reaches barrier
                System.out.println("All parties are arrived at barrier, lets play");
            }
        });

        //starting each of thread
        Thread t1 = new Thread(new BrokenBarrierExceptionTest.Task(cb, 1), "Thread 1");
        Thread t2 = new Thread(new BrokenBarrierExceptionTest.Task(cb, 3), "Thread 2");
        Thread t3 = new Thread(new BrokenBarrierExceptionTest.Task(cb, 4), "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}
