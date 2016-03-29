package edu.american.homework.four;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by knappa on 3/24/16.
 */
public class CodonConverter {

    private static void populateHashTable(HashTable<Codon, String> codonTable) {
        codonTable.put(new Codon("GCT"),"Ala/A");
        codonTable.put(new Codon("GCC"),"Ala/A");
        codonTable.put(new Codon("GCA"),"Ala/A");
        codonTable.put(new Codon("GCG"),"Ala/A");

        codonTable.put(new Codon("TTA"),"Leu/L");
        codonTable.put(new Codon("TTG"),"Leu/L");
        codonTable.put(new Codon("CTT"),"Leu/L");
        codonTable.put(new Codon("CTC"),"Leu/L");
        codonTable.put(new Codon("CTA"),"Leu/L");
        codonTable.put(new Codon("CTG"),"Leu/L");

        codonTable.put(new Codon("CGT"),"Arg/R");
        codonTable.put(new Codon("CGC"),"Arg/R");
        codonTable.put(new Codon("CGA"),"Arg/R");
        codonTable.put(new Codon("CGG"),"Arg/R");
        codonTable.put(new Codon("AGA"),"Arg/R");
        codonTable.put(new Codon("AGG"),"Arg/R");

        codonTable.put(new Codon("AAA"),"Lys/K");
        codonTable.put(new Codon("AAG"),"Lys/K");

        codonTable.put(new Codon("AAT"),"Asn/N");
        codonTable.put(new Codon("AAC"),"Asn/N");

        codonTable.put(new Codon("ATG"),"Met/M");

        codonTable.put(new Codon("GAT"),"Asp/D");
        codonTable.put(new Codon("GAC"),"Asp/D");

        codonTable.put(new Codon("TTT"),"Phe/F");
        codonTable.put(new Codon("TTC"),"Phe/F");

        codonTable.put(new Codon("TGT"),"Cys/C");
        codonTable.put(new Codon("TGC"),"Cys/C");

        codonTable.put(new Codon("CCT"),"Pro/P");
        codonTable.put(new Codon("CCC"),"Pro/P");
        codonTable.put(new Codon("CCA"),"Pro/P");
        codonTable.put(new Codon("CCG"),"Pro/P");

        codonTable.put(new Codon("CAA"),"Gln/Q");
        codonTable.put(new Codon("CAG"),"Gln/Q");

        codonTable.put(new Codon("TCT"),"Ser/S");
        codonTable.put(new Codon("TCC"),"Ser/S");
        codonTable.put(new Codon("TCA"),"Ser/S");
        codonTable.put(new Codon("TCG"),"Ser/S");
        codonTable.put(new Codon("AGT"),"Ser/S");
        codonTable.put(new Codon("AGC"),"Ser/S");

        codonTable.put(new Codon("GAA"),"Glu/E");
        codonTable.put(new Codon("GAG"),"Glu/E");

        codonTable.put(new Codon("ACT"),"Thr/T");
        codonTable.put(new Codon("ACC"),"Thr/T");
        codonTable.put(new Codon("ACA"),"Thr/T");
        codonTable.put(new Codon("ACG"),"Thr/T");

        codonTable.put(new Codon("GGT"),"Gly/G");
        codonTable.put(new Codon("GGC"),"Gly/G");
        codonTable.put(new Codon("GGA"),"Gly/G");
        codonTable.put(new Codon("GGG"),"Gly/G");

        codonTable.put(new Codon("TGG"),"Trp/W");

        codonTable.put(new Codon("CAT"),"His/H");
        codonTable.put(new Codon("CAC"),"His/H");

        codonTable.put(new Codon("TAT"),"Try/Y");
        codonTable.put(new Codon("TAC"),"Try/Y");

        codonTable.put(new Codon("ATT"),"Ile/I");
        codonTable.put(new Codon("ATC"),"Ile/I");
        codonTable.put(new Codon("ATA"),"Ile/I");

        codonTable.put(new Codon("GTT"),"Val/V");
        codonTable.put(new Codon("GTC"),"Val/V");
        codonTable.put(new Codon("GTA"),"Val/V");
        codonTable.put(new Codon("GTG"),"Val/V");

        codonTable.put(new Codon("ATG"),"START");

        codonTable.put(new Codon("TAA"),"STOP");
        codonTable.put(new Codon("TGA"),"STOP");
        codonTable.put(new Codon("TAG"),"STOP");

    }


    public static void main(String[] args) {
        HashTable<Codon,String> codonTable = new MyHashTable<Codon,String>();
        populateHashTable(codonTable);

        FileReader reader;
        try {
            reader = new FileReader("res/ecoli.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // for loop to find the first START codon
        char[] searchWindow = new char[5];
        try {
            boolean startCodonFound = false;
            while(reader.read(searchWindow,2,3)!= -1) {
                for (int offset = 0; offset < 3 && !startCodonFound; offset++) {
                    Codon codon;
                    try {
                        codon = new Codon(searchWindow[offset],
                                searchWindow[offset+1],searchWindow[offset+2]);
                        if("START".equals(codonTable.get(codon))) {
                            startCodonFound = true;
                            System.out.println("START:");
                        }
                    } catch (IllegalArgumentException ignored) {}

                }
            }

            // print out codons until we reach STOP
            char[] codonArray = new char[3];
            Codon codon;
            String translation;
            boolean ranOutOfFile = false;
            do {
                if(reader.read(codonArray) == -1) ranOutOfFile = true;
                codon = new Codon(codonArray);
                translation = codonTable.get(codon);
                System.out.print(translation+" ");
            } while (!"STOP".equals(translation) && !ranOutOfFile);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
