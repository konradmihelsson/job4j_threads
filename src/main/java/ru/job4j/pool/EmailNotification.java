package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        this.pool.submit(() -> {
            String userName = user.getUserName();
            String eMail = user.geteMail();
            String subject = "Notification " + userName + " to email " + eMail + ".";
            String body = "Add a new event to " + userName;
            send(subject, body, eMail);
        });
    }

    public void close() {
        this.pool.shutdown();
        while (!this.pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
