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
            Thread thread = new Thread(new WorkingRunnable());
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        this.tasks.offer(job);
            this.tasks.notify();
    }

    public void shutdown() {
        this.threads.forEach(Thread::interrupt);
    }

    private class WorkingRunnable implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                while (tasks.isEmpty()) {
                    try {
                        tasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
