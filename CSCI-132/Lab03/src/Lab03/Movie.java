package Lab03;

/**
 * Author: Patrick O'Connor
 * Modified: July 15, 2020
 */

public class Movie {
    /**
     * Initialize the necessary fields
     */
    public String title;
    public Integer year;

    /**
     * Create get Methods for title and year
     */
    public String getTitle(){
        return this.title;
    }

    public Integer getYear(){
        return this.year;
    }
    /**
     * Constructors and overriding the toString Method
     */
    public Movie(String title, int year){
        this.title = title;
        this.year = year;
    }

    public String toString(){
        return ("Title: " +  title + " Released: " + year);
    }
}
