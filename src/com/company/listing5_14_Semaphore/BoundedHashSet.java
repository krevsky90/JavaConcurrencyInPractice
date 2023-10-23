package com.company.listing5_14_Semaphore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Семафор инициализируется
 * желаемым максимальным значением размера коллекции. Операция add
 * приобретает разрешение перед добавлением элемента в базовую коллекцию. Если
 * базовая операция add фактически ничего не добавляет, она немедленно
 * освобождает разрешение. Аналогичным образом, успешное выполнение операции
 * remove освобождает разрешение, позволяя добавлять дополнительные элементы.
 * Базовая реализация интерфейса Set ничего не знает об ограничении; всё это
 * ложится на класс BoundedHashSet.
 *
 * @param <T>
 */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded)
                sem.release();
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved)
            sem.release();
        return wasRemoved;
    }
}
