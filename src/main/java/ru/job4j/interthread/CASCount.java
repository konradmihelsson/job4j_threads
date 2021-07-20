package ru.job4j.interthread;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count;

    public CASCount() {
        count = new AtomicReference<>(0);
    }

    public CASCount(int initialValue) {
        count = new AtomicReference<>(initialValue);
    }

    public void increment() {
        int tmp;
        int nxt;
        do {
            tmp = this.count.get();
            nxt = tmp + 1;
        } while (!count.compareAndSet(tmp, nxt));
    }

    public int get() {
        return this.count.get();
    }
}
