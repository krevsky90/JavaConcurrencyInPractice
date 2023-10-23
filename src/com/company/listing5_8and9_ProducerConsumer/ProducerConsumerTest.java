package com.company.listing5_8and9_ProducerConsumer;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerTest {
    public static final int BOUND = 5;  //for example 5
    public static final int N_CONSUMERS = 2;  //for example 2

    public static void main(String[] args) {
        //dummy start
        File[] roots = new File[2];
        roots[0] = new File("file1");
        roots[0] = new File("file2");
        //dummy end
        startIndexing(roots);
    }

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
        FileFilter filter = file -> true;

        for (File root : roots) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }
}
