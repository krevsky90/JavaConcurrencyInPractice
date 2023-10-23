package com.company.DeadLock_10_1;

public class LeftRightDeadlockTest {
    public static void main(String[] args) {
        LeftRightDeadlock leftRightDeadlock = new LeftRightDeadlock();
        System.out.println("leftRight will be called in separate thread");
        new Thread(null, () ->
        {
            try {
                leftRightDeadlock.leftRight();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },
                "krev1"
        ).start();

        System.out.println("rightLeft will be called in separate thread");
        new Thread(null, () ->
        {
            try {
                leftRightDeadlock.rightLeft();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "krev2"
        ).start();

        System.out.println("main end");
    }
}
