package edu.american.homework.four;

/**
 * @author knappa
 * @version 1.0
 */
public class Codon {
    private final char[] codon;

    public static void main(String[] args) {
        char[] letters = {'A', 'C', 'G', 'T'};
        for (char s1 : letters) for (char s2 : letters) for (char s3 : letters) {
            Codon codon = new Codon(s1,s2,s3);
            System.out.println(codon+" hc="+codon.hashCode());
        }
    }

    public Codon(String string) {
        if (string.length() != 3) throw new IllegalArgumentException();
        codon = new char[3];
        for (int i = 0; i < 3; i++) {
            codon[i] = string.charAt(i);
            codon[i] = Character.toUpperCase(codon[i]);
            if (codon[i] != 'A' && codon[i] != 'C' && codon[i] != 'G' && codon[i] != 'T')
                throw new IllegalArgumentException();
        }
    }

    public Codon(char[] chars) {
        if (chars.length != 3) throw new IllegalArgumentException();
        codon = new char[3];
        for (int i = 0; i < 3; i++) {
            codon[i] = Character.toUpperCase(chars[i]);
            if (codon[i] != 'A' && codon[i] != 'C' && codon[i] != 'G' && codon[i] != 'T')
                throw new IllegalArgumentException();
        }
    }


    public Codon(char one, char two, char three) {
        codon = new char[3];
        one = Character.toUpperCase(one);
        two = Character.toUpperCase(two);
        three = Character.toUpperCase(three);
        if ((one != 'A' && one != 'C' && one != 'G' && one != 'T')
                || (two != 'A' && two != 'C' && two != 'G' && two != 'T')
                || (three != 'A' && three != 'C' && three != 'G' && three != 'T')) {
            throw new IllegalArgumentException();
        }
        codon[0] = one;
        codon[1] = two;
        codon[2] = three;
    }

    public boolean equals(Codon other) {
        return this.codon[0] == other.codon[0] && this.codon[1] == other.codon[1]
                && this.codon[2] == other.codon[2];
    }

    public int hashCode() {
        return 0;
    }

    public String toString() { return new String(codon); }
}
