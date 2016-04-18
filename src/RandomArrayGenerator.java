/**
 * A class consisting of static methods which generate random arrays
 *
 * @author knappa
 * @version 1.0
 */
public class RandomArrayGenerator {

    /**
     * Unit tests
     *
     * @param args ignored
     */
    public static void main(String[] args) {

        /*
        for (double d : randomDoubleArray(10, 1.0, true))
            System.out.println(d);

        System.out.println();
        */

        for (int i : randomSortedIntArray(100, 1000, true))
            System.out.println(i);

    }

    /**
     * Returns a sorted array of unique ints. See {@link #randomIntArray(int, int, boolean)} for requirements on the
     * size of the array.
     *
     * @param size           size of array
     * @param entryMagnitude maximum norm of generated entries
     * @return generated array, if possible. {@code null} otherwise
     */
    public static int[] randomSortedIntArray(int size, int entryMagnitude, boolean uniqueEntries) {

        int[] array = randomIntArray(size, entryMagnitude, uniqueEntries);

        Sort.mergeSort(array);

        return array;
    }


    /**
     * Generates an array of integers of length {@code size}. Each entry has norm at most {@code entryMagnitude}. All
     * entries are unique if {@code uniqueEntries} is set to {@code true}. Thus, you must have {@code size <=
     * 2*entryMagnitude} if {@code uniqueEntries} is set.
     *
     * @param size           length of generated array
     * @param entryMagnitude maximum norm of generated entries
     * @param uniqueEntries  if {@code true}, no repeated entries
     * @return generated array, if possible. {@code null} otherwise
     */
    public static int[] randomIntArray(int size, int entryMagnitude, boolean uniqueEntries) {

        // assert that pigeon-hole principle does not fail
        assert !uniqueEntries || size <= 2 * entryMagnitude :
                "Can't create that many unique values in that range";

        int[] array = new int[size];

        for (int i = 0; i < size; i++)
            do array[i] = (int) ((2 * Math.random() - 1) * entryMagnitude);
            while (uniqueEntries && intEntryPresent(i, array, array[i]));

        return array;

    }

    /**
     * check to see if an element is already in the array
     *
     * @param top      how far up to check
     * @param array    array o check in
     * @param checkVal value to check for
     * @return {@code true} if value is in the array up to {@code top}
     */
    private static boolean intEntryPresent(int top, int[] array, int checkVal) {

        for (int i = 0; i < top; i++)
            if (array[i] == checkVal)
                return true;

        return false;
    }

    /**
     * Generates an array of doubles of length {@code size}. Each entry has norm at most {@code entryMagnitude}. All
     * entries are unique if {@code uniqueEntries} is set to {@code true}. Setting {@code uniqueEntries} to {@code true}
     * may fail by infinite loop if {@code size} exhausts the number of doubles in the interval
     * [-entryMagnitude,entryMagnitude)
     *
     * @param size           length of generated array
     * @param entryMagnitude maximum norm of generated entries
     * @param uniqueEntries  if {@code true}, no repeated entries
     * @return generated array
     */
    public static double[] randomDoubleArray(int size, double entryMagnitude, boolean uniqueEntries) {

        double[] array = new double[size];

        for (int i = 0; i < size; i++)
            do array[i] = (2 * Math.random() - 1) * entryMagnitude;
            while (uniqueEntries && doubleEntryPresent(i, array, array[i]));

        return array;
    }

    /**
     * check to see if an element is already in the array
     *
     * @param top      how far up to check
     * @param array    array o check in
     * @param checkVal value to check for
     * @return {@code true} if value is in the array up to {@code top}
     */
    private static boolean doubleEntryPresent(int top, double[] array, double checkVal) {

        for (int i = 0; i < top; i++)
            if (array[i] == checkVal)
                return true;

        return false;
    }

}
