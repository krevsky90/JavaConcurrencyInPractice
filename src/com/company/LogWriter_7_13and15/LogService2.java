package com.company.LogWriter_7_13and15;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class LogService2 {
    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private final PrintWriter writer = new PrintWriter(System.out);

    public static final int TIMEOUT = 5;


    public void start() {
        //...
    }

    public void stop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(TIMEOUT, TimeUnit.SECONDS);   //
        } finally {
            writer.close();
        }
    }
    public void log(String msg) {
        try {
            exec.execute(new WriteTask(msg));
        } catch (RejectedExecutionException ignored) {
        }
    }

    private class WriteTask implements Runnable {
        private final String msg;

        public WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            //write smth
        }
    }
}
