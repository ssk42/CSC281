package edu.american.unionfind;

import edu.american.util.Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author knappa
 * @version 1.0
 */
public class MonteCarloTester {

    static final int gridXSize = 320;
    static final int gridYSize = 240;
    static final int gridArea = gridXSize*gridYSize;
    static final int sourceID = gridArea;
    static final int sinkID = gridArea+1;
    static final int CLOSED = 0;
    static final int OPEN = 1;
    static final int CONNECTED_TO_SOURCE = 2;
    private int[][] openCells;

    /**
     *
     * @param classType a class which extends UnionFind
     * @param showUpdates display intermediary updates, or just final one
     * @param numRuns number of times to run the test
     */
    public void monteCarlo(Class classType, boolean showUpdates, int numRuns) {

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        for (int run = 0; run < numRuns; run++) {

            /***********************************************************/
            /** Here I am using the java reflections API to pass the  **/
            /** _type_ of the particular UnionFind given in classType **/
            /** then I pull out the constructor I wanted, and create  **/
            /** a new UnionType out of it. This is relatively deep    **/
            /** voodoo and probably should be avoided in practice.    **/
            /***********************************************************/
            UnionFind sourceSinkUnionFind = null;
            UnionFind sourceOnlyUnionFind = null;
            try {
                // the following is a comment to stop IntelliJ IDEA from complaining:
                //noinspection unchecked
                Constructor constructor = classType.getConstructor(int.class);
                sourceSinkUnionFind = (UnionFind) constructor.newInstance(2+gridArea);
                sourceOnlyUnionFind = (UnionFind) constructor.newInstance(2+gridArea);

            } catch (NoSuchMethodException | IllegalAccessException |
                    InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
                return;
            }
            /***********************************************************/

        /* keep track of the CLOSED, OPEN, and CONNECTED_TO_SOURCE cells */
            openCells = new int[gridXSize][gridYSize]; // inits to CLOSED=0

            // set up the display class
            Display display = new Display(openCells, sourceOnlyUnionFind);
            display.updateImage();

            // and a JFrame to put the display in
            JFrame frame = new JFrame();
            frame.setTitle("Monte Carlo Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(display);
            frame.setPreferredSize(frame.getPreferredSize());
            frame.pack();
            if (!showUpdates)
                frame.setVisible(false);
            else
                frame.setVisible(true);

            int numOpenCells = 0;
            boolean connected = false;

            for (int i = 0; !connected && i < gridArea; i++) {

                // find an available OPEN cell
                int x;
                int y;
                do {
                    x = (int) (Math.random()*gridXSize);
                    y = (int) (Math.random()*gridYSize);
                } while (openCells[x][y] != CLOSED);

                // open it up!
                openCells[x][y] = OPEN;
                numOpenCells++;

                // connect it to its open horizontal neighbors
                if (x+1 < gridXSize && open(x+1, y)) {
                    sourceSinkUnionFind.union(translate(x, y), translate(x+1, y));
                    sourceOnlyUnionFind.union(translate(x, y), translate(x+1, y));
                }

                if (x >= 1 && open(x-1, y)) {
                    sourceSinkUnionFind.union(translate(x, y), translate(x-1, y));
                    sourceOnlyUnionFind.union(translate(x, y), translate(x-1, y));
                }

                // connect it to its open vertical neighbors (inc. source and sink)
                if (y == 0) {
                    sourceSinkUnionFind.union(translate(x, y), sourceID);
                    sourceOnlyUnionFind.union(translate(x, y), sourceID);
                }

                if (y == gridYSize-1)
                    sourceSinkUnionFind.union(translate(x, y), sinkID);

                if (y+1 < gridYSize && open(x, y+1)) {
                    sourceSinkUnionFind.union(translate(x, y), translate(x, y+1));
                    sourceOnlyUnionFind.union(translate(x, y), translate(x, y+1));
                }

                if (y >= 1 && open(x, y-1)) {
                    sourceSinkUnionFind.union(translate(x, y), translate(x, y-1));
                    sourceOnlyUnionFind.union(translate(x, y), translate(x, y-1));
                }


                // see if the source and sink are connected
                connected = sourceSinkUnionFind.connected(sourceID, sinkID);

                // redraw the image
                if (showUpdates) display.updateImage();

            }

            frame.setVisible(true);
            display.updateImage();
            System.out.println("Connected with percentage open: "+(double) numOpenCells/gridArea);

        }

        stopwatch.stop();

        System.out.println("Elapsed Time (secs): "+stopwatch.getElapsedTimeSecs());


    }

    private boolean open(int x, int y) {
        return openCells[x][y] == OPEN || openCells[x][y] == CONNECTED_TO_SOURCE;
    }

    /**
     * translate x,y coords to the ID numbers for UnionFind
     *
     * @param x x coord. of cell
     * @param y y coord. of cell
     * @return UnionFind id number for this cell
     */
    public static int translate(int x, int y) {
        return gridXSize*y+x;
    }

    class Display extends JComponent {

        private final BufferedImage bufferedImage;
        private final UnionFind unionFind;
        int[][] openCells;

        public Display(int[][] openCells, UnionFind unionFind) {

            this.openCells = openCells;
            this.unionFind = unionFind;

            bufferedImage = new BufferedImage(gridXSize, gridYSize, BufferedImage.TYPE_INT_RGB);

        }

        /**
         * Redraw the image and update OPEN cells to ones CONNECTED_TO_SOURCE as appropriate
         */
        public void updateImage() {
            for (int i = 0; i < gridXSize; i++) {
                for (int j = 0; j < gridYSize; j++) {
                    if (openCells[i][j] == CLOSED) {
                        bufferedImage.setRGB(i, j, Color.BLACK.getRGB());
                    } else if (openCells[i][j] == OPEN) {
                        // check if a OPEN is now connected to the source
                        if (unionFind.connected(sourceID, translate(i, j))) {
                            openCells[i][j] = CONNECTED_TO_SOURCE;
                        } else
                            bufferedImage.setRGB(i, j, Color.WHITE.getRGB());
                    }

                    if (openCells[i][j] == CONNECTED_TO_SOURCE)
                        bufferedImage.setRGB(i, j, Color.BLUE.getRGB());
                }
            }
            this.repaint();
        }

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D graphics2D = (Graphics2D) graphics;

            graphics2D.drawImage(bufferedImage, 0, 0, null);
        }

        /**
         * @return a new Dimension which corresponds to the size of the image we contain
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(gridXSize, gridYSize);
        }
    }

}
