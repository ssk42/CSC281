package edu.american.homework.one;

import java.util.Scanner;

/**
 * @author knappa
 * @version 1.0
 */
public class QuadraticSolver {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        QuadraticEquation quadraticEquation =
                new QuadraticEquation(
                        scanner.nextDouble(),
                        scanner.nextDouble(),
                        scanner.nextDouble()
                );

        if (quadraticEquation.hasRealSolutions()) {

            System.out.println(quadraticEquation.getSolution1());
            System.out.println(quadraticEquation.getSolution2());

        } else System.out.println("No real solutions");

    }

}
