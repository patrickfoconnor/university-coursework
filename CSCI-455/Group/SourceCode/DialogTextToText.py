#from TangoRobot import *
import dialogEngine
import random
import sys
#import speech_recognition as sr

rulesList = dialogEngine.fileReader()
listening = True;
r = random.seed


def listen():
    while self.listening:
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
            return word
        except sr.UnknownValueError:
            print("Word not recognized")


def typing():
    humanInput = input("Human: ")
    if humanInput == "exit":  # exits program
        sys.exit()
    return humanInput

# Return the human data previously recorded


def getHumanData(varName, dicts):
    if varName in dicts.keys():
        humanData = dicts.get(varName)
    else:
        humanData = "I Dont Know"
    return humanData


def typeBack(out, dicts, varName):
    # Thinking that checking here for the $ sign
    if isinstance(out, list):  # if response is list
        output = ""
        if bool(out):
            output = random.choice(out)
        if "$" in output or not bool(out):
            humanData = getHumanData(varName, dicts)
            if humanData is None:
                print(output.replace(varName, "I don't know").title())
            elif humanData != "":
                print(output.replace(varName, humanData).title())
        else:
            print(output.title())

    else:
        if varName != "":
            humanData = getHumanData(varName, dicts)
            if humanData != "":
                print(humanData.title())
            else:
                print("I don't know")
        else:
            print(out.title())  # if response is string


def checkForVariables(humanInput, humanRes):
    # if "_" in humanRes:
    grabbingVarName = True
    parsedHumanVar = ""
    varIndex = humanRes.find("_")
    if humanInput[:varIndex] == humanRes[:varIndex]:
        while grabbingVarName:
            if varIndex < len(humanInput) and humanInput[varIndex].isalpha():
                parsedHumanVar += humanInput[varIndex]
                varIndex += 1
            else:
                grabbingVarName = False
        return parsedHumanVar


def getVarName(out):
    output = random.choice(out)
    if "$" in output:
        varName = "$"
        varFound = False
        for i in range(0, len(output)):
            if output[i] == "$":
                varFound = True

            if varFound and output[i].isalpha():
                varName += output[i]
            if varFound and not output[i].isalpha() and not output[i] == "$":
                return varName
        return varName


def findDif(i, rules, humanRes, humanInput, humanDataDict, varName):
    if humanInput == humanRes:
        out = rules[i][2]
        if varName == "":
            varName = getVarName(rules[i][2])
        typeBack(out, humanDataDict, varName)
        return True
    elif "_" in humanRes:
        humanData = checkForVariables(humanInput, humanRes)
        if humanData != "" and humanRes[:humanRes.index("_")] in humanInput:
            if varName == "":
                varName = getVarName(rules[i][2])
            humanDataDict[varName] = humanData
            typeBack(rules[i][2], humanDataDict, varName)
            return True


def main():
    i = 0
    humanDataDict = {}
    humanInput = typing().lower()
    breaking = False
    varName = ""
    while not breaking:  # this while loop checks the top level of options
        humanRes = rulesList[i][1]  # what the robot is looking to respond to
        if int(rulesList[i][0]) > 0:  # if the level is higher that first level it skips the loop.
            pass
        if isinstance(humanRes, str):
            breaking = findDif(i, rulesList, humanRes, humanInput, humanDataDict, varName)
        elif isinstance(humanRes, list):  # if human option is a list
            for word in humanRes:
                breaking = findDif(i, rulesList, word, humanInput, humanDataDict, varName)
        i += 1
    level = 1
    nextList = []
    currentLevel = 1  # currentLevel is the level we want to look at
    while len(rulesList) > 0:  # while there are still rules (might not be needed)
        currentList = nextList
        while currentLevel <= level and i < len(rulesList):
            if int(rulesList[i][0]) == level:  # places rule into the current list if it is the level we are looking at
                currentList.append(rulesList[i])
            else:
                if int(rulesList[i][0]) < currentLevel:  # if nothing left on lower levels, leaves and goes to responses
                    currentLevel += 1
                else:
                    nextList.append(rulesList[i])  # places ruleList into the list to look at next

            i += 1
        j = 0

        while True:
            humanInput = typing()
            breaking = False
            while not breaking:
                humanRes = currentList[j][1]
                if isinstance(humanRes, str):
                    breaking = findDif(j, currentList, humanRes, humanInput, humanDataDict, varName)
                elif isinstance(humanRes, list):  # if human option is a list
                    for word in humanRes:
                        breaking = findDif(j, currentList, word, humanInput, humanDataDict, varName)

                j += 1


while True:
    print("")
    main()
