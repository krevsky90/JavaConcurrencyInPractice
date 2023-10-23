package com.company.effective_scalable_cache_5_6;

import java.util.HashMap;
import java.util.Map;

/**
 * listing 5.16
 *
 * Класс HashMap не является потокобезопасным, поэтому для обеспечения того,
 * чтобы два потока не обращались к экземпляру HashMap одновременно, класс
 * Memorizer1 использует консервативный подход, заключающийся в синхронизации
 * всего метода compute.
 *
 * Такой подход гарантирует потокобезопасность, но имеет очевидную
 * ПРОБЛЕМУ
 * с масштабируемостью: в общем случае, только один поток за
 * раз может выполнять вычисления. Если другой поток занят вычислением
 * результата, другие потоки, вызывающие метод compute, могут быть
 * заблокированы на длительное время
 * РЕШЕНИЕ: использовать ConcurrentHashMap
 * см. Memorizer2
 *
 * @param <A>
 * @param <V>
 */
public class Memorizer1<A, V> implements Computable<A, V> {
    private final Map<A,V> cache = new HashMap<>();
    private final Computable<A, V> c;

    public Memorizer1(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
