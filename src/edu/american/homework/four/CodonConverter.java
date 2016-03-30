package edu.american.homework.four;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author knappa
 * @version 1.0
 */
public class CodonConverter {

    public static void main(String[] args) throws IOException {
        HashTable<Codon, String> codonTable = new MyHashTable<>();
        populateHashTable(codonTable);

        System.out.println(codonTable.get(new Codon("ATG")));

        FileReader reader = new FileReader("res/ecoli.txt");

        // initialize codon array
        char[] codonArray = new char[3];
        for (int i = 0; i < 3; i++) {
            codonArray[i] = (char) reader.read();
        }

        int geneCount = 0;

        // loop to find START codons
        int readInt = -1;
        do {

            Codon codon = new Codon(codonArray[0], codonArray[1], codonArray[2]);

            if ("Met/M".equals(codonTable.get(codon))) {
                System.out.println("START:");
                // read until we hit STOP
                readGene(reader, codonTable);
                geneCount++;
                // read in a new codon
                for (int i = 0; i < 3; i++) {
                    readInt = reader.read();
                    codonArray[i] = (char) readInt;
                }
            } else {
                readInt = reader.read();
                codonArray[0] = codonArray[1];
                codonArray[1] = codonArray[2];
                codonArray[2] = (char) readInt;
            }

        } while (readInt != -1);

        System.out.println("number of genes found: " + geneCount);
    }

    // print out codons until we reach STOP
    private static void readGene(FileReader reader, HashTable<Codon, String> codonTable)
            throws IOException {
        char[] codonArray = new char[3];
        Codon codon;
        do {

            for (int i = 0; i < 3; i++) {
                codonArray[i] = (char) reader.read();
            }

            try {
                codon = new Codon(codonArray[0], codonArray[1], codonArray[2]);
                System.out.print(codonTable.get(codon) + " ");
            } catch (IllegalArgumentException e) {
                return;
            }

        } while (!"STOP".equals(codonTable.get(codon)));

        System.out.println();

    }

    private static void populateHashTable(HashTable<Codon, String> codonTable) {
        System.out.println("Populating Hashtable");

        codonTable.put(new Codon("GCT"), "Ala/A");
        codonTable.put(new Codon("GCC"), "Ala/A");
        codonTable.put(new Codon("GCA"), "Ala/A");
        codonTable.put(new Codon("GCG"), "Ala/A");

        codonTable.put(new Codon("TTA"), "Leu/L");
        codonTable.put(new Codon("TTG"), "Leu/L");
        codonTable.put(new Codon("CTT"), "Leu/L");
        codonTable.put(new Codon("CTC"), "Leu/L");
        codonTable.put(new Codon("CTA"), "Leu/L");
        codonTable.put(new Codon("CTG"), "Leu/L");

        codonTable.put(new Codon("CGT"), "Arg/R");
        codonTable.put(new Codon("CGC"), "Arg/R");
        codonTable.put(new Codon("CGA"), "Arg/R");
        codonTable.put(new Codon("CGG"), "Arg/R");
        codonTable.put(new Codon("AGA"), "Arg/R");
        codonTable.put(new Codon("AGG"), "Arg/R");

        codonTable.put(new Codon("AAA"), "Lys/K");
        codonTable.put(new Codon("AAG"), "Lys/K");

        codonTable.put(new Codon("AAT"), "Asn/N");
        codonTable.put(new Codon("AAC"), "Asn/N");

        codonTable.put(new Codon("ATG"), "Met/M");

        codonTable.put(new Codon("GAT"), "Asp/D");
        codonTable.put(new Codon("GAC"), "Asp/D");

        codonTable.put(new Codon("TTT"), "Phe/F");
        codonTable.put(new Codon("TTC"), "Phe/F");

        codonTable.put(new Codon("TGT"), "Cys/C");
        codonTable.put(new Codon("TGC"), "Cys/C");

        codonTable.put(new Codon("CCT"), "Pro/P");
        codonTable.put(new Codon("CCC"), "Pro/P");
        codonTable.put(new Codon("CCA"), "Pro/P");
        codonTable.put(new Codon("CCG"), "Pro/P");

        codonTable.put(new Codon("CAA"), "Gln/Q");
        codonTable.put(new Codon("CAG"), "Gln/Q");

        codonTable.put(new Codon("TCT"), "Ser/S");
        codonTable.put(new Codon("TCC"), "Ser/S");
        codonTable.put(new Codon("TCA"), "Ser/S");
        codonTable.put(new Codon("TCG"), "Ser/S");
        codonTable.put(new Codon("AGT"), "Ser/S");
        codonTable.put(new Codon("AGC"), "Ser/S");

        codonTable.put(new Codon("GAA"), "Glu/E");
        codonTable.put(new Codon("GAG"), "Glu/E");

        codonTable.put(new Codon("ACT"), "Thr/T");
        codonTable.put(new Codon("ACC"), "Thr/T");
        codonTable.put(new Codon("ACA"), "Thr/T");
        codonTable.put(new Codon("ACG"), "Thr/T");

        codonTable.put(new Codon("GGT"), "Gly/G");
        codonTable.put(new Codon("GGC"), "Gly/G");
        codonTable.put(new Codon("GGA"), "Gly/G");
        codonTable.put(new Codon("GGG"), "Gly/G");

        codonTable.put(new Codon("TGG"), "Trp/W");

        codonTable.put(new Codon("CAT"), "His/H");
        codonTable.put(new Codon("CAC"), "His/H");

        codonTable.put(new Codon("TAT"), "Try/Y");
        codonTable.put(new Codon("TAC"), "Try/Y");

        codonTable.put(new Codon("ATT"), "Ile/I");
        codonTable.put(new Codon("ATC"), "Ile/I");
        codonTable.put(new Codon("ATA"), "Ile/I");

        codonTable.put(new Codon("GTT"), "Val/V");
        codonTable.put(new Codon("GTC"), "Val/V");
        codonTable.put(new Codon("GTA"), "Val/V");
        codonTable.put(new Codon("GTG"), "Val/V");

        codonTable.put(new Codon("TAA"), "STOP");
        codonTable.put(new Codon("TGA"), "STOP");
        codonTable.put(new Codon("TAG"), "STOP");

        System.out.println("Done populating hashtable");
    }

}
