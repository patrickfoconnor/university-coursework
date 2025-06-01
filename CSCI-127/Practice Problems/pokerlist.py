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

def evaluate(poker_hand):
    poker_hand.sort()
    poker_hand_ranks = get_all_ranks(poker_hand)

    if one_pair(poker_hand_ranks):
        print("One Pair")
    else:
        print("Nothing")

def one_pair(ranks):
    for i in range(len(ranks)):
        if ranks[-i][1] == ranks[i][1]:
            return True

        else:
            return False
      

def main():
    print("CSCI 127: Poker Hand Evaluation Program")
    print("---------------------------------------")
#    evaluate([[10, "spades"], [14, "spades"], [12, "spades"], [13, "spades"], [11, "spades"]])  # royal flush
#    evaluate([[10, "clubs"], [9, "clubs"], [6, "clubs"], [7, "clubs"], [8, "clubs"]])           # straight flush
#    evaluate([[2, "diamonds"], [7, "clubs"], [2, "hearts"], [2, "clubs"], [2, "spades"]])       # 4 of a kind
#    evaluate([[8, "diamonds"], [7, "clubs"], [8, "hearts"], [8, "clubs"], [7, "spades"]])       # full house
#    evaluate([[13, "diamonds"], [7, "clubs"], [7, "hearts"], [8, "clubs"], [7, "spades"]])      # 3 of a kind
#    evaluate([[10, "clubs"], [9, "clubs"], [6, "clubs"], [7, "clubs"], [8, "spades"]])          # straight
#    evaluate([[10, "spades"], [9, "clubs"], [6, "diamonds"], [9, "diamonds"], [6, "hearts"]])   # 2 pair
    evaluate([[10, "spades"], [12, "clubs"], [6, "diamonds"], [9, "diamonds"], [12, "hearts"]]) # 1 pair
    evaluate([[2, "spades"], [7, "clubs"], [8, "diamonds"], [13, "diamonds"], [11, "hearts"]])  # nothing

main()

#def fullHouse(lst):
#   for i in range(len(lst)):
#        firstLetter = 

#print(Extract(hand)) 
