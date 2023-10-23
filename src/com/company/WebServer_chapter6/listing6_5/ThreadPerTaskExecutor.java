package com.company.WebServer_chapter6.listing6_5;

import java.util.concurrent.Executor;

/**
 * to emulate listing6_2
 */
public class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    };
}