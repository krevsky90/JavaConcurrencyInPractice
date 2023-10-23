package com.company.LogWriter_7_13and15;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LogServiceTest {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        PrintWriter printWriter = new PrintWriter(System.out);

        LogService logService = new LogService(printWriter);

        Thread thread = new Thread(() -> {
            try {
                //write to log each 0.5 second
                for (int i = 0; i < 10; i++) {
                    logService.log("i = " + i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        );

        thread.start();

        logService.start();
        Thread.sleep(3200);
        logService.stop();

        System.out.println("main end");
    }
}
