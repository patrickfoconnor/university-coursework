def solve_btfc( puzzle ):    
   
    # get a list of the empty squares (remaining variables)
    empty_squares = get_empty_squares( puzzle )

    # if there are no remaining empty squares we're done
    if len(empty_squares) == 0: 
        print "Woohoo, success! Check it out:"
        print_puzzle( puzzle )
        return 1
    
    square = get_random_square( empty_squares )
    row = square[0]
    col = square[1]
    
    remaining_values = get_remaining_values( puzzle )
   
    values = list( remaining_values[col+row*9] )
    
    while len( values ) != 0:        
        value = values[ int( math.floor( random.random()*len( values ) ) ) ]
        values.remove(value)        
        if forward_check( remaining_values, value, row, col ):
            puzzle[row][col] = value
            if solve_btfc( puzzle ):
                return 1
            else:
                puzzle[row][col] = 0
                
    return 0







    # Removes the specified value from constrained squares and returns the new list
def remove_values( row, col, value, remaining_values ):
    
    # use a value of zero to indicate that the square is assigned
    remaining_values[col+row*9] = [0]
    
    # Remove the specified value from each row, column, and block if it's there
    for x in remaining_values[row*9:row*9+9]:
        try:
            x.remove( value )
        except ValueError:  
           pass 
            
    for i in range(9):
        try:
            remaining_values[col+9*i].remove( value )
        except ValueError:
            pass

    block_row = row/3
    block_col = col/3  
    for i in range(3):
        for j in range(3):
            try:
                remaining_values[block_col*3+j+(block_row*3+i)*9].remove( value )
            except ValueError:
                pass

    return remaining_values






# return the list of empty squares indices for the puzzle
def get_empty_squares ( puzzle ):
    empty_squares = []
    # scan the whole puzzle for empty cells
    for row in range(len( puzzle )):
        for col in range(len( puzzle[1] )):
            if puzzle[row][col] == 0:
                empty_squares.append( [row,col] ) 
    return empty_squares

# return a randomly selected square from the list of empties
def get_random_square( empty_squares ):   
    # randomly pick one of the empty squares to expand and return it
    return empty_squares[ int(math.floor(random.random()*len(empty_squares))) ] 

     # Returns a list of the remaining potential values for each of the 81 squares
# The list is structured row by row with respect to the puzzle
# Only gets called once, at the beginning of the BT-FC search to initialize
def get_remaining_values( puzzle ):
    remaining_values = []
    # initialize all remaining values to the full domain
    [remaining_values.append( range(1,10) ) for i in range(81) ]    
    for row in range( len(puzzle) ):
        for col in range( len(puzzle[1]) ):
            if puzzle[row][col] != 0:
                # remove the value from the constrained squares  
                value = puzzle[row][col]  
                remaining_values = remove_values( row, col, value, remaining_values )   
                
    return remaining_values  