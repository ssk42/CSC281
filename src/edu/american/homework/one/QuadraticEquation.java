package edu.american.homework.one;

/**
 * Created by knappa on 1/27/16.
 */
public class QuadraticEquation {

    /* store the coefficients a,b,c of ax^2+bx+c */
    double a, b, c;

    /**
     * Construct the quadratic equation <code>ax^2+bx+c</code>
     *
     * @param a coeff of x^2
     * @param b coeff of x
     * @param c constant term
     */
    public QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * detects is the quadratic equation has real solutions
     *
     * @return true if quadratic equation real solutions
     */
    public boolean hasRealSolutions() {
        return Math.pow(b, 2) - 4 * a * c >= 0;
    }

    /**
     * Finds real solutions to quadratic equations of the form
     * (-b+sqrt(b^2-4ac))/(2a)
     *
     * @return real solution to the quadratic equation, or NaN if none exist
     */
    public double getSolution1() {
        if (this.hasRealSolutions())
            return (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        else
            return Double.NaN;
    }

    /**
     * Finds real solutions to quadratic equations of the form
     * (-b-sqrt(b^2-4ac))/(2a)
     *
     * @return real solution to the quadratic equation, or NaN if none exist
     */
    public double getSolution2() {
        if (this.hasRealSolutions())
            return (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        else
            return Double.NaN;
    }

}
