class Character(object):

    def __init__(self, name, HP, skills, attack, defense, loot, expget, flvrtxt):
        # Set name for enemy
        self.name = name
        self.HP = HP
        self.skills = skills
        self.attack = attack
        self.defense = defense
        self.loot = loot
        self.expget = expget
        self.flvrtxt = flvrtxt
        self.char = "?"

    def setName(self, name):
        self.name = name

    def getName(self):
        return self.name

    def setHP(self, HP):
        self.HP = HP

    def getHP(self):
        return self.HP

    def setLoot(self, loot):
        self.loot = loot

    def getLoot(self):
        return self.loot

    def hasKey(self):
        return self.loot == "Golden Key"

    def getChar(self):
        return self.char


class Player(Character):

    def __init__(self):
        super(Player, self).__init__("Player01", 100, "Baseball Bat", 50, 5, "None", 50, "Im a fucking winner")
        self.positionX = None
        self.positionY = None
        self.spot = "S"
        self.direction = "North"
        self.char = "X"
        self.lastChar = "P"

    def setPosition(self, positionY, positionX):
        self.positionX = positionX
        self.positionY = positionY

    def getPosition(self):
        return self.positionY, self.positionX


class Easy(Character):

    def __init__(self):
        super(Easy, self).__init__("Bulbasaur", 50, "Dandelion", 15, 5, "none", 50, ", Congrats you beat just a dumb "
                                                                                    "bulbasaur")
        self.char = "Y"


class Hard(Character):

    def __init__(self):
        super(Hard, self).__init__("Mewtwo", 75, "Flamethrower", 40, 10, "none", 150, ", You have defeated the boss. Go "
                                                                                      "get some sun")
        self.char = "H"
