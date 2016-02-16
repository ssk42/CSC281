package edu.american.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public class ArrayBackedQueue<Item> implements Queue<Item> {

    final int size;
    Item[] queueArray;
    int head, tail;
    int numStored;

    public ArrayBackedQueue(int size) {

        this.size = size;
        //noinspection unchecked
        queueArray = (Item[]) new Object[size];

        head = 0;
        tail = 0;
        numStored = 0;
    }

    @Override
    public void enqueue(Item item) {

        if (isFull()) throw new IllegalStateException();

        queueArray[tail] = item;

        tail = (tail+1)%size;

        numStored++;

    }

    @Override
    public Item dequeue() {

        if (isEmpty()) return null;

        int oldHead = head;

        head = (head+1)%size;

        numStored--;

        return queueArray[oldHead];
    }

    @Override
    public boolean isEmpty() {
        return numStored == 0;
    }

    public boolean isFull() {
        return numStored == size;
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        int next;
        int numberOfIterables;

        public QueueIterator() {
            next = head;
            numberOfIterables = numStored;
        }

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
         * #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return numberOfIterables != 0;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item next() {

            if (!hasNext()) throw new NoSuchElementException();

            int oldNext = next;
            next = (next+1)%size;
            numberOfIterables--;
            return queueArray[oldNext];
        }
    }

}
