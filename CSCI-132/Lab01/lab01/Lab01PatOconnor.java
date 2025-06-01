package lab01;

/**
 * Author: Patrick O'Connor
 * Last Modified: June 29, 2020
 * Create a program that outputs a top five list.
 * Modify this list of strings with the user inputted
 * new number one.
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Lab01PatOconnor {
    public static void main(String[] args) {


        /** ******************** Part A ****************************
         * Create and display a starting list of strings
         * to work with throughout lab
         */
        ArrayList<String>topFiveSports = new ArrayList<String>();
        topFiveSports.add("Football");
        topFiveSports.add("Baseball");
        topFiveSports.add("Basketball");
        topFiveSports.add("Ice Hockey");
        topFiveSports.add("Soccer");

        System.out.println("**** top five sports(USA) ****");


        /** ******************** Part B ****************************
         * Display the initial top five sports using for loop
         */
        for (String sport : topFiveSports) {
            System.out.println(sport);
        }


        /** ******************** Part C ****************************
         * Ask for user input, store this value as a String
         * Display user input and close Scanner
         */
        Scanner input = new Scanner(System.in);

        System.out.println("Type the new number 1: ");

        // put the next thing the user types into a String variable called userInput
        String userInputNumbOne = input.next();

        input.close();

        System.out.println("You typed " + userInputNumbOne);


        /** ******************** Part D ****************************
         * Add the user input to index zero, remove index 5
         * Print out the edited top five sports title and list
         */
        topFiveSports.add(0, userInputNumbOne);
        topFiveSports.remove(5);

        System.out.println("**** the new top five ****");

        for (String sport : topFiveSports) {
            System.out.println(sport);
        }
    }
}