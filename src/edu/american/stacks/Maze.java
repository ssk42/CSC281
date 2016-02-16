package edu.american.stacks;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author knappa
 * @version 1.0
 */
public class Maze extends JFrame implements ActionListener {

    private static final int MAX_COUNT = 50;
    private static final String TITLE_STRING = "Maze";
    private static final int TIMER_FREQ = 10;
    public static int EMPTY = 0, WALL = 1, ROBOT = 2, TREASURE = 4;
    private static String ROBOT_IMAGE_LOCATION = "res/robot.png";
    private static String WALL_IMAGE_LOCATION = "res/wall.png";
    private static String TREASURE_IMAGE_LOCATION = "res/treasure.png";
    private final int rows, columns;
    private final DisplayPanel displayPanel;
    private final Timer timer;
    private BufferedImage robotImage, wallImage, treasureImage;
    private int SQ_SIZE;
    private int count;
    private int[][] maze;
    private boolean movingRobot = false;
    private int robotRow = -1, robotCol = -1;
    private int sourceRobotRow = -1, sourceRobotCol = -1;
    private int visitingRow, visitingCol;
    private boolean highlight = false;

    /**
     * Constructor
     *
     * @param rows    number of rows in the maze
     * @param columns number of columns in the maze
     */
    public Maze(int rows, int columns) {

        this.rows = rows;
        this.columns = columns;
        maze = new int[rows][columns]; // init to empty

        // load the robot's image and check if it's square
        try {
            robotImage = ImageIO.read(new File(ROBOT_IMAGE_LOCATION));
            SQ_SIZE = robotImage.getWidth();
            if (SQ_SIZE != robotImage.getHeight()) {
                System.err.println("Robot image not square!");
                System.exit(-1);
            }
        } catch (IOException ignored) {
            System.err.println("Robot image not found");
            System.exit(-1);
        }

        // load the wall's image and check if it's square & the size of the robot's image
        try {
            wallImage = ImageIO.read(new File(WALL_IMAGE_LOCATION));
            if (wallImage.getWidth() != SQ_SIZE) {
                System.err.println("Wall image wrong width");
                System.exit(-1);
            } else if (wallImage.getWidth() != wallImage.getHeight()) {
                System.err.println("Wall image not square");
                System.exit(-1);
            }
        } catch (IOException ignored) {
            System.err.println("Wall image not found");
            System.exit(-1);
        }

        // load the treasure's image and check if it's square & the size of the robot's image
        try {
            treasureImage = ImageIO.read(new File(TREASURE_IMAGE_LOCATION));
            if (treasureImage.getWidth() != SQ_SIZE) {
                System.err.println("Treasure image wrong width");
                System.exit(-1);
            } else if (treasureImage.getWidth() != treasureImage.getHeight()) {
                System.err.println("Treasure image not square");
                System.exit(-1);
            }
        } catch (IOException ignored) {
            System.err.println("Treasure image not found");
            System.exit(-1);
        }

        displayPanel = new DisplayPanel();
        add(displayPanel);
        pack();

        setTitle(TITLE_STRING);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        timer = new Timer(TIMER_FREQ, this);
    }

