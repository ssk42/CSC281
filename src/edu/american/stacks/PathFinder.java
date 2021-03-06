package edu.american.stacks;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author knappa
 * @version 1.0
 */
public class PathFinder {

    protected static final int ROWS = 10;
    protected static final int COLUMNS = 20;
    protected Maze maze;
    protected int robotRow;
    protected int robotCol;

    /**
     * @param args ignored
     */
    public static void main(String[] args) {
        PathFinder pathFinder = new PathFinder();
        pathFinder.start();
    }

    protected void start() {

        generateMaze();

        Iterable<GridPoint> path = findPath();

        moveRobot(path);
    }

    /**
     * setup the maze, randomly
     */
    private void generateMaze() {

        maze = new Maze(ROWS, COLUMNS);

        robotRow = ROWS - 1;
        robotCol = COLUMNS - 1;

        maze.initRobot(robotRow, robotCol);
        maze.addTreasure(0, 0);

        double p = 0.3;

        for (int row = 2; row < ROWS - 3; row++) {
            if (Math.random() < p)
                maze.addWall(row, 0);
            if (Math.random() < p)
                maze.addWall(row, COLUMNS - 1);
        }

        for (int col = 2; col < COLUMNS - 3; col++) {
            if (Math.random() < p)
                maze.addWall(0, col);
            if (Math.random() < p)
                maze.addWall(ROWS - 1, col);
        }

        for (int row = 1; row < ROWS - 1; row++)
            for (int col = 1; col < COLUMNS - 1; col++)
                if (Math.random() < p)
                    maze.addWall(row, col);

    }

    /**
     * Depth-first search
     *
     * @return if it exists, a path connecting the robot to the treasure
     */
    public Iterable<GridPoint> findPath() {

        boolean[][] checkedArray = new boolean[ROWS][COLUMNS];

        Stack<GridPoint> path = new ArrayBackedStack<GridPoint>(ROWS * COLUMNS);

        GridPoint current = new GridPoint(robotRow, robotCol);

        do {

            maze.setVisiting(current.row, current.col);

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }

            if (current.row > 0 &&
                    maze.movableCell(current.row - 1, current.col) &&
                    !checkedArray[current.row - 1][current.col]) {

                path.push(current);

                checkedArray[current.row - 1][current.col] = true;

                GridPoint newCurrent = new GridPoint(current.row - 1, current.col);
                maze.addConnectionNorth(current, newCurrent);
                current = newCurrent;

            } else if (current.row < ROWS - 1 &&
                    maze.movableCell(current.row + 1, current.col) &&
                    !checkedArray[current.row + 1][current.col]) {

                path.push(current);

                checkedArray[current.row + 1][current.col] = true;

                GridPoint newCurrent = new GridPoint(current.row + 1, current.col);
                maze.addConnectionSouth(current, newCurrent);
                current = newCurrent;


            } else if (current.col > 0 &&
                    maze.movableCell(current.row, current.col - 1) &&
                    !checkedArray[current.row][current.col - 1]) {

                path.push(current);

                checkedArray[current.row][current.col - 1] = true;

                GridPoint newCurrent = new GridPoint(current.row, current.col - 1);
                maze.addConnectionWest(current, newCurrent);
                current = newCurrent;


            } else if (current.col < COLUMNS - 1 &&
                    maze.movableCell(current.row, current.col + 1) &&
                    !checkedArray[current.row][current.col + 1]) {

                path.push(current);

                checkedArray[current.row][current.col + 1] = true;

                GridPoint newCurrent = new GridPoint(current.row, current.col + 1);
                maze.addConnectionEast(current, newCurrent);
                current = newCurrent;

            } else {
                try {
                    current = path.pop();
                } catch (StackEmptyException ignored) {
                    assert false;
                }
            }

        } while (!maze.treasureCell(current.row, current.col) && !path.isEmpty());

        path.push(current);


        // "path" contains the path in the wrong direction, reverse it
        // print while you do it
        Stack<GridPoint> reversedPath = new ArrayBackedStack<GridPoint>(ROWS * COLUMNS);
        GridPoint prev = null;
        for (GridPoint point : path) {
            reversedPath.push(point);

            maze.setVisiting(point.row, point.col);
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }
            if (prev != null) {
                maze.addPathToTreasure(prev, point);
            }
            prev = point;
        }

        maze.visitingOff();

        return reversedPath;
    }

    /**
     * moves the robot along the specified path
     *
     * @param path the path to follow
     */
    private void moveRobot(Iterable<GridPoint> path) {

        if (path == null) return;

        GridPoint point = null;

        for (GridPoint pt : path) {
            point = pt;
            try {
                maze.moveRobot(point.row, point.col);
            } catch (Maze.InvalidMoveException ignored) {
            }
        }

        if (point != null && maze.treasureCell(point.row, point.col)) {

            Clip clip = null;
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/tada.wav"));
                AudioFormat format = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ignored) {

            }

        }

    }

    /**
     * a wrapper for points in the maze
     */
    public class GridPoint {
        public int row;
        public int col;

        public GridPoint(int r, int c) {
            row = r;
            col = c;
        }

        @Override
        public boolean equals(Object other) {

            if (!(other instanceof GridPoint)) return false;

            GridPoint otherPoint = (GridPoint) other;

            return otherPoint.row == this.row && otherPoint.col == this.col;

        }

        @Override
        public String toString() {
            return "row= " + row + " col=" + col;
        }

    }

}
