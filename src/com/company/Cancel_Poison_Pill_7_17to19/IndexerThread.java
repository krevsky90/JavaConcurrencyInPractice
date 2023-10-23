package com.company.Cancel_Poison_Pill_7_17to19;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import static com.company.Cancel_Poison_Pill_7_17to19.IndexingService.POISON;

/**
 * Листинг 7.19 Поток потребителя в классе IndexingService
 */
public class IndexerThread extends Thread {
    private final BlockingQueue<File> queue;

    public IndexerThread(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                File file = queue.take();
                if (file == POISON) {
                    break;
                } else {
                    indexFile(file);
                }
            }
        } catch (InterruptedException consumed) {

        }
    }

    //some implementation
    private void indexFile(File file) {
        System.out.println("indexing of file " + file.getName());
    }
}
