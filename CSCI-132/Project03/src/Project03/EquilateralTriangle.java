package Project03;

import java.lang.Math;

public class EquilateralTriangle extends Shape implements regularPolygon{


    /**
     * Create private variables that will be used to take the input from Test File
     */
    private double side;
    private double area;
    private double perimeter;


    /**
     * Base EquilateralTriangle method for assigning values and utilizing the setters and getters
     */
    public EquilateralTriangle(double sideLength, String inputColor){
        this.side = sideLength;
        setColor(inputColor);
        this.color = getColor();
        this.area = area();
        this.perimeter = perimeter();
    }


    /**
     * Create two secondary methods that will return a value when null is given
     */
    public EquilateralTriangle(double sideLength){
        this(sideLength, "White");
    }

    public EquilateralTriangle(String inputColor){
        this(1, inputColor);
    }


    /**
     * Calculate the area and perimeter
     * Returning the calculated values
     */
    public double area() {
        double fraction = (Math.sqrt(3))/4;
        double sideSquared = Math.pow(side, 2);
        double equilateralTriangleArea = fraction * sideSquared;
        return equilateralTriangleArea;
    }

    public double perimeter() {
        double trianglePerimeter = 3 * this.side;
        return trianglePerimeter;
    }

    /**
     * Override the java.toString() with the given format for each shape
     */
    public String toString(){

        String shapeLine = ("• Shape: Equilateral Triangle \n");
        String shapeColor = ("• Color: " + color + "\n");
        String shapeSide = ("• Side: " + side + "\n");
        String shapeArea = ("• Area: " + this.area + "\n");
        String shapePerimeter = ("• Perimeter: "+ this.perimeter + "\n");
        return shapeLine + shapeColor + shapeSide + shapeArea + shapePerimeter;
    }

}
