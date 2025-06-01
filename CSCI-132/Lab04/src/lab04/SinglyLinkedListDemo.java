package lab04;

/**
 * Author: Patrick O'Connor
 * Modified: July 22, 2020
 */


public class SinglyLinkedListDemo extends SinglyLinkedList {
    public static void main(String[] args) {

        /**
         * Initialize a new singly linked list of strings called airports
         */
        SinglyLinkedList<String> airports = new SinglyLinkedList<>();
        airports.addFirst("LAX");
        airports.addFirst("SFO");
        airports.addLast("SLC");
        airports.addLast("BOS");
        airports.addLast("BZN");
        System.out.println(airports);
        /**
         * Remove the first two elements from the Singly Linked List airports
         */
        airports.removeFirst();
        airports.removeFirst();
        System.out.println(airports);


        /**
         * Initialize a new singly linked list of Movie called watchlist
         * After creating watchlist print formatted form by calling println and toString
         */
        SinglyLinkedList<Movie> watchList = new SinglyLinkedList<>();
        watchList.addFirst(new Movie("Moonrise Kingdom", 2012));
        watchList.addFirst(new Movie("Alive", 2015));
        watchList.addLast(new Movie("PurpleHeart", 2018));

        System.out.println(watchList.toString());

        /**
         * Show off the get method
         * for airports (SLC, BOS, BZN)
         *                0    1    2
         * For watchlist/movies
         * (--Alive, 2015--, --Moonrise Kingdom, 2012--, --PurpleHeart, 2018--)
         *         0                    1                           2
         *
         * Also the final watchlist get throws an exception because requested index 5 is out of range
         */
        System.out.println(airports.get(1));
        System.out.println(airports.get(0));

        System.out.println(watchList.get(1));
        System.out.println(watchList.get(5));


    }
}
