# 300 by 300
# sides should be 100 in length
import turtle

window = turtle.Screen()
turt = turtle.Turtle()

sides = 100

turt.fillcolor("red")

turt.begin_fill()
for i in range(3):
    turt.right(90)
    turt.forward(sides)

for i in range(3):
    turt.left(90)
    turt.forward(sides)
    turt.right(90)
    turt.forward(sides)
    turt.right(90)
    turt.forward(sides)
turt.end_fill()

turt.hideturtle()

window.exitonclick()