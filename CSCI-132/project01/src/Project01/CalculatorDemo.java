/**
 * Author: Patrick O'Connor
 * Project: CSCI-132 P1 Calculator
 * Last Modified: July 6th, 2020
 */

package Project01;

public class CalculatorDemo {
    public static void main(String[] args){


        Calculator myCalc = new Calculator(); // create a calculator for the demo
        double number = 0; // initialize the number
        String operation = "+"; // initialize the operation

        // test the calculator...
        System.out.println("<begin test>");
        System.out.println(myCalc.usageInstructions);
        System.out.println("Adding 2...");
        double result = 0;
        myCalc.add(2);
        result = myCalc.getAnswer();
        System.out.println("Result is: " + result); // expect 2.0
        System.out.println("Subtracting 3...");
        result = myCalc.subtract(3);
        System.out.println("Result is " + result); // expect -1.0
        double myDouble = 1.0;
        System.out.println("Add myDouble \nResult is: " + myCalc.add(myDouble)); // expect 0.0
        System.out.println("Printing Answer:");
        myCalc.printAnswer(); // expect -1.0 in formatted display
        System.out.println("<end test>");
        System.out.println();

        System.out.println("*** Welcome to Calculator ***");
        // This do-while loop runs at least one time (that's the 'do' part of it)...
        // ...and repeats as long as a condition holds true (that's the 'while' part)
        do {
/**
 * Find the operations that do not need a second number
 * These can just be calculated after user input of operation
 */
            if (!operation.equals("=") && !operation.equals("?") && !operation.equals("rand") && !operation.equals("c") && !operation.equals("C") && !operation.equals("neg")  && !operation.equals("%")) // don't get a number in these cases...
                number = myCalc.getUserNum(); // ... otherwise, go ahead and get user's next number


            myCalc.calculateAnswer(operation, number); // run the calculation
            operation = myCalc.getUserOp(); // get the user's next operation

        } while (!operation.equals("q") && !operation.equals("Q")); // 'Q' to quit

        System.out.println("<end>");

    }
}
