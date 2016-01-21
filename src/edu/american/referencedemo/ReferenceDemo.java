package edu.american.referencedemo;

/**
 * An elementary demonstration of how references work as compared to operations on primitive data types
 *
 * @author knappa
 * @version 1.0
 */
class ReferenceDemo {

    /**
     * @param args command line arguments
     */
    public static void main(String[] args) {

        /**
         * Demonstrate how primitive types work
         */

        int i = 5; // i is 5
        int j = i; // j is the value of i (i.e. 5)

        // increment j by 1
        j += 1;

        // print out i and j
        System.out.println(i);
        System.out.println(j);

        /**
         * Demonstrate how object references/pointers work
         */

        // create a new StringContainer and have it referenced by firstString
        StringContainer firstString = new StringContainer("Adam");
        // create a second reference, secondString, which will point to the
        // same place as the reference firstString
        StringContainer secondString = firstString;

        // convert the object referenced by secondString to upper case
        secondString.toUpperCase();

        // print the objects referenced by firstString and secondString
        System.out.println(firstString.getString());
        System.out.println(secondString.getString());

    }

}
