package org.tasks.reservation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;


public class CustomList<T> implements Iterable<T>, Serializable {
    private T[] data;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    // Java can't verify the safety of this cast. So we use this annotation and
    // take responsibility for this operation.
    public CustomList() {
        this.capacity = 10;
        this.data = (T[]) new Object[capacity];
        this.size = 0;
    }

    // check the need of resizing and add element at the end
    protected void add(T element) {
        if (size == capacity) {
            resize();
        }
        data[size++] = element;
    }

    @SafeVarargs
    //the method contains a variable number of arguments (varargs).
    //In some cases, a warning may be issued when compiling a method with varargs.
    protected final void addAll(T... elements) {
        for (T element : elements) {
            add(element);
        }
    }

    private void resize() {
        capacity *= 2;
        data = Arrays.copyOf(data, capacity);
    }

    protected T get(int index) {
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

    protected int size() {
        return size;
    }

    protected boolean isNotEmpty() {
        return size > 0;
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

    protected Stream<T> stream() {
        return Arrays.stream(data, 0, size);
    }

}