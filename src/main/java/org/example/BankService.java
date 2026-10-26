package org.example;
import java.util.concurrent.locks.ReentrantLock;

public class BankService {
    private final ReentrantLock lock = new ReentrantLock();

    public void transfer(User from, User to, long amount) {
        lock.lock();
        try {
            if (from.getAccount() < amount) {
                throw new IllegalStateException(
                        "Недостатньо коштів на рахунку користувача " + from.getId()
                );
            }

            from.withdraw(amount);
            to.deposit(amount);
        } finally {
            lock.unlock();
        }
    }
}