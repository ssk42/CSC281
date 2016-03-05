package edu.american.stacks;

/**
 * Created by knappa on 2/11/16.
 */
public interface StackOfInts {

    /**
     * @param i integer to push onto the stack
     */
    void push(int i);

    /**
     * @return the integer on the top of the stack
     */
    int pop() throws StackEmptyException;

    boolean isEmpty();

}
