package com.company.TestingParallelPrograms_12;

import junit.framework.TestCase;

/**
 * Листинг 12.2 Простой модульный тест для класса BoundedBuffer
 *
 * ПОСЛЕДОВАТЕЛЬНОЕ тестирование
 */
public class BoundedBufferSequentialTest extends TestCase {
    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        for (int i = 0; i < 10; i++)
            bb.put(i);
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }

}
