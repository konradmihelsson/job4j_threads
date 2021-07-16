package ru.job4j.interthread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    @GuardedBy("this")
    private final int maxSize;

    public SimpleBlockingQueue() {
        this.maxSize = Integer.MAX_VALUE;
    }

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (this.queue.size() == this.maxSize) {
                    this.wait();
            }
            this.queue.offer(value);
            this.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (this.queue.isEmpty()) {
                    this.wait();
            }
            T result = this.queue.poll();
            this.notifyAll();
            return result;
        }
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
