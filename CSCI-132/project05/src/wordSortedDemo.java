/**
 * Author: Patrick O'Connor
 * Date Modified: August 3, 2020
 *
 * A driver that demonstrates:
 *      • Reading in a text file
 *      • All letters are converted to lowercase and all punctuation is removed
 *      • Sorting the words with MergeSort using Comparators
 *      √ • Outputting sorted words into a new text file (TITLED: SortedByLength.txt)
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

public class wordSortedDemo {

    public static <T> void main (String [] args) throws Exception
    {
        String string1;
        T string2;
        List<T> orderedList = new ArrayList<>(50);

        try
        {
            /**
             * Create scanner to read in the Housman.txt file
             * Use PrintWriter to create new output file to write to
             */
            Scanner fileInput = new Scanner (new File ("Housman.txt" ));
            PrintWriter outputFile = new PrintWriter (new FileWriter ("PunctuationRemoved.txt"));

            /**
             * While the input file has another string
             *      read in that string removing all non alphanumerical values and making all of the strings lowercase
             */
            while (fileInput.hasNext())
            {
                string1 = fileInput.next().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                System.out.println(string1);

                /**
                 * split the strings at any point that has one or more whitespaces
                  */
                String[] stripString1 = string1.split("\\s+");

            /**
             * use println to print the stripped string in a new file
            */
                outputFile.println(stripString1);
            }

            fileInput.close();
            outputFile.close();

        }
        catch (FileNotFoundException exc)
        {
            System.out.println("There was a problem opening the input file or writing to the output file");
        }


        try
        {
            /**
             * Create scanner to read in the PunctuationRemoved.txt file
             * Use PrintWriter to create new output file to write to
             */
            Scanner fileInput = new Scanner(new File("PunctuationRemoved.txt" ));
            PrintWriter outputFile = new PrintWriter (new FileWriter ("SortedByLength.txt"));

            /**
             * While the input file has another string
             *      read in that string removing all non alphanumerical values and making all of the strings lowercase
             */
            while (fileInput.hasNext()){


                string2 = (T) fileInput.nextLine();

                orderedList.add(string2);
            }

            /**
             * Call mergeSort on the new list
             * I could not get the comparator to end up working but Believe I have everything besides the final implementation complete and correct
             */
            MergeSort.mergeSort(orderedList, Comparator);
            fileInput.close();
            outputFile.close();
        }

        catch (IOException exc)
        {
            System.out.println("There was a problem opening the file for output");
        }

    }
}

