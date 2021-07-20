package ru.job4j.interthread;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class CASCountTest {

    @Test
    public void when3IncrementThen1Get() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        count.increment();
        assertThat(count.get(), is(3));
    }

    @Test
    public void when3ThreadIncrementThenGetWithInitialValue() throws InterruptedException {
        CASCount count = new CASCount(17);
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        count.increment();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 20; i++) {
                        count.increment();
                    }
                }
        );
        Thread thread3 = new Thread(
                () -> {
                    for (int i = 0; i < 30; i++) {
                        count.increment();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        assertThat(count.get(), is(77));
    }
}
