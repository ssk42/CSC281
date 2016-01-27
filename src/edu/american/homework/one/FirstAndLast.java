package edu.american.homework.one;

import java.util.Scanner;

/**
 * @author knappa
 * @version 1.0
 */
public class FirstAndLast {

    /* what happens for other bases? */
    static int base = 10;

    public static void main(String[] args) {

        /* read in the number from stdio */
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        /* compute 1's place */
        int onesPlace = num % base;
        System.out.println(onesPlace);

        /* remove least sig digit and shift */
        num = (num - onesPlace) / base;

        /* if 1's place was only digit then the 1's place was both the first and last digit */
        int digit = onesPlace;

        while (num != 0) {
            /* compute least sig digit and remove */
            digit = num % base;
            num = (num - digit) / base;
        }

        System.out.println(digit);

    }

}
