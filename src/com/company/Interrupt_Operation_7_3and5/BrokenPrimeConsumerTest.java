package com.company.Interrupt_Operation_7_3and5;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BrokenPrimeConsumerTest {
    void consumePrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<>();
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
        producer.start();
        try {
            while (needMorePrimes())
                consume(primes.take());
        } finally {
            producer.cancel();
        }
    }

    public boolean needMorePrimes() {
        return true;// stub
    }

    public void consume (BigInteger number) {
        //stub
    }
}
