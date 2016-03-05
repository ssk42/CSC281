package edu.american.queues;

/**
 * @author knappa
 * @version 1.0
 */
public interface Queue<Item> extends Iterable<Item> {

    /**
     * @param item item to place at the end of the queue
     */
    void enqueue(Item item);

    /**
     * @return item at the front of the queue
     */
    Item dequeue();

    /**
     * @return true if the queue is empty
     */
    boolean isEmpty();

}
