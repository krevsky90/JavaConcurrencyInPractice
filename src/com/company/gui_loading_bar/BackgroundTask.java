package com.company.gui_loading_bar;

import java.util.concurrent.*;

/**
 * Листинг 9.7 Класс фоновой задачи, поддерживающий механизм отмены, у
 *
 * @param <V>
 */
abstract class BackgroundTask<V> implements Runnable, Future<V> {
    //todo: не понял! а где используется этот объект?
    private final FutureTask<V> computation = new Computation();

    private final ExecutorService guiExecutor = Executors.newCachedThreadPool();

    private class Computation extends FutureTask<V> {
        public Computation() {
            super(new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return BackgroundTask.this.compute();
                }
            });
        }

        protected void done() {
            guiExecutor.execute(() -> {
                V value = null;
                Throwable thrown = null;
                boolean cancelled = false;
                try {
                    value = get();
                } catch (ExecutionException e) {
                    thrown = e.getCause();
                } catch (CancellationException e) {
                    cancelled = true;
                } catch (InterruptedException consumed) {
                } finally {
                    onCompletion(value, thrown, cancelled);
                }
            });
        }

        protected void setProgress(final int current, final int max) {
            guiExecutor.execute(() -> {
                    onProgress(current, max);
                }
            );
        }
    }

    // Called in the background thread
    protected abstract V compute() throws Exception;

    // Called in the event thread
    protected void onCompletion(V result, Throwable exception, boolean cancelled) {
    }

    // Called in the event thread
    protected void onProgress(int current, int max) {
    }

    // Other Future methods forwarded to computation

}
