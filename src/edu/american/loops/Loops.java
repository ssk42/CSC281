package edu.american.loops;

/**
 * Let's learn loops
 *
 * @author knappa
 * @version 1.0
 */
public class Loops {


    public static void main(String[] args) {

        String name = "Adam";

        // note the < in the condition
        for (int i = 0; i < name.length(); i++) {

            System.out.println(name.charAt(i));

        }

    }

}
