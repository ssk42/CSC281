package edu.american.homework.three;

import edu.american.queues.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author TODO: add your name
 */
public class LinkedListBackedQueue<Item> implements Queue<Item> {

    public static void main(String[] args) {
        // TODO: test cases
    }

    @Override
    public void enqueue(Item item) {
        // TODO: implement this
    }

    @Override
    public Item dequeue() {
        // TODO: implement this
        return null;
    }

    @Override
    public boolean isEmpty() {
        // TODO: implement this
        return false;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Item> iterator() {
        // TODO: change if necessary
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link #next}
         * would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            // TODO: implement this
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();

            // TODO: implement this
            return null;
        }
    }

}
