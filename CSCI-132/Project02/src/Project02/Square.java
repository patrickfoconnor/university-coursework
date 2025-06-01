package Project02;

public class Square implements RegularPolygon{

    /**
     * Create variable that will be used to take the input from Test File
     * and use it throughout following calculations
     */
    private double side;

    /**
     * Use variable side and assign it the double value of side Length
     */
    public Square(double sideLength){
        side = sideLength;
    }

    /**
     * Break the equations for area and perimeter into small digestible pieces that can
     * be calculated. Return the calculated value
     */
    public double area() {
        double squareArea = Math.pow(side, 2);
        return squareArea;
    }

    public double perimeter() {
        double squarePerimeter = 4 * side;
        return squarePerimeter;
    }
}
