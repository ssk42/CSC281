package edu.american.conditionals;

/**
 * A playground for short circuiting of conditionals
 *
 * @author knappa
 * @version 1.0
 */
public class ShortCircuit {

    /**
     * A function which prints something and returns true
     *
     * @return always returns true
     */
    public static boolean definitelyTrue() {
        System.out.println("1<2 is true");
        return 1 < 2;
    }

    /**
     * A function which prints something and returns false
     *
     * @return always returns false
     */
    public static boolean definitelyFalse() {
        System.out.println("6 != 6 is false");
        return 6 != 6;
    }

    public static void main(String[] args) {

        if (definitelyFalse() && definitelyTrue()) {
            System.out.print("Evaluated to true");
        } else {
            System.out.print("Evaluated to false");
        }

    }

}
