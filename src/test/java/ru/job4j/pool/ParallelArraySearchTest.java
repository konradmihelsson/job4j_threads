package ru.job4j.pool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;

public class ParallelArraySearchTest {

    @Test
    public void whenArrayContainsNeededObjectLinearSearch() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Object[] array = arrayForLinearSearch();
        ParallelArraySearch parallelArraySearch =
                new ParallelArraySearch(array, "This is some object.", 0, array.length - 1);
        assertEquals(2, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    @Test
    public void whenArrayNotContainsNeededObjectLinearSearch() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Object[] array = arrayForLinearSearch();
        ParallelArraySearch parallelArraySearch =
                new ParallelArraySearch(array, "This is another object.", 0, array.length - 1);
        assertEquals(-1, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    @Test
    public void whenArrayContainsNeededObject() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Object[] array = arrayForFullSearch();
        ParallelArraySearch parallelArraySearch =
                new ParallelArraySearch(array, "This is some object.", 0, array.length - 1);
        assertEquals(44, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    @Test
    public void whenArrayNotContainsNeededObject() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Object[] array = arrayForFullSearch();
        ParallelArraySearch parallelArraySearch =
                new ParallelArraySearch(array, "This is another object.", 0, array.length - 1);
        assertEquals(-1, (int) forkJoinPool.invoke(parallelArraySearch));
    }

    @Test
    public void whenArrayContainsSeveralNeededObjectsThenFindLastIndexOfAllThem() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Object[] array = arrayForFullSearch();
        array[11] = "This is some object.";
        array[22] = "This is some object.";
        array[33] = "This is some object.";
        array[55] = "This is some object.";
        array[104] = "This is some object.";
        ParallelArraySearch parallelArraySearch =
                new ParallelArraySearch(array, "This is some object.", 0, array.length - 1);
        assertEquals(104, (int) forkJoinPool.invoke(parallelArraySearch));
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
