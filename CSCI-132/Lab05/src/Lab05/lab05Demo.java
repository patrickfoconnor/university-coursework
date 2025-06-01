package Lab05;

/**
 * Author: Patrick O'Connor
 * Date Modified: July 29, 2020
 * Some of the code below was taken from page 232 in Data Structures & Algorithms Ed.5
 */


public class lab05Demo {
    public static void main(String[] args) {

        /**
         * Integer Stack
         */
        System.out.println("Creating a Stack of Integers");
        Stack<Integer> integerStack = new LinkedStack<>();
        integerStack.push(5);
        integerStack.push(3);
        System.out.println("Size: " + integerStack.size());
        System.out.println("Element Popped: " + integerStack.pop());
        System.out.println("The Stack is Empty: " + integerStack.isEmpty());
        System.out.println("Element Popped: " + integerStack.pop());
        System.out.println("The Stack is Empty: " + integerStack.isEmpty());
        System.out.println("Element Popped: " + integerStack.pop());

        integerStack.push(7);
        integerStack.push(9);
        System.out.println("Top Element: " + integerStack.peek());
        integerStack.push(4);
        System.out.println("Size: " + integerStack.size());
        System.out.println("Element Popped: " + integerStack.pop());
        integerStack.push(6);
        integerStack.push(8);
        System.out.println();

        /**
         * String Stack
         */
        System.out.println("Creating a Stack of Strings");
        Stack<String> stringStack = new LinkedStack<>();
        stringStack.push("Green");
        stringStack.push("Red");
        System.out.println("Size: " + stringStack.size());
        System.out.println("Element Popped: " + stringStack.pop());
        System.out.println("The Stack is Empty: " + stringStack.isEmpty());
        System.out.println("Element Popped: " + stringStack.pop());
        System.out.println("The Stack is Empty: " + stringStack.isEmpty());
        System.out.println("Element Popped: " + stringStack.pop());

        stringStack.push("Yellow");
        stringStack.push("Purple");
        System.out.println("Top Element: " + stringStack.peek());
        stringStack.push("Black");
        System.out.println("Size: " + stringStack.size());
        System.out.println("Element Popped: " + stringStack.pop());
        stringStack.push("White");
        stringStack.push("Blue");
        System.out.println();
    }
}
