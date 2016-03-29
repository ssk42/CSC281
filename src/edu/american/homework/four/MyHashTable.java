package edu.american.homework.four;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author knappa
 * @version 1.0
 */
public class MyHashTable<Key, Value> implements HashTable<Key, Value> {

    /**
     * inserts the key-value pair (key,value) into the hash table, overwriting any previous pair of the form (key,*) if
     * present
     *
     * @param key   the key
     * @param value the value
     */
    @Override
    public void put(Key key, Value value) {

    }

    /**
     * gets the Value corresponding to the Key, key
     *
     * @param key the key
     * @returns the associated value or null if no value is associated to key
     */
    @Override
    public Value get(Key key) {
        return null;
    }

    /**
     * determines if the hash table contains a key-value pair with Key equal to key
     *
     * @param key the key to test for
     * @returns true if such a key-value pair is present, false otherwise
     */
    @Override
    public boolean containsKey(Key key) {
        return false;
    }
}
