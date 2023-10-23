package com.company.WebServer_chapter6.listing6_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Последовательная обработка запросов
 *
 * ПРОБЛЕМА: производительность, если клиентов больше, чем 1
 * РЕШЕНИЕ: обрабатывать запросы в отдельных потоках
 * см. src/com/company/WebServer_chapter6/listing6_2/ThreadPerTaskWebServer.java
 */
public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) {
        //do smth
    }
}
