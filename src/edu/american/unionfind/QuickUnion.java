package edu.american.unionfind;

/**
 * @author knappa
 * @version 1.0
 */
public class QuickUnion extends UnionFind {

    /**
     * initialize UnionFind structure
     *
     * @param size number of objects
     */
    public QuickUnion(int size) {
        super(size);
    }


    public static void main(String[] args) {
        (new MonteCarloTester()).monteCarlo(QuickUnion.class, false, 50);
    }

    @Override
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // set root of q to be a child of the root of p
        id[rootQ] = rootP;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * @param p id of an object
     * @return root of p in tree
     */
    @Override
    public int find(int p) {
        while (p != id[p]) p = id[p];
        return p;
    }

}
