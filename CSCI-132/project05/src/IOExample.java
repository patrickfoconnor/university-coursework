/**
 * Take a look at this example and experiment to see how to use IO for file handling.
 *
 * Notice, running this program will create a file called vacation.txt, in your workspace.
 * If you don't see it in your IDE, you can use your file system to navigate to it.
 */

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

public class IOExample
{
    public static void main (String [] args)
    {
    	// Scanner object is constructed with System.in for user input
        Scanner userInput = new Scanner(System.in);
        String destination;
        int year;

        // Create and write to a file
        try
        {
        	// Create a variable of type PrintWriter to store userInput to a file
            PrintWriter outputFile = new PrintWriter (new FileWriter ("vacation.txt"));

            // Loop a couple of times, and write some lines of text to the new file.
            for (int i = 0; i < 2; i++)
            {
                System.out.print("Destination> ");
                destination = userInput.nextLine();
                System.out.print("Year> ");
                year  = userInput.nextInt();
                userInput.nextLine();
             // Use the output stream as you would use System.out to display to the console
                outputFile.println(destination + " " + year);
            }
            outputFile.close();
        }
        catch (IOException exc)
        {
            System.out.println("There was a problem opening the file for output");
        }


        // Open and read from a file
        try
        {
        	// Scanner object is constructed with new File() for file input
            Scanner fileInput = new Scanner (new File ("vacation.txt" ));
            while (fileInput.hasNext())	// check  for next token (String)
            {
                destination = fileInput.next();	// now get that String into the destination variable
                year = fileInput.nextInt();		// and get that int into the year variable
                System.out.println(destination + " " + year);
            }
            fileInput.close();
        }
        catch (FileNotFoundException exc)
        {
            System.out.println("There was a problem opening the input file");
        }
    }
}
