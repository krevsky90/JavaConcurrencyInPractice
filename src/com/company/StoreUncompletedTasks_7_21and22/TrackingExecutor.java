package com.company.StoreUncompletedTasks_7_21and22;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Листинг 7.21 Экземпляр ExecutorService отслеживающий отмененные задачи после завершения работы
 */
public class TrackingExecutor extends AbstractExecutorService {
    private final ExecutorService exec;
    private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    public List<Runnable> getCancelledTasks() {
        if (!exec.isTerminated()) {
            throw new IllegalStateException("ExecutorService is NOT terminated!");
        }
        return new ArrayList<>(tasksCancelledAtShutdown);
    }

    @Override
    public void execute(Runnable command) {
        Runnable wrappedCommand = () -> {
          try {
              command.run();
          } finally {
              if (isShutdown() && Thread.currentThread().isInterrupted()) {
                  tasksCancelledAtShutdown.add(command);
              }
          }
        };

        exec.execute(wrappedCommand);
    }

    @Override
    public void shutdown() {
        exec.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }
}
