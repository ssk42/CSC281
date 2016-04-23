package edu.american.homework.four;

import com.sun.javaws.exceptions.InvalidArgumentException;

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

    // testing code
    public static void main(String[] args) {
        HashTable<Integer, String> hashTable = new MyHashTable<>();

        // insert some key/value pairs, enough to trigger an array resize
        for (int i = 0; i < 32; i++) {
            System.out.println("inserting i=" + i);
            hashTable.put(i, String.valueOf(i));
        }

        // get and print the values
        for (int i = 0; i < 32; i++) {
            System.out.println(hashTable.get(i));
        }

    }

    public Key[] getKeyArray() {
        Key[] keyCopy = (Key[]) new Object[keys.length];
        for (int i = 0; i < keys.length; i++) {
            keyCopy[i] = (Key) keys[i];
        }
        return keyCopy;
    }

    public Value[] getValueArray() {
        Value[] valueCopy = (Value[]) new Object[values.length];
        for (int i = 0; i < values.length; i++) {
            valueCopy[i] = (Value) values[i];
        }
        return valueCopy;
    }

    @Override
    public void put(Key key, Value value) {
        if(key == null) throw new IllegalArgumentException();
        //System.out.println("key="+key+" value="+value);
        if (numberStored >= N) resizeArray();
        int index = ((key.hashCode() % N) + N) % N;
        // stop searching forward when we either reach an empty space or
        // if we reach an already existing insertion of this key
        while (keys[index] != null && !keys[index].equals(key))
            index = (index + 1) % N;
        keys[index] = key;
        values[index] = value;
        numberStored++;
    }

    @Override
    public Value get(Key key) {
        int baseIndex = ((key.hashCode() % N) + N) % N;
        for (int i = 0; i < N; i++) {
            if (keys[(baseIndex + i) % N] == null) {
                return null;
            } else if (keys[(baseIndex + i) % N].equals(key)) {
                return values[(baseIndex + i) % N];
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Key key) {
        return this.get(key) != null;
    }

    private void resizeArray() {
        //System.out.println("resizing array");
        N *= 2;
        Key[] oldKeys = keys;
        Value[] oldValues = values;
        keys = (Key[]) new Object[N];
        values = (Value[]) new Object[N];
        numberStored = 0;
        for (int i = 0; i < oldKeys.length; i++)
            if (oldKeys[i] != null)
                this.put(oldKeys[i], oldValues[i]);
    }
}
