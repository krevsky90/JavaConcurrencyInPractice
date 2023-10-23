package com.company.BoundedExecutor_8_4;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Semaphore;

public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;

    //bound должна быть равна размеру пула + желаемому максимальному размеру очереди
    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(
                    () -> {
                        try {
                            command.run();
                        } finally {
                            semaphore.release();
                        }
                    }
            );
        } catch (Throwable e) {
            if (e instanceof RejectedExecutionHandler) {
                semaphore.release();
            } else {
                throw e;
            }
        }

    }
}
