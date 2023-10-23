package com.company.effective_scalable_cache_5_6;

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
