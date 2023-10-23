package com.company.TryLock_13_3;

import com.company.DeadLock_10_1.InsufficientFundsException;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Листинг 13.3 Избегание возникновения взаимоблокировок с помощью метода tryLock
 */
public class TryLock_13_3 {
    public boolean transferMoney(Account fromAcct,
                                 Account toAcct,
                                 Integer amount,
                                 long timeout,
                                 TimeUnit unit)
            throws InsufficientFundsException, InterruptedException {
        long fixedDelay = 1L;//getFixedDelayComponentNanos(timeout, unit);
        long randMod = new Random(10).nextLong();//getRandomDelayModulusNanos(timeout, unit);
        long stopTime = System.nanoTime() + unit.toNanos(timeout);
        while (true) {
            if (fromAcct.getLock().tryLock()) {
                try {
                    if (toAcct.getLock().tryLock()) {
                        try {
                            if (fromAcct.getBalance().compareTo(amount) < 0)
                                throw new InsufficientFundsException();
                            else {
                                fromAcct.debit(amount);
                                toAcct.credit(amount);
                                return true;
                            }
                        } finally {
                            toAcct.getLock().unlock();
                        }
                    }
                } finally {
                    fromAcct.getLock().unlock();
                }
            }
            if (System.nanoTime() > stopTime)
                return false;
            NANOSECONDS.sleep(fixedDelay + new Random(10).nextLong() % randMod);
        }
    }
}
