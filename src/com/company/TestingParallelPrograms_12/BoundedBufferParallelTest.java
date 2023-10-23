package com.company.TestingParallelPrograms_12;

import junit.framework.TestCase;

public class BoundedBufferParallelTest extends TestCase {
    public static final long LOCKUP_DETECT_TIMEOUT = 2;

    public void testTakeBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        Thread taker = new Thread() {
            public void run() {
                try {
                    Integer unused = bb.take();
                    fail(); // if we get here, it’s an error
                } catch (InterruptedException success) {
                }
            }
        };

        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            taker.interrupt();
            //Ограниченная по времени версия метода join гарантирует, что тест
            //завершится, даже если выполнение метода take каким-то неожиданным образом
            //застопорится.
            taker.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(taker.isAlive());
        } catch (Exception unexpected) {
            fail();
        }
    }
}