    /**
     * @param args ignored
     */
    public static void main(String[] args) {

        int rows = 10, cols = 20;

        Maze maze = new Maze(rows, cols);

        for (int row = 1; row < rows-1; row++)
            for (int col = 1; col < cols-1; col++) {
                if (Math.random() < 0.2)
                    maze.addWall(row, col);
            }

        int robotRow = rows-1, robotCol = cols-1;

        maze.initRobot(robotRow, robotCol);
        maze.addTreasure(0, 0);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {

            double p = Math.random();

            if (p < 0.25) {
                int newRobotRow = Math.max(0, robotRow-1);
                try {
                    maze.moveRobot(newRobotRow, robotCol);
                    robotRow = newRobotRow;
                } catch (InvalidMoveException ignored) {
                }
            } else if (p < 0.5) {
                int newRobotRow = Math.min(robotRow+1, rows-1);
                try {
                    maze.moveRobot(newRobotRow, robotCol);
                    robotRow = newRobotRow;
                } catch (InvalidMoveException ignored) {
                }
            } else if (p < 0.75) {
                int newRobotCol = Math.max(0, robotCol-1);
                try {
                    maze.moveRobot(robotRow, newRobotCol);
                    robotCol = newRobotCol;
                } catch (InvalidMoveException ignored) {
                }
            } else {
                int newRobotCol = Math.min(robotCol+1, cols-1);
                try {
                    maze.moveRobot(robotRow, newRobotCol);
                    robotCol = newRobotCol;
                } catch (InvalidMoveException ignored) {
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * @param i row
     * @param j col
     * @return true if *new* wall successfully added
     */
    public boolean addWall(int i, int j) {
        assert i >= 0;
        assert j >= 0;
        assert i < rows;
        assert j < columns;

        if (maze[i][j] == EMPTY) {
            maze[i][j] = WALL;
            repaint();
            return true;
        } else
            return false;
    }

    /**
     * places a robot
     *
     * @param i row to put robot in
     * @param j col to put robot in
     * @return true if success
     */
    public boolean initRobot(int i, int j) {
        assert i >= 0;
        assert j >= 0;
        assert i < rows;
        assert j < columns;


        // must be empty
        if (maze[i][j] != EMPTY) {
            System.err.println("not empty");
            return false;
        }

        // must not have already placed a robot
        if (robotRow != -1 && robotCol != -1) {
            System.err.println("already placed robot");
            return false;
        }

        robotRow = i;
        robotCol = j;
        maze[i][j] = ROBOT;

        movingRobot = false;

        return true;
    }

    /**
     * @param i row
     * @param j col
     * @return true if *new* treasure successfully added
     */
    public boolean addTreasure(int i, int j) {
        assert i >= 0;
        assert j >= 0;
        assert i < rows;
        assert j < columns;

        if (maze[i][j] == EMPTY) {
            maze[i][j] = TREASURE;
            repaint();
            return true;
        } else
            return false;
    }

    /**
     * moves a robot, blocks if already moving
     *
     * @param i row to move to
     * @param j col to move to
     */
    public void moveRobot(int i, int j) throws InvalidMoveException {
        assert i >= 0;
        assert j >= 0;
        assert i < rows;
        assert j < columns;

        // can only move to adjacent blocks
        if (Math.abs(robotRow-i)+Math.abs(robotCol-j) > 1) {
            System.err.println("moving too far");
            throw new InvalidMoveException();
        }

        // only move to empty slot
        if (maze[i][j] == WALL) {
            System.err.println("ouch! tried to move to wall");
            throw new InvalidMoveException();
        }

        while (movingRobot) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        maze[robotRow][robotCol] -= ROBOT;
        maze[i][j] += ROBOT;

        sourceRobotRow = robotRow;
        sourceRobotCol = robotCol;
        robotRow = i;
        robotCol = j;

        movingRobot = true;
        count = 0;
        timer.restart();

        while (movingRobot) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * turn on highlighting of a cell
     *
     * @param i row
     * @param j col
     */
    public void setVisiting(int i, int j) {
        highlight = true;
        visitingRow = i;
        visitingCol = j;
        repaint();
    }

    public void visitingOff() {
        highlight = false;
    }

    /**
     * @param i row
     * @param j column
     * @return state of location (i,j)
     */
    public int getState(int i, int j) {
        return maze[i][j];
    }

    /**
     * @param i row
     * @param j column
     * @return true if Cell may be occupied by the robot (i.e. not a wall)
     */
    public boolean movableCell(int i, int j) {
        return (maze[i][j] & WALL) == 0;
    }

    /**
     * @param i row
     * @param j column
     * @return true if Cell contains treaure
     */
    public boolean treasureCell(int i, int j) {
        return (maze[i][j] & TREASURE) != 0;
    }


    /**
     * called when timer goes off
     *
     * @param e ignored
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        repaint();
        if (count > MAX_COUNT) {
            movingRobot = false;
            timer.stop();
        }
    }

    /**
     * class to encapsulate the display
     */
    private class DisplayPanel extends JPanel {

        /**
         * we definitely want this thing to be a particular size
         */
        public DisplayPanel() {
            Dimension size = new Dimension(SQ_SIZE*columns, SQ_SIZE*rows);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
        }

        @Override
        public void paint(Graphics g) {

            super.paint(g);

            // draw the grid lines

            g.setColor(Color.BLACK);

            for (int row = 1; row < rows; row++)
                g.drawLine(0, SQ_SIZE*row, SQ_SIZE*columns, SQ_SIZE*row);

            for (int col = 1; col < columns; col++)
                g.drawLine(SQ_SIZE*col, 0, SQ_SIZE*col, SQ_SIZE*rows);


            // draw the images
            for (int row = 0; row < rows; row++)
                for (int col = 0; col < columns; col++) {
                    if (maze[row][col] == WALL) {
                        g.drawImage(wallImage,
                                SQ_SIZE*col, SQ_SIZE*row,
                                SQ_SIZE, SQ_SIZE, null);
                    } else if (maze[row][col] == TREASURE) {
                        g.drawImage(treasureImage,
                                SQ_SIZE*col, SQ_SIZE*row,
                                SQ_SIZE, SQ_SIZE, null);
                    } else if (maze[row][col] == ROBOT && !movingRobot) {
                        g.drawImage(robotImage,
                                SQ_SIZE*col, SQ_SIZE*row,
                                SQ_SIZE, SQ_SIZE, null);
                    }

                    if (highlight && visitingRow == row && visitingCol == col) {
                        g.setColor(Color.RED);
                        g.fillRect(SQ_SIZE*col, SQ_SIZE*row, SQ_SIZE, SQ_SIZE);
                    }
                }

            if (movingRobot) {
                double t = ((double) count)/MAX_COUNT;
                double s = t*(2-t);
                g.drawImage(robotImage,
                        (int) (SQ_SIZE*((1-s)*sourceRobotCol+s*robotCol)),
                        (int) (SQ_SIZE*((1-s)*sourceRobotRow+s*robotRow)),
                        SQ_SIZE, SQ_SIZE, null);
            }

            // required for some window managers to redraw the screen on a reasonable frame
            Toolkit.getDefaultToolkit().sync();
        }
    }

    public class InvalidMoveException extends Exception {}
}
