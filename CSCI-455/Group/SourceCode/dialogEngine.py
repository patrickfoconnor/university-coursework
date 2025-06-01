#file in
def_lists = []


def command(commandString, lineNumber):
    level = 0
    humanInput = ""
    robotOutputs = []
    variableName = ""
    variable = "$"
    for i in range(len(commandString)):
        if commandString[i] == "u":
            i += 1
        elif commandString[i].isdigit():
            level = commandString[i]
            i += 1
        elif commandString[i] == "(":
            i += 1
            if commandString[i] == "~":
                i += 1
                temp = ""
                while commandString[i].isalpha():
                    temp += commandString[i]
                    i += 1
                for vari in def_lists:
                    if vari[0] == temp:
                        humanInput = vari[1]

            else:
                while commandString[i] != ")":
                    humanInput += commandString[i]
                    i += 1
                    if commandString[i] == "_":
                        x = i
                        while commandString[x] != "$":
                            x += 1
                            test = commandString[x]
                        x += 1
                        while commandString[x].isalpha():
                            variable += commandString[x]
                            x += 1
                        variableName = variable
        elif commandString[i] == ":":
            i += 1
            if commandString[i] == "[":
                while commandString[i] != "]":
                    i += 1
                    robotOut = ""
                    while commandString[i].isalpha() or commandString[i] == "\"":
                        if commandString[i] != "\"":
                            robotOut += commandString[i]
                        else:
                            i += 1
                            while commandString[i] != "\"":
                                robotOut += commandString[i]
                                i += 1
                        i += 1
                    if robotOut != "":
                        robotOutputs.append(robotOut)
            elif commandString[i].isalpha() or commandString[i] == " ":
                robotOut = ""
                while i < len(commandString) - 1:
                    robotOut += commandString[i]
                    i += 1
                robotOutputs.append(robotOut)
            elif commandString[i] == "~":
                i += 1
                temp = ""
                while commandString[i].isalpha():
                    temp += commandString[i]
                    i += 1
                for vari in def_lists:
                    if vari[0] == temp:
                        robotOutputs = vari[1]
            elif commandString[i] == "$":
                while commandString[i] != "$":
                    i += 1
                    test = commandString[i]
                i += 1
                while commandString[i].isalpha():
                    variable += commandString[i]
                    i += 1
                variableName = variable
    if not robotOutputs:
        robotOutputs = [variableName]
    return level, humanInput, robotOutputs, variableName


def definition(definitionString, lineNumber):
    variable = ""
    variable_names = ""
    variableOptions = []
    for i in range(0, len(definitionString)):
        if definitionString[i] == "~":
            i += 1
            while definitionString[i].isalpha():
                variable += definitionString[i]
                i += 1
            variable_names = variable
        if definitionString[i] == ":" and definitionString[i + 1] == "[":
            i += 1
            while definitionString[i] != "]":
                robotOut = ""
                i += 1
                while definitionString[i].isalpha() or definitionString[i] == "\"":
                    if definitionString[i] != "\"":
                        robotOut += definitionString[i]
                    else:
                        i += 1
                        while definitionString[i] != "\"":
                            robotOut += definitionString[i]
                            i += 1
                    i += 1
                if robotOut != "":
                    variableOptions.append(robotOut)
        elif definitionString[i] == ":" and (definitionString[i + 1].isalpha() or definitionString[i + 1] == " "):
            i += 1
            robotOut = ""
            while i < len(definitionString) - 1:
                robotOut += definitionString[i]
                i += 1
            variableOptions.append(robotOut)
        temp = variable_names, variableOptions
        def_lists.append(temp)
        return "how the fuck did you get here?"


def lineReader(line, lineNumber):
    lineValue = ""
    for token in line:
        if token == "#":
            print("Comment at line ", lineNumber)
        elif token == "u":
            if line.count(':') == 2:
                lineValue = command(line, lineNumber)
            else:
                print("error at line " + str(lineNumber))
            break
        elif token == "~":
            if line.count(':') == 1:
                definition(line, lineNumber)
            else:
                print("error at line " + str(lineNumber))
            break

    return lineValue


def fileReader():
    lines = []
    lineNumber = 1

    file = open("../DialogRules/liveFile.txt")
    for line in file:
        returned = lineReader(line, lineNumber)
        if returned != "":
            lines.append(returned)
        lineNumber += 1

    return lines