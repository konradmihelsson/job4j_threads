package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return content(c -> c > 0);
    }

    public String getContentWithoutUnicode() {
        return content(c -> c < 0x80);
    }

    private String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        int data;
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = i.read()) > 0) {
                char symbol = (char) data;
                if (filter.test(symbol)) {
                    output.append(symbol);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
