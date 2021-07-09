package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WgetLimited implements Runnable {

    private final long SEC_IN_MILLISEC = 1000;
    private final String url;
    private final int speed;

    public WgetLimited(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long time = System.currentTimeMillis();
            int bytesPerSecWrited = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long diffTime = System.currentTimeMillis() - time;
                bytesPerSecWrited = bytesPerSecWrited + 1024;
                if (bytesPerSecWrited > this.speed && diffTime < SEC_IN_MILLISEC) {
                    Thread.sleep(SEC_IN_MILLISEC - diffTime);
                    bytesPerSecWrited = 0;
                    time = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetLimited(url, speed));
        wget.start();
        wget.join();
    }
}
