package edu.american.homework.four;

/**
 * Created by knappa on 3/24/16.
 */
public interface HashTable<Key, Value> {

    /**
     * inserts the key-value pair (key,value) into the hash table, overwriting any previous pair of the form (key,*) if
     * present
     *
     * @param key   the key
     * @param value the value
     */
    void put(Key key, Value value);


    /**
     * gets the Value corresponding to the Key, key
     *
     * @param key the key
     * @returns the associated value or null if no value is associated to key
     */
    Value get(Key key);

    /**
     * determines if the hash table contains a key-value pair with Key equal to key
     *
     * @param key the key to test for
     * @returns true if such a key-value pair is present, false otherwise
     */
    boolean containsKey(Key key);
}
