package com.company.DeadLock_10_1;

/**
 * Листинг 10.1 Простая взаимоблокировка, вызванная порядком наложения блокировок. Не делайте так.
 *
 */
public class LeftRightDeadlock {
    private final long duration = 1000L;
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() throws InterruptedException {
        synchronized (left) {
            //my addition to increase probability of deadlock
            //otherwise the object that calls leftRight may take both blocks before the other object calls rightLeft
            Thread.sleep(duration);

            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() throws InterruptedException {
        synchronized (right) {
            Thread.sleep(duration); //my addition to increase probability of deadlock
            synchronized (left) {
                doSomethingElse();
            }
        }
    }

    public void doSomething() {
    }

    public void doSomethingElse() {
    }
}
