package org.example;

public class User {
    private final int id;
    private long account;

    public User(int id, long account) {
        this.id = id;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public long getAccount() {
        return account;
    }

    public void deposit(long amount) {
        account += amount;
    }

    public void withdraw(long amount) {
        account -= amount;
    }
}