package edu.american.homework.one;

import java.util.Scanner;

/**
 * @author knappa
 * @version 1.0
 */
public class ChangeMaker {

    public static void main(String[] args) {

        /* read the amount of $$$ from stdio */
        Scanner scanner = new Scanner(System.in);
        double amountOfMoney = scanner.nextDouble();

        /* compute the corresponding number of cents */
        int numberOfCents = (int) (amountOfMoney * 100);

        /* find the number of 20's */
        int numberOfTwenties = numberOfCents / 2000;
        System.out.println("Num $20: " + numberOfTwenties);
        numberOfCents -= 2000 * numberOfTwenties; // these cents are taken care of

        /* find the number of 10's */
        int numberOfTens = numberOfCents / 1000;
        System.out.println("Num $10: " + numberOfTens);
        numberOfCents -= 1000 * numberOfTens; // these cents are taken care of

        /* find the number of 5's */
        int numberOfFives = numberOfCents / 500;
        System.out.println("Num $5: " + numberOfFives);
        numberOfCents -= 500 * numberOfFives; // these cents are taken care of

        /* find the number of 1's */
        int numberOfOnes = numberOfCents / 100;
        System.out.println("Num $1: " + numberOfOnes);
        numberOfCents -= 100 * numberOfOnes; // these cents are taken care of

        /* find the number of quarters */
        int numberOfQuarters = numberOfCents / 25;
        System.out.println("Num quarters: " + numberOfQuarters);
        numberOfCents -= 25 * numberOfQuarters; // these cents are taken care of

        /* find the number of dimes */
        int numberOfDimes = numberOfCents / 10;
        System.out.println("Num dimes: " + numberOfDimes);
        numberOfCents -= 10 * numberOfDimes; // these cents are taken care of

        /* find the number of nickels */
        int numberOfNickles = numberOfCents / 5;
        System.out.println("Num nickles: " + numberOfNickles);
        numberOfCents -= 5 * numberOfNickles; // these cents are taken care of

        /* everything left */
        System.out.println("Num pennies: " + numberOfCents);


    }

}
