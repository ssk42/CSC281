package edu.american.stacks;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author knappa
 * @version 1.0
 */
public class ArrayBackedStack<Item> implements Stack<Item> {

    private int size;
    private int top;
    private Item[] stackArray;

    public ArrayBackedStack(int size) {
        this.size = size;
        //noinspection unchecked
        stackArray = (Item[]) new Object[size];
        top = -1;
    }

    /**
     * for testing
     *
     * @param args unused
     */
    public static void main(String[] args) {

        Stack<Integer> stack =
                new ArrayBackedStack<Integer>(10);

        stack.push(7);
        stack.push(-6);
        stack.push(4);

        for (int i = 0; i < 10; i++) {
            try {
                stack.push(i);
            } catch (StackOverflowError e) {
                System.out.println("Couldn't push "+i+" onto the stack.");
            }
        }


        try {
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
        } catch (StackEmptyException e) {
            System.out.println("Tried to pop when the stack was empty");
        }
    }

    @Override
    public void push(Item i) throws StackOverflowError {

        if (top == size-1) throw new StackOverflowError();

        top++;
        stackArray[top] = i;
    }

    @Override
    public Item pop() throws StackEmptyException {

        if (isEmpty()) throw new StackEmptyException();
        int oldTop = top;
        top--;
        return stackArray[oldTop];
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public String toString() {
        String retVal = "";
        for (int i = 0; i <= top; i++) {
            retVal += "("+stackArray[i]+")";
        }
        return retVal;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Item> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<Item> {

        int next;

        public StackIterator() {
            next = top;
        }

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
         * #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return next != -1;
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

            next--;

            return stackArray[next+1];
        }
    }


}
