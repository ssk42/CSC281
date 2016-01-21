package edu.american.loops;

import java.util.Scanner;

/**
 * Factors integers, not in the most efficient way possible
 *
 * @author knappa
 * @version 1.0
 */
public class FactorInteger {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        do {

            System.out.print("Enter a positive integer:");

            int numberToFactor = scanner.nextInt();

            for (int potentialFactor = 1; potentialFactor <= Math.sqrt(numberToFactor); potentialFactor++) {

                if (numberToFactor % potentialFactor == 0) {

                    if (potentialFactor * potentialFactor == numberToFactor) {
                        System.out.println("Double factor: " + potentialFactor);
                    } else {
                        System.out.println("Factor pair: " + potentialFactor +
                                " " + (numberToFactor / potentialFactor));
                    }

                }

            }

            System.out.println("Do again? (true/false):");

        } while (scanner.nextBoolean());

        System.out.println("Ok, see you later!");

    }

}
