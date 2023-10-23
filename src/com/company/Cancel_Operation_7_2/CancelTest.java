package com.company.Cancel_Operation_7_2;

import java.math.BigInteger;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * PrimeGenerator works during 1 second. After that it calculates one more prime number and finishes its run() method
 */
public class CancelTest {
    public List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        return generator.get();
    }
}
