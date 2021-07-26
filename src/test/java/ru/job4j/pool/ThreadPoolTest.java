package ru.job4j.pool;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.sync.Count;

import static org.junit.Assert.*;

public class ThreadPoolTest {

    private ThreadPool threadPool;
    private Count count;

    @Before
    public void SetUp() {
        threadPool = new ThreadPool();
        count = new Count(0);
    }

    @Test
    public void WhenUsingShutdownMethod() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            Runnable runnable = new CounterThread(this.count);
            this.threadPool.work(runnable);
        }
        this.threadPool.shutdown();
        Thread.sleep(100);
        assertNotEquals(300000, this.count.get());
    }

    @Test
    public void WhenNotUsingShutdownMethod() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            Runnable runnable = new CounterThread(this.count);
            this.threadPool.work(runnable);
        }
        Thread.sleep(100);
        assertEquals(300000, this.count.get());
    }

    private class CounterThread implements Runnable {

        private final Count count;

        public CounterThread(Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                this.count.increment();
            }
        }
    }
}
