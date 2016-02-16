package edu.american.test;

/**
 * Created by knappa on 2/8/16.
 */
public class Whatever {

    public static double PI = 3.14;

    public double x, y, z;

    public Whatever() {
        this.x = 1;
        this.y = 2;
        this.z = 3;
        System.out.println("in contructor for Whatever");
    }

    public Whatever(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void main(String[] args) {

        System.out.println("Hi there!");

        Whatever wOne = new Whatever();
        Whatever wTwo = new Whatever();

        Whatever wThree = wOne;

        wOne.x = 500;


        System.out.println(wOne.x);
        System.out.println(wTwo.x);
        System.out.println(wThree.x);

    }

    public static int square(int n) {
        System.out.println("Squaring n="+n);
        return n*n;
    }


}
