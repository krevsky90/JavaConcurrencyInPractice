package com.company.CancelTask;

import java.util.concurrent.*;

import static com.company.Renderer_6_3.AbstractRenderer.launderThrowable;

/**
 * Листинг 7.10 Отмена задачи с использованием Future
 */
public class CancelByFuture {
    private static final ExecutorService taskExec = Executors.newFixedThreadPool(3); //no matter what type of executor is used

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            //get throws TimeoutException because get waits too long (more then value of 'timeout' variable)
            // task will be cancelled below
        } catch (ExecutionException e) {
            //smth went wrong during execution of the task. We need report the object that called timerRun method about this problem!
            // exception thrown in task; rethrow
            throw launderThrowable(e.getCause());
        } finally {
            // Life hack: Harmless if task already completed
            task.cancel(true); // interrupt if running
        }
    }
}
