package com.company.effective_scalable_cache_5_6;

import java.math.BigInteger;

public class ExpensiveFunction<A, V> implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        //after deep thought...
        return new BigInteger(arg);
    }
}
