/**
 * Author: Patrick O'Connor
 * Project: CSCI-132 P1 Calculator
 * Last Modified: July 6th, 2020
 */

package Project01;

import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    // FIELDS
    private double answer;
    private Scanner scannedInput = new Scanner(System.in);
    /**
     * assigning roughly pi to a final double to make it immutable
     */
    public final double ROUGHLY_PI = 3.14159;
    public final String usageInstructions = "Valid operations are: \n"
            + " + \t add \n - \t subtract \n * \t multiply \n / \t divide \n"
            + " c \t clear  \n neg \t negate \n % \t percent \n"
            + " ^ \t raise to power of next value entered \n inv \t invert the current value \n"
            + " rand \t radomize current value by a fractional amount \n"
            + " round \t round to number of places given next \n" + " = \t print answer \n ? \t Help \n q \t Quit \n";

    // CONSTRUCTORS
    Calculator() {
        this.answer = 0;
    };

    // METHODS
    // prompt user for a double and check before returning
    public double getUserNum() {
        boolean validNumber = false;
        double userVal = 0;

        System.out.print("Enter number\n>>> ");

        while (validNumber == false) {
            if (scannedInput.hasNext("pi")) {
                userVal = ROUGHLY_PI;
                scannedInput.next();
                validNumber = true;
            } else if (scannedInput.hasNextDouble()) {
                userVal = scannedInput.nextDouble();
                validNumber = true;
            } else {
                System.out.print("That's not a number. \nEnter a valid number\n>>> ");
                scannedInput.next();
            }
        }
        return userVal;
    }

    // prompt user for operation and check before returning
    public String getUserOp() {
        String op;

        System.out.print("Enter operation\n>>> ");
        op = scannedInput.next();
        while (!(this.checkUserOp(op))) {
            op = scannedInput.next();
        }
        return op;
    }

    // private helper method for getUserInput()
    private boolean checkUserOp(String op) {
        if (op.equals("+") || op.equals("-") || op.equals("=") || op.equals("?") || op.equals("Q") || op.equals("q")
                || op.equals("*") || op.equals("/") || op.equals("c") || op.equals("C") || op.equals("neg")
                || op.equals("%") || op.equals("^") || op.equals("inv") || op.equals("rand") || op.equals("round")) {
            return true;
        } else {
            System.out.print("Invalid Entry. Enter '?' for help." + "\nEnter a valid operation \n>>> ");
            return false;
        }
    }

    public double calculateAnswer(String op, double num) {
        switch (op) {
            case "+":
                add(num);
                break;
            case "-":
                subtract(num);
                break;
            case "*":
                multiply(num);
                break;
            case "/":
                divide(num);
                break;
            case "c":
            case "C":
                clearAnswer();
                break;
            case "neg":
                neg(num);
                break;
            case "%":
                percentOfNumber(num);
                break;
            case "^":
                pow(num);
                break;
            /**
             * Find the inverse of the number
             */
            case "inv":
                inverse(num);
                break;
            /**
             * add or subtract some random decimal amount between 0 and 1
             */
            case "rand":
                // TODO:
                randomNum(num);
                break;

            case "round":
            /**
            * round to a given number of decimal places
            */
                round(num);
                break;
            case "=":
                printAnswer();
                break;
            case "?":
                System.out.println(this.usageInstructions);
                break;
            default:
                System.out.println("Invalid Operator");
        }
        return this.answer;
    }

    public double add(double operand) {
        this.answer += operand;
        return this.answer;
    }

    public double subtract(double operand) {
        this.answer -= operand;
        return this.answer;
    }

    public double multiply(double operand){
        this.answer *= operand;
        return this.answer;
    }

    public double divide(double operand){
        this.answer /= operand;
        return  this.answer;
    }
/**
* Set answer to zero. Clearing any values that were present
 */
    public double clearAnswer(){
        this.answer = 0.0;
        return this.answer;
    }

/**
* Multiply by -1 in order to flip the sign
*/
    public double neg(double operand){
        this.answer = operand * (-1);
        return this.answer;
    }

    /**
     * Find the percentage the answer is of 1 by dividing by 100
     */

    public double percentOfNumber(double operand){
        this.answer = (operand / 100);
        return this.answer;
    }

    /**
     * Find the inverse by using the reciprocal of the number
     */

    public double inverse(double operand){
        this.answer = 1/operand;
        return this.answer;
    }

    /**
     * Utilize the math module provided to raise num to power
     */

    public double pow(double operand){
        this.answer = Math.pow(this.answer, operand);
        return this.answer;
    }
    /**
     * Utilize the Big Decimal module and round to specific decimal point always rounding up if decimal >= .5
     */
    public double round(double operand){
        BigDecimal setUpRound = new BigDecimal(this.answer).setScale((int) operand, RoundingMode.HALF_UP);
        this.answer = setUpRound.doubleValue();
        return this.answer;
    }

    public double getAnswer() {
        return this.answer;
    }
    /**
     * Based on random number either 0 or 1
     * either add or subtract the random duble
     */
    public double randomNum(double operand){
        double changeDecider = new Random().nextInt(1);
        double changeNum = new Random().nextDouble();
        if (changeDecider == 1) {
            this.answer += changeNum;
        }

        else{
            this.answer -= changeNum;
        }
        return this.answer;
    }


    public void printAnswer() {

        double solution = this.answer;
/**
 * Using unicode chars create the output box
 * Left justify the side piece on the left to one place
 * Create a body with solution and side
 *      Right justify this to 26 spaces, add footer and print the three lines
 */
        String header = "\n┌──────────────────────────┐ \n";
        String side = "|";
        String body = solution + " " + side;
        String footer = "└──────────────────────────┘ \n";
        System.out.print(header);
        System.out.printf("%-1s %26s %n",side,body);
        System.out.print(footer);
    }

}
