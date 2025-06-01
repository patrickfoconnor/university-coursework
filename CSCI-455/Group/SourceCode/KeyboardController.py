import tkinter as tk

from TangoRobot import *
import time


class KeyboardController:
    def __init__(self):
        # tkinter window
        self.win = tk.Tk()
        # setup keybindings
        self.win.bind('<Up>', self.arrows)  # key code: 111
        self.win.bind('<Down>', self.arrows)  # key code: 116
        self.win.bind('<Left>', self.arrows)  # key code: 113
        self.win.bind('<Right>', self.arrows)  # key code: 114
        self.win.bind('<space>', self.stop)  # key code: 65
        self.win.bind('<w>', self.head)  # key code: 25
        self.win.bind('<s>', self.head)  # key code: 39
        self.win.bind('<a>', self.head)  # key code: 38
        self.win.bind('<d>', self.head)  # key code: 40
        self.win.bind('<z>', self.waist)  # key code: 52
        self.win.bind('<c>', self.waist)  # key code: 54
        self.win.bind('<Shift_L>', self.waist)  # key code: 23
        self.win.bind('<Tab>', self.waist)  # key code: 50
        # start tkinter window
        self.win.mainloop()

    def arrows(self, event):
        keycode = event.keycode
        if keycode == 111:
            print("Up Arrow")
            time.sleep(.2)
            robot.driveForward()
        elif keycode == 116:
            print("Down Arrow")
            time.sleep(.2)
            robot.driveBackward()
        elif keycode == 113:
            print("Left Arrow")
            time.sleep(.2)
            robot.turnLeft()
        elif keycode == 114:
            print("Right Arrow")
            time.sleep(.2)
            robot.turnRight()

    def waist(self, event):
        keycode = event.keycode
        if keycode == 52:
            print("Z (Left)")
            time.sleep(.2)
            robot.waistLeft()
        elif keycode == 54:
            print("C (Right)")
            time.sleep(.2)
            robot.waistRight()

    def head(self, event):
        keycode = event.keycode
        if keycode == 25:
            print("W: Head Up")
            time.sleep(.2)
            robot.headUp()
        elif keycode == 39:
            print("S: Head Down")
            time.sleep(.2)
            robot.headDown()
        elif keycode == 38:
            print("A: Head Left")
            time.sleep(.2)
            robot.headLeft()
        elif keycode == 40:
            print("D: Head Right")
            time.sleep(.2)
            robot.headRight()

    def stop(self, event):
        keycode = event.keycode
        if keycode == 65:
            print("Space: Kill Switch")
            robot.resetRobot()

    def shoulder(self, event):
        keycode = event.keycode
        if keycode == 23:
            print("Left tab: Shoulder Up")
            time.sleep(.2)
            robot.adventureAttack()
        elif keycode == 50:
            print("Left Shift: Shoulder Down")
            time.sleep(.2)
            #robot.headDown()

KeyboardController()
