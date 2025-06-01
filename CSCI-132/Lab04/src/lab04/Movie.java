package lab04;

/**
 * Author: Patrick O'Connor
 * Modified: July 22, 2020
 */


public class Movie{


    /**
     * Initialize the necessary fields
     */
    public String title = null;
    public Integer year;


    /**
     * Create get Methods for title and year of Movie
     */
    public String getTitle(){
        return this.title;
    }

    public Integer getYear(){
        return this.year;
    }


    /**
     * Constructors
     */
    public Movie(String title, int year){
        this.title = title;
        this.year = year;
    }


    /**
     * Use string builder to create a string that is formatted like
     * --title, year-- +..next object
     * return as a String
     */
    public String toString() {
        return ("—" + getTitle() + ", " + getYear() + "—");
    }


}
