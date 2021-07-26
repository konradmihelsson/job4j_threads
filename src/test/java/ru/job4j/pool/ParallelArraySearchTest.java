package ru.job4j.pool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;

public class ParallelArraySearchTest {

    private ParallelArraySearch parallelArraySearch;
    private Object[] array;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Test
    public void whenArrayContainsNeededObjectLinearSearch() {
        array = arrayForLinearSearch();
        parallelArraySearch = new ParallelArraySearch(array, "This is some object.");
        assertEquals(2, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    @Test
    public void whenArrayNotContainsNeededObjectLinearSearch() {
        array = arrayForLinearSearch();
        parallelArraySearch = new ParallelArraySearch(array, "This is another object.");
        assertEquals(-1, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    @Test
    public void whenArrayContainsNeededObject() {
        array = arrayForFullSearch();
        parallelArraySearch = new ParallelArraySearch(array, "This is some object.");
        assertEquals(44, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    @Test
    public void whenArrayNotContainsNeededObject() {
        array = arrayForFullSearch();
        parallelArraySearch = new ParallelArraySearch(array, "This is another object.");
        assertEquals(-1, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    @Test
    public void whenArrayContainsSeveralNeededObjectsThenFindLeastIndexOfAllThem() {
        array = arrayForFullSearch();
        array[11] = "This is some object.";
        array[22] = "This is some object.";
        array[33] = "This is some object.";
        array[55] = "This is some object.";
        array[104] = "This is some object.";
        parallelArraySearch = new ParallelArraySearch(array, "This is some object.");
        assertEquals(11, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    private Object[] arrayForLinearSearch() {
        Object[] result = new Object[5];
        result[0] = 33;
        result[1] = false;
        result[2] = "This is some object.";
        result[3] = 333.333f;
        result[4] = new ArrayList<>();
        return result;
    }

    private Object[] arrayForFullSearch() {
        Object[] result = new Object[105];
        Arrays.fill(result, false);
        result[44] = "This is some object.";
        return result;
    }
}
