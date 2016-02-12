package edu.american.stacks;

/**
 * @author knappa
 * @version 1.0
 */
public class ArrayBackedStackOfInts implements StackOfInts {

    private int size;
    private int top;
    private int[] stackArray;

    public ArrayBackedStackOfInts(int size) {
        this.size = size;
        stackArray = new int[size];
        top = -1;
    }

    /**
     * for testing
     *
     * @param args
     */
    public static void main(String[] args) {

        StackOfInts stack = new ArrayBackedStackOfInts(10);

        stack.push(7);
        stack.push(-6);
        stack.push(4);

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
    public void push(int i) throws StackOverflowError {

        if (top == size-1) throw new StackOverflowError();

        top++;
        stackArray[top] = i;
    }

    @Override
    public int pop() throws StackEmptyException {

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
