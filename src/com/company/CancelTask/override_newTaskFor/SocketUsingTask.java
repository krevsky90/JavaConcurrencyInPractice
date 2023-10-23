package com.company.CancelTask.override_newTaskFor;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * Листинг 7.12 Инкапсуляция нестандартной отмены в задачу с помощью метода newTaskFor
 *
 * @param <T>
 */
public abstract class SocketUsingTask<T> implements CancellableTask<T> {
    private Socket socket;

    protected synchronized void setSocket(Socket s) {
        socket = s;
    }

    public synchronized void cancel() {
        try {
            System.out.println("Closing socket of current SocketUsingTask...");
            if (socket != null)
                socket.close();
        } catch (IOException ignored) {
            System.out.println("IOException is ignored when socket is closed");
        }
    }

    public RunnableFuture<T> newTask() {
        System.out.println("new SocketUsingTask will be created by custom newTask method");
        return new FutureTask<T>(this) {
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    System.out.println("Attention! Socket will be closed!");
                    SocketUsingTask.this.cancel();
                    System.out.println("SocketUsingTask#cancel() is finished");
                } finally {
                    System.out.println("execute super#cancel method...");
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}
