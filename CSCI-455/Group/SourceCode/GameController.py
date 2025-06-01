import tkinter as tk
import AdventureDriver as ad
import os
import pyttsx3
import speech_recognition as sr
from TangoRobot import *


import time as timeLib

clear = lambda: os.system('cls')


class GameController:

    def __init__(self):
        self.game = ad.AdventureDriver()
        self.game.start()
        self.game.outputBoard()
        temp = self.game.getCharacterPosition()
        print("Y = ", temp[0], "X = ", temp[1])

        self.robot = TangoRobot()

        self.engine = pyttsx3.init()
        self.engine.setProperty('voice', self.engine.getProperty('voices')[1].id)
        self.engine.setProperty('rate', 150)
        self.engine.setProperty('voice', 'english')

        self.saying()

        self.roundsPlayed = 0
        self.maxRounds = 25

        self.currentDir = ""
        self.listening = True

        self.output = ""


        '''
        # tkinter window
        self.win = tk.Tk()
        # setup keybindings
        self.win.bind('<w>', self.move)  # key code: 25
        self.win.bind('<s>', self.move)  # key code: 39
        self.win.bind('<a>', self.move)  # key code: 38
        self.win.bind('<d>', self.move)  # key code: 40
        # start tkinter window
        self.win.mainloop()
        '''

    def saying(self):
        moves = self.game.checkForMoves()
        print(moves)
        spokenMoves = ",, You can go"
        if moves[0]:
            spokenMoves += " , North"
        if moves[1]:
            spokenMoves += " , South"
        if moves[2]:
            spokenMoves += " , East"
        if moves[3]:
            spokenMoves += " , West"
        self.engine.say(spokenMoves)
        self.engine.runAndWait()

    def left(self):
        waitTime = 5
        runTime = 0
        while runTime <= waitTime:
            timeStart = timeLib.time()
            self.robot.turnRight()
            timeEnd = timeLib.time()
            runTime += timeEnd - timeStart
        time.sleep( 0.2 )
        self.robot.resetWheels()

    def right(self):
        waitTime = 5
        runTime = 0
        while runTime <= waitTime:
            timeStart = timeLib.time()
            self.robot.turnLeft()
            timeEnd = timeLib.time()
            runTime += timeEnd - timeStart
        time.sleep( 0.2 )
        self.robot.resetWheels()

    def forward(self):
        runTimeD = 0
        waitTimeD = 1
        self.robot.speed = 4800

        while runTimeD <= waitTimeD:
            timeStartD = timeLib.time()
            self.robot.wheelMotorCycle()
            timeEndD = timeLib.time()
            runTimeD += timeEndD - timeStartD
        self.robot.resetWheels()
        '''
        while runTimeD <= waitTimeD:
            timeStartD = timeLib.time()
            self.robot.driveForward()
            timeEndD = timeLib.time()
            runTimeD += timeEndD - timeStartD
        self.robot.resetWheels()
        '''

    def around(self):
        waitTime = 22
        runTime = 0
        while runTime <= waitTime:
            timeStart = timeLib.time()
            self.robot.turnLeft()
            timeEnd = timeLib.time()
            runTime += timeEnd - timeStart
        self.robot.resetWheels()

    def move(self, word):
        y = self.game.getCharacterPosition()[0]
        x = self.game.getCharacterPosition()[1]
        yLen = self.game.getSize()[0]
        xLen = self.game.getSize()[1]
        moves = self.game.checkForMoves()
        self.output = " "


        if "North" in word:
            if moves[0] and y-4 >= 0:
                time.sleep(.2)
                if self.currentDir == "South":
                    self.around()
                elif self.currentDir == "East":
                    self.right()
                elif self.currentDir == "West":
                    self.left()
                self.forward()
                self.robot.speed = 6000

                self.currentDir = "North"
                timeLib.sleep( 1 )
                self.game.move(y - 4, x)
            else:
                self.output = "That's a wall!"

        if "South" in word:
            if moves[1] and y+4 < yLen:
                if self.currentDir == "North":
                    self.around()
                elif self.currentDir == "East":
                    self.left()
                elif self.currentDir == "West":
                    self.right()
                self.forward()
                self.robot.speed = 6000

                self.currentDir = "South"
                timeLib.sleep( 1 )
                self.game.move( y + 4, x )
            else:
                self.output = "That's a wall!"

        if "East" in word:
            if moves[2] and x+4 < xLen:
                if self.currentDir == "North":
                    self.left()
                elif self.currentDir == "South":
                    self.right()
                elif self.currentDir == "West":
                    self.around()
                self.forward()
                self.robot.speed = 6000

                self.currentDir = "East"
                timeLib.sleep( 1 )
                self.game.move( y, x + 4 )
            else:
                self.output = "That's a wall!"

        if "West" in word:
            if moves[3] and x-4 >= 0:
                if self.currentDir == "North":
                    self.right()
                elif self.currentDir == "South":
                    self.left()
                elif self.currentDir == "East":
                    self.around()
                self.forward()
                self.robot.speed = 6000

                self.currentDir = "West"
                timeLib.sleep(1)
                self.game.move( y, x - 4 )
            else:
                self.output = "That's a wall!"

        if self.output in "That's a wall!":
            self.engine.say(self.output)
            self.engine.runAndWait()
        self.game.outputBoard()
        temp = self.game.getCharacterPosition()
        print( "Y = ", temp[0], "X = ", temp[1] )

        self.roundsPlayed += 1

    def listen(self):
        while self.listening and self.roundsPlayed <= self.maxRounds:
            with sr.Microphone() as source:
                r = sr.Recognizer()
                r.adjust_for_ambient_noise( source )
                r.dynamic_energythreshhold = 3000

                self.output = ""
                self.saying()

                try:
                    print("listening")
                    audio = r.listen(source)
                    print("Got audio")
                    word = r.recognize_google(audio)
                    print(word)
                    self.move(word)
                except sr.UnknownValueError:
                    print( "Word not recognized" )
    '''
    def listen(self):
        while self.roundsPlayed <= self.maxRounds:
            print("...")
            word = input()
            self.saying()
            self.move(word)
    '''

GameController().listen()
