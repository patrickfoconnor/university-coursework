import turtle
import random

window = turtle.Screen()

window.title("Turtle 500")
window.bgcolor("forestgreen")

# Initialize the turtles

# Turtle Number 1
racer_1 = turtle.Turtle()
racer_1.up()
racer_1.shape("turtle")
racer_1.color(random.random(), random.random(), random.random())
racer_1.goto(-200, 100)
racer_1.down()
racer_1.stamp()

# Turtle Number 2
racer_2 = turtle.Turtle()
racer_2.up()
racer_2.shape("turtle")
racer_2.color(random.random(), random.random(), random.random())
racer_2.goto(-200, 0)
racer_2.down()
racer_2.stamp()

# Turtle Number 3
racer_3 = turtle.Turtle()
racer_3.up()
racer_3.shape("turtle") 
racer_3.color(random.random(), random.random(), random.random())
racer_3.goto(-200, -100)
racer_3.down()
racer_3.stamp()

# Announce Winner
winner = turtle.Turtle()
winner.color("black")
winner.shape("blank")

#Run the turtles through the race
for i in range(10):
    racer_1.forward(random.randint(1, 40))
    racer_1.dot()
    racer_2.forward(random.randint(1, 40))
    racer_2.dot()
    racer_3.forward(random.randint(1, 40))
    racer_3.dot()

#Decide who is the winner of the race
if racer_1.xcor() > racer_2.xcor() and racer_1.xcor() > racer_3.xcor():
    style = ("Courier", 30, 'italic')
    winner.write('#1 Wins!', font=style, align='center')
    for i in range(3):
        racer_1.circle(30)
    print("Turtle racer #1 wins!")

elif racer_2.xcor() > racer_1.xcor() and racer_2.xcor() > racer_3.xcor():
    style = ("Courier", 30, 'italic')
    winner.write('#2 Wins!', font=style, align='center')
    for i in range(3):
        racer_2.circle(30)
    print("Turtle racer #2 wins!")

else:
    style = ("Courier", 30, 'italic')
    winner.write('#3 Wins!', font=style, align='center')
    for i in range(3):    
        racer_3.circle(30)
    print("Turtle racer #3 wins!")
                      


window.exitonclick()
    
