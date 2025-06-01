# -----------------------------------------+
# Patrick O'Connor                         |
# CSCI 127, Practicum #1                   |
# Last Updated: 02, 20, 2020               |
# -----------------------------------------|
# Evaluate a valid card sequence for a     |
# cribbage game using a list               |
# -----------------------------------------+

def cribbage_sequence(card_list):
    card_list.sort()
    if (card_list[0]+1) == (card_list[1]) and (card_list[1]+1) == (card_list[2]):
        return True

    else:
        return False




def main():
    print(cribbage_sequence([2, 3, 4])) # returns True
    print(cribbage_sequence([2, 4, 3])) # returns True
    print(cribbage_sequence([2, 3, 8])) # returns False

main()

