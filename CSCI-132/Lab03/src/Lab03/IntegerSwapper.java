/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Lab03;

import java.util.Arrays;

/**
 * Warm up: Make a Movie class with...
 * fields: String title, and Integer year
 * methods: getTitle() returns a String, getYear() returns an int, toString returns a string.
 *
 * For the lab, create a new class called GenericSwapper that can swap elements from
 * any sort of array of objects, regaurtless of type. Test with Integers, Strings, and Movies.
 *
 * Hint! Check out page 95 of the book for a syntax example of how a generic
 * array can be reversed. (You could even try dropping that in here and testing it.)
 *
 */

// TODO make this generic... Copy this file and rename it GenericSwapper, then complete the following...
public class IntegerSwapper {

    // swaps integers a and b in the data array
    // TODO make it swap object a and b in the data array
    public static void swap(Integer[] data, int a, int b) {
        // TODO use exception handling in case a or b is out of bounds
        // in that case, output, "No swap happened because a requested index does not exist."

        int temp = data[a];
        data[a] = data[b];
        data[b] = temp;

    }

    public static void main(String[] args) {

        // testing swap() method with an array of Integers
        Integer[] myIntegers = new Integer[10];
        for (int i = 0; i < myIntegers.length; i++) {
            myIntegers[i] = (i + 1) * 5;
        }
        System.out.println(Arrays.toString(myIntegers));

        GenericSwapper.swap(myIntegers, 0, 9);
        System.out.println(Arrays.toString(myIntegers));


        // TODO make the swap() method generic as described above, then:

        // TODO test with an array of Strings...

        // TODO test with an array of Movies...

    }

}
