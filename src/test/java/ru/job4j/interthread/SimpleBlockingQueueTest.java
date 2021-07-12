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
        Thread consumer = new Thread(() -> System.out.println(queue.poll()));
        Thread producer = new Thread(() -> queue.offer("We are the greatest!"));
        consumer.start();
        producer.start();
        consumer.join();
        assertThat(out.toString(), is("We are the greatest!" + System.lineSeparator()));
    }
}
