package ru.job4j.immutable;

public final class Node<T> {
    private final T value;
    private final Node<T> next;

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public T getValue() {
        return this.value;
    }
}
