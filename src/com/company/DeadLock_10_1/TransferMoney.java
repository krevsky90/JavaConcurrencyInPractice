package com.company.DeadLock_10_1;

/**
 * Листинг 10.2 Взаимоблокировки, вызванные динамическим порядком наложения блокировок. Не делайте так.
 *
 * В целом метод transferMoney нормальный. Но автор метода не может гарантировать, что метод не будет вызван так, что
 * X дает деньги Y
 * Y дает деньги X
 * одновременно.
 * Тогда последовательность локов будет противоположная, и мы попадем в ситуацию, как в LeftRightDeadlock.java
 */
public class TransferMoney {

    // Warning: deadlock-prone!
    public void transferMoney(Account fromAccount, Account toAccount, int amount) throws InsufficientFundsException {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }
}