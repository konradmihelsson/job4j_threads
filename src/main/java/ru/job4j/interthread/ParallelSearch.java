package ru.job4j.interthread;

public class ParallelSearch {

    public static void main(String[] args) {

        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        Integer pleaseKillMeTonight = 777;

        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        Integer tmp = queue.poll();
                        if (tmp.equals(pleaseKillMeTonight)) {
                            break;
                        }
                        System.out.println(tmp);
                    }
                }
        );
        consumer.start();

        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(pleaseKillMeTonight);
                }
        ).start();
    }
}
