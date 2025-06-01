package Project02;

public class RegularHexagon implements RegularPolygon{

    /**
     * Create variable that will be used to take the input from Test File
     * and use it throughout following calculations
     */
    private double side;

    /**
     * Use variable side and assign it the double value of side Length
     */

    public RegularHexagon(double sideLength){
        side = sideLength;
    }

    /**
     * Break the equations for area and perimeter into small digestible pieces that can
     * be calculated. Return the calculated value
     */

    public double area() {
        double numerator = 3 * Math.sqrt(3);
        double denominator = 2;
        double sideSquared = Math.pow(side, 2);
        double regularHexagonArea = (numerator / denominator) * sideSquared;
        return regularHexagonArea;
    }

    public double perimeter() {
        double regularHexagonPerimeter = 6 * side;
        return regularHexagonPerimeter;
    }
}
