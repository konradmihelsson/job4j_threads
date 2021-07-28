package ru.job4j.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {

        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums();
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i].setRowSum(result[i].getRowSum() + matrix[i][j]);
                result[j].setColSum(result[j].getColSum() + matrix[i][j]);
            }
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws InterruptedException, ExecutionException {
        Sums[] result = new Sums[matrix.length];
        List<CompletableFuture<Sums>> asyncResult = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            asyncResult.add(i, getSums(matrix, i));
        }
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums();
            Sums sums = asyncResult.get(i).get();
            result[i].setRowSum(sums.getRowSum());
            result[i].setColSum(sums.getColSum());
        }
        return result;
    }

    private static CompletableFuture<Sums> getSums(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Sums result = new Sums();
                    for (int i = 0; i < matrix.length; i++) {
                        result.setRowSum(result.getRowSum() + matrix[index][i]);
                        result.setColSum(result.getColSum() + matrix[i][index]);
                    }
                    return result;
                }
        );
    }
}
