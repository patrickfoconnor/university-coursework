# Main class that will hold the driver for the adventure-based combat game
import random
import pyttsx3
import speech_recognition as sr
import copy
from Character import *
import GameAnimations as ga
from TangoRobot import *
import threading

import time as timeLib
# from enum import Enum

# What needs to get done
# • Screen Animation
# • Have start be in one of the four corners √
# • Run Action results in random placement √
# • Full battle run through
# •

# Base game board

# Numerical = Node, P = Path, W = Wall
# +-----------------+
# |1 P P P 2 P P P 3|
# |W W W W P W W W P|
# |W W W W P W W W P|
# |W W W W P W W W P|
# |W W W W P W W W P|
# |4 P P P 5 W W W 6|
# |P W W W P W W W W|
# |P W W W P W W W W|
# |P W W W P W W W W|
# |7 W W W 8 P P P 9|

from Character import Player, Easy, Hard


def populateGameBoard(baseGameBoard, objectArray, start_coords):
    objectsPlaced = 0
    availableRun = {}
    for i in range(len(baseGameBoard)):
        for j in range(len(baseGameBoard[i])):
            if isinstance(baseGameBoard[i][j], int):
                baseGameBoard[i][j] = objectArray[objectsPlaced]
                availableRun[objectsPlaced] = (j, i)
                objectsPlaced += 1

    return baseGameBoard, start_coords[0], start_coords[1], availableRun


def checkNorth(player, gameBoard):
    playerY, playerX = player.getPosition()
    if playerY > 0:
        if gameBoard[playerY - 1][playerX] != "W":
            return True
    return False


def checkSouth(player, gameBoard):
    playerY, playerX = player.getPosition()
    if playerY < len(gameBoard) - 1:
        if gameBoard[playerY + 1][playerX] != "W":
            return True
    return False


def checkEast(player, gameBoard):
    playerY, playerX = player.getPosition()
    if playerX < len(gameBoard[0]) - 1:
        if gameBoard[playerY][playerX + 1] != "W":
            return True
    return False


def checkWest(player, gameBoard):
    playerY, playerX = player.getPosition()
    if playerX > 0:
        if gameBoard[playerY][playerX - 1] != "W":
            return True
    return False


def placeStart(baseGameBoard):
    fourCorners = [[0, 0], [0, 8], [8, 0], [8, 8]]
    selectedCorner = random.choice(fourCorners)
    '''
    for i, x in enumerate(baseGameBoard):
        if selectedCorner in x:
            indexY = i
            indexX = x.index(selectedCorner)
            print(indexY, indexX)
    baseGameBoard[indexY][indexX] = "S"
    '''
    baseGameBoard[selectedCorner[0]][selectedCorner[1]] = "S"
    # baseGameBoard = [[val.replace(fourCorners[selectedCorner], 'S') for val in row] for row in baseGameBoard]
    return baseGameBoard, selectedCorner


