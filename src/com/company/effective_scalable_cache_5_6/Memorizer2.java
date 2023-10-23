package com.company.effective_scalable_cache_5_6;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * listing 5.17
 *
 * заменяем HashMap на ConcurrentHashMap.
 * Поскольку класс ConcurrentHashMap потокобезопасен, нет необходимости
 * обеспечивать синхронизацию при доступе к кэширующему объекту Map, тем
 * самым устраняется сериализация, вызванная синхронизацией метода compute в
 * классе Memorizer2.
 *
 * ОСТАЕТСЯ ПРОБЛЕМА:
 * если один поток начинает выполнение дорогостоящего вычисления, другие потоки не знают,
 * что вычисление выполняется, и поэтому могут начать то же вычисление
 * РЕШЕНИЕ: использовать FutureTask
 * см Memorizer3
 *
 * @param <A>
 * @param <V>
 */
public class Memorizer2<A, V> implements Computable<A, V> {
    private final Map<A,V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memorizer2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
