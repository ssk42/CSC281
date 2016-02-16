package edu.american.homework.one;

import java.util.Scanner;

/**
 * @author knappa
 * @version 1.0
 */
public class QuadraticSolver {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        QuadraticEquation quadraticEquation = new QuadraticEquation(a,b,c);

        if (quadraticEquation.hasRealSolutions()) {

            System.out.println(quadraticEquation.getSolution1());
            System.out.println(quadraticEquation.getSolution2());

        } else System.out.println("No real solutions");

        System.exit(0);

    }

}