class AdventureDriver(threading.Thread):

    # constructor
    def __init__(self):
        threading.Thread.__init__(self)
        # Create the player
        self.player = Player()
        self.player.name = "Player01"
        self.objectArray = self.createObjectArray()
        self.ogBoard, self.startingPositionY, self.startingPositionX, self.availableRun = self.createGameBoard(2)
        self.gameBoard = copy.deepcopy(self.ogBoard)
        self.player.setPosition(self.startingPositionY, self.startingPositionX)

        self.ani = ga.GameAnimations()
        self.ani.start()

        self.engine = pyttsx3.init()
        self.engine.setProperty('voice', self.engine.getProperty('voices')[1].id)
        self.engine.setProperty('rate', 150)


    def getCharacterPosition(self):
        return self.player.getPosition()

    def setCharacterPosition(self, y, x):
        self.player.setPosition(y, x)

    def move(self, y, x):
        oldPositionX = self.player.getPosition()[1]
        oldPositionY = self.player.getPosition()[0]
        oldChar = self.player.lastChar
        self.setCharacterPosition(y, x)

        if self.gameBoard[y][x] != "P" or self.gameBoard[y][x] != "S":
            if self.gameBoard[y][x] == "E":
                if self.player.hasKey():
                    self.ani.victory()
                    self.engine.say("Congratulations, we have survived the Dungeon of the Mad Mage!")
                    self.engine.runAndWait()
                else:
                    self.engine.say("The door is locked, lets keep looking")
                    self.engine.runAndWait()
            elif self.gameBoard[y][x] == "R":
                self.ani.recharge()
                saying = "I have healed ", (100 - self.player.getHP()), "health"
                self.rechargeHealth()
                self.engine.say(saying)
            elif not isinstance(self.gameBoard[y][x], str):
                print("enemy")
                self.engine.say("Gasp, an enemy")
                self.engine.runAndWait()
                self.ani.battle()
                self.battleSequence(self.gameBoard[y][x])
            self.engine.runAndWait()
        self.gameBoard = self.ogBoard
        self.gameBoard[oldPositionY][oldPositionX] = oldChar
        self.player.lastChar = self.gameBoard[y][x]
        self.gameBoard[y][x] = "#"

    def getSize(self):
        return len(self.gameBoard), len(self.gameBoard[0])

    def checkForMoves(self):
        availableActions = [checkNorth(self.player, self.gameBoard), checkSouth(self.player, self.gameBoard),
                            checkEast(self.player, self.gameBoard), checkWest(self.player, self.gameBoard)]
        return availableActions

    # Define all of the actions are available for game
    def battle(self, player, enemy):
        playersAtackValue = random.randint(0, player.attack)
        enemyAttackValue = random.randint(0, enemy.attack)
        player.HP -= enemyAttackValue
        enemy.HP -= playersAtackValue
        robot.adventureAttack()
        temp = ", Using " + player.skills + " dealing " + str(playersAtackValue) + " damage"
        self.engine.say(temp)
        temp = ", Using " + enemy.skills + ", " + enemy.name + " hit you for " + str(enemyAttackValue) + " damage"
        self.engine.say(temp)
        self.engine.runAndWait()


    # Get random num between 1-100 if 1-25 fleeing failed, if greater than 25 run successful
    def run(self):
        runChance = random.randint(0, 100)
        if runChance < 25:
            self.engine.say("Run attempt failed. You must fight!")
        else:
            randomXY = random.choice(self.availableRun)
            (y, x) = randomXY
            self.player.positionX = x
            self.player.positionY = y
            temp = "Run Success new position is: " + str(y) + ", " + str(x)
            self.engine.say(temp)

    # Recharge all Hit points for given player
    def rechargeHealth(self):
        self.engine.say(", You've hit a recharge station. Health reset to 100")
        self.player.setHP(100)

    def battleSequence(self, enemy):
        while True:
            playerHealth = self.player.HP
            enemyHealth = enemy.HP
            temp = ",You have " + str(playerHealth) + " hitpoints"
            self.engine.say(temp)
            temp = "," + enemy.name + " has " + str(enemyHealth) + " hitpoints"
            self.engine.say(temp)
            self.engine.say(", Would you like to battle or run")
            self.engine.runAndWait()

            #word = input()
            while True:
                with sr.Microphone() as source:
                    r = sr.Recognizer()
                    r.adjust_for_ambient_noise( source )
                    r.dynamic_energythreshhold = 3000

                    try:
                        print("listening")
                        audio = r.listen(source)
                        print("Got audio")
                        word = r.recognize_google(audio)
                        print(word)
                        break
                    except sr.UnknownValueError:
                        print( "Word not recognized" )

            if word in "run":
                self.run()
                break

            self.battle(self.player, enemy)
            enemyHealth = enemy.HP
            if self.player.HP <= 0:
                self.engine.say("You have fainted")
                self.player.HP = 100
                self.run()
            elif enemyHealth <= 0:
                self.engine.say(enemy.flvrtxt)
                temp = self.player.getPosition()
                self.gameBoard[temp[0]][temp[1]] = "X"
                if enemy.hasKey():
                    self.player.setLoot("Golden Key")
                    self.ani.chests
                self.engine.say( "Enemy Defeated" )
                self.engine.runAndWait()
                break




    def createGameBoard(self, level):
        if level == 1:
            pass
        elif level == 2:
            baseGameBoard = [[1, "P", "P", "P", 2, "P", "P", "P", 3],
                             ["W", "W", "W", "W", "P", "W", "W", "W", "P"],
                             ["W", "W", "W", "W", "P", "W", "W", "W", "P"],
                             ["W", "W", "W", "W", "P", "W", "W", "W", "P"],
                             [4, "P", "P", "P", 5, "W", "W", "W", 6],
                             ["P", "W", "W", "W", "P", "W", "W", "W", "W"],
                             ["P", "W", "W", "W", "P", "W", "W", "W", "W"],
                             ["P", "W", "W", "W", "P", "W", "W", "W", "W"],
                             [7, "W", "W", "W", 8, "P", "P", "P", 9]]
            baseGameBoard, start_coords = placeStart(baseGameBoard)
            self.player.setPosition(start_coords[0], start_coords[1])
            return populateGameBoard(baseGameBoard, self.objectArray, start_coords)

    # The array will be created and then shuffled
    #   Each index will then hold the object
    #  Start = S
    #  End = E
    #  Recharge Station = R
    #  (4) Weak Bad Guys = Y
    #  (2) Hard bad guys = H
    #  Player = X
    def createObjectArray(self):
        # Create four bad guys
        easyEnemyTurtle = Easy()
        easyEnemyTurtle.setName("Turtle")
        easyEnemyRabbit = Easy()
        easyEnemyRabbit.setName("Rabbit")
        easyEnemySnail = Easy()
        easyEnemySnail.setName("Snail")
        easyEnemyMagpie = Easy()
        easyEnemyMagpie.setName("Magpie")

        hardEnemyKeyLess = Hard()
        hardEnemyKeyLess.loot = "none"
        hardEnemyKeyBearer = Hard()
        hardEnemyKeyBearer.loot = "Golden Key"

        objectArray = [easyEnemyTurtle, easyEnemyRabbit, easyEnemySnail, easyEnemyMagpie, hardEnemyKeyLess,
                       hardEnemyKeyBearer, "E", "R"]
        # Randomize the association of index and object
        random.shuffle(objectArray)
        return objectArray

    def outputBoard(self):
        for i in range(len(self.gameBoard)):
            for j in range(len(self.gameBoard[i])):
                if isinstance(self.gameBoard[i][j], str):
                    print(self.gameBoard[i][j], end=" ")
                elif isinstance(self.gameBoard[i][j], Character):
                    objectTemp = (self.gameBoard[i][j])
                    print(objectTemp.getChar(), end=" ")
                else:
                    print("?", end=" ")
            print("")
