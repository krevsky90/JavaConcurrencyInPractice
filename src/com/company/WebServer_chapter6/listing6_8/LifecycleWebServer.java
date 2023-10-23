package com.company.WebServer_chapter6.listing6_8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class LifecycleWebServer {
    private final ExecutorService exec = Executors.newFixedThreadPool(2);

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                final Socket conn = socket.accept();
                exec.execute(new Runnable() {
                    public void run() {
                        handleRequest(conn);
                    }
                });
            } catch (RejectedExecutionException e) {
                if (!exec.isShutdown())
                    System.out.println("task submission rejected" + e);
            }
        }
    }

    public void stop() {
        exec.shutdown();
    }

    void handleRequest(Socket connection) {
//        Request req = readRequest(connection);
//        if (isShutdownRequest(req)) {
//            stop();
//        } else {
//            dispatchRequest(req);
//        }
    }
}
