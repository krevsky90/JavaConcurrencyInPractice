package com.company.WebServer_chapter6.listing6_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Обработка запросов по принципу "одна задача - один тред" (притом создается НОВЫЙ тред каждый раз)
 *
 * ПРОБЛЕМА: слишком много тредов -> слишком много затрачено ресурсов. отработавшие треды простаивают
 * РЕШЕНИЕ: создавать треды не напрямую, а через фреймворк Executor
 * см. src/com/company/WebServer_chapter6/listing6_4/TaskExecutionWebServer.java
 */

public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            new Thread(task).start();
        }
    }

    private static void handleRequest(Socket connection) {
        //do smth
    }
}
