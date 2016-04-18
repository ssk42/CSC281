import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * @author knappa
 * @version 1.0
 */
public class Krushkal extends GraphDisplay {

    public Krushkal() {
        super();
    }

    public static void main(String[] args) {
        Krushkal krushkal = new Krushkal();
        JFrame frame = new JFrame();
        frame.add(krushkal);
        frame.setTitle("Krushkal's Algorithm");
        frame.setSize(300, 300);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void run() {

        // collect edges
        ArrayList<Integer> sources = new ArrayList<>();
        ArrayList<Integer> targets = new ArrayList<>();
        ArrayList<Double> lengths = new ArrayList<>();
        for (int i = 0; i < numVert; i++) {
            for (int neighbor : graph.getNeighbors(i)) {
                if (neighbor > i) {
                    sources.add(i);
                    targets.add(neighbor);
                    lengths.add(points[i].distance(points[neighbor]));
                }
            }
        }

        // sort edges
        int temp;
        double tempD;
        for (int i = lengths.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (lengths.get(j) > lengths.get(j + 1)) {
                    tempD = lengths.get(j);
                    lengths.set(j, lengths.get(j + 1));
                    lengths.set(j + 1, tempD);

                    temp = sources.get(j);
                    sources.set(j, sources.get(j + 1));
                    sources.set(j + 1, temp);

                    temp = targets.get(j);
                    targets.set(j, targets.get(j + 1));
                    targets.set(j + 1, temp);
                }
            }
        }

        UnionFind components = new WeightedQuickUnionPathCompression(numVert);

        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(4));

        for (int i = 0; i < lengths.size(); i++) {
            int source = sources.get(i);
            int target = targets.get(i);
            if (!components.connected(source, target)) {
                components.union(source, target);

                try {Thread.sleep(200);} catch (InterruptedException ignored) { }

                g2.fillOval(
                        (int) points[source].x - pointRadius,
                        (int) points[source].y - pointRadius,
                        2 * pointRadius, 2 * pointRadius);
                g2.fillOval(
                        (int) points[target].x - pointRadius,
                        (int) points[target].y - pointRadius,
                        2 * pointRadius, 2 * pointRadius);
                g2.drawLine(
                        (int) points[source].x, (int) points[source].y,
                        (int) points[target].x, (int) points[target].y);
                repaint();

            }
        }

    }
}
