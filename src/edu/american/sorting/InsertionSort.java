package edu.american.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author knappa
 * @version 1.0
 */
public class InsertionSort {

    /**
     * test cases
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(7);
        list.add(6);
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);

        System.out.println(list);

        sort(list);

        System.out.println(list);

    }


    /**
     * @param list   list of comparable objects
     * @param <Item> a class implementing the comparable interface
     */
    public static <Item extends Comparable<? super Item>> void sort(List<Item> list) {

        if (list == null) return;
        if (list.size() == 0 || list.size() == 1) return;

        for (int i = 1; i < list.size(); i++) {
            for (int j = i-1; j >= 0 &&
                    list.get(j+1).compareTo(list.get(j)) < 0; j--) {
                Item temp = list.get(j);
                list.set(j, list.get(j+1));
                list.set(j+1, temp);
            }
        }
    }

    public static <Item> void sort(List<Item> list, Comparator<? super Item> comparator) {

        if (list == null) return;
        if (list.size() == 0 || list.size() == 1) return;

        for (int i = 1; i < list.size(); i++) {
            for (int j = i-1; j >= 0 &&
                    comparator.compare(list.get(j+1), list.get(j)) < 0; j--) {
                Item temp = list.get(j);
                list.set(j, list.get(j+1));
                list.set(j+1, temp);
            }
        }
    }
}
