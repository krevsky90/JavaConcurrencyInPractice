package com.company.Interrupt_Operation_7_3and5;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled)
                queue.put(p = p.nextProbablePrime());
        } catch (InterruptedException consumed) {
            //read smth
        }
    }

    public void cancel() {
        cancelled = true;
    }
}