package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ParallelArraySearch extends RecursiveTask<Integer> {

    private final Object[] array;
    private final Object someObject;

    public ParallelArraySearch(Object[] array, Object someObject) {
        this.array = array;
        this.someObject = someObject;
    }

    @Override
    protected Integer compute() {
        if (this.array.length < 11) {
            return linearSearch(this.array, this.someObject);
        }
        int mid = this.array.length / 2;
        ParallelArraySearch leftHalfOfArray =
                new ParallelArraySearch(Arrays.copyOfRange(this.array, 0, mid), this.someObject);
        ParallelArraySearch rightHalfOfArray =
                new ParallelArraySearch(Arrays.copyOfRange(this.array, mid, this.array.length), this.someObject);
        leftHalfOfArray.fork();
        rightHalfOfArray.fork();
        int leftIndexOfSomeObject = leftHalfOfArray.join();
        int rightIndexOfSomeObject = rightHalfOfArray.join();
        if (leftIndexOfSomeObject != -1) {
            return leftIndexOfSomeObject;
        } else if (rightIndexOfSomeObject != -1) {
            return mid + rightIndexOfSomeObject;
        }
        return -1;
    }

    private int linearSearch(Object[] array, Object someObject) {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(someObject)) {
                return i;
            }
        }
        return result;
    }
}
