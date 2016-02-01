package edu.american.homework.one;

/**
 * @author knappa
 * @version 1.0
 */
public class DoubleLoopTest {

    public static void main(String[] args) {

        for (double i = 1.1; i < 2; i += 0.1)
            System.out.println(i);

        System.out.println("a more precise version");

        /* higher precision version with same output */
        for (int i = 0; i < 9; i++) {
            System.out.println(1.1 + (double) i / 10);
        }

    }

}
