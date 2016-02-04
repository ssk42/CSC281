package edu.american.unionfind;

/**
 * @author knappa
 * @version 1.0
 */
public class QuickFind extends UnionFind {

    /**
     * initialize UnionFind structure
     *
     * @param size number of objects
     */
    public QuickFind(int size) {
        super(size);
    }

    @Override
    public void union(int p, int q) {

        if (this.connected(p, q)) return;

        /* store id[q] in qID as it will be overwritten in the following loop */
        int qID = id[q];

        /* change all elements of id with value qID to id[p] */
        for (int i = 0; i < size; i++) {
            if (id[i] == qID) id[i] = id[p];
        }

    }

    @Override
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    public static void main(String[] args) {
        (new MonteCarloTester()).monteCarlo(QuickFind.class, false, 1);
    }

}
