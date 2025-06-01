import turtle

window = turtle.Screen()

def draw_Square(turt, total_boxes,sides):
    
    for i in range(total_boxes):
        turt.left(90)
        turt.forward(sides)
                
        turt.right(90)
        turt.forward(sides)

        turt.right(90)
        turt.forward(sides)

        turt.right(90)
        turt.forward(sides)

        #turt.hideturtle()
        turt.penup()
        turt.right(180)
        turt.forward(2*sides)
        turt.pendown()
        #turt.showturtle()

def main():
    
    turtleW = turtle.Turtle()
    amount = int(input("Enter the number of squares you would like: "))
    length = int(input("Enter the side length: "))
    
    draw_Square(turtleW, amount, length)

main()
window.exitonclick()
