/**
 * Author: Patrick O'Connor
 * Date Modified: August 3, 2020
 */

import java.util.Comparator;

/**
 * Comparator Interface from page 364
 */

public class stringLengthComparator<T> implements Comparator<T>{
    public int compare(T stringA, T stringB) {

        /**
         * Compare the length of string A to String B
         *      Return -1 if A < B
         *      Return  0 if A = B
         *      Return  1 if A > B
         */
        if (stringA.toString().length() < stringB.toString().length())
                return -1;
            else if (stringA.toString().length() == stringB.toString().length())
                return 0;
            else
                return 1;
        }
}

