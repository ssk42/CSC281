package edu.american.homework.four;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author knappa
 * @version 1.0
 */
public class MyHashTable<Key, Value> implements HashTable<Key, Value> {

    private int N;
    private int numberStored;
    private Key[] keys;
    private Value[] values;

    public MyHashTable() {
        N = 16;
        numberStored = 0;
        keys = (Key[]) new Object[N];
        values = (Value[]) new Object[N];
    }

    @Override
    public synchronized void put(Key key, Value value) {
        if (numberStored == N) resizeArray();
        int index = key.hashCode() % N;
        while (keys[index] != null)
            index = (index + 1) % N;
        keys[index] = key;
        values[index] = value;
    }

    @Override
    public synchronized Value get(Key key) {
        int baseIndex = key.hashCode() % N;
        for (int i = 0; i < N; i++) {
            if (keys[(baseIndex + i) % N] == null) {
                return null;
            } else if (keys[(baseIndex + i) % N].equals(key)) {
                Value retVel = values[(baseIndex + i) % N];
                return retVel;
            }
        }
        return null;
    }

    @Override
    public synchronized boolean containsKey(Key key) {
        boolean contains = this.get(key) != null;
        return contains;
    }

    private void resizeArray() {
        N *= 2;
        Key[] oldKeys = keys;
        Value[] oldValues = values;
        keys = (Key[]) new Object[N];
        values = (Value[]) new Object[N];
        for (int i = 0; i < keys.length; i++)
            this.put(oldKeys[i], oldValues[i]);
    }
}
