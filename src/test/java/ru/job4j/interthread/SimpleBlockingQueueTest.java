package ru.job4j.interthread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleBlockingQueueTest {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
    }

    @Test
    public void whenProducerAndConsumerWorkingTogether() throws InterruptedException {

        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>();
        Thread consumer = new Thread(() -> {
            try {
                System.out.println(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread producer = new Thread(() -> {
            try {
                queue.offer("We are the greatest!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer.start();
        producer.start();
        consumer.join();
        assertThat(out.toString(), is("We are the greatest!" + System.lineSeparator()));
    }
}
