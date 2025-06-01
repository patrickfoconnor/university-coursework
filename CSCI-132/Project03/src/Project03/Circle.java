package Project03;

import java.lang.Math;

public class Circle extends Shape{


    /**
     * Create private variables that will be used to take the input from Test File
     */
    private double radius;
    private double circleArea;
    private double circlePerimeter;


    /**
     * Base Circle method for assigning values and utilizing the setters and getters
     */
    public Circle(double inputRadius, String inputColor){
        radius = inputRadius;
        setColor(inputColor);
        this.color = getColor();
        this.circleArea = area();
        this.circlePerimeter = perimeter();
    }


    /**
     * Create two secondary methods that will return a value when null is given
     */
    public Circle(double inputRadius){
        this(inputRadius, "White");
    }

    public Circle(String inputColor){
        this(1, inputColor);
    }


    /**
     * Calculate the area and perimeter
     * Returning the calculated values
     */
    public double area(){
        double numberTwo = 2.0;
        double calculatedCircleArea = Math.PI * (Math.pow(radius, numberTwo));
        return calculatedCircleArea;
    }

    public double perimeter(){
        double calculatedCirclePerimeter = 2 * (Math.PI * radius);
        return calculatedCirclePerimeter;
    }


    /**
     * Override the java.toString() with the given format for each shape
     */
    public String toString(){
        String shapeLine = ("• Shape: Circle \n");
        String shapeColor = ("• Color: " + color + "\n");
        String shapeRadius = ("• Radius: " + radius+ "\n");
        String shapeArea = ("• Area: " + circleArea + "\n");
        String shapePerimeter = ("• Perimeter: " + circlePerimeter + "\n");
        return shapeLine + shapeColor + shapeRadius + shapeArea + shapePerimeter;
    }
}
