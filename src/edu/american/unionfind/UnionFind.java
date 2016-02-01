package edu.american.unionfind;

/**
 * @author knappa
 * @version 1.0
 */
public abstract class UnionFind {

    protected final int size;
    protected final int[] id;

    /**
     * initialize UnionFind structure
     *
     * @param size number of objects
     */
    public UnionFind(int size) {
        this.size = size;
        id = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }

    /**
     * Join the specified objects
     *
     * @param p id of first object
     * @param q id of second object
     */
    public abstract void union(int p, int q);

    /**
     * determine if the specified objects are connected
     *
     * @param p id of first object
     * @param q id of second object
     * @return true if connected
     */
    public abstract boolean connected(int p, int q);

    /**
     * Counts the number of components
     *
     * @return number of components
     */
    public int count() {

        int componentCount = 0;
        int componentIdList[] = new int[size]; // init to all zeros

        for (int i = 0; i < size; i++) {
            // check to see if component of id==i is in an already found new component
            boolean inAlreadyFoundComponent = false;
            for (int j = 0; !inAlreadyFoundComponent && j < componentCount; j++)
                if (find(i) == componentIdList[j]) inAlreadyFoundComponent = true;

            // if it's new, add it to the list
            if (!inAlreadyFoundComponent) {
                componentIdList[componentCount] = find(i);
                componentCount++;
            }
        }

        return componentCount;
    }

    /**
     * Finds the id of the component containing p
     *
     * @param p id of an object
     * @return id of component containing p
     */
    public abstract int find(int p);

}
