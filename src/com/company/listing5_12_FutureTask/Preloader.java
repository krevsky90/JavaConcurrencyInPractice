package com.company.listing5_12_FutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.company.listing5_12_FutureTask.FutureTaskTest.LOADING_DURATION;

public class Preloader {
    private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(
            new Callable<ProductInfo>() {
                public ProductInfo call() throws DataLoadException {
                    return loadProductInfo();
                }
            });

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get()
            throws DataLoadException, InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException)
                throw (DataLoadException) cause;
            else
                throw launderThrowable(cause);
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

    private final ProductInfo loadProductInfo() throws DataLoadException {
        try {
            System.out.println("start loadProductInfo");
            Thread.sleep(LOADING_DURATION); //emulate call to DataBase that might throw DataLoadException
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("end loadProductInfo");
        }

        return new ProductInfo();
    }
}
