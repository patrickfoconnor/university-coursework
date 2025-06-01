import tkinter as tk
import time
import threading

class GameAnimations( threading.Thread ):
    def __init__(self):
        threading.Thread.__init__(self)
        self.win = tk.Tk()
        self.win.title("Adventure Game")
        self.win.configure(background='darkgrey')
        self.win.geometry("700x480")
        self.canvas = tk.Canvas(self.win, highlightthickness=0, bg="darkgrey", width=480, height=480)
        self.canvas.pack()
        self.canvaslist = []
        self.chests = []
        self.chests.append(tk.PhotoImage(file="Game/chest1.png"))
        self.chests.append(tk.PhotoImage(file="Game/chest2.png"))
        self.chests.append(tk.PhotoImage(file="Game/chest3.png"))
        self.chests.append(tk.PhotoImage(file="Game/chest4.png"))
        self.chests.append(tk.PhotoImage(file="Game/chest5.png"))
        self.hearts = []
        self.hearts.append(tk.PhotoImage(file="Game/heart0.png"))
        self.hearts.append(tk.PhotoImage(file="Game/heart.png"))
        self.hearts.append(tk.PhotoImage(file="Game/heart2.png"))
        self.hearts.append(tk.PhotoImage(file="Game/heart3.png"))
        self.hearts.append(tk.PhotoImage(file="Game/heart4.png"))
        self.hearts.append(tk.PhotoImage(file="Game/heart5.png"))
        self.swords = []
        self.swords.append(tk.PhotoImage(file="Game/sword1.png"))
        self.swords.append(tk.PhotoImage(file="Game/sword2.png"))
        self.swords.append(tk.PhotoImage(file="Game/sword3.png"))
        self.swords.append(tk.PhotoImage(file="Game/sword4.png"))
        self.swords.append(tk.PhotoImage(file="Game/sword5.png"))
        self.win.update_idletasks()
        self.win.update()

    def start(self):
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
        count = 1
        self.canvas.pack()
        self.win.update()
        for i in range(20):
            if count % 2 == 0:
                ch = "+"
            else:
                ch = "-"
            self.win.after(250)
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
            count+=1
            self.canvas.pack()
            self.win.update()
        self.win.after(2000)
        self.canvas.delete('all')
        self.canvas.pack()
        self.win.update()
        self.canvaslist = []

    def chest(self):
        self.canvas.create_image((250, 250), image=self.chests[0])
        self.canvas.pack()
        self.win.update()
        self.win.after(2000)
        for img in self.chests[1:]:
            self.canvas.create_image((250,250),image=img)
            self.canvas.pack()
            self.win.update()
            self.win.after(500)
        self.win.after(3000)
        self.canvas.delete('all')
        self.canvas.pack()
        self.win.update()

    def victory(self):
        txt = self.canvas.create_text((240,240),fill ="yellow", text="VICTORY ACHIEVED",font=("Times New Roman", 4))
        self.canvas.pack()
        self.win.update()
        self.win.after(2000)
        for i in range(30):
            self.win.after(10)
            self.canvas.itemconfig(txt, font=("Times New Roman", 4 + (i)))
            self.canvas.pack()
            self.win.update()
        self.canvas.itemconfig(txt, font=("Times New Roman", 34))
        self.canvas.pack()
        self.win.update()
        self.win.after(3000,self.canvas.delete('all'))
        self.canvas.pack()
        self.win.update()

    def recharge(self):
        self.canvas.create_image((100, 250), image=self.hearts[0], anchor=tk.W)
        self.canvas.pack()
        self.win.update()
        self.win.after(2000)
        for img in self.hearts[1:]:
            self.canvas.create_image((100,250),image=img,anchor=tk.W)
            self.canvas.pack()
            self.win.update()
            self.win.after(750)
        self.win.after(3000, self.canvas.delete('all'))
        self.canvas.pack()
        self.win.update()

    def battle(self):
        self.canvas.create_image((240,240),image=self.swords[0])
        self.canvas.pack()
        self.win.update()
        self.win.after(300,self.canvas.delete('all'))
        self.canvas.pack()
        self.win.update()
        self.canvas.create_image((240,240),image=self.swords[1])
        self.canvas.pack()
        self.win.update()
        self.win.after(300)
        self.canvas.delete('all')
        self.canvas.create_image((240, 240), image=self.swords[2])
        self.canvas.pack()
        self.win.update()
        self.win.after(300)
        self.canvas.delete('all')

        count = 0
        for i in range(30):
            img = []
            if count % 2 == 0:
                img = self.swords[3]
            else:
                img = self.swords[4]
            self.canvas.create_image((240, 240), image=img)
            self.canvas.pack()
            self.win.update()
            self.win.after(300)
            self.canvas.delete('all')
            count += 1
        self.win.after(1000, self.canvas.delete('all'))
        self.canvas.pack()
        self.win.update()