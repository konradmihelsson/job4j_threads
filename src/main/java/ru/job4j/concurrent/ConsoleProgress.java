package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(3000); /* симулируем выполнение параллельной задачи в течение 3 секунд. */
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = "\\|/".toCharArray();
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[index++]);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            if (index == process.length) {
                index = 0;
            }
        }
    }
}
