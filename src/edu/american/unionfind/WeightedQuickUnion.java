package edu.american.unionfind;

/**
 * @author knappa
 * @version 1.0
 */
public class WeightedQuickUnion extends QuickUnion {

    /**
     * childCount[i] stores the size of the tree rooted at i
     */
    protected final int[] childCount;

    /**
     * initialize UnionFind structure
     *
     * @param size number of objects
     */
    public WeightedQuickUnion(int size) {
        super(size);

        childCount = new int[size];
        for (int i = 0; i < size; i++) childCount[i] = 1;

    }

    public static void main(String[] args) {
        (new MonteCarloTester()).monteCarlo(WeightedQuickUnion.class, false, 50);
    }

    @Override
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        if (childCount[rootP] < childCount[rootQ]) {
            // smaller tree goes under the larger one
            id[rootP] = rootQ;
            childCount[rootQ] += childCount[rootP];
        } else { // childCount[rootP] >= childCount[rootQ]
            // smaller tree goes under the larger one
            // or, if equal, root of Q goes under root of P
            id[rootQ] = rootP;
            childCount[rootP] += childCount[rootQ];
        }
    }

}
