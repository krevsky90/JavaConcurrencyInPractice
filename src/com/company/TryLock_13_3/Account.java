package com.company.TryLock_13_3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private Integer balance;
    private final Lock lock = new ReentrantLock();

    public Integer getBalance() {
        return balance;
    }

    public void debit(int amount) {
        balance -= amount;
    }

    public Lock getLock() {
        return lock;
    }

    public void credit(int amount) {
        balance += amount;
    }
}
