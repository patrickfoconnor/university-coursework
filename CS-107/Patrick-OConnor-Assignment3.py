# -----------------------------------------+
# Patrick O'Connor                         |
# CSCI 107, Assignment #3                  |
# Last Updated: September 20, 2019         |
#------------------------------------------|
# Use Turtle Graphics to recreate VG       |
#------------------------------------------+
#if you get File "<stdin>", line 1 use exit()

import turtle
#window setup
wn = turtle.Screen()
wn.bgcolor("white smoke")
wn.setup(width=350, height=350)

t = turtle.Turtle()
t.speed(1)
wn.tracer(0)
side = 10
t.pensize(1)
numSquares = 30

#Define three functions draw a square && move up a row && draw a row 
def drawSquare(color):
    t.color(color)
    t.begin_fill()
    for i in range(4):
        t.forward(side)
        t.right(90)
    t.end_fill()
    t.forward(side)

def nextRow():
    t.penup()
    t.backward(numSquares * side)
    t.left(90)
    t.forward(side)
    t.right(90)
    t.pendown()

#Utilize two list the length of row and the color of blocklen== length and colors== color you would like
def drawRow(colors, numbers):
    for j in range(len(colors)):
        for k in range(numbers[j]):
            drawSquare(colors[j])
    nextRow()

#Move turtle into position so drawing is centered
t.hideturtle()
t.penup()
t.goto(-160, -120)
t.showturtle()
t.pendown()

#Start drawing Crash using defined functions

#ROW #1
colors = ["white smoke", "black", "white smoke"]
numbers = [11,8,11]
drawRow (colors,numbers)

#ROW #2
colors = ["white smoke", "black", "dark orange", "wheat", "black"]
numbers = [10,1,1,7,1,10]
drawRow (colors,numbers)

#ROW #3
colors = ["white smoke", "black", "dark orange", "wheat", "black"]
numbers = [19,1,1,2,7]
drawRow (colors,numbers)

#ROW #4
colors = ["white smoke", "black", "dark orange", "wheat", "black", "white", "black"]
numbers = [18,1,1,2,1,6,1]
drawRow (colors,numbers)

#ROW #5
colors = ["white smoke", "black", "wheat", "black", "white", "black"]
numbers = [18,1,2,1,7,1]
drawRow (colors,numbers)

#ROW #6
colors = ["white smoke", "black", "dark orange", "wheat", "black", "white", "dim gray", "white", "black"]
numbers = [17,1,1,1,1,3,3,3,1]
drawRow (colors,numbers)

#ROW #7
colors = ["white smoke", "black", "wheat", "black", "white", "dim gray", "white", "black"]
numbers = [16,1,2,1,2,1,6,1]
drawRow (colors,numbers)

#ROW #8
colors = ["white smoke", "black", "dark orange", "wheat", "black", "white", "black"]
numbers = [15,1,1,1,1,11,1]
drawRow (colors,numbers)

#ROW #9
colors = ["white smoke", "black", "wheat", "black", "white", "black", "white", "black"]
numbers = [14,1,2,1,5,5,2,1]
drawRow (colors,numbers)

#ROW #10
colors = ["white smoke", "black", "dark orange", "wheat", "black", "white", "black", "wheat", "black" ]
numbers = [12,1,1,2,1,3,2,5,3]
drawRow (colors,numbers)

#ROW #11
colors = ["white smoke", "black", "dark orange", "wheat", "black", "wheat", "black" ]
numbers = [12,1,1,2,4,10,1]
drawRow (colors,numbers)

#ROW #12
colors = ["white smoke", "black", "dark orange", "wheat", "dark orange", "wheat", "black" ]
numbers = [11,1,2,7,5,4,1]
drawRow (colors,numbers)

#ROW #13
colors = ["white smoke", "black", "dark orange", "wheat", "dark orange", "wheat", "black" ]
numbers = [11,1,2,3,11,2,1]
drawRow (colors,numbers)

#ROW #14
colors = ["white smoke", "black", "dark orange","white", "dark orange","white","dark orange", "wheat", "black" ]
numbers = [10,1,5,3,3,2,4,2,1]
drawRow (colors,numbers)

#ROW #15
colors = ["white smoke", "black", "dark orange", "white", "black", "white", "dark orange", "black", "white", "black", "black" ]
numbers = [8,1,5,1,2,2,1,2,2,3,3]
drawRow (colors,numbers)

#ROW #16
colors = ["white smoke", "black", "dark orange", "black", "dark orange", "white", "black", "white", "dark orange", "black", "white", "white smoke", "black", "black"  ]
numbers = [7,1,1,2,3,1,2,2,1,2,3,3,1,3]
drawRow (colors,numbers)

#ROW #17
colors = ["white smoke", "black", "firebrick", "black", "dark orange","saddle brown", "white", "dark orange", "white", "black", "white smoke", "black", "silver", "black"  ]
numbers = [6,2,1,1,2,1,5,1,3,1,4,1,1,1]
drawRow (colors,numbers)

#ROW #18
colors = ["white smoke", "black", "firebrick", "black", "dark orange", "saddle brown", "white", "saddle brown", "dark orange", "saddle brown", "white", "saddle brown", "white smoke", "black" ]
numbers = [5,2,3,1,1,2,3,1,1,1,2,1,4,3]
drawRow (colors,numbers)

#ROW #19
colors = ["white smoke", "black", "firebrick", "black", "dark orange", "black", "saddle brown", "black", "saddle brown" ]
numbers = [5,1,3,1,1,2,4,3,3]
drawRow (colors,numbers)


#ROW #20
colors = ["white smoke", "black", "firebrick", "black", "dark orange", "black", "firebrick", "black", "saddle brown", "firebrick", "black", "saddle brown" ]
numbers = [11,2,1,2,1,1,2,2,2,3,1,1]
drawRow(colors,numbers)

#ROW #21
colors = ["white smoke", "black", "dark orange", "black", "firebrick", "black"]
numbers = [11,3,2,2,10,1]
drawRow(colors,numbers)

#ROW #22
colors = ["white smoke", "black", "dark orange", "black", "white smoke", "black", "firebrick", "black"]
numbers = [12,1,2,2,1,1,10,1]
drawRow(colors,numbers)

#ROW #23
colors = ["white smoke", "black", "white smoke", "black", "firebrick", "black", "firebrick", "black", "firebrick", "black", "firebrick", "black"]
numbers = [12,3,4,1,1,1,1,1,2,1,2,1]
drawRow(colors,numbers)


#ROW #25
colors = ["white smoke", "black", "firebrick", "black", "firebrick", "black", "firebrick", "black", "firebrick", "black"]
numbers = [19,1,1,1,1,2,2,1,2,1]
drawRow(colors,numbers)

#ROW #26
colors = ["white smoke", "black", "white smoke", "black", "white smoke", "black", "firebrick", "black", "firebrick", "black"]
numbers = [19,1,1,2,1,1,1,2,1,1]
drawRow(colors,numbers)

#ROW #27
colors = ["white smoke", "black", "white smoke", "black", "white smoke", "black" ]
numbers = [22,1,2,2,1,2]
drawRow(colors,numbers)

#ROW #28
colors = ["white smoke", "black", "white smoke", "black"]
numbers = [26,1,2,1]
drawRow(colors,numbers)

#set window to exit on user input
wn.exitonclick()
