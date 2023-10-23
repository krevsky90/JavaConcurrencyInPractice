package com.company.Interrupt_Operation_7_3and5;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * здесь есть две cancellation point:
 * 1 - Thread.currentThread().isInterrupted() - перед вычислениями по задаче. необязательна, но делает задачу более отзывчивой к прерыванию
 * 2 - catch (InterruptedException consumed)
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted())
                queue.put(p = p.nextProbablePrime());
        } catch (InterruptedException consumed) {
            //Allow thread to exit
        }
    }

    public void cancel() {
        interrupt();
    }
}