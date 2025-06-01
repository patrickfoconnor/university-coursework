import speechEngine
from TangoRobot import *
import tkinter as tk
import pyttsx3
import time as timeLib
import speech_recognition as sr


def errorPopUpWind(message):
    top = tk.Toplevel(win)
    top.geometry("250x250")
    top.title("Input Error")
    tk.Label(top, text=message).place(x=150, y=80)





class MyTK:

    def __init__(self, win):
        self.instructions = []
        self.win = win
        self.win.geometry("800x480")
        self.leftfr = tk.Frame(self.win, width=60)
        self.leftfr.pack(side=tk.LEFT)
        self.leftfr['borderwidth'] = 2
        self.leftfr['relief'] = 'raised'
        self.midframe = tk.Frame(self.win)
        self.midframe.pack(side=tk.BOTTOM)
        self.rightfr = tk.Frame(self.win, width=60)
        self.rightfr.pack(side=tk.RIGHT)
        self.turnimg = tk.PhotoImage(file="GUI/turn.png")
        self.headpanimg = tk.PhotoImage(file="GUI/headtilt.png")
        self.headtiltimg = tk.PhotoImage(file="GUI/headpan.png")
        self.motorsimg = tk.PhotoImage(file="GUI/motors.png")
        self.speechimg = tk.PhotoImage(file="GUI/speech.png")
        self.talkingimg = tk.PhotoImage(file="GUI/talking.png")
        self.waistimg = tk.PhotoImage(file="GUI/waist.png")
        self.emptyimg = tk.PhotoImage(file="GUI/empty.png")
        self.runimg = tk.PhotoImage(file="GUI/run.png")
        self.clearimg = tk.PhotoImage(file="GUI/clear.png")
        self.quitimg = tk.PhotoImage(file="GUI/quit.png")
        self.topfr = tk.Frame(self.win, bg="light blue")
        self.topfr.pack(side=tk.TOP)
        self.leftfr['borderwidth'] = 2
        self.leftfr['relief'] = 'raised'
        self.engine = pyttsx3.init()
        self.canvas = tk.Canvas(self.topfr, highlightthickness=0, bg="light blue", width=300, height=300)
        self.eye1 = []
        self.eye2 = []
        self.canvaslist = []
        self.drawBot()
        self.canvas.pack()
        self.guiRobot = TangoRobot()

    def drawBot(self):
        self.canvaslist.append(self.canvas.create_rectangle(100, 50, 200, 150, fill="#83CAC9"))
        self.canvaslist.append(self.canvas.create_rectangle(115, 70, 140, 90, fill="white"))
        self.canvaslist.append(self.canvas.create_rectangle(160, 70, 185, 90, fill="white"))
        self.eye1 = self.canvas.create_rectangle(120, 82, 122, 84, fill="black")
        self.eye2 = self.canvas.create_rectangle(165, 82, 167, 84, fill="black")
        self.canvaslist.append(self.canvas.create_rectangle(115, 110, 185, 145, fill="yellow"))
        self.canvaslist.append(self.canvas.create_rectangle(115, 50, 118, 35, fill="red"))
        self.canvaslist.append(self.canvas.create_rectangle(185, 50, 188, 35, fill="red"))
        self.canvaslist.append(self.canvas.create_rectangle(100, 80, 90, 100, fill="blue"))
        self.canvaslist.append(self.canvas.create_rectangle(200, 80, 210, 100, fill="blue"))
        for i in range(7):
            self.canvaslist.append(self.canvas.create_line(115 + (10 * i), 110, 115 + (10 * i), 145))
            i += 10


    def animate(self, ch):
        for c in self.canvaslist:
            if ch in "+":
                self.canvas.move(c, 0, 5)
            elif ch in "-":
                self.canvas.move(c, 0, -5)
        if ch in "+":
            self.canvas.move(self.eye1, -16, 5)
            self.canvas.move(self.eye2, -16, 5)
        elif ch in "-":
            self.canvas.move(self.eye1, 16, -5)
            self.canvas.move(self.eye2, 16, -5)

    def arrows(self, command):
        if "forward" in command:
            print( "Forward" )
            time.sleep( .2 )
            self.guiRobot.driveForward()
        elif "back" in command:
            print( "Backward" )
            time.sleep( .2 )
            self.guiRobot.driveBackward()
        elif "left" in command:
            print( "Left" )
            time.sleep( .2 )
            self.guiRobot.turnLeft()
        elif "right" in command:
            print( "Right" )
            time.sleep( .2 )
            self.guiRobot.turnRight()

    def waist(self, command):
        if "left" in command:
            print( "Bend Left" )
            time.sleep( .2 )
            self.guiRobot.waistLeft()
        elif "right" in command:
            print( "Bend Right" )
            time.sleep( .2 )
            self.guiRobot.waistRight()

    def head(self, command):
        if "up" in command:
            print( "Head Up" )
            time.sleep( .2 )
            self.guiRobot.headUp()
        elif "down" in command:
            print( "Head Down" )
            time.sleep( .2 )
            self.guiRobot.headDown()
        elif "left" in command:
            print( "Head Left" )
            time.sleep( .2 )
            self.guiRobot.headLeft()
        elif "right" in command:
            print( "Head Right" )
            time.sleep( .2 )
            self.guiRobot.headRight()

    def stop(self):
        print( "Halting" )
        self.guiRobot.resetRobot()

    def updateInstructions(self):
        for i in range(len(self.instructions)):
            b = self.midframe.winfo_children()[i]
            name = self.instructions[i][0]
            if name in "motors":
                b.configure(image=self.motorsimg)
            elif name in "turn":
                b.configure(image=self.turnimg)
            elif name in "headtilt":
                b.configure(image=self.headtiltimg)
            elif name in "headpan":
                b.configure(image=self.headpanimg)
            elif name in "waist":
                b.configure(image=self.waistimg)
            elif name in "speech":
                b.configure(image=self.speechimg)
            elif name in "talking":
                b.configure(image=self.talkingimg)
        print(self.instructions)
        if len(self.instructions) < 8:
            for j in range(len(self.instructions), 8):
                self.midframe.winfo_children()[j].configure(image=self.emptyimg)

    def deleteInstruction(self, i):
        self.instructions.pop(i)
        self.updateInstructions()

    def deletePopUp(self, j):
        top = tk.Toplevel(self.win)
        x = self.win.winfo_pointerx()
        y = self.win.winfo_pointery()
        top.geometry("%dx%d+%d+%d" % (150, 90, x, y))
        top.title("Warning")
        fr = tk.Frame(top, background="light blue")
        fr.pack()
        tk.Label(fr, text="Remove?", width=20, background="light blue").grid(column=0, row=0)
        tk.Button(fr, text="Yes", pady=5, padx=10, background="green",
                  command=lambda: [self.deleteInstruction(j), top.destroy()]).grid(column=0, row=1)
        tk.Button(fr, text="No!", pady=5, padx=10, background="red", command=lambda: top.destroy()).grid(column=0,
                                                                                                         row=2)
        # selected = tk.StringVar()

    def createButtons(self):
        # command = fun
        tk.Button(self.leftfr, text='Motors', image=self.motorsimg, command=lambda m="motors": self.commands(m)).grid(
            column=0, row=0)
        tk.Button(self.leftfr, text='Turn', image=self.turnimg, command=lambda m="turn": self.commands(m)).grid(
            column=0, row=1)
        tk.Button(self.leftfr, text='Head Tilt', image=self.headtiltimg,
                  command=lambda m="headtilt": self.commands(m)).grid(column=0, row=2)
        tk.Button(self.leftfr, text='Head Pan', image=self.headpanimg,
                  command=lambda m="headpan": self.commands(m)).grid(column=0, row=3)
        tk.Button(self.leftfr, text='Waist Turn', image=self.waistimg, command=lambda m="waist": self.commands(m)).grid(
            column=0, row=4)
        tk.Button(self.leftfr, text='Speech Input', image=self.speechimg,
                  command=lambda m="speech": self.commands(m)).grid(column=0, row=5)
        tk.Button(self.leftfr, text='Talking', image=self.talkingimg,
                  command=lambda m="talking": self.commands(m)).grid(column=0, row=6)

        tk.Button(self.midframe, text="1", image=self.emptyimg, command=lambda j=0: self.deletePopUp(j)).grid(column=0,
                                                                                                              row=0)
        tk.Button(self.midframe, text="2", image=self.emptyimg, command=lambda j=1: self.deletePopUp(j)).grid(column=1,
                                                                                                              row=0)
        tk.Button(self.midframe, text="3", image=self.emptyimg, command=lambda j=2: self.deletePopUp(j)).grid(column=2,
                                                                                                              row=0)
        tk.Button(self.midframe, text="4", image=self.emptyimg, command=lambda j=3: self.deletePopUp(j)).grid(column=3,
                                                                                                              row=0)
        tk.Button(self.midframe, text="5", image=self.emptyimg, command=lambda j=4: self.deletePopUp(j)).grid(column=4,
                                                                                                              row=0)
        tk.Button(self.midframe, text="6", image=self.emptyimg, command=lambda j=5: self.deletePopUp(j)).grid(column=5,
                                                                                                              row=0)
        tk.Button(self.midframe, text="7", image=self.emptyimg, command=lambda j=6: self.deletePopUp(j)).grid(column=6,
                                                                                                              row=0)
        tk.Button(self.midframe, text="8", image=self.emptyimg, command=lambda j=7: self.deletePopUp(j)).grid(column=7,
                                                                                                              row=0)

        tk.Button(self.rightfr, text="Run Program", image=self.runimg, command=lambda: self.runProgram()).grid(column=0,
                                                                                                               row=0)
        tk.Button(self.rightfr, text="Clear Program", image=self.clearimg,
                  command=lambda: [self.instructions.clear(), self.updateInstructions()]).grid(column=0, row=1)
        tk.Button(self.rightfr, text="Quit", image=self.quitimg, command=lambda: self.win.destroy()).grid(column=0,
                                                                                                          row=2)

    def createInstruction(self, name, args):
        temp = [name]
        for arg in args:
            temp.append(arg)
        self.instructions.append(temp)
        self.updateInstructions()

    # Need some more info on the actual details of speed and direction
    def motors(self, waitTime, direction):
        waitTime += 1
        if direction in "forward":
            runTime = 0
            while runTime <= waitTime:
                timeStart = timeLib.time()
                self.guiRobot.driveForward()
                timeEnd = timeLib.time()
                print(runTime)
                runTime += timeEnd - timeStart

        elif direction in "backwards":
            runTime = 0
            while runTime <= waitTime:
                timeStart = timeLib.time()
                self.guiRobot.driveBackward()
                timeEnd = timeLib.time()
                runTime += timeEnd - timeStart
        else:
            errorPopUpWind("Invalid Motor Direction")
        self.guiRobot.resetWheels()
        self.guiRobot.speed = 6000

    #
    def turn(self, direction, waitTime):
        waitTime += 1
        if direction in "left":
            runTime = 0
            while runTime <= waitTime:
                timeStart = timeLib.time()
                self.guiRobot.turnLeft()
                timeEnd = timeLib.time()
                runTime += timeEnd - timeStart
        elif direction in "right":
            runTime = 0
            while runTime <= waitTime:
                timeStart = timeLib.time()
                self.guiRobot.turnRight()
                timeEnd = timeLib.time()
                runTime += timeEnd - timeStart
        else:
            errorPopUpWind("Invalid Robot Turn Direction")

        self.guiRobot.resetWheels()
        self.guiRobot.speed = 6000

    def headtilt(self, direction):
        if direction in "up":

            self.guiRobot.headUp()
            # runTime = 0
            # while (runTime != time):
            #     print("headtilt up")
            #     timeStart = timeLib.time()
            #     timeEnd = timeLib.time()
            #     runTime += timeEnd - timeStart
        elif direction in "down":

            self.guiRobot.headDown()
            # runTime = 0
            # while (runTime != time):
            #     timeStart = timeLib.time()
            #     timeEnd = timeLib.time()
            #     runTime += timeEnd - timeStart
        else:
            errorPopUpWind("Invalid Robot Head Tilt Direction")

    def headpan(self, direction):
        print("head pan")
        if direction in "left":
            self.guiRobot.headLeft()
            # runTime = 0
            # while (runTime != time):
            #     timeStart = timeLib.time()
            #     timeEnd = timeLib.time()
            #     runTime += timeEnd - timeStart
        elif direction in "right":
            self.guiRobot.headRight()
            # runTime = 0
            # while (runTime != time):
            #     timeStart = timeLib.time()
            #     timeEnd = timeLib.time()
            #     runTime += timeEnd - timeStart
        else:
            errorPopUpWind("Invalid Robot Head Turn Direction")

    def waistturn(self, direction):
        print("waist turn")
        if direction == "left":
            self.guiRobot.waistLeft()
            # runTime = 0
            # while (runTime != time):
            #     timeStart = timeLib.time()
            #     timeEnd = timeLib.time()
            #     runTime += timeEnd - timeStart
        elif direction == "right":
            self.guiRobot.waistRight()
            # runTime = 0
            # while (runTime != time):
            #     timeStart = timeLib.time()
            #     timeEnd = timeLib.time()
            #     runTime += timeEnd - timeStart
        else:
            errorPopUpWind("Invalid Robot Waist Turn Direction")

    def speech(self):
        listening = True;

        while listening:
            with sr.Microphone() as source:
                r = sr.Recognizer()
                r.adjust_for_ambient_noise(source)
                r.dynamic_energythreshhold = 3000
                listening = False
                try:
                    print("listening")
                    audio = r.listen(source)
                    print("Got audio")
                    word = r.recognize_google(audio)
                    print(word)
                except sr.UnknownValueError:
                    print("Word not recognized")
                    listening = True
                    word = ""

                if "waist" in word or "waste" in word:
                    self.waist(word)
                elif "head" in word:
                    self.head(word)
                elif "robot" in word:
                    self.arrows(word)
                elif "stop" in word:
                    self.stop()
                else:
                    print("Command not recognized")
                    listening = True
            self.guiRobot.resetWheels()


    def talking(self, sentence):
        print("The robot says %s" % sentence)
        voices = self.engine.getProperty('voices')

        self.engine.setProperty('voice', voices[1].id)
        self.engine.setProperty('rate', 150)

        self.engine.say(sentence)
        self.engine.runAndWait()

    def commands(self, name):
        top = tk.Toplevel(self.win)
        x = self.win.winfo_pointerx()
        y = self.win.winfo_pointery()
        top.geometry("%dx%d+%d+%d" % (400, 300, x, y))
        top.title(name)
        fr = tk.Frame(top)
        fr.pack(fill='x', padx=5, pady=5)
        selected = tk.StringVar()
        args = []
        if name in "motors":
            speed = tk.IntVar()
            speed.set(6000)
            time = tk.IntVar()
            tk.Label(fr, text="Direction to turn:").grid(column=0, row=0)
            tk.Radiobutton(fr, text="Right", value="right", variable=selected, indicator=0,
                           background="light blue").grid(column=0, row=1)
            tk.Radiobutton(fr, text="Left", value="left", variable=selected, indicator=0, background="light blue").grid(
                column=0, row=2)
            tk.Radiobutton(fr, text="Up", value="up", variable=selected, indicator=0, background="light blue").grid(
                column=0, row=3)
            tk.Radiobutton(fr, text="Down", value="down", variable=selected, indicator=0, background="light blue").grid(
                column=0, row=4)
            tk.Radiobutton(fr, text="Forward", value="forward", variable=selected, indicator=0,
                           background="light blue").grid(column=0, row=5)
            tk.Radiobutton(fr, text="Backwards", value="backward", variable=selected, indicator=0, background="light blue").grid(
                column=0, row=6)
            tk.Label(fr, text="Time:").grid(column=1, row=0)
            tk.Button(fr, text="+", width=5, command=lambda: time.set(time.get() + 1)).grid(column=1, row=2)
            tk.Button(fr, text="-", width=5, command=lambda: time.set(time.get() - .5)).grid(column=1, row=3)
            tk.Entry(fr, textvariable=time, width=5).grid(column=1, row=1)
            tk.Label(fr, text="Speed:").grid(column=2, row=0)
            tk.Button(fr, text="+", width=5, command=lambda: speed.set(speed.get() + 100)).grid(column=2, row=2)
            tk.Button(fr, text="-", width=5, command=lambda: speed.set(speed.get() - 100)).grid(column=2, row=3)
            tk.Entry(fr, textvariable=speed, width=5).grid(column=2, row=1)
            tk.Button(fr, height=5, width=5, text="Ok", background="green",
                      command=lambda: [args.append(speed.get()), args.append(time.get()), args.append(selected.get()),
                                       self.createInstruction(name, args), top.destroy()]).grid(column=1, row=7)
        elif name in "turn":
            time = tk.IntVar(0)
            label = tk.Label(fr, text="Direction to turn:")
            r1 = tk.Radiobutton(fr, text="Right", value="right", variable=selected, indicator=0,
                                background="light blue")
            r2 = tk.Radiobutton(fr, text="Left", value="left", variable=selected, indicator=0, background="light blue")
            label.pack(fill='x', padx=5, pady=5)
            r1.pack(fill='x', padx=5, pady=5)
            r2.pack(fill='x', padx=5, pady=5)
            label = tk.Label(fr, text="Time")
            label.pack(fill='x', padx=5, pady=5)
            minus = tk.Button(fr, text="-", width=5, command=lambda: time.set(time.get() - 1))
            plus = tk.Button(fr, text="+", width=5, command=lambda: time.set(time.get() + 1))
            plus.pack(side=tk.LEFT)
            minus.pack(side=tk.LEFT)
            text = tk.Entry(fr, textvariable=time, width=5)
            text.pack(side=tk.LEFT)
            ok = tk.Button(fr, height=5, width=5, text="Ok", background="green",
                           command=lambda: [args.append(selected.get()), args.append(time.get()),
                                            self.createInstruction(name, args), top.destroy()])
            ok.pack(side=tk.TOP)
        elif name in "headtilt":
            label = tk.Label(fr, text="Direction to tilt head:")

            r1 = tk.Radiobutton(fr, text="Down", value="down", variable=selected, indicator=0, background="light blue")
            r2 = tk.Radiobutton(fr, text="Up", value="up", variable=selected, indicator=0, background="light blue")
            label.pack(fill='x', padx=5, pady=5)
            r1.pack(fill='x', padx=5, pady=5)
            r2.pack(fill='x', padx=5, pady=5)
            ok = tk.Button(fr, height=5, text="Ok", background="green",
                           command=lambda: [args.append(selected.get()), self.createInstruction(name, args),
                                            top.destroy()])
            ok.pack(fill='x', padx=5, pady=5)
        elif name in "headpan":
            label = tk.Label(fr, text="Direction to pan head:")
            r1 = tk.Radiobutton(fr, text="Right", value="right", variable=selected, indicator=0,
                                background="light blue")
            r2 = tk.Radiobutton(fr, text="Left", value="left", variable=selected, indicator=0, background="light blue")
            label.pack(fill='x', padx=5, pady=5)
            r1.pack(fill='x', padx=5, pady=5)
            r2.pack(fill='x', padx=5, pady=5)
            ok = tk.Button(fr, height=5, text="Ok", background="green",
                           command=lambda: [args.append(selected.get()), self.createInstruction(name, args),
                                            top.destroy()])
            ok.pack(fill='x', padx=5, pady=5)
        elif name in "waist":
            label = tk.Label(fr, text="Direction to spin waist:")
            r1 = tk.Radiobutton(fr, text="Right", value="right", variable=selected, indicator=0,
                                background="light blue")
            r2 = tk.Radiobutton(fr, text="Left", value="left", variable=selected, indicator=0, background="light blue")
            label.pack(fill='x', padx=5, pady=5)
            r1.pack(fill='x', padx=5, pady=5)
            r2.pack(fill='x', padx=5, pady=5)
            ok = tk.Button(fr, height=5, text="Ok", background="green",
                           command=lambda: [args.append(selected.get()), self.createInstruction(name, args),
                                            top.destroy()])
            ok.pack(fill='x', padx=5, pady=5)
        elif name in "speech":
            label = tk.Label(fr, text="Think of an instruction to")
            label2 = tk.Label(fr, text="say to the robot and press Ok")
            label.pack(fill='x', padx=5, pady=5)
            label2.pack(fill='x', padx=5, pady=5)
            ok = tk.Button(fr, height=5, text="Ok", background="green",
                           command=lambda: [self.createInstruction(name, args), top.destroy()])
            ok.pack(fill='x', padx=5, pady=5)

        elif name in "talking":
            talklab = tk.Label(fr, text="What would you like\nthe robot to say?")
            talklab.pack(fill='x', expand=True)
            text = tk.Entry(fr, textvariable=selected)
            text.pack(fill='x', expand=True)
            text.focus()
            ok = tk.Button(fr, height=5, text="Ok", background="green",
                           command=lambda: [args.append(selected.get()), self.createInstruction(name, args),
                                            top.destroy()])
            ok.pack(fill='x', expand=True)

    def runProgram(self):
        for com in self.instructions:
            if com[0] in "motors":
                self.motors(com[2], com[3])
            elif com[0] in "turn":
                self.turn(com[1], com[2])
                print(com)
            elif com[0] in "headtilt":
                self.headtilt(com[1])
            elif com[0] in "headpan":
                self.headpan(com[1])
            elif com[0] in "waist":
                self.waistturn(com[1])
            elif com[0] in "speech":
                self.speech()
            elif com[0] in "talking":
                self.talking(com[1])
            for i in range(9):
                self.win.after(250, self.animate("-"))
                self.canvas.pack()
                self.win.update()
                self.win.after(250, self.animate("+"))
                self.canvas.pack()
                self.win.update()
            timeLib.sleep(.5)


win = tk.Tk()
win.title("Robot Program GUI")
win.configure(background='light blue')
v = MyTK(win)
# Key bindings
# win.bind('<Motion>', v.motion)

# create a canvas and add it to window
# v.createCanvas()
# Place a label on Window
# v.createLabel()
v.createButtons()
v.win.mainloop()

