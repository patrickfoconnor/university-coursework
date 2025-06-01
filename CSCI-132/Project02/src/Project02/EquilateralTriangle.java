package Project02;


public class EquilateralTriangle implements RegularPolygon{

    /**
     * Create variable that will be used to take the input from Test File
     * and use it throughout following calculations
     */
    private double side;

    /**
     * Use variable side and assign it the double value of side Length
     */
    public EquilateralTriangle(double sideLength){
        side = sideLength;
    }

    /**
     * Break the equations for area and perimeter into small digestible pieces that can
     * be calculated. Return the calculated value
     */
    public double area() {
        double fraction = (Math.sqrt(3))/4;
        double sideSquared = Math.pow(side, 2);
        double equilateralTriangleArea = fraction * sideSquared;
        return equilateralTriangleArea;
    }

    public double perimeter() {
        double trianglePerimeter = 3 * side;
        return trianglePerimeter;
    }
}
