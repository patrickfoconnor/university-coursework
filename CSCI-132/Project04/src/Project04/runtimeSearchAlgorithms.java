package Project04;
/**
 * Author: Patrick O'Connor
 * Date Modified: July 27, 2020
 */

import java.util.Arrays;
import java.util.Random;

/**
 *  QUESTION #6: Running the code for large values of n:
 *  Mathematically speaking for very large values of n the binary search is more efficient yet my testing could not confirm this.
 *  In my personal testing I could not find a single test that supported the asymptotic runtime math and therefore would choose the
 *  linear search as it has proven to be faster on every test I have thrown at it.
 *
 *  This is not to say there are not any data sets that the binary search will not be more efficient and mathematically with massive data sets
 *  the linear search will get left in the dust by the binary search as it cuts the amount of comparisons in half each time the
 *  target is not the middle.
 *
 *  Overall without knowing exactly how large, what kind of computations, the required speed or what kind of data (sorted, unsorted, etc...)
 *  I am being given at my job a decision could not be made on which algorithm I would want to implement.
 *  There are positives and negatives to implementing both and it depends greatly on what exactly the job that needs to be ran fast.
 */

public class runtimeSearchAlgorithms {

    public static void main(String[] args) {
        /**
         * CONTROL THE SIZE OF THE ARRAY AND SELECT TARGET NUMBER IN THE TWO LINES BELOW THIS COMMENT
         */
        int targetNumberTwo = 70000000;   // 70,000,000
        int arraySize = 100000000;       // 100,000,000

        int[] test = createNewArray(arraySize);

        long startTime1 = System.currentTimeMillis();
        boolean results = linearSearch(test, targetNumberTwo, arraySize);

        long endTime1 = System.currentTimeMillis();
        long linearAlgoTime = endTime1 - startTime1;
        System.out.println("**** Linear Search ****");
        if (results == true){
            System.out.println("Input: n = " + test.length + ", Target: " + targetNumberTwo + " found in " + linearAlgoTime + " microseconds");
        }
        else
            System.out.println("Target was not found in " + linearAlgoTime + " microseconds");

        System.out.println("**** Binary Search ****");
        long startTime2 = System.currentTimeMillis();
        Arrays.sort(test, 0, test.length);
        boolean binaryResults = binarySearch(test, targetNumberTwo, 0, arraySize);
        long endTime2 = System.currentTimeMillis();
        long binaryAlgoTime = endTime2 - startTime2;
        if (binaryResults == true){
            System.out.println("Input: n = " + test.length + ", Target: " + targetNumberTwo + " found in " + binaryAlgoTime + " microseconds");
        }
        else
            System.out.println("Target was not found in "+ binaryAlgoTime + " microseconds");
    }

    public static int[] createNewArray(int inputNumber){
        int counter = 0;
        int[] unsortedArray = new int[inputNumber];

        while (counter < inputNumber){
            Random rand = new Random();
            unsortedArray[counter] = rand.nextInt(inputNumber);

            counter++;
        }
        return unsortedArray;
    }


    /**
     * LinearSearch Method:
     *   Iterate through the entire array
     *   return true if iteration data matched target
     *   return false if the target is not found
     *
     *   QUESTION #4: ASYMPTOTIC RUNTIME: The asymptotic runtime for a linear search through a list is O(n).
     *      The use of a single for loop that has a constant number of operations within
     */
    public static boolean linearSearch(int[] data, int target, int size){
        for(int i = 0; i <= (size-1); i++ ) {
            if (data[i] == target) {
                return true;
            }
        }
        return false;
    }


    /**
     *  Recursive Binary Search Algorithm
     *  Page 197
     *
     *   QUESTION #5: ASYMPTOTIC RUNTIME: The complexity of this algorithm without sorting being included is O(log(n)).
     *   All of the operations are constant besides the recursive calls that are utilizing the constant operations that
     *   narrow down the parameters of where we are looking within the data for our target.
     *
     *   PS: While comparing the Linear Search to the Binary it is essential to know whether the data is being accessed
     *   as sorted or unsorted. This is essential because sorting is of complexity O(nlog(n)) which would make it less
     *   efficient than a linear search when the data is not pre-sorted.
     */
    public static boolean binarySearch(int[] data, int target, int lowerBound, int upperBound){
        if (upperBound < lowerBound){
            return false;
        }
        else {
            int mid = lowerBound + ((upperBound-lowerBound) / 2);

            if (data[mid] == target)
                return true;
            else if (target < data[mid]) {
                return binarySearch(data, target, lowerBound, mid - 1);
            }
            else{
                return binarySearch(data, target, mid + 1, upperBound);
            }
        }
    }
}


