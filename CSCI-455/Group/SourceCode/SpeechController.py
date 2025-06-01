import speech_recognition as sr
from TangoRobot import *
import time

listening = True;


def arrows(command):
    if "forward" in command:
        print("Forward")
        time.sleep(.2)
        robot.driveForward()
    elif "back" in command:
        print("Backward")
        time.sleep(.2)
        robot.driveBackward()
    elif "left" in command:
        print("Left")
        time.sleep(.2)
        robot.turnLeft()
    elif "right" in command:
        print("Right")
        time.sleep(.2)
        robot.turnRight()


def waist(command):
    if "left" in command:
        print("Bend Left")
        time.sleep(.2)
        robot.waistLeft()
    elif "right" in command:
        print("Bend Right")
        time.sleep(.2)
        robot.waistRight()


def head(command):
    if "up" in command:
        print("Head Up")
        time.sleep(.2)
        robot.headUp()
    elif "down" in command:
        print("Head Down")
        time.sleep(.2)
        robot.headDown()
    elif "left" in command:
        print("Head Left")
        time.sleep(.2)
        robot.headLeft()
    elif "right" in command:
        print("Head Right")
        time.sleep(.2)
        robot.headRight()


def stop():
    print("Halting")
    robot.resetRobot()


while listening:
    with sr.Microphone() as source:
        r = sr.Recognizer()
        r.adjust_for_ambient_noise(source)
        r.dynamic_energythreshhold = 3000

        try:
            print("listening")
            audio = r.listen(source)
            print("Got audio")
            word = r.recognize_google(audio)
            print(word)
        except sr.UnknownValueError:
            print("Word not recognized")

        if "waist" in word or "waste" in word:
            waist(word)
        elif "head" in word:
            head(word)
        elif "robot" in word:
            arrows(word)
        elif "stop" in word:
            stop()
        else:
            print("Command not recognized")
