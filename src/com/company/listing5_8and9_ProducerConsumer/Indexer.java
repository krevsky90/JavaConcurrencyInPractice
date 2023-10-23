package com.company.listing5_8and9_ProducerConsumer;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Листинг 7.18 Поток производителя в классе IndexingService
 */
public class Indexer implements Runnable {
    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true)
                indexFile(queue.take());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //some implementation
    private void indexFile(File file) {
        System.out.println("indexing of file " + file.getName());
    }
}
