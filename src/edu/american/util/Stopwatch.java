package edu.american.util;

/**
 * A stopwatch utility class
 */

public class Stopwatch {

    private long startTime;
    private long stopTime;
    private boolean running;

    public Stopwatch() {
        this.reset();
    }

    public void reset() {

        startTime = 0;
        stopTime = 0;
        running = false;

    }

    public void start() {

        if (running) return;

        this.startTime = System.nanoTime();
        this.running = true;

    }


    public void stop() {

        if (!running) return;

        this.stopTime = System.nanoTime();
        this.running = false;

    }

    /**
     * @return elapsed time in milliseconds
     */
    public long getElapsedTime() {
        long elapsed;
        if (running) elapsed = (System.nanoTime()-startTime);
        else elapsed = (stopTime-startTime);
        return elapsed;
    }

    /**
     * @return elapsed time in seconds
     */
    public double getElapsedTimeSecs() {
        double elapsed;
        if (running) elapsed = (double) (System.nanoTime()-startTime)/1000000000;
        else elapsed = (double) (stopTime-startTime)/1000000000;
        return elapsed;
    }
}