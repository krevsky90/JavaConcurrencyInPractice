package com.company.TestingParallelPrograms_12;

import java.util.concurrent.Semaphore;

/**
 * Листинг 12.1 Ограниченный буфер с использованием экземпляра Semaphore
 *
 * @param <E>
 */
public class BoundedBuffer<E> {
    private final Semaphore availableItems; //количество элементов, которое может быть удалено из буфера
    private final Semaphore availableSpaces; //количество элементов, которое может быть добавлено в буфер,
    private final E[] items;
    private int putPosition = 0;
    private int takePosition = 0;

    public BoundedBuffer(int capacity) {
        this.availableItems = new Semaphore(0);
        this.availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }
    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
//        availableItems.acquire();
        E item = doExtract();
//        availableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length)? 0 : i;
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length)? 0 : i;
        return x;
    }

}
