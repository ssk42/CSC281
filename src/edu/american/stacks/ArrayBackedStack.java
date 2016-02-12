package edu.american.stacks;

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
        stackArray = (Item[]) new Object[size];
        top = -1;
    }

    /**
     * for testing
     *
     * @param args
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


}
