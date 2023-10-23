package com.company.krevsky_examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class KrevskyCommonExample {
    public static void main(String[] args) {
        commonMethod();
    }

    public static void commonMethod() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        /**
         * newFixedThreadPool calls
         *      new ThreadPoolExecutor(..)
         * where ThreadPoolExecutor extends extends AbstractExecutorService
         *      where AbstractExecutorService implements ExecutorService
         *          where ExecutorService extends Executor
         */

        RunnableTask runnableTask = new RunnableTask();

        CallableTask callableTask = new CallableTask();

        /**
         * executorService.submit calls AbstractExecutorService#submit
         *     where new RunnableFuture task is created by code: RunnableFuture ftask = newTaskFor(task ...);
         *
         *     NOTE: here is good place (method newtaskFor) to override smth you want to customize created Future object!
         */
        Future<?> futureForRunnable = executorService.submit(runnableTask);

        Future<String> futureForCallable = executorService.submit(callableTask);

        System.out.println("");
    }
}
