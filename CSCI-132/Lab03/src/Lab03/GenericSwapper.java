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

/**
 * Author: Patrick O'Connor
 * Modified: July 15, 2020
 */
package Lab03;

import java.util.ArrayList;
import java.util.Arrays;

public class GenericSwapper {

    /**
     * Place <T> to make the input type of array flexible to generic data types
     */
    public static <T> void swap(T[] data, int first, int second) {
        /**
         * At first try and complete the swap( replace index[first] with index[second] and vice versa
         */
        try {
            T temp = data[first];
            data[first] = data[second];
            data[second] = temp;
        }
        /**
         * If the given index is out of range return exception statement below
         */
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("No swap happened because a requested index does not exist.");
        }
    }

    public static void main(String[] args) {

        /**
         * Utilizing the IntegerSwap code snippet
         * create 10 integers
         * Swap index 0(5) with index 9(50)
         */
        Integer[] myIntegers = new Integer[10];
        for (int i = 0; i < myIntegers.length; i++) {
            myIntegers[i] = (i + 1) * 5;
        }
        System.out.println(Arrays.toString(myIntegers));

        GenericSwapper.swap(myIntegers, 0, 9);
        System.out.println(Arrays.toString(myIntegers));

        /**
         * Create an array of strings that will be swapped
         */
        String[] myStrings = new String[5];
        myStrings[0] = "Java";
        myStrings[1] = "Data Structures and";
        myStrings[2] = "Algorithms";
        myStrings[3] = "with";
        myStrings[4] = "Learning";

        /**
        * Print out the original array of strings
         * Swap index 0 ("Java") and index 4("Learning")
        */
        System.out.println(Arrays.toString(myStrings));
        GenericSwapper.swap(myStrings, 0, 4);
        System.out.println(Arrays.toString(myStrings));

        /**
         * Create 5 Movie object with the class created
         */
        Movie movie1 = new Movie("LoR Two Towers", 1997);
        Movie movie2 = new Movie("Superman", 1983);
        Movie movie3 = new Movie("Irishman", 2020);
        Movie movie4 = new Movie("Life of Pi", 2012);
        Movie movie5 = new Movie("Bird Box", 2018);

        /**
         * Create an array of Movie objects that will be swapped
         */
        Movie[] myMovies = new Movie[5];
        myMovies[0] = movie1;
        myMovies[1] = movie2;
        myMovies[2] = movie3;
        myMovies[3] = movie4;
        myMovies[4] = movie5;

        /**
         * Iterate through the array of Movie printing out each of the movies
         * with the toString() and separating them with a comma
         */
        for (Movie m: myMovies)
            System.out.print(m + ", ");
        System.out.println("");

        /**
         * Swap Movie object 0("LoR Two Towers", 1997) and Movie object 3("Life of Pi", 2012)
         */
        GenericSwapper.swap(myMovies, 0, 3);

        /**
         * Iterate through the swapped list
         */
        for (Movie m: myMovies)
            System.out.print(m + ", ");
    }

}
