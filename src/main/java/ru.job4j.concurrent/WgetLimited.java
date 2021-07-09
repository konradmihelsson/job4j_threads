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
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream());
             FileOutputStream fileOutputStream =
                     new FileOutputStream(Paths.get(new URI(url).getPath()).getFileName().toString())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long time = System.currentTimeMillis();
            int bytesPerSecWrited = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long diffTime = System.currentTimeMillis() - time;
                bytesPerSecWrited = bytesPerSecWrited + 1024;
                long SEC_IN_MILLISEC = 1000;
                if (bytesPerSecWrited > this.speed && diffTime < SEC_IN_MILLISEC) {
                    Thread.sleep(SEC_IN_MILLISEC - diffTime);
                    bytesPerSecWrited = 0;
                    time = System.currentTimeMillis();
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
            String sb = "Wrong arguments. Usage: WgetLimited url rate" + ls +
                    "when url - file http(s) web address, rate - download speed limin in byte per second" + ls +
                    "For example: WgetLimited https://proof.ovh.net/files/1Mb.dat 262144" + ls;
            throw new IllegalArgumentException(sb);
        }
    }
}
