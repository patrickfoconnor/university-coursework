package analysis;

import java.io.*;

import static java.lang.Integer.parseInt;

public class MainAnalyis {
    public static void main(String[] args) throws IOException {
        int boardSize = 5;
        while (boardSize <= 25)   {
            StringBuilder stringRABuilder = new StringBuilder();
            stringRABuilder.append("Reactive Agent results: Board Size ");
            stringRABuilder.append(boardSize);
            stringRABuilder.append("x");
            stringRABuilder.append(boardSize);
            String RAResults = String.valueOf(stringRABuilder.append(getStats(boardSize + "raTestResults.csv")));

            StringBuilder stringFOLBuilder = new StringBuilder();
            stringFOLBuilder.append("First Order Logic results: Board Size ");
            stringFOLBuilder.append(boardSize);
            stringFOLBuilder.append("x");
            stringFOLBuilder.append(boardSize);
            String FOLResults = String.valueOf(stringFOLBuilder.append(getStats(boardSize + "folTestResults.csv")));
            boardSize = boardSize + 5;
        //Print Results
            System.out.println("For the board size " + (boardSize-5) + "x" + (boardSize-5) + ":");
            System.out.println("--------------------------------------------------------");
            System.out.println(stringRABuilder);
            System.out.println("--------------------------------------------------------");
            System.out.println(stringFOLBuilder);
            System.out.println("--------------------------------------------------------");
        }

    }

    // Final Results based on the outputted TestResults.txt
        private static String getStats(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        int winCount = 0;
        int lostCount = 0;
        int goldFoundCount = 0;
        int goldNotCount = 0;
        int wumpusKilledCount = 0;
        int pitKilledCount = 0;
        int noMovesKilledCount = 0;
        int totalScore = 0;
        int cellsExploredCount = 0;

        // Neeed to figure this out
        int killedWumpusCount = 0;
        String line = "";
        double lineCounter = 0.0;
        while ((line = br.readLine()) != null) {
            String[] runResult = line.split(",");
            //handle win/lose
            switch (runResult[0]) {
                case ("WIN") -> {
                    winCount++;
                    goldFoundCount++;
                }
                case ("LOSE") -> {
                    lostCount++;
                    goldNotCount++;
                }
            }
            //Handle Cause of Death
            switch (runResult[1]) {
                case ("NO_MOVES") -> noMovesKilledCount++;
                case ("FALL") -> pitKilledCount++;
                case ("EATEN") -> wumpusKilledCount++;

            }
            totalScore = totalScore + parseInt(runResult[2]);
            cellsExploredCount = cellsExploredCount + parseInt(runResult[3]);
            lineCounter++;
        }

        // Calculate Averages
        double getPercentTen = lineCounter/100;
        double getPercentThousand = lineCounter;
        double percentWin = (winCount / getPercentTen);
        double percentLose = (lostCount / getPercentTen);
        double percentGold = (goldFoundCount / getPercentTen);
        double percentNoGold = (goldNotCount / getPercentTen);
        double percentPitDeath = (pitKilledCount / getPercentTen);
        double percentWumpusDeath = (wumpusKilledCount / getPercentTen);
        double percentNoSteps = (noMovesKilledCount / getPercentTen);
        double averageScore = (totalScore / getPercentThousand);
        double averageCellsExplored = cellsExploredCount / getPercentThousand;
        String results = ("\nWin Percentage " + percentWin + "% \n"
        + "Lost Percentage " + percentLose + "%\n"
        +"Gold Found Percentage " + percentGold + "%\n"
        +"Gold Not Found Percentage " + percentNoGold + "%\n"
        +"Pit Killed Percentage " + percentPitDeath + "%\n"
        +"Wumpus Killed Percentage " + percentWumpusDeath + "%\n"
        +"No Steps Percentage " + percentNoSteps + "%\n"
        +"Average Score " + averageScore+ "\n"
        +"Average Cells Explored " + averageCellsExplored);
            return results;
    }
}

