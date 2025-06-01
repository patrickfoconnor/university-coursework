package Project02;

public class RegularPentagon implements RegularPolygon{

    /**
     * Create variable that will be used to take the input from Test File
     * and use it throughout following calculations
     */
    private double side;

    /**
     * Use variable side and assign it the double value of side Length
     */
    public RegularPentagon(double sideLength){
        side = sideLength;
    }

    /**
     * Break the equations for area and perimeter into small digestible pieces that can
     * be calculated. Return the calculated value
     */
    public double area() {
        double underSquareRoot = Math.sqrt(5 * (5+(2*Math.sqrt(5))));
        double sideSquared = Math.pow(side, 2);
        double regularPentagonArea = (.25)* underSquareRoot * sideSquared;
        return regularPentagonArea;
    }

    public double perimeter() {
        double regularPentagonPerimeter = 5 * side;
        return regularPentagonPerimeter;
    }
}
