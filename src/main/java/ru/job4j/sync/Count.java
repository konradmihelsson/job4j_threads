package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class Count {

    @GuardedBy("this")
    private final AtomicInteger value;

    public Count(int value) {
        this.value = new AtomicInteger(value);
    }

    public synchronized void increment() {
        this.value.incrementAndGet();
    }

    public synchronized int get() {
        return this.value.get();
    }
}
