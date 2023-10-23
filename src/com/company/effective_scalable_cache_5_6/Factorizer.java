package com.company.effective_scalable_cache_5_6;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Factorizer {
    private final Computable<BigInteger, BigInteger[]> c = (arg) -> factor(arg);

    private final Computable<BigInteger, BigInteger[]> cache = new Memorizer4<>(c);

    public void service() throws InterruptedException {
        List<BigInteger> inputData = Arrays.asList(BigInteger.valueOf(10), BigInteger.valueOf(20), BigInteger.valueOf(30));
        for (BigInteger data : inputData) {
            BigInteger[] tempArr = cache.compute(data);
            print(tempArr);
        }
    }

    private final BigInteger[] factor(BigInteger arg) {
        //factorization process
        return null;    //stub
    }

    private <V> void print(V[] arr) {
        final StringBuilder sb = new StringBuilder();
        Arrays.stream(arr).forEach((val) -> sb.append(val));
        System.out.println(sb.toString());
    }
}
