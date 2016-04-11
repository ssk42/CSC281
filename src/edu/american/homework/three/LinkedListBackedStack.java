package edu.american.homework.three;

import edu.american.stacks.Stack;
import edu.american.stacks.StackEmptyException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author knappa
 * @version 1.0
 */
public class LinkedListBackedStack<Item> implements Stack<Item> {

    private class ListNode {
        Item data;
        ListNode next;
    }

    private ListNode top;

    public static void main(String[] args) {
        // TODO: add test code
    }

    public LinkedListBackedStack() {
        // no-op
    }

    @Override
    public void push(Item i) {
        ListNode newTop = new ListNode();
        newTop.data = i;
        newTop.next = top;
        top = newTop;
    }

    @Override
    public Item pop() throws StackEmptyException {

        if (isEmpty()) throw new StackEmptyException();

        ListNode oldTop = top;
        top = top.next;
        return oldTop.data;

    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Item> iterator() {
        return new StackOIterator();
    }

    private class StackOIterator implements Iterator<Item> {

        ListNode current;

        public StackOIterator() {
            current = top;
        }

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link #next}
         * would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
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

            ListNode oldCurrent = current;
            current = current.next;

            return oldCurrent.data;
        }
    }

}
