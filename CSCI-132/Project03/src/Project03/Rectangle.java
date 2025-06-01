package Project03;

public class Rectangle extends Shape{


    /**
     * Create private variables that will be used to take the input from Test File
     */
    private double height;
    private double width;
    private double area;
    private double perimeter;


    /**
     * Base Rectangle method for assigning values and utilizing the setters and getters
     */
    public Rectangle(double inputWidth, double inputHeight, String inputColor) {
        width = inputWidth;
        height = inputHeight;
        setColor(inputColor);
        this.color = getColor();
        this.area = area();
        this.perimeter = perimeter();
    }


    /**
     * Create two secondary methods that will return a value when null is given
     */
    public Rectangle(double sideLength, double sideWidth) {
        this(sideLength,  sideWidth, "White");
    }

    public Rectangle(double sideWidth, String inputColor) {
        this(sideWidth, 1, inputColor);
    }


    /**
     * Calculate the area and perimeter
     * Returning the calculated values
     */
    public double area() {
        double rectangleArea = height * width;
        return rectangleArea;
    }

    public double perimeter() {
        double rectanglePerimeter = (height * 2) + (width * 2);
        return rectanglePerimeter;
    }


    /**
     * Override the java.toString() with the given format for each shape
     */
    public String toString(){
        String shapeLine = ("• Shape: Rectangle \n");
        String shapeColor = ("• Color: " + color + "\n");
        String shapeWidth = ("• Width: " + width+ "\n");
        String shapeHeight = ("• Height: " + height+ "\n");
        String shapeArea = ("• Area: " + area + "\n");
        String shapePerimeter = ("• Perimeter: " + perimeter + "\n");
        return shapeLine + shapeColor + shapeWidth + shapeHeight + shapeArea + shapePerimeter;
    }
}

