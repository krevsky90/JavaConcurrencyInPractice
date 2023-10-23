package com.company.effective_scalable_cache_5_6;

import java.util.Map;
import java.util.concurrent.*;

/**
 *
 */
public class Memorizer4<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap();
    private final Computable<A, V> c;

    public Memorizer4(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        //todo: не понял, а зачем while(true)?
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                FutureTask<V> ft = new FutureTask<>(
                        () -> c.compute(arg)
                );

                // improvement: usage of atomic putIfAbsent
                // putIfAbsent - returns the previous value associated with the specified key, or null if there was no mapping for the key
                // KREVSKY EXPLANATION:
                // Let's consider 2 threads, both of them count cache.get(arg) = null. That's why thread 1 starts to execute f = cache.putIfAbsent(arg, ft)
                // meanwhile thread 2 waits for thread 1, because putIfAbsent uses synchronized block.
                // When thread 1 ends calculation inside putIfAbsent it returns null (i.e. previous value) and runs ft.run()
                // meanwhile thread 2 calls putIfAbsent (that is not blocked now) and... gets NOT null, because thread 1 has already put 'ft' value!
                // both threads get result from f.get() when it is computed
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }

            try {
                V result = f.get();
                return result;
            } catch (CancellationException e) { //improvement!
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }
    }

    /**
     * If the Throwable is an Error, throw it; if it is a RuntimeException return it,
     * otherwise throw IllegalStateException
     **/
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }
}
