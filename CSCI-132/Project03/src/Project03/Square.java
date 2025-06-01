package Project03;

public class Square extends Rectangle implements regularPolygon{


    /**
     * Create private variables that will be used to take the input from Test File
     */
    private double side;
    private double area;
    private double perimeter;


    /**
     * Base Square method for assigning values and utilizing the setters and getters
     */
    public Square(double sideLength, String inputColor){
        super(sideLength, inputColor);
        side = sideLength;
        setColor(inputColor);
        this.color = getColor();
        this.area = area();
        this.perimeter = perimeter();
    }


    /**
     * Create two secondary methods that will return a value when null is given
     */
    public Square(double sideLength){
        this(sideLength, "White");
    }

    public Square(String inputColor){
        this(1, inputColor);
    }


    /**
     * Calculate the area and perimeter
     * Returning the calculated values
     */
    public double area() {
        double squareArea = Math.pow(side, 2);
        return squareArea;
    }

    public double perimeter() {
        double squarePerimeter = 4 * side;
        return squarePerimeter;
    }

    /**
     * Override the java.toString() with the given format for each shape
     */
    public String toString(){
        String shapeLine = ("• Shape: Square \n");
        String shapeColor = ("• Color: " + color + "\n");
        String shapeSide = ("• Side: " + side + "\n");
        String shapeArea = ("• Area: " + area+ "\n");
        String shapePerimeter = ("• Perimeter: " + perimeter + "\n");
        return shapeLine + shapeColor + shapeSide + shapeArea + shapePerimeter;
    }

}

