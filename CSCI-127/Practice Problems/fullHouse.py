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

cards = [[10, "spades"], [14, "spades"], [12, "club"], [13, "spades"], [11, "spades"]]
for i in range(len(cards)):
    print(str(cards[(-i)-1][0]) + " "+ str(cards[i][0]))
    #print(cards[i][0])

def fullHouse(hand):
    pass
    #Not Needed: case = hand[i][1]

        #if hand[0+i][1] == hand[1+i][1]:
        #    return True

        #else:
         #   return False


#print(fullHouse(cards))
#def Extract(lst): 
#    return [item[1] for item in hand] 
      


#def fullHouse(lst):
#   for i in range(len(lst)):
#        firstLetter = 

#print(Extract(hand)) 
