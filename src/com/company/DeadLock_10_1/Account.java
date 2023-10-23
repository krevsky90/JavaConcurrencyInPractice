package com.company.DeadLock_10_1;

public class Account {
    private Integer balance;

    public Integer getBalance() {
        return balance;
    }

    public void debit(int amount) {
        balance -= amount;
    }

    public void credit(int amount) {
        balance += amount;
    }
}
