package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class WgetLimited implements Runnable {

    private final String url;
    private final int speed;

    public WgetLimited(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        System.out.println("Downloading started...");
        long start = System.currentTimeMillis();
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream());
             FileOutputStream fileOutputStream =
                     new FileOutputStream(Paths.get(new URI(url).getPath()).getFileName().toString())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long time = System.currentTimeMillis();
            int bytesWrited = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWrited = bytesWrited + bytesRead;
                if (bytesWrited > this.speed) {
                    long diffTime = System.currentTimeMillis() - time;
                    System.out.println(bytesWrited + " " + diffTime);
                    if (diffTime < 1000) {
                        System.out.println("sleeping for " + (1000 - diffTime) + " ms");
                        Thread.sleep(1000 - diffTime);
                    }
                    bytesWrited = 0;
                    time = System.currentTimeMillis();
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Downloading is complete: " + ((System.currentTimeMillis() - start) / 1000f));
    }

    public static void main(String[] args) throws InterruptedException {
        argsValidator(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetLimited(url, speed));
        wget.start();
        wget.join();
    }

    private static void argsValidator(String[] args) {
        if (args.length != 2) {
            String ls = System.lineSeparator();
            String sb = "Wrong arguments. Usage: WgetLimited url rate" + ls
                    + "when url - file http(s) web address, rate - download speed limin in byte per second" + ls
                    + "For example: WgetLimited https://proof.ovh.net/files/1Mb.dat 262144" + ls;
            throw new IllegalArgumentException(sb);
        }
    }
}
