package edu.american.homework.two;

/**
 * @author knappa
 * @version 1.0
 */
public class FirstBigOh {

    public static void main(String[] args) {

        for (int N = 0; N < 50000; N++) {
            System.out.println(triangle(N)+": "+lg(triangle(N))/lg(N));
        }

    }

    public static int triangle(int N) {
        int sum = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < i; j++)
                sum++;
        return sum;
    }

    public static double lg(double x) {
        return Math.log(x)/Math.log(2);
    }

}
