package Project03;

public abstract class Shape {
    String color;

    /**
     * Create get and set methods for setting and changing private color variable
     */
    public String getColor(){
        return this.color;
    }

    public void setColor(String userColor){
        this.color = userColor;
    }


    /**
     * Create the three abstract methods that will be called
     */
    abstract double area();
    abstract double perimeter();
    public abstract String toString();
}
