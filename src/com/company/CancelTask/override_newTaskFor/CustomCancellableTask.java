package com.company.CancelTask.override_newTaskFor;

public class CustomCancellableTask extends SocketUsingTask<Void> {
    @Override
    public Void call() throws Exception {
        System.out.println("CustomCancellableTask is called in thread " + Thread.currentThread().getName());
        Thread.sleep(2000);
        return null;
    }
}
