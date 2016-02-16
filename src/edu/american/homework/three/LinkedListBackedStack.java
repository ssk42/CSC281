package edu.american.homework.three;

import edu.american.stacks.Stack;
import edu.american.stacks.StackEmptyException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author TODO: add your name
 */
public class LinkedListBackedStack<Item> implements Stack<Item> {

    public static void main(String[] args) {
        // TODO: add test code
    }

    public LinkedListBackedStack() {
        // TODO: implement this
    }

    @Override
    public void push(Item i) {
        // TODO: implement this
    }

    @Override
    public Item pop() throws StackEmptyException {
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
        return new StackOIterator();
    }

    private class StackOIterator implements Iterator<Item> {

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
