/**
 * Author: Patrick O'Connor
 * Date Modified: July 8, 2020
 *  Build an java class and a corresponding driver class
 *  Assignment: Lab02
 */


package Lab02;

import java.util.Scanner;

public class AnimalDemo {
    public static void main(String[] args){
        /**
         * Call the Constructor animals
         * One with no arguments the other with three
         */
        Animal animal01 = new Animal();
        Animal animal02 = new Animal("Tommy", 45, "Squirrel");
        /**
         * Print animal one and two
         */
        animal01.print();
        animal02.print();

        /**
         * Create scanner object to get user input
         */
        Scanner userInfo = new Scanner(System.in);

        /**
         * Go through each argument(name, age, species)
         * For nextInt() use nextLine() to capture the token "\n" that is placed next to inputted int
         */
        System.out.println("\nEnter Animal Name: ");
        String inputName = userInfo.nextLine();

        System.out.println("Enter Animal Age: ");
        int inputAge = userInfo.nextInt();
        userInfo.nextLine();
        System.out.println("Enter Animal Species: ");
        String inputSpecies = userInfo.nextLine();

        /**
         * Create a new animal with user input via the animals constructor
         */
        Animal animal03 = new Animal(inputName, inputAge, inputSpecies);
        animal03.print();

    }
}