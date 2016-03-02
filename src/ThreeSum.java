import java.util.Arrays;

/**
 * Created by knappa on 2/25/16.
 */
public class ThreeSum {


    public static void main(String[] args) {

        int SIZE = 20000; // number of elements in array
        int MAGNITUDE = 30000; // entries are -30...29

        int[] array = RandomArrayGenerator.randomIntArray(SIZE, MAGNITUDE, true);

        Arrays.sort(array);

        for (int i = 0; i < SIZE-2; i++) {
            for (int j = i+1; j < SIZE-1; j++) {
                // find some k such that array[k] = -(array[i]+array[j])
                int top = SIZE-1;
                int bottom = j+1;
                int mid = (top+bottom)/2;
                if(array[top] == -(array[i]+array[j])) {
                    System.out.println("(i,j,k)=("+i+","+j+","+top+") "+
                            array[i]+", "+array[j]+", "+array[top]);
                    break;
                }
                if(array[bottom] == -(array[i]+array[j])) {
                    System.out.println("(i,j,k)=("+i+","+j+","+bottom+") "+
                            array[i]+", "+array[j]+", "+array[bottom]);
                    break;
                }

                while( array[mid] != -(array[i]+array[j]) && mid !=top && mid !=bottom) {
                    if(array[mid] < -(array[i]+array[j])) {
                        bottom = mid;
                        mid = (top+bottom)/2;
                    } else if(array[mid] > -(array[i]+array[j])) {
                        top = mid;
                        mid = (top+bottom)/2;
                    }
                }

                if(array[mid] == -(array[i]+array[j])) {
                    System.out.println("(i,j,k)=("+i+","+j+","+mid+") "+
                            array[i]+", "+array[j]+", "+array[mid]);
                    break;
                }


            }
        }

    }

}
