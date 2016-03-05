package edu.american.stacks;

/**
 * @author knappa
 * @version 1.0
 */
public interface Stack<Item> extends Iterable<Item> {

    /**
     * @param i integer to push onto the stack
     */
    void push(Item i);

    /**
     * @return the integer on the top of the stack
     */
    Item pop() throws StackEmptyException;

    boolean isEmpty();


}
