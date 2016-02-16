package edu.american.queues;

import edu.american.stacks.ArrayBackedStack;
import edu.american.stacks.PathFinder;
import edu.american.stacks.Stack;

import java.util.HashMap;

/**
 * @author knappa
 * @version 1.0
 */
public class BetterPathFinder extends PathFinder {

    /**
     * @param args ignored
     */
    public static void main(String[] args) {
        BetterPathFinder pathFinder = new BetterPathFinder();
        pathFinder.start();
    }


    @Override
    public Iterable<GridPoint> findPath() {

        boolean[][] checkedArray = new boolean[ROWS][COLUMNS];

        HashMap<GridPoint, GridPoint> parents = new HashMap<GridPoint, GridPoint>();

        Queue<GridPoint> pointQueue = new ArrayBackedQueue<GridPoint>(ROWS*COLUMNS);

        GridPoint current = new GridPoint(robotRow, robotCol);

        pointQueue.enqueue(current);

        while (!maze.treasureCell(current.row, current.col) && !pointQueue.isEmpty()) {

            current = pointQueue.dequeue();

            maze.setVisiting(current.row, current.col);

            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }

            if (current.row > 0 &&
                    maze.movableCell(current.row-1, current.col) &&
                    !checkedArray[current.row-1][current.col]) {

                GridPoint newPoint = new GridPoint(current.row-1, current.col);
                maze.addConnectionNorth(current, newPoint);
                pointQueue.enqueue(newPoint);
                checkedArray[current.row-1][current.col] = true;
                parents.put(newPoint, current);

            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }

            if (current.row < ROWS-1 &&
                    maze.movableCell(current.row+1, current.col) &&
                    !checkedArray[current.row+1][current.col]) {

                GridPoint newPoint = new GridPoint(current.row+1, current.col);
                maze.addConnectionSouth(current, newPoint);
                pointQueue.enqueue(newPoint);
                checkedArray[current.row+1][current.col] = true;
                parents.put(newPoint, current);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }

            if (current.col > 0 &&
                    maze.movableCell(current.row, current.col-1) &&
                    !checkedArray[current.row][current.col-1]) {

                GridPoint newPoint = new GridPoint(current.row, current.col-1);
                maze.addConnectionWest(current, newPoint);
                pointQueue.enqueue(newPoint);
                checkedArray[current.row][current.col-1] = true;
                parents.put(newPoint, current);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }

            if (current.col < COLUMNS-1 &&
                    maze.movableCell(current.row, current.col+1) &&
                    !checkedArray[current.row][current.col+1]) {

                GridPoint newPoint = new GridPoint(current.row, current.col+1);
                maze.addConnectionEast(current, newPoint);
                pointQueue.enqueue(newPoint);
                checkedArray[current.row][current.col+1] = true;
                parents.put(newPoint, current);
            }

        }

        if(!maze.treasureCell(current.row, current.col)) {
            maze.visitingOff();
            return null;
        }

        // extract the path
        Stack<GridPoint> path = new ArrayBackedStack<GridPoint>(ROWS*COLUMNS);
        GridPoint prev = null;
        do {
            path.push(current);
            prev = current;
            current = parents.get(current);
            if(current != null) {
                maze.setVisiting(current.row, current.col);
                maze.addPathToTreasure(prev, current);
                try { Thread.sleep(20);
                } catch (InterruptedException ignored) {
                }
            }
        } while (current != null && !current.equals(parents.get(current)));

        maze.visitingOff();

        return path;
    }

}
