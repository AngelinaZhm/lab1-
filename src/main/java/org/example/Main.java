package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args)
            throws InterruptedException, ExecutionException {

        final long initialBalance = 100_000;
        final long transactionAmount = 1_000;
        final int transactionCount = 100;

        User user1 = new User(1, initialBalance);
        User user2 = new User(2, initialBalance);

        BankService bankService = new BankService();

        System.out.println("Початкові баланси:");
        printBalances(user1, user2);

        ExecutorService executor = Executors.newFixedThreadPool(transactionCount);
        List<Future<?>> tasks = new ArrayList<>();

        for (int i = 0; i < transactionCount; i++) {
            tasks.add(executor.submit(() ->
                    bankService.transfer(user1, user2, transactionAmount)
            ));
        }

        for (Future<?> task : tasks) {
            task.get();
        }

        executor.shutdown();

        System.out.println("\nРезультат після 100 паралельних транзакцій:");
        printBalances(user1, user2);

        long totalBalance = user1.getAccount() + user2.getAccount();

        System.out.println("\nЗагальна сума коштів: " + totalBalance + " у.о.");

        if (user1.getAccount() == 0
                && user2.getAccount() == 200_000
                && totalBalance == 200_000) {

            System.out.println("Перевірка пройдена успішно.");
            System.out.println("Усі 100 транзакцій виконані коректно.");
        } else {
            System.out.println("Помилка: результати не відповідають очікуваним.");
        }
    }

    private static void printBalances(User user1, User user2) {
        System.out.println(
                "User " + user1.getId() + ": " + user1.getAccount() + " у.о."
        );
        System.out.println(
                "User " + user2.getId() + ": " + user2.getAccount() + " у.о."
        );
    }
}