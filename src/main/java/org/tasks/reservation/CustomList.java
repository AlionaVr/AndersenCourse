package org.tasks.reservation;

import java.util.Arrays;
import java.util.Iterator;


public class CustomList<T> implements Iterable<T> {
    private T[] data;
    private int size;
    private int capacity;

    public CustomList() {
        this.capacity = 10;
        this.data = (T[]) new Object[capacity];
        this.size = 0;
    }

    // add element at the end of list
    public void add(T element) {
        if (size == capacity) {
            resize();
        }
        data[size++] = element;
    }

    private void resize() {
        capacity *= 2;
        data = Arrays.copyOf(data, capacity);
    }

    public T get(int index) {
        checkIndex(index);
        return data[index];
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    protected T remove(int index) {
        checkIndex(index);
        T removedElement = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size--] = null;
        return removedElement;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                return data[currentIndex++];
            }
        };
    }
}