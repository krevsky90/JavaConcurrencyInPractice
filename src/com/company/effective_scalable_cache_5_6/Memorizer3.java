package com.company.effective_scalable_cache_5_6;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Класс Memorizer3 сначала проверяет, было ли запущено
 * соответствующее вычисление (в отличие от принятого по умолчанию как
 * “завершённое”, в классе Memorizer2). Если это не так, он создает экземпляр класса
 * FutureTask, регистрирует его в экземпляре Map и запускает вычисление; в
 * противном случае он ожидает результата существующего вычисления. Результат
 * может быть доступен немедленно или находиться в процессе вычисления - но он
 * прозрачен для объекта, вызывающего метод Future.get
 *
 * ОСТАЮТСЯ ПРОБЛЕМЫ:
 * 1) все еще существует небольшое окно уязвимости, в котором два потока могли бы начать вычисление одного и того же значения.
 * РЕШЕНИЕ: атомарный метод putIfAbsent интерфейа ConcurrentMap
 * 2) Кэширование экземпляра Future вместо значения создает предпосылки к загрязнению кэша (cache pollution):
 * если вычисление отменяется или завершается неудачей, дальнейшие попытки вычислить результат также будут указывать на отмену или сбой
 * РЕШЕНИЕ: удалять экземпляр Future из кэша, если он обнаруживает, что вычисление было отменено
 * см. Memorizer4
 *
 * @param <A>
 * @param <V>
 */
public class Memorizer3<A, V> implements Computable<A, V> {
    private final Map<A,Future<V>> cache = new ConcurrentHashMap();
    private final Computable<A, V> c;

    public Memorizer3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            /**
             * java < 8 implementation:
             *
             * FutureTask<V> ft = new FutureTask<V>(
             *                     new Callable<V>() {
             *                         @Override
             *                         public V call() throws Exception {
             *                             return c.compute(arg);
             *                         }
             *                     }
             *             );
             */

            /**
             * java 8+ implementation
             */
            FutureTask<V> ft = new FutureTask<>(
                    () -> c.compute(arg)
            );

            f = ft;     //just to call f.get out of current if (f == null)
            cache.put(arg, ft);
            ft.run();   // call to c.compute happens here
        }

        try {
            V result = f.get();
            return result;
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
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
