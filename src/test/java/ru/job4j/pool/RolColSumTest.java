package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RolColSumTest {

    @Test
    public void whenSerialMethod() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] expected = {new RolColSum.Sums(), new RolColSum.Sums(), new RolColSum.Sums()};
        expected[0].setRowSum(6);
        expected[0].setColSum(12);
        expected[1].setRowSum(15);
        expected[1].setColSum(15);
        expected[2].setRowSum(24);
        expected[2].setColSum(18);
        assertThat(expected, is(RolColSum.sum(matrix)));
    }

    @Test
    public void whenFutureMethod() throws InterruptedException, ExecutionException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] expected = {new RolColSum.Sums(), new RolColSum.Sums(), new RolColSum.Sums()};
        expected[0].setRowSum(6);
        expected[0].setColSum(12);
        expected[1].setRowSum(15);
        expected[1].setColSum(15);
        expected[2].setRowSum(24);
        expected[2].setColSum(18);
        assertThat(expected, is(RolColSum.asyncSum(matrix)));
    }
}
