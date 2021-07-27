package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

public class ParallelArraySearch extends RecursiveTask<Integer> {

    private final Object[] array;
    private final Object someObject;
    private final int indexFrom;
    private final int indexTo;

    public ParallelArraySearch(Object[] array, Object someObject, int indexFrom, int indexTo) {
        this.array = array;
        this.someObject = someObject;
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
    }

    @Override
    protected Integer compute() {
        int range = indexTo - indexFrom;
        if (range < 11) {
            return linearSearch(array, someObject, indexFrom, indexTo);
        }
        int indexOfMiddle = indexFrom + range / 2;
        ParallelArraySearch leftHalfOfArray =
                new ParallelArraySearch(array, someObject, indexFrom, indexOfMiddle);
        ParallelArraySearch rightHalfOfArray =
                new ParallelArraySearch(array, someObject, indexOfMiddle, indexTo);
        leftHalfOfArray.fork();
        rightHalfOfArray.fork();
        int leftIndexOfSomeObject = leftHalfOfArray.join();
        int rightIndexOfSomeObject = rightHalfOfArray.join();
        return Math.max(leftIndexOfSomeObject, rightIndexOfSomeObject);
    }

    private int linearSearch(Object[] array, Object someObject, int indexFrom, int indexTo) {
        int result = -1;
        for (int i = indexFrom; i <= indexTo; i++) {
            if (array[i].equals(someObject)) {
                return i;
            }
        }
        return result;
    }
}
