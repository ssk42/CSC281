package edu.american.homework.four;

/**
 * @author knappa
 * @version 1.0
 */
public class CodonTest {
    public static void main(String[] args) {
        String[] letters = {"A", "C", "G", "T"};
        int arraySize = 128;
        for (String s1 : letters)
            for (String s2 : letters)
                for (String s3 : letters)
                    System.out.println(s1 + s2 + s3 + " hc=" + ((s1 + s2 + s3).hashCode() % arraySize));
    }
}
