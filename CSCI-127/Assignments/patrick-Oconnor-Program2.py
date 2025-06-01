# -----------------------------------------+
# Patrick O'Connor                         |
# CSCI 127, Program 2                      |
# Last Updated: 02, 10, 2020               |
# -----------------------------------------|
# Simplified Poker Hand evaluation system. |
# -----------------------------------------+
# One:              1 | Eight:           8 |
# Two:              2 | Nine:            9 |
# Three:            3 | Ten:            10 |
# Four:             4 | Jack:           11 |
# Five:             5 | Queen:          12 |
# Six:              6 | King:           13 |
# Seven:            7 | Ace:            14 |
# -----------------------------------------+
# Diamonds:         6 | Hearts:         13 |
# Spades:           7 | Clubs:          14 |
# -----------------------------------------+

def get_all_ranks(hand):
    result = []
    for card in hand:
        result.append(card[0])
    return result

# --------------------------------------------------------------
# Contains five consecutive cards that share the same suit and are the highest possible number
def royal_flush(hand):
# Create new list with only the numbers
    numbers = []
    for cardvalue in range(len(hand)):
        value = hand[cardvalue][0]
        numbers.append(value)

# Create new list with only the face shape
    faces = []
    for faceshape in range(len(hand)):
        value = hand[faceshape][1]
        faces.append(value)

# For Loop: for the highest card set to i if the count  if statement: Checking the hand for highest possible cards
    officialCount = 0
    for i in range (10,15):
        counter = numbers.count(i)
        if counter == 1:
            officialCount = officialCount + 1
        else:
             officialCount =+ 0
    if officialCount == 5:
# Go through the spades checking
        spadeCounter = 0
        spadeCounter = faces.count('spades')
        if spadeCounter == 5:
            return True    
# Go through the clubs
        clubsCounter = 0
        clubsCounter = faces.count("clubs")
        if clubsCounter == 5:
            return True
# Go through the clubs
        diamondsCounter = 0
        diamondsCounter = faces.count("diamonds")
        if diamondsCounter == 5:
            return True
# Go through the clubs
        heartsCounter = 0
        heartsCounter = faces.count("hearts")
        if heartsCounter == 5:
            return True
    else:
        return False


# --------------------------------------------------------------
# Contains five consecutive cards that share the same suit. 
# A straight flush does not contain an ace.
def straight_flush(hand):
    numbers = []
    for cardvalue in range(len(hand)):
        value = hand[cardvalue][0]
        numbers.append(value)

# Create new list with only the face shape
    faces = []
    for faceshape in range(len(hand)):
        value = hand[faceshape][1]
        faces.append(value)

# Check to see if an ace is present
    counter = numbers.count(14)
    if counter >= 1:
        return False
    elif counter == 0:
# Check to see that the numbers are consecutive
        if numbers[-1] - numbers[-2] == (1):
            if numbers[-2] - numbers[-3] == (1):
                if numbers[-4] - numbers[-5] == (1):
# Checking that all 5 are the same suit
# Go through the spades 
                    spadeCounter = 0
                    spadeCounter = faces.count('spades')
                    if spadeCounter == 5:
                        return  True   
# Go through the clubs
                    clubsCounter = 0
                    clubsCounter = faces.count("clubs")
                    if clubsCounter == 5:
                        return True
# Go through the clubs
                    diamondsCounter = 0
                    diamondsCounter = faces.count("diamonds")
                    if diamondsCounter == 5:
                        return True
# Go through the clubs
                    heartsCounter = 0
                    heartsCounter = faces.count("hearts")
                    if heartsCounter == 5:
                        return True
    else:
        return False


# --------------------------------------------------------------
# Contains five consecutive cards that do not all share the same suit.
def straight(hand):
    numbers = []
    for cardvalue in range(len(hand)):
        value = hand[cardvalue][0]
        numbers.append(value)
    
    if numbers[-1] - numbers[-2] == (1):
        if numbers[-2] - numbers[-3] == (1):
            if numbers[-4] - numbers[-5] == (1):
                return True
    else:
        return False


# --------------------------------------------------------------
# Contains four cards that share the same rank.
def four_of_a_kind(ranks):
# Create new list that includes only the numbers 
    numbers = []
    for cardvalue in range(len(ranks)):
        value = ranks[cardvalue]
        numbers.append(value)

# Go through each number and count how many times it is seen
# If the number is 4 then the hand is a 4 of a kind
    for i in range (14):
        counter = numbers.count(i)
        if counter == 4:
            return True


# --------------------------------------------------------------
# Contains three cards that share the same rank and 
# two cards that share a different rank.
def full_house(ranks):
# Create new list that includes only the numbers 
    numbers = []
    for cardvalue in range(len(ranks)):
        value = ranks[cardvalue]
        numbers.append(value)
