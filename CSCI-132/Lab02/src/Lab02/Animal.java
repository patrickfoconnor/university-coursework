/**
 * Author: Patrick O'Connor
 * Date Modified: July 8, 2020
 *  Build a java class and a corresponding driver file
 *  Assignment: Lab02
  */

package Lab02;

public class Animal {

    /**
     * Create Three Private Data Members (name, age, and species)
     */
    private String name;
    private int age;
    private String species = "Unknown";

    /** Constructors: Create two
      *  One for when no information is given
      *  The other for when the three argumentss are given
     */
    public Animal(){
        name = "Unnamed";
        age = -1;
        species = "Unknown";
    }


    public Animal(String animalName, int animalAge, String animalSpecies){
        this.name = animalName;
        this.age = animalAge;
        this.species = animalSpecies;
    }
    /**


/**
 * Methods:
 * (One for getting value & One for setting the value)
     * For each set(): use this.ITEM = inputted name
     * For each get(): return the set value(this.item)
 */

/**
 * Name() Get and Set for Animal
 */
    public void setName(String newName){
        this.name = newName;
    }

    public String getName(){
        return this.name;
    }

/**
 * Age() Get and Set  for Animal
 */
    public void setAge(int newAge){
        this.age = newAge;
    }

    public int getAge(){
        return this.age;
    }

/**
 * Species() Get and Set for Animal
 */
    public void setSpecies(String newSpecies){
        this.species = newSpecies;
    }

    public String getSpecies(){
        return this.species;
    }

/**
 * Create the formatted information String in the following format
 * Name: Tommy, Age: 45, Species: Squirrel
 * Print out the created statement
 */
    public void print(){
        String formattedStatement = ("Name: " + this.name + ", Age: " + this.age + ", Species: " + this.species + "\n");
        System.out.print(formattedStatement);
    }

}