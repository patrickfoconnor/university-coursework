import numpy as np
import string

# ---------------------------------------
# CSCI 127, Joy and Beauty of Data      |
# Program 5: Peg Rectangle Solitaire    |
# Your Name(, Your Partner's Name)      |
# Last Modified: ??, 2020               |
# ---------------------------------------
# A brief overview of the program.
# ---------------------------------------

# ---------------------------------------
# Start of PegRectangleSolitaire Class  |
# ---------------------------------------

class PegRectangleSolitaire:

    def __init__(self, rows, columns, empty_row, empty_col):
        self.board = np.full((rows, columns), True)
        self.board[empty_row][empty_col] = False
        self.pegs_left = rows * columns - 1
        
# ---------------------------------------

    def __str__(self):
        answer = "   "
        for column in range(self.board.shape[1]):
            answer += " " + str(column + 1) + "  "
        answer += "\n"
        answer += self.separator()
        for row in range(self.board.shape[0]):
            answer += str(row + 1) + " |"
            for col in range(self.board.shape[1]):
                if self.board[row][col]:
                    answer += " o |"
                else:
                    answer += "   |"
            answer += "\n"
            answer += self.separator()
        return answer
    
# ---------------------------------------

    def separator(self):
        answer = "  +"
        for _ in range(self.board.shape[1]):
            answer += "---+"
        answer += "\n"
        return answer

# ---------------------------------------
# The four missing methods go here.  Do |
# not modify anything else.             |
# --------------------------------------|
    def make_move(self,row_start, col_start, row_end, col_end):
        peg_in_between_row = int((row_start+row_end)/2)
        peg_in_between_col = int((col_start+col_end)/2)

# Set original position to empty
        self.board[row_start, col_start] = False
# Move the peg to destination
        self.board[row_end, col_end] = True
# Remove middle peg that was jumped
        self.board[peg_in_between_row,peg_in_between_col] = False
# Subtract one peg from peg counter
        self.pegs_left = self.pegs_left - 1
    
    def legal_move(self,row_start, col_start, row_end, col_end):
        check_for_peg_in_between_row = int((row_start+row_end)/2)
        check_for_peg_in_between_col = int((col_start+col_end)/2)
        y_dis = abs(row_end-row_start)
        x_dis = abs(col_end-col_start)
# Make sure destination is empty
        if self.board[row_end, col_end] == False:
# Ensure you are not moving nowhere
            if col_end != col_start and row_end != row_start:
                return False
# Make sure there is a peg to move
            if self.board[row_start,col_start] == True:
# Check to see if peg is between move
                if self.board[check_for_peg_in_between_row,check_for_peg_in_between_col] == True:
# Check the distance between pegs
                    if y_dis == 2 or x_dis == 2:
                        return True
    
    def game_over(self):
# Game is over when there is one left
        if self.pegs_left == 1:
            return True
        else:
            legal_moves_remaining = False
            columns = self.board.shape[1]
            rows = self.board.shape[0]
# Iterate through and check to see if there are legal moves available
            for column in range(columns):
                for row in range(rows):
                    if self.board[row][column] == True:
                        for end_column in range(columns):
                            for end_row in range(rows):
                                if self.legal_move(row,column,end_row,end_column) == True:
                                    legal_moves_remaining = True
        if legal_moves_remaining == False:
            return True
                    

    def final_message(self):
        num_of_pegs_left_string = ("Number of pegs left:" + str(self.pegs_left))
# Notify the player of the remaining pegs
        print(num_of_pegs_left_string)
# 2 pegs or fewer are left: return "You're a DigiPeg Genius!"
        if self.pegs_left <= 2:
            print("You're a DigiPeg Genius!")
# 3 or 4 pegs left: return "Not to shabby, rookie."
        elif self.pegs_left == 3 or self.pegs_left == 4:
            print("Not to shabby, rookie.")
# 5 or 6 pegs left: return "That's nothing to write home about."
        elif self.pegs_left == 5 or self.pegs_left == 6:
            print("That's nothing to write home about.")
# 7 or more pegs are left: return "You're a DigiPeg Igno-Ra-Moose"
        elif self.pegs_left >= 7:
            print("You're a DigiPeg Igno-Ra-Moose")
        


# ---------------------------------------
# End of PegRectangleSolitaire Class    |
# ---------------------------------------

def get_choice(low, high, message):
    message += " [" + str(low) + " - " + str(high) + "]: "
    legal_choice = False
    while not legal_choice:
        legal_choice = True
        answer = str(input(message))
        for character in answer:
            if character not in string.digits:
                legal_choice = False
                print("That is not a number, try again.")
                break 
        if legal_choice:
            answer = int(answer)
            if (answer < low) or (answer > high):
                legal_choice = False
                print("That is not a valid choice, try again.")
    return answer

# ---------------------------------------

def main():
    print("Welcome to Peg Rectangle Solitaire!")
    print("-----------------------------------\n")
    
    rows = get_choice(1, 9, "Enter the number of rows")
    columns = get_choice(1, 9, "Enter the number of columns")
    row = get_choice(1, rows, "Enter the empty space row") - 1
    column = get_choice(1, columns, "Enter empty space column") - 1   
    game = PegRectangleSolitaire(rows, columns, row, column)
    print

    print(game)
    while (not game.game_over()):
        row_start = get_choice(1, rows, "Enter the row of the peg to move") - 1
        col_start = get_choice(1, columns, "Enter the column of the peg to move") - 1
        row_end = get_choice(1, rows, "Enter the row where the peg lands") - 1
        col_end = get_choice(1, columns, "Enter the column where the peg lands") - 1
        if game.legal_move(row_start, col_start, row_end, col_end):
            game.make_move(row_start, col_start, row_end, col_end)
        else:
            print("Sorry.  That move is not allowed.")
        print()
        print(game)

    game.final_message()

# ---------------------------------------

main()