# Go through each number and count how many time it is seen
# If the number is 3 then the three Pair count will increase to 1 
# notifying the function that it is a three of a kind
    tripleCount = 0
    pairCount = 0
    for i in range (14):
        counter = numbers.count(i)
        if counter == 2:
            pairCount =+ 1
        elif counter == 3:
            tripleCount =+ 1
        else:
             tripleCount =+ 0
             pairCount =+ 0
        if tripleCount == 1 and pairCount == 1:
            return True
    else:
        return False

# --------------------------------------------------------------
# Contains three cards that share the same rank and
# two cards that have unique ranks.
def three_of_a_kind(ranks):
# Create new list that includes only the numbers 
    numbers = []
    for cardvalue in range(len(ranks)):
        value = ranks[cardvalue]
        numbers.append(value)
# Go through each number and count how many time it is seen
# If the number is 3 then the official count will increase to 1 
# notifying the function that it is a three of a kind
    officialCount = 0
    for i in range (14):
        counter = numbers.count(i)
        if counter == 3:
            officialCount =+ 1
        else:
            officialCount =+ 0
        if officialCount == 1:
            return True
    else:
        return False


# --------------------------------------------------------------
# Contains two cards that share the same rank,
# two cards that share a different rank, and 
# one card that has a unique rank.
def two_pair(ranks):
# Create new list that includes only the numbers 
    numbers = []
    for cardvalue in range(len(ranks)):
        value = ranks[cardvalue]
        numbers.append(value)
# Start a counter to add up the pairs within the hand
# If the counter reaches two pairs then the hand is two pairs
    pairCounter = 0
    for i in range (1,15):
        counter = numbers.count(i)
        if counter == 2:
            pairCounter += 1
        else:
            pairCounter += 0
        if pairCounter == 2:
            return True
    else:
        return False


# --------------------------------------------------------------
# Contains two cards that share the same rank and
# three cards that have unique ranks.
def one_pair(ranks):
# Create new list that includes only the numbers 
    numbers = []
    for cardvalue in range(len(ranks)):
        value = ranks[cardvalue]
        numbers.append(value)

# Go through each number and count how many times it is seen
# If a pair is identified it will add to the official count
    officialCount = 0
    for i in range (14):
        counter = numbers.count(i)
        if counter == 2:
            officialCount =+ 1
        else:
            officialCount =+ 0
        if officialCount == 1:
            return True
    else:
        return False


# -----------------------------------------+
# Do not modify the evaluate function.     |
# -----------------------------------------+

def evaluate(poker_hand):
    poker_hand.sort()
    poker_hand_ranks = get_all_ranks(poker_hand)
    print(poker_hand, "--> ", end="")
    if royal_flush(poker_hand):
        print("Royal Flush")
    elif straight_flush(poker_hand):
        print("Straight Flush")
    elif four_of_a_kind(poker_hand_ranks):
        print("Four of a Kind")
    elif full_house(poker_hand_ranks):
        print("Full House")
    elif straight(poker_hand):
        print("Straight")
    elif three_of_a_kind(poker_hand_ranks):
        print("Three of a Kind")
    elif two_pair(poker_hand_ranks):
        print("Two Pair")
    elif one_pair(poker_hand_ranks):
        print("One Pair")
    else:
        print("Nothing")
		
# -----------------------------------------+

def main():
    print("CSCI 127: Poker Hand Evaluation Program")
    print("-----------------------------------------------------------------------------------------------------")
    evaluate([[10, "spades"], [14, "spades"], [12, "spades"], [13, "spades"], [11, "spades"]])  # royal flush
    evaluate([[10, "clubs"], [9, "clubs"], [6, "clubs"], [7, "clubs"], [8, "clubs"]])           # straight flush
    evaluate([[2, "diamonds"], [7, "clubs"], [2, "hearts"], [2, "clubs"], [2, "spades"]])       # 4 of a kind
    evaluate([[8, "diamonds"], [7, "clubs"], [8, "hearts"], [8, "clubs"], [7, "spades"]])       # full house
    evaluate([[13, "diamonds"], [7, "clubs"], [7, "hearts"], [8, "clubs"], [7, "spades"]])      # 3 of a kind
    evaluate([[10, "clubs"], [9, "clubs"], [6, "clubs"], [7, "clubs"], [8, "spades"]])          # straight
    evaluate([[10, "spades"], [9, "clubs"], [6, "diamonds"], [14, "diamonds"], [14, "hearts"]])   # 2 pair
    evaluate([[10, "spades"], [12, "clubs"], [6, "diamonds"], [9, "diamonds"], [12, "hearts"]]) # 1 pair
    evaluate([[2, "spades"], [7, "clubs"], [8, "diamonds"], [13, "diamonds"], [11, "hearts"]])  # nothing

# -----------------------------------------+

main()
