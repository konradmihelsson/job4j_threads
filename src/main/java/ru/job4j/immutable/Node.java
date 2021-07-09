package ru.job4j.immutable;

public final class Node<T> {
    private final T value;
    private final Node<T> next;

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = new Node<>(next.getValue(), next.getNext());
    }

    public Node<T> getNext() {
        return new Node<>(this.next.getValue(), this.next.getNext());
    }

    public T getValue() {
        return this.value;
    }
}
