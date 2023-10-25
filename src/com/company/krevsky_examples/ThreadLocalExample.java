package com.company.krevsky_examples;

/**
 * https://javarush.com/quests/lectures/jru.module2.lecture26
 */
public class ThreadLocalExample {

    public static void main(String[] args) {
        class ThreadDemo implements Runnable {

            int counter;
            ThreadLocal<Integer> threadLocalCounter = new ThreadLocal<>();

            public void run() {
                counter++;

                if(threadLocalCounter.get() != null) {
                    threadLocalCounter.set(threadLocalCounter.get() + 1);
                } else {
                    threadLocalCounter.set(0);
                }
                printCounters();
            }

            public void printCounters(){
                System.out.println("Counter of " + Thread.currentThread().getName() + ": " + counter);
                System.out.println("threadLocalCounter of " + Thread.currentThread().getName() + ": " + threadLocalCounter.get());
            }
        }

        ThreadDemo threadDemo = new ThreadDemo();

        Thread t1 = new Thread(threadDemo, "T1");
        Thread t2 = new Thread(threadDemo, "T2");
        Thread t3 = new Thread(threadDemo, "T3");

        t1.start();
        t2.start();
        t3.start();

    }
}
