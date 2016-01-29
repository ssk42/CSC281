package edu.american.unionfind;

/**
 * @author knappa
 * @version 1.0
 */
public class WeightedQuickUnionPathCompression extends WeightedQuickUnion {

    /**
     * initialize UnionFind structure
     *
     * @param size number of objects
     */
    public WeightedQuickUnionPathCompression(int size) {
        super(size);
    }

    public static void main(String[] args) {
        (new MonteCarloTester()).monteCarlo(WeightedQuickUnionPathCompression.class, false, 50);
    }

    @Override
    public int find(int p) {
        while (p != id[p]) {
            /**
             * if we cared about the meaning of childCount at places other than the root,
             * the following code would be necessary
             *
             * int parent = id[p];
             * int grandparent = id[parent];
             * if(parent != grandparent) childCount[parent] -= childCount[p];
             */
            id[p] = id[id[p]]; // p's parent is now its grandparent
            p = id[p];
        }
        return p;
    }

}
