package com.mycompany.app;

import java.util.*;
import java.util.function.Predicate;

/**
 * Generic dynamic array implementation
 * Demonstrates advanced data structure implementation
 */
public class DynamicArray<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5;
    
    private Object[] elements;
    private int size;
    private int capacity;
    
    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }
    
    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative");
        }
        this.capacity = Math.max(initialCapacity, DEFAULT_CAPACITY);
        this.elements = new Object[capacity];
        this.size = 0;
    }
    
    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }
    
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }
    
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }
    
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        @SuppressWarnings("unchecked")
        T oldElement = (T) elements[index];
        elements[index] = element;
        return oldElement;
    }
    
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        @SuppressWarnings("unchecked")
        T removedElement = (T) elements[index];
        
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        
        elements[--size] = null; // Clear reference
        return removedElement;
    }
    
    public boolean remove(T element) {
        int index = indexOf(element);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }
    
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], element)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
    
    public DynamicArray<T> filter(Predicate<T> predicate) {
        DynamicArray<T> result = new DynamicArray<>();
        for (T element : this) {
            if (predicate.test(element)) {
                result.add(element);
            }
        }
        return result;
    }
    
    public List<T> toList() {
        List<T> list = new ArrayList<>(size);
        for (T element : this) {
            list.add(element);
        }
        return list;
    }
    
    private void ensureCapacity() {
        if (size >= capacity) {
            int newCapacity = (int) (capacity * GROWTH_FACTOR);
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
            capacity = newCapacity;
        }
    }
    
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<T> {
        private int currentIndex = 0;
        private int lastReturnedIndex = -1;
        
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
        
        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturnedIndex = currentIndex;
            return (T) elements[currentIndex++];
        }
        
        @Override
        public void remove() {
            if (lastReturnedIndex < 0) {
                throw new IllegalStateException();
            }
            
            DynamicArray.this.remove(lastReturnedIndex);
            currentIndex = lastReturnedIndex;
            lastReturnedIndex = -1;
        }
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
