/*
 * Copyright 2011 Ian D. Bollinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.celeria.minecraft.blather;

import java.util.AbstractList;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Ian D. Bollinger
 */
public class OverwritingRingBuffer<E> extends AbstractList<E> implements
        Deque<E> {
    private E[] elements;
    private int first;
    private int last = 1;
    private int numberOfElements;

    @SuppressWarnings("unchecked")
    OverwritingRingBuffer(final int capacity) {
        elements = (E[]) new Object[capacity + 1];
    }

    /**
     * @param capacity
     * @return
     */
    public static <T> OverwritingRingBuffer<T> create(final int capacity) {
        return new OverwritingRingBuffer<T>(capacity);
    }

    /**
     * @return
     */
    private boolean isFull() {
        return size() == elements.length;
    }

    @Override
    public boolean add(final E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(final E element) {
        final int capacity = elements.length;
        if (isFull()) {
            last = (capacity + last - 1) % capacity;
        } else {
            ++numberOfElements;
        }
        elements[first] = element;
        first = (capacity + first - 1) % capacity;
    }

    @Override
    public void addLast(final E element) {
        final int capacity = elements.length;
        if (isFull()) {
            first = (first + 1) % capacity;
        } else {
            ++numberOfElements;
        }
        elements[last] = element;
        last = (last + 1) % capacity;
    }

    @Override
    public void clear() {
        last = first + 1;
        numberOfElements = 0;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new ReverseIterator<E>(listIterator());
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E get(final int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return elements[(first + index + 1) % elements.length];
    }

    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements[first + 1];
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements[last - 1];
    }

    @Override
    public boolean offer(final E element) {
        return offerLast(element);
    }

    @Override
    public boolean offerFirst(final E element) {
        addFirst(element);
        return true;
    }

    @Override
    public boolean offerLast(final E element) {
        addLast(element);
        return true;
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public E peekFirst() {
        return isEmpty() ? null : elements[first + 1];
    }

    @Override
    public E peekLast() {
        return isEmpty() ? null : elements[last - 1];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        try {
            return removeFirst();
        } catch (final NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public E pollLast() {
        try {
            return removeLast();
        } catch (final NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public void push(final E element) {
        addFirst(element);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        --numberOfElements;
        final E result = elements[first];
        first = (first + 1) % elements.length;
        return result;
    }

    @Override
    public boolean removeFirstOccurrence(
            @SuppressWarnings("unused") final Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        --numberOfElements;
        final E result = elements[last];
        final int capacity = elements.length;
        last = (capacity + last - 1) % capacity;
        return result;
    }

    @Override
    public boolean removeLastOccurrence(
            @SuppressWarnings("unused") final Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(final int index, final E element) {
        final E oldElement = get(index);
        elements[(first + index + 1) % elements.length] = element;
        return oldElement;
    }

    @Override
    public int size() {
        return numberOfElements;
    }
}
