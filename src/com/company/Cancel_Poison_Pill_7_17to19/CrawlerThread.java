package com.company.Cancel_Poison_Pill_7_17to19;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

import static com.company.Cancel_Poison_Pill_7_17to19.IndexingService.POISON;

public class CrawlerThread extends Thread {
    private final File root;
    private final FileFilter fileFilter;
    private final BlockingQueue<File> queue;

    public CrawlerThread(File root, FileFilter fileFilter, BlockingQueue queue) {
        this.root = root;
        this.fileFilter = fileFilter;
        this.queue = queue;
    }

    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            // fall through
        } finally {
            while (true) {
                try {
                    queue.put(POISON);
                    break;
                } catch (InterruptedException e1) {
                    // retry
                }
            }
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);
        if (entries != null) {
            for (File entry : entries) {
                if (entry.isDirectory()) {
                    crawl(entry);
                } else if (!alreadyIndexed(entry)) {
                    queue.put(entry);
                }
            }
        }
    }

    private boolean alreadyIndexed(File file) {
        //some implementation
        return false;
    }
}
