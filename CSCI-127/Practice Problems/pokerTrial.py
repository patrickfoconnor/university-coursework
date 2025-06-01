def royal_flush(hand):
    for cards in len(hand):
        if hand[1:0] == s:
            return True
        else:
            return False

def get_all_ranks(hand):
    result = []
    for card in hand:
        result.append(card[0])
    return result

def evaluate(poker_hand):
    poker_hand.sort()
    poker_hand_ranks = get_all_ranks(poker_hand)
    print(poker_hand, "--> ", end="")
    if royal_flush(poker_hand):
        print("Royal Flush")
    else:
        print("Nothing")


# -----------------------------------------+

def main():
    print("CSCI 127: Poker Hand Evaluation Program")
    print("---------------------------------------")
    evaluate([[10, "spades"], [14, "spades"], [12, "spades"], [13, "spades"], [11, "spades"]])  
    # royal flush

main()