package edu.american.loops;

import java.util.Scanner;

/**
 * Finds the first n primes where n is determined by the user
 *
 * @author knappa
 * @version 1.0
 */
public class FirstPrimes {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("How many primes should we search for?:");

        int numPrimesToFind = scanner.nextInt();

        int[] primeArray = new int[numPrimesToFind];

        int numPrimesFound = 0;

        // search the numbers >=2 one by one until we find the desired number of primes
        for (int potentialPrime = 2; numPrimesFound < numPrimesToFind; potentialPrime++) {

            boolean divisibleByPrevPrimes = false;
            // search through the
            for (int primeIndex = 0; primeIndex < numPrimesFound && !divisibleByPrevPrimes; primeIndex++) {
                if (potentialPrime % primeArray[primeIndex] == 0)
                    divisibleByPrevPrimes = true;
            }

            if (!divisibleByPrevPrimes) {
                System.out.println("Found a prime: " + potentialPrime);
                primeArray[numPrimesFound] = potentialPrime;
                numPrimesFound++;
            }
        }

    }

}
