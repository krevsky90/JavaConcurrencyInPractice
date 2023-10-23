package com.company.StoreUncompletedTasks_7_21and22;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class WebCrawler {
    private volatile TrackingExecutor exec;
    private static final int TIMEOUT = 5;

    private final Set<URL> urlsToCrawl = new HashSet<URL>();

    public synchronized void start() {
        exec = new TrackingExecutor(Executors.newCachedThreadPool());

        for (URL url : urlsToCrawl) {
            submitCrawlTask(url);
        }

        urlsToCrawl.clear();
    }

    public synchronized void stop() throws InterruptedException {
        try {
            //exec.shutdownNow() returns all tasks that are in queue but still haven't started
            saveUncrawled(exec.shutdownNow());
            //awaitTermination returns true if executor is terminated
            //                 returns false if timeout comes before termination
            if (exec.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                //add all cancelled tasks added to collection by TrackingExecutor#execute() method
                saveUncrawled(exec.getCancelledTasks());
            }
        } finally {
            exec = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    private void saveUncrawled(List<Runnable> uncrawled) {
        for (Runnable task : uncrawled) {
            urlsToCrawl.add(((CrawlTask) task).getPage());
        }
    }

    private void submitCrawlTask(URL u) {
        exec.execute(new CrawlTask(u));
    }

    private class CrawlTask implements Runnable {
        private final URL url;

        public CrawlTask(URL url) {
            this.url = url;
        }

        public void run() {
            for (URL link : processPage(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                submitCrawlTask(link);
            }
        }

        public URL getPage() {
            return this.url;
        }
    }
}
