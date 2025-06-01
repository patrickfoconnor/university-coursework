

        ArrayList<Integer[]> updatedRemainingValues = new ArrayList<>();

        final int zero = 0;

        // Remove the value from each row, column, and box
        // Remove in row
        int lowerBoundRow = row;
        int upperBoundRow = (row * 9) + 9;

        // Step through each of the cells and if there is still the value present remove it
        // First for loop is for getting each domain for the row of cells
        for(int i = lowerBoundRow; i < upperBoundRow; i++) {
            //System.out.println(Arrays.toString(remainingValues.get(i)));
            Integer[] currentCellDomainRows = remainingValues.get(i);
            System.out.println("Cell: " + i + "'s options are "+Arrays.toString(currentCellDomainRows));

            Integer[] updatedCellDomainR = new Integer[9];
            // Step through each of the 9 domains in the row to determine if the value inserted into sudoku is present
            for (int j = 0; j < 9; j++) {
                if (currentCellDomainRows[j] == value) {
                    currentCellDomainRows[j] = zero;
                }

            }
            remainingValues.set(i, currentCellDomainRows);
            //System.out.println("UPDATED: Cell: " + i + "'s options are "+Arrays.toString(currentCellDomainRows));
    /*
                int index = 0;
                int changes = 0;
                for (Integer num : currentCellDomainRows) {
                    if (num != null) {
                        if (num.intValue() != value) {
                            updatedCellDomainR[index] = num;
                            index++;
                            changes++;
                        }
                    }
    // HAVE TO SOMEHOW CHANGE THIS TO ONLY EITHER ADD THE UPDATED WHEN THERE IS A CHANGE OR SIMPLY REMOVE THE
    // NUMBER FROM THE ARRAY OF DOMAINS
                    if (changes == 0) {
                        updatedRemainingValues.add(currentCellDomainRows);
                    } else {
                        updatedRemainingValues.add(updatedCellDomainR);
                    }

                }*/
        }



        // Remove in column



        // Remove in Box
/*        int firstBoxRow = row- row % 3;
        int firstBoxCol = col - col %3;
        System.out.println("Row: "+ firstBoxCol+ " Col: "+ firstBoxCol);

        //Check if valid in 3 x 3 box
        for(int i = firstBoxRow; i < firstBoxRow + 3; i++) {
            for (int j = firstBoxCol; j < firstBoxCol + 3; j++) {
                try{
                    int index = ((firstBoxCol * 3 +j)+ (firstBoxRow * 3 +i) * 9);
                    //System.out.println("Test: "+Arrays.toString(remainingValues.get(index)));
                    //System.out.println("Test: "+ remainingValues.get(index));
                }
                catch (Exception exception){
                    assert true;
                }

            }
        }*/



        return remainingValues;
    }

    /**
     * Get cells that are empty within puzzle
     * @param board
     */
    private ArrayList<Point> getEmptyCells(Cell[][] board) {
        ArrayList<Point> emptyCells = new ArrayList<Point>();
        // Scan the board for empty cells
        for (int row = 0; row < boardSize; row++) {
            for (int col =0; col < boardSize; col++) {
                // Check if the value is 0
                if(board[row][col].getValue() == 0){
                    // Store this empty cell
                    Point emptyCell = new Point(row, col);
                    emptyCells.add(emptyCell);
                }
            }
        }
        return emptyCells;
    }

    /**
     * Output data to either terminal or output file
     * @param emptyCells
     */
    private Point getRandomCell(ArrayList<Point> emptyCells) {
        // Use math function to find random index and return it
        int randIndex = (int)(Math.random() * emptyCells.size());
        Point emptyCell = emptyCells.get(randIndex);
        return emptyCell;
    }