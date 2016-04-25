import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * @author knappa
 * @version 1.0
 */
public abstract class GraphDisplay extends JComponent implements Runnable {
    protected final int pointRadius = 4;
    protected final int numVert = 200;
    private final int xSize = 800, ySize = 600;
    protected final Graph graph;
    protected final BufferedImage bufferedImage;
    protected final Point2D.Float[] points;

    public GraphDisplay() {
        Dimension size = new Dimension(xSize, ySize);
        setPreferredSize(size);

        // distribute points
        points = new Point2D.Float[numVert];
        for (int i = 0; i < numVert; i++)
            points[i] = new Point2D.Float(
                    (float) (xSize * Math.random()),
                    (float) (ySize * Math.random()));

        graph = Graph.getRandomConnectedGraph(points);

        // initialize image
        bufferedImage = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, xSize, ySize);
        g2.setColor(Color.BLACK);
        for (int i = 0; i < numVert; i++) {
            g2.fillOval(
                    (int) points[i].x - pointRadius,
                    (int) points[i].y - pointRadius,
                    2 * pointRadius, 2 * pointRadius);
        }
        for (int i = 0; i < numVert; i++) {
            for (int nbr : graph.getNeighbors(i)) {
                if (nbr > i) // prevent double drawing
                    g2.drawLine(
                            (int) points[i].x, (int) points[i].y,
                            (int) points[nbr].x, (int) points[nbr].y);

            }

        }

        // start the algorithm
        new Thread(this).start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(bufferedImage, 0, 0, null);
    }


}
