package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class SudokuSolver {
    private int boardSize = 9;
    private Cell[][] grid;


    public static void main(String[] args) throws Exception{
        String sudokuInput = "puzzles/Easy-P3.csv";
        String sudokuOutput = "Easy-P3-Solution.csv";



        SudokuSolver board = new SudokuSolver();
        // Read in data from csv
        board.getData(sudokuInput);
        board.backtrack(board.grid);

        System.out.println("Final Output: ");
        board.printData(sudokuOutput);


    }

/*--------- Begin Data Input ----------------------  */
    /**
     * Begin read in of data from the input csv file
     * @param filePath
     */

    private void getData(String filePath) {
        grid = new Cell[boardSize][boardSize];
        Scanner scanIn = null;
        int row = 0;

        try {
            scanIn = new Scanner(new BufferedReader(new FileReader(filePath)));

            while (scanIn.hasNextLine()) {
                String inputLine = scanIn.nextLine();
                String[] inArray = inputLine.split(",");
                for(int i = 0; i < 9; i++){
                    Cell curCell = new Cell();
                    if (row == 0 && i == 0){
                        inArray[i] = inArray[i].substring(1);
                    }
                    //if input is a blank space
                    if (inArray[i].equals("?")){
                        curCell.setValue(0);
                        curCell.setChangeFlag(false);
                        grid[row][i] = curCell;
                    }
                    //else if input is a number
                    else{
                        curCell.setValue(Integer.parseInt(inArray[i]));
                        curCell.setChangeFlag(true);
                        grid[row][i] = curCell;
                    }

                }
                row++;
            }
            scanIn.close();

        } catch (Exception e) {
            System.out.println("Bad Input: " + e);
            scanIn.close();
        }
    }
/*--------- End Data Input ----------------------  */



/*--------- Begin Data Output ----------------------  */
    /**
     * Output data to either terminal or output file
     * @param filePath
     */

    private void printData(String filePath) {
        FileWriter output;
        BufferedWriter bw;

        try {
            output = new FileWriter(filePath);
            bw = new BufferedWriter(output);
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (j==boardSize-1)
                    {
                        output.write(grid[i][j].getValue() + "\r");
                        System.out.println(grid[i][j].getValue());
                    }
                    else
                    {
                        output.write(grid[i][j].getValue() + ",");
                        System.out.print(grid[i][j].getValue() + ",");
                    }
                }

            }
            bw.close();
            output.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }

/*--------- End Data Output ----------------------  */

/*--------- Begin Valid Check --------------------  */
    public static boolean isValid(Cell[][] board, int row, int col, int num)
    {
        //Check if valid in row
        for(int i = 0; i < 9; i++){
            if(board[row][i].getValue() == num){
                return false;
            }
        }
        //Check if valid in column
        for(int i = 0; i < 9; i++){
            if(board[i][col].getValue() == num){
                return false;
            }
        }
        int firstBoxRow = row - row % 3;
        int firstBoxCol = col - col % 3;

        //Check if valid in 3 x 3 box
        for(int i = firstBoxRow; i < firstBoxRow + 3; i++){
            for(int j = firstBoxCol; j < firstBoxCol + 3; j++){

                if(board[i][j].getValue() == num){
                    return false;
                }
            }
        }
        return true;

    }

/*--------- End Valid Check ----------------------  */


/*--------- Begin Backtrack ----------------------  */

    public static boolean backtrack(Cell[][] board){
        boolean isEmpty = true;
        int row = -1;
        int col = -1;

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){

                if(board[i][j].getValue() == 0){
                    row = i;
                    col = j;

                    isEmpty = false;
                    break;
                }
            }
            if(!isEmpty){
                break;
            }
        }
        if(isEmpty){
            return true;
        }

        for(int i = 1; i <= 9; i++){
            //maybe make the guess a random number between 1&9
            int num = i;
            if(isValid(board, row, col, num)){
                board[row][col].setValue(num);

                if(backtrack(board)){
                    return true;

                }
                else{
                    board[row][col].setValue(0);
                }
            }
        }

        return false;
    }

/*--------- End Backtrack ----------------------  */

}
