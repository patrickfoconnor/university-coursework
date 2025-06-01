import numpy as np
import random

# -------------------------------------------------
# CSCI 127, Lab 9
# April 2, 2020
# Patrick O'Connor
# -------------------------------------------------

class Die:

    def __init__(self, sides):
        """A constructor method to create a die"""
        self.sides = sides

    def roll(self):
        """A general method to roll the die"""
        return random.randint(1, self.sides)

# -------------------------------------------------

class Yahtzee:

    def __init__(self, sides):
        """A constructor method that can record 5 dice rolls"""
        self.rolls = np.zeros(5, dtype=np.int16)
        self.sides = sides

    def roll_dice(self):
        """A general method that rolls 5 dice"""
        for i in range(len(self.rolls)):
            self.rolls[i] = Die(self.sides).roll()

    def count_outcomes(self):
        """A helper method that determines how many 1s, 2s, etc. were rolled"""
        counts = np.zeros(self.sides + 1, dtype=np.int16)
        for roll in self.rolls:
            counts[roll] += 1
        return counts

# Low Roll occurs when each of the five dice is either a 1 or an 2
    def is_it_low_roll(self):
    # Using self.count_outcomes() find the count for each set of rolls
        counts = self.count_outcomes() # An array of 9 sides (not using 0) how many of each number was rolled
    # Loop through the counts and make sure that index 1 and 2(Die Number 1 and 2) added together are equal to 5
        return counts[1] + counts[2] == 5


# --------------------------------------------------
# Three of a Kind occurs when three of the dice show the same number. 
# The other two dice must not show this number and must also be different from one another.
    def is_it_three_of_a_kind(self):
        three_of_a_kind_counter = 0
        one_of_a_kind_counter = 0
    # Using self.count_outcomes() find the count for each set of rolls
        counts = self.count_outcomes()
    # Step through the counts     
        for count in counts:
    # If the count is eqaul to three it is a three of a kind number
            if count == 3:
                three_of_a_kind_counter += 1
    # Other counts must equal only 1
            elif count == 1:
                one_of_a_kind_counter += 1
    # If there is only one three_of_kind_counter and two one_of_a_kinds
        if three_of_a_kind_counter == 1 and one_of_a_kind_counter == 2:
            return True


# Large Straight occurs when the five numbers can be arranged consecutively
    def is_it_large_straight(self):
    # Assign the rolls to a variable so they can be sorted
        five_rolls = self.rolls

    # Sort the block of rolls by number rolled
        sorted_rolls = np.sort(five_rolls)
    # Go through the sorted roll and check from the larger number in list is 1 away from one step/roll before
        if  (sorted_rolls[-1] -  sorted_rolls[-2]) == (1) and (sorted_rolls[-2] -  sorted_rolls[-3]) == (1) and \
            (sorted_rolls[-3] -  sorted_rolls[-4]) == (1) and (sorted_rolls[-4] -  sorted_rolls[-5]) == (1):
                    return True
        else:
            return False


# -------------------------------------------------
        
def main(how_many):

    low_rolls = 0
    three_of_a_kinds = 0
    large_straights = 0
    game = Yahtzee(8)       # 8-sided dice

    for i in range(how_many):
        game.roll_dice()
        if game.is_it_low_roll():
            low_rolls += 1
        elif game.is_it_three_of_a_kind():
            three_of_a_kinds += 1
        elif game.is_it_large_straight():
            large_straights += 1

    print("Number of Rolls:", how_many)
    print("---------------------")
    print("Number of Low Rolls:", low_rolls)
    print("Percent:", "{:.2f}%\n".format(low_rolls * 100 / how_many))
    print("Number of Three of a Kinds:", three_of_a_kinds)
    print("Percent:", "{:.2f}%\n".format(three_of_a_kinds * 100 / how_many))
    print("Number of Large Straights:", large_straights)
    print("Percent:", "{:.2f}%".format(large_straights * 100 / how_many))

# -------------------------------------------------

# Change back to 20000
main(20000)
    
        
