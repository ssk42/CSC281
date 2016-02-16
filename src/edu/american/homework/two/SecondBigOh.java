package edu.american.homework.two;

/**
 * @author knappa
 * @version 1.0
 */
public class SecondBigOh {


    public static void main(String[] args) {

        for (int N = 0; N < 2000; N++) {
            System.out.println(sumOfTriangles(N)+": "+lg(sumOfTriangles(N))/lg(N));
        }

    }

    public static int sumOfTriangles(int N) {
        int sum = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < i; j++)
                for (int k = 0; k < j; k++)
                    sum++;
        return sum;
    }

    public static double lg(double x) {
        return Math.log(x)/Math.log(2);
    }

}
