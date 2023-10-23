package com.company.LogWriter_7_13and15;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogService {
    private static final int CAPACITY = 3;

    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;

    private boolean isShutdown;
    private int reservations;

    public LogService(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<>(CAPACITY);
        this.loggerThread = new LoggerThread();
        this.writer = writer;
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown)
                throw new IllegalStateException("IllegalStateException in log() method");
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0)
                                break;
                        }
                        String msg = queue.take();
                        synchronized (LogService.this) {
                            --reservations;
                        }
                        writer.println(msg);
                        writer.flush();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //retry
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
