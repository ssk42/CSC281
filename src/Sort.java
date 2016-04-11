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


    public static void mergeSort(int[] array) {
        int[] tempArray = new int[array.length];
        mergeSortRecursive(array, tempArray, 0, array.length - 1);
    }

    private static void mergeSortRecursive(int[] array,
                                           int[] tempArray,
                                           int low, int high) {
        if (low == high) return; // length 1 arrays are sorted

        int mid = (low + high) / 2;

        // sort the two sides
        mergeSortRecursive(array, tempArray, low, mid);
        mergeSortRecursive(array, tempArray, mid + 1, high);

        // copy the region into the temporary array
        copyArray(array, tempArray, low, high);

        // merge the temporary array back into the array
        int lowIndex = low, highIndex = mid + 1;
        int destinationIndex = low;

        while (destinationIndex <= high
                && lowIndex <= mid
                && highIndex <= high) {
            if (tempArray[lowIndex] <= tempArray[highIndex]) {
                array[destinationIndex] = tempArray[lowIndex];
                lowIndex++;
                destinationIndex++;
            } else {
                array[destinationIndex] = tempArray[highIndex];
                highIndex++;
                destinationIndex++;
            }
        }

        if (lowIndex <= mid) {
            System.arraycopy(tempArray, lowIndex, array, destinationIndex, mid - lowIndex + 1);
        } else if (highIndex <= high) {
            System.arraycopy(tempArray, highIndex, array, destinationIndex, high - highIndex + 1);
        } else
            System.out.println("bloop");

    }

    private static void copyArray(int[] array, int[] tempArray, int low, int high) {
        System.arraycopy(array, low, tempArray, low, high - low + 1);
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
        mergeSort(array);


        /***************************/
        System.out.println("sorted array:");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}
