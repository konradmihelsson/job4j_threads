package ru.job4j.interthread;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            this.count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (this.count < this.total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
