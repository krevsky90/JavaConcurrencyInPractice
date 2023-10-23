package com.company;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class RecursionInParallel_8_11and12 {
    public <T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> n : nodes) {
            results.add(n.compute());
            sequentialRecursive(n.getChildren(), results);
        }
    }

    public <T> void parallelRecursive(final Executor exec, List<Node<T>> nodes, final Collection<T> results) {
        for (final Node<T> n : nodes) {
            exec.execute(() -> {
                results.add(n.compute());
            });
        }
    }

    public<T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedQueue<>();
        parallelRecursive(executorService, nodes, resultQueue);
        executorService.shutdown();
        //waiting for all compute calls for each node
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQueue;
    }

    private class Node<T> {
        private T value;
        private List<Node<T>> children;

        public <T> T compute() {
            return (T) value;
        }

        public List<Node<T>> getChildren() {
            return this.children;
        }
    }

}
