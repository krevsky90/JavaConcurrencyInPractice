package com.company.CancelTask.override_newTaskFor;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        SocketUsingTask socketUsingTask = new CustomCancellableTask();
        Socket socket = new Socket();
        socketUsingTask.setSocket(socket);

        int maxThreads = 3;
        ExecutorService executorService = new CancellingExecutor(maxThreads, maxThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        Future<Void> future = executorService.submit(socketUsingTask);

        //requests to interrupt CustomCancellableTask#call method
        future.cancel(true);

        System.out.println("main is finished");

        //NOTE: to finish finish main thread it is necessary to call executorService.shutdown() or executorService.shutdownNow();
        executorService.shutdown(); //waiting all tasks that are executing
//        executorService.shutdownNow(); //tries to interrupt all tasks that are executing
    }
}
