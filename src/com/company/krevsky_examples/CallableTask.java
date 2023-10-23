package com.company.krevsky_examples;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        //do smth and
        return "return smth";
    }
}
