package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        for (int i = 0; i <= 100; i++) {
                            System.out.print("\rLoading : " + i + "%");
                            Thread.sleep(10);
                        }
                        System.out.println(" Loaded.");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        thread.start();
        thread.join();
        System.out.println("Main complete");
    }
}
