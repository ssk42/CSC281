package edu.american.stacks;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * @author knappa
 * @version 1.0
 */
public class PathFinder {

    static final int ROWS = 10, COLUMNS = 10;
    boolean[][] checkedArray;
    Maze maze;
    private int robotRow;
    private int robotCol;

    public PathFinder() {
    }

    /**
     * @param args ignored
     */
    public static void main(String[] args) {
        PathFinder pathFinder = new PathFinder();
        pathFinder.start();
    }

    private void start() {

        generateMaze();

        Stack<GridPoint> path = findPath();

        moveRobot(path);
    }

    private void generateMaze() {

        maze = new Maze(ROWS, COLUMNS);

        robotRow = ROWS-1;
        robotCol = COLUMNS-1;

        maze.initRobot(robotRow, robotCol);
        maze.addTreasure(0, 0);

        double p = 0.3;

        for (int row = 2; row < ROWS-3; row++) {
            if (Math.random() < p)
                maze.addWall(row, 0);
            if (Math.random() < p)
                maze.addWall(row, COLUMNS-1);
        }

        for (int col = 2; col < COLUMNS-3; col++) {
            if (Math.random() < p)
                maze.addWall(0, col);
            if (Math.random() < p)
                maze.addWall(ROWS-1, col);
        }

        for (int row = 1; row < ROWS-1; row++)
            for (int col = 1; col < COLUMNS-1; col++)
                if (Math.random() < p)
                    maze.addWall(row, col);

    }

    private Stack<GridPoint> findPath() {

        checkedArray = new boolean[ROWS][COLUMNS];

        Stack<GridPoint> path = new ArrayBackedStack<GridPoint>(ROWS*COLUMNS);

        GridPoint current = new GridPoint(robotRow, robotCol);

        do {

            maze.setVisiting(current.row, current.col);

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }

            if (current.row > 0 &&
                    maze.movableCell(current.row-1, current.col) &&
                    !checkedArray[current.row-1][current.col]) {

                path.push(current);

                checkedArray[current.row-1][current.col] = true;
                current = new GridPoint(current.row-1, current.col);

            } else if (current.row < ROWS-1 &&
                    maze.movableCell(current.row+1, current.col) &&
                    !checkedArray[current.row+1][current.col]) {

                path.push(current);

                checkedArray[current.row+1][current.col] = true;
                current = new GridPoint(current.row+1, current.col);

            } else if (current.col > 0 &&
                    maze.movableCell(current.row, current.col-1) &&
                    !checkedArray[current.row][current.col-1]) {

                path.push(current);

                checkedArray[current.row][current.col-1] = true;
                current = new GridPoint(current.row, current.col-1);

            } else if (current.col < COLUMNS-1 &&
                    maze.movableCell(current.row, current.col+1) &&
                    !checkedArray[current.row][current.col+1]) {

                path.push(current);

                checkedArray[current.row][current.col+1] = true;
                current = new GridPoint(current.row, current.col+1);

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
        Stack<GridPoint> reversedPath = new ArrayBackedStack<GridPoint>(ROWS*COLUMNS);
        while (!path.isEmpty())
            try {
                GridPoint point = path.pop();
                maze.setVisiting(point.row, point.col);
                Thread.sleep(20);
                reversedPath.push(point);
            } catch (StackEmptyException | InterruptedException ignored) {
            }

        maze.visitingOff();

        return reversedPath;
    }

    private void moveRobot(Stack<GridPoint> path) {

        GridPoint point = null;

        while (!path.isEmpty()) {

            try {
                point = path.pop();
                maze.moveRobot(point.row, point.col);
            } catch (StackEmptyException | Maze.InvalidMoveException ignored) {
            }

        }

        if (point != null && maze.treasureCell(point.row, point.col)) {

            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File("res/tada.wav")));
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ignored) {

            }

        }

    }

    private class GridPoint {
        int row, col;

        GridPoint(int r, int c) {
            row = r;
            col = c;
        }

        @Override
        public String toString() {
            return "row= "+row+" col="+col;
        }

    }

}
