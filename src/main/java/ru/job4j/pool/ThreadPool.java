package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;
import ru.job4j.interthread.SimpleBlockingQueue;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() {
        final int threadsSize = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i != threadsSize; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        this.tasks.offer(job);
    }

    public void shutdown() {
        this.threads.forEach(Thread::interrupt);
    }
}
