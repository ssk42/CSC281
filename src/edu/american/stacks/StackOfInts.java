package edu.american.stacks;

/**
 * Created by knappa on 2/11/16.
 */
public interface StackOfInts {

    /**
     *
     * @param i integer to push onto the stack
     */
    public void push(int i);

    /**
     *
     * @return the integer on the top of the stack
     */
    public int pop() throws StackEmptyException;

    public boolean isEmpty();

}