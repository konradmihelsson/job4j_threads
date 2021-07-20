package ru.job4j.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        assertThat(cache.add(model1), is(true));
    }

    @Test
    public void whenAddNewVersionOfSameModel() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(1, 2);
        cache.add(model1);
        assertThat(cache.add(model2), is(false));
    }

    @Test
    public void whenDeleteModel() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        cache.add(model1);
        cache.delete(model1);
        assertThat(cache.add(model1), is(true));
    }

    @Test
    public void whenUpdateSuccessfully() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(1, 1);
        cache.add(model1);
        assertThat(cache.update(model2), is(true));
    }

    @Test
    public void whenUpdateNotAvailableElement() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(2, 1);
        cache.add(model1);
        assertThat(cache.update(model2), is(false));
    }

    @Test (expected = OptimisticException.class)
    public void whenUpdateElementWithAnotherVersionThenThrowException() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(1, 2);
        cache.add(model1);
        cache.update(model2);
    }
}
