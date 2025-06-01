# -----------------------------------------+
# Patrick O'Connor                         |
# CSCI 107, At-Home Practice               |
# Last Updated: January , 2020             |
#------------------------------------------|
# Create a racetrack for turtles to race   |
# around and declare a winner with text    |
# and a victory "burnout"                  |
#------------------------------------------+

# Import the necessary modules
import turtle
import random

# Set-up the initial screen
window = turtle.Screen()
window.title("Turtle 500")
window.bgcolor("forestgreen")

# Draw the track for the turtles to race on 
track = turtle.Turtle()
track.shape("blank")

# Outer Track
track.fillcolor("gray")
track.begin_fill()
for i in range(2):
    track.forward(150)
    track.circle(150,180)
track.end_fill()

    #Position turtle for drawing of outside of track
track.penup()
track.forward(40)
track.left(90)
track.forward(70)
track.right(90)
track.pendown()

# Inner Track
track.fillcolor("black")
track.begin_fill()
for i in range(2):
    track.forward(80)
    track.circle(80,180)
track.end_fill()

# Middle Stripe
track.penup()
track.forward(-20)
track.right(90)
track.forward(40)
track.left(90)
track.pendown()

track.color("yellow")

for i in range(2):
    track.forward(115)
    track.circle(115,180)


# Define the race function that each turtle will use


# Decide who is the winner of the race

window.exitonclick()