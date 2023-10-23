package com.company.Cancel_Poison_Pill_7_17to19;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IndexingService {
    public static final File POISON = new File("");
    private final IndexerThread consumer;
    private final CrawlerThread producer;
    private final BlockingQueue<File> queue;
    private final FileFilter fileFilter;
    private final File root;

    private IndexingService(BlockingQueue<File> queue, FileFilter fileFilter, File root) {
        this.queue = new LinkedBlockingQueue<>(10);
        this.fileFilter = fileFilter;
        this.root = root;
        this.consumer = new IndexerThread(queue);
        this.producer = new CrawlerThread(root, fileFilter, queue);
    }

    public void start() {
        producer.start();
        consumer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();    //wait for consumer
    }

}
