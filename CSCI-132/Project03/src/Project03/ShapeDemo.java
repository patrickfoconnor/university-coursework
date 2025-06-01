package Project03;

/**
 * Author:Patrick O'Connor
 * Modified: July 20, 2020
 * CSCI - 132 - SMR 2020
 */

public class ShapeDemo {
    public static void main(String[] args) {
/**
 * THE FOLLOWING 12 TEST WILL PRODUCE CORRECT OUTPUT
 * THE FORMAT FOR TEST:
 * 1) All parameters filled (All values)     "Periwinkle"
 * 2) Parameter #1 missing (distance = 1)    "Orange"
 * 3) Parameter #2 missing (color = unknown) "White"
 *
 * After the 12 output producing test there are 4 color changes
 *
 * BELOW THAT ARE FOUR THAT DO NOT WORK COMMENTED OUT
 */

        /**
         * EQUILATERAL TRIANGLE
         */
        Shape fullTriangle = new EquilateralTriangle(3.5, "Periwinkle");
        System.out.println(fullTriangle);

        Shape noSideLengthTriangle = new EquilateralTriangle("Orange");
        System.out.println(noSideLengthTriangle);

        Shape noColorTriangle = new EquilateralTriangle(5);
        System.out.println(noColorTriangle);

        /**
         * SQUARE
         */
        Rectangle fullSquare = new Square(1, "Periwinkle");
        System.out.println(fullSquare);

        Rectangle noSideSquare = new Square("Orange");
        System.out.println(noSideSquare);

        Rectangle noColorSquare = new Square(5);
        System.out.println(noColorSquare);

        /**
         * RECTANGLE
         */
        Rectangle fullRectangle = new Rectangle(3.5, 4, "Periwinkle");
        System.out.println(fullRectangle);

        Rectangle noHeightRectangle = new Rectangle(1.5, "Orange");
        System.out.println(noHeightRectangle);

        Rectangle noColorRectangle = new Rectangle(2, 7.0);
        System.out.println(noColorRectangle);

        /**
         * CIRCLE
         */
        Circle fullCircle = new Circle(1, "Periwinkle");
        System.out.println(fullCircle);

        Circle noRadiusCircle = new Circle("Orange");
        System.out.println(noRadiusCircle);

        Circle noColorCircle = new Circle(5);
        System.out.println(noColorCircle);


        /**
         * Test the functionality of setting new color
         */
        System.out.println("Changing the colors of the shapes to Green: \n");
        noColorTriangle.setColor("Green");
        System.out.println(noColorTriangle);

        noColorSquare.setColor("Green");
        System.out.println(noColorSquare);

        noColorRectangle.setColor("Green");
        System.out.println(noColorRectangle);

        noColorCircle.setColor("Green");
        System.out.println(noColorCircle);
    }
}

/**
 *
 *
 *       Circle checkTriangle = new EquilateralTriangle(3.5, "Periwinkle");
 *       System.out.println(checkTriangle);
 *
 *       Rectangle checkCircle = new Circle(3.5, "Periwinkle");
 *       System.out.println(checkCircle);
 *
 *        Circle checkRectangle = new Rectangle(3.5, "Periwinkle");
 *        System.out.println(checkRectangle);
 *
 *        EquilateralTriangle checkSquare = new Square(3.5, "Periwinkle");
 *        System.out.println(checkSquare);
 *
 *   }
 *}
*/