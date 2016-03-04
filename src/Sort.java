/**
 * @author knappa
 * @version 1.0
 */
public class Sort {

    static int SIZE = 10;
    static int MAGNITUDE = 100;

    public static void selectionSort(int[] array) {
        for (int j = 0; j < SIZE - 1; j++) {

            int smallestValFound = array[j];
            int locationOfSmallestVal = j;
            for (int i = j + 1; i < SIZE; i++) {
                if (array[i] < smallestValFound) {
                    smallestValFound = array[i];
                    locationOfSmallestVal = i;
                }
            }
            exchange(j, locationOfSmallestVal, array);

        }
    }

    private static void exchange(int i, int j, int[] array) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void insertionSort(int[] array) {
        for (int i = 1; i < SIZE; i++) {
            for (int j = i; j > 0 && array[j - 1] > array[j]; j--) {
                exchange(j, j - 1, array);
            }
        }
    }

    public static void bubbleSort(int[] array) {
        for (int j = SIZE - 1; j > 0; j--) {
            boolean madeExchange = false;
            for (int i = 0; i < j; i++) {
                if (array[i] > array[i + 1]) {
                    exchange(i, i + 1, array);
                    madeExchange = true;
                }
            }
            if (!madeExchange) break;
        }
    }

    public static void main(String[] args) {

        int[] array = RandomArrayGenerator.randomIntArray(SIZE, MAGNITUDE, false);

        /****************************/
        System.out.println("original array:");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();

        /****************************/
        // sort the array

        /***************************/
        System.out.println("sorted array:");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();


    }
}
