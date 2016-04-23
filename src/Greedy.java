import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;

/**
 * @author knappa
 */
public class Greedy extends GraphDisplay {

    public Greedy() {
        super();
    }

    public static void main(String[] args) {
        Greedy greedy = new Greedy();
        JFrame frame = new JFrame();
        frame.add(greedy);
        frame.setTitle("Greedy Algorithm");
        frame.setSize(300, 300);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void run() {
        HashSet<Integer> colored = new HashSet<>();
        UnionFind components = new WeightedQuickUnionPathCompression(numVert);

        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setColor(Color.RED);

        colored.add(0);
        g2.fillOval(
                (int) points[0].x - pointRadius,
                (int) points[0].y - pointRadius,
                2 * pointRadius, 2 * pointRadius);

        g2.setStroke(new BasicStroke(4));

        while (colored.size() < numVert) {

            // find min exiting edge
            int minSource = -1, minTarget = -1;
            double minDist = Double.POSITIVE_INFINITY;
            for (int source : colored) {
                for (int target : graph.getNeighbors(source)) {
                    if (!colored.contains(target)) {
                        double dist = points[source].distance(points[target]);
                        if (dist < minDist) {
                            minSource = source;
                            minTarget = target;
                            minDist = dist;
                        }
                    }
                }
            }

            // should not happen
            if (minSource == -1) return;

            components.union(minSource, minTarget);
            colored.add(minTarget);

            try {Thread.sleep(100);} catch (InterruptedException ignored) { }

            g2.fillOval(
                    (int) points[minTarget].x - pointRadius,
                    (int) points[minTarget].y - pointRadius,
                    2 * pointRadius, 2 * pointRadius);
            g2.drawLine(
                    (int) points[minSource].x, (int) points[minSource].y,
                    (int) points[minTarget].x, (int) points[minTarget].y);
            repaint();
        }
    }


}
