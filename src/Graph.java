import java.awt.geom.Point2D;
import java.util.HashSet;

/**
 * @author knappa
 * @version 1.0
 */
public class Graph {

    private final int numV;
    private final HashSet[] adjacencyList;

    public Graph(int numVertices) {
        numV = numVertices;
        adjacencyList = new HashSet[numV];
    }

    public static Graph getRandomConnectedGraph(Point2D[] points) {
        if (points == null) throw new IllegalArgumentException();
        int numVertices = points.length;

        Graph graph = new Graph(numVertices);

        UnionFind uf = new WeightedQuickUnionPathCompression(numVertices);

        int v1, v2;
        double minDist = 10.0;
        while (uf.count() > 1) {

            for (int i = 0; i < 1000 && uf.count() > 1; i++) {

                v1 = (int) (numVertices * Math.random());
                v2 = (int) (numVertices * Math.random());

                if (v1 != v2) {
                    double dist = points[v1].distance(points[v2]);

                    if (dist < minDist && !graph.containsEdge(v1, v2)) {
                        graph.addEdge(v1, v2);
                        uf.union(v1, v2);
                    }
                }
            }
            minDist *= 1.1;
        }

        return graph;
    }

    public void addEdge(int v1, int v2) {

        // check for validity
        if (v1 < 0 || v2 < 0 || v1 >= numV || v2 >= numV || v1 == v2)
            throw new IllegalArgumentException();

        // no-op if edge already present
        if (containsEdge(v1, v2)) return;

        if (adjacencyList[v1] == null)
            adjacencyList[v1] = new HashSet<Integer>();

        adjacencyList[v1].add(v2);

        if (adjacencyList[v2] == null)
            adjacencyList[v2] = new HashSet<Integer>();

        adjacencyList[v2].add(v1);
    }

    public boolean containsEdge(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= numV || v2 >= numV)
            throw new IllegalArgumentException();

        return adjacencyList[v1] != null && adjacencyList[v1].contains(v2);
    }

    public static Graph getRandomGraph(Point2D[] points, int numEdges) {
        if (points == null) throw new IllegalArgumentException();
        int numVertices = points.length;

        if (numEdges < 0)
            throw new IllegalArgumentException();

        // exception on impossible number of edges
        if (numEdges > numVertices * (numVertices - 1) / 2)
            throw new IllegalArgumentException("too many edges for that many vertices");

        Graph graph = new Graph(numVertices);

        int numEdgesPlaced = 0;
        int v1, v2;
        double minDist = 1.0;
        while (numEdgesPlaced < numEdges) {

            v1 = (int) (numVertices * Math.random());
            v2 = (int) (numVertices * Math.random());

            if (v1 != v2) {
                double dist = points[v1].distance(points[v2]);

                if (dist < minDist) {
                    graph.addEdge(v1, v2);
                    numEdgesPlaced++;
                } else
                    minDist += 0.1;
            }
        }

        return graph;
    }

    public static Graph getRandomGraph(int numVertices, int numEdges) {
        Graph graph = new Graph(numVertices);

        if (numVertices < 0 || numEdges < 0)
            throw new IllegalArgumentException();

        // exception on impossible number of edges
        if (numEdges > numVertices * (numVertices - 1) / 2)
            throw new IllegalArgumentException("too many edges for that many vertices");

        int v1, v2;
        for (int i = 0; i < numEdges; i++) {
            do {
                v1 = (int) (numVertices * Math.random());
                v2 = (int) (numVertices * Math.random());
            } while (v1 == v2 || graph.containsEdge(v1, v2));
            graph.addEdge(v1, v2);
        }

        return graph;
    }

    public Iterable<Integer> getNeighbors(int v) {
        if (v < 0 || v >= numV) throw new IllegalArgumentException();
        else if (adjacencyList[v] != null)
            return adjacencyList[v];
        else return new HashSet<Integer>(0);
    }
}
