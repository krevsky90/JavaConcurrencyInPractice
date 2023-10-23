package com.company.CountDownLatch_5_5_1.internet;

import java.util.concurrent.CountDownLatch;

public class ProcessThread implements Runnable {
    private final CountDownLatch latch;
    private long workDuration;
    private String name;

    public ProcessThread(String name, CountDownLatch latch, long duration) {
        this.name = name;
        this.latch = latch;
        this.workDuration = duration;
    }

    public void run() {
        long start = System.nanoTime();
        try {
            System.out.println(name + " Processing Something for " + workDuration / 1000 + " Seconds");
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "completed its works in " + (System.nanoTime() - start));
        //when task finished.. count down the latch count...

        // basically this is same as calling lock object notify(), and object here is latch
        latch.countDown();
    }
}
