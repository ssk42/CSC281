package edu.american.stacks;

/**
 * Created by knappa on 2/11/16.
 */
public interface Stack<Item> {

    /**
     *
     * @param i integer to push onto the stack
     */
    public void push(Item i);

    /**
     *
     * @return the integer on the top of the stack
     */
    public Item pop() throws StackEmptyException;

    public boolean isEmpty();


}
