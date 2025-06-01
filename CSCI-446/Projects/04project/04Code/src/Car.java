import java.io.IOException;
import java.util.Random;

public class Car {
    public boolean training;
    public int numCrashes = 0;
    public int xPosition;
    public int xVelocity;
    public int yPosition;
    public int yVelocity;
    public int xStart;
    public int yStart;
    int[][] unvisitedCells;
    int[][] startCells;
    int[][] finishCells;
    int[][] boundaryCells;
    private final String[][] racetrack;
    private final String restartType;
    private int xCrash = -1;
    private int yCrash = -1;

    public Car(String[][] racetrack, String restartType) throws IOException {
        this.racetrack = racetrack;
        this.restartType = restartType;
        unvisitedCells = getUnvisitedCells();
        finishCells = getFinishCells();
        boundaryCells = getBoundaryCells();
        startCells = getStartCells();
        xVelocity = 0;
        yVelocity = 0;
    }

    public boolean move(int newX, int newY, int previousX, int previousY) {
        for (int i = 0; i < unvisitedCells.length; i++) {
            if (unvisitedCells[i][0] == previousX && unvisitedCells[i][1] == previousY) {
                racetrack[previousY][previousX] = ".";
            }
        }
        for (int i = 0; i < startCells.length; i++) {
            if (startCells[i][0] == previousX && startCells[i][1] == previousY) {
                racetrack[previousY][previousX] = "S";
            }
        }
        boolean crashed = false;
        boolean finished = false;

        crashed = checkCrash(newX, newY, previousX, previousY);
        if (!crashed) {
            if (!training) {
                racetrack[newY][newX] = "C";
            }
            xPosition = newX;
            yPosition = newY;
            finished = isFinished(newX, newY, previousX, previousY);
        } else {
            finished = isFinished(newX, newY, previousX, previousY);

            if (!finished) {
                racetrack[yCrash][xCrash] = "X";
                restartPosition();


            }
        }
        return finished;
    }

    public void startingLine() {
        Random random = new Random();
        int randNum = random.nextInt(startCells.length);

        yStart = startCells[randNum][1];
        xStart = startCells[randNum][0];

        xPosition = xStart;
        yPosition = yStart;

        if (!training) {
            racetrack[yStart][xStart] = "C";
        }
    }

    public boolean isFinished(int newX, int newY, int previousX, int previousY) {
        boolean finished = false;
        int diffX = Math.abs(previousX - newX);
        int diffY = Math.abs(previousY - newY);
        int x = previousX;
        int y = previousY;
        int numLoops = 1 + diffX + diffY;
        int xInc = (newX > previousX) ? 1 : -1;
        int yInc = (newY > previousY) ? 1 : -1;

        int error = diffX - diffY;
        diffX *= 2;
        diffY *= 2;

        for (; numLoops > 0; --numLoops) {
            for (int i = 0; i < finishCells.length; i++) {
                if (finishCells[i][0] == x && finishCells[i][1] == y) {
                    if (!training) {
                        racetrack[y][x] = "C";
                    }
                    if (!checkCrash(xPosition, yPosition, previousX, previousY)) {
                        xPosition = x;
                        yPosition = y;
                        finished = true;
                    }

                }
            }
            if (error > 0) {
                x += xInc;
                error -= diffY;
            } else {
                y += yInc;
                error += diffX;
            }

        }
        return finished;
    }
    public void isTraining() {
        this.training = true;
    }

    public boolean checkCrash(int newX, int newY, int previousX, int previousY) {
        int diffX = Math.abs(previousX - newX);
        int diffY = Math.abs(previousY - newY);
        int x = previousX;
        int y = previousY;
        int numLoops = 1 + diffX + diffY;
        int xInc = (newX > previousX) ? 1 : -1;
        int yInc = (newY > previousY) ? 1 : -1;
        int error = diffX - diffY;
        diffX *= 2;
        diffY *= 2;

        for (; numLoops > 0; --numLoops) {
            for (int i = 0; i < boundaryCells.length; i++) {
                if (boundaryCells[i][0] == x && boundaryCells[i][1] == y) {
                    xCrash = x;
                    yCrash = y;
                    return true;
                }
            }
            if (error > 0) {
                x += xInc;
                error -= diffY;
            } else {
                y += yInc;
                error += diffX;
            }
        }
        return false;
    }

    private int[][] getStartCells() {
        int startCellCount = 0;
        int[][] startingArray;
        for (int i = 0; i < racetrack.length; i++) {
            for (int j = 0; j < racetrack[i].length; j++) {
                if (racetrack[i][j].equalsIgnoreCase("S")) {
                    startCellCount++;
                }
            }
        }
        startingArray = new int[startCellCount][2];
        //int[][] localArray = new int[startCellCount][2];

        int index = 0;

        for (int i = 0; i < racetrack.length; i++) {
            for (int j = 0; j < racetrack[i].length; j++) {
                if (racetrack[i][j].equalsIgnoreCase("S")) {
                    startingArray[index][0] = j;
                    startingArray[index][1] = i;
                    index++;
                }
            }
        }
        //startingArray = localArray;
        return startingArray;
    }

    private int[][] getUnvisitedCells() {
        int unvisitedCellCount = 0;
        for (int i = 0; i < racetrack.length; i++) {
            for (int j = 0; j < racetrack[i].length; j++) {
                if (racetrack[i][j].equalsIgnoreCase(".")) {
                    unvisitedCellCount++;
                }
            }
        }
        int[][] unvisitedArray = new int[unvisitedCellCount][2];

        int index = 0;

        for (int i = 0; i < racetrack.length; i++) {
            for (int j = 0; j < racetrack[i].length; j++) {
                if (racetrack[i][j].equalsIgnoreCase(".")) {
                    unvisitedArray[index][0] = j;
                    unvisitedArray[index][1] = i;
                    index++;
                }
            }
        }
        return unvisitedArray;


    }

    private int[][] getFinishCells() {
        int finishCellCount = 0;
        for (int i = 0; i < racetrack.length; i++) {
            for (int j = 0; j < racetrack[i].length; j++) {
                if (racetrack[i][j].equalsIgnoreCase("F")) {
                    finishCellCount++;
                }
            }
        }
        int[][] finishArray = new int[finishCellCount][2];

        int index = 0;

        for (int i = 0; i < racetrack.length; i++) {
            for (int j = 0; j < racetrack[i].length; j++) {
                if (racetrack[i][j].equalsIgnoreCase("F")) {
                    finishArray[index][0] = j;
                    finishArray[index][1] = i;
                    index++;
                }
            }
        }
        return finishArray;


    }

    private int[][] getBoundaryCells() {
        int boundaryCellCount = 0;
        for (int i = 0; i < racetrack.length; i++) {
            for (int j = 0; j < racetrack[i].length; j++) {
                if (racetrack[i][j].equalsIgnoreCase("#")) {
                    boundaryCellCount++;
                }
            }
        }
        int[][] boundaryArray = new int[boundaryCellCount][2];

        int index = 0;

        for (int i = 0; i < racetrack.length; i++) {
            for (int j = 0; j < racetrack[i].length; j++) {
                if (racetrack[i][j].equalsIgnoreCase("#")) {
                    boundaryArray[index][0] = j;
                    boundaryArray[index][1] = i;
                    index++;
                }
            }
        }
        return boundaryArray;

    }

    public void restartPosition() {
        xVelocity = 0;
        yVelocity = 0;
        numCrashes++;

        if (!training) {
            racetrack[yStart][xStart] = "C";
        }
        xPosition = xStart;
        yPosition = yStart;
    }

    public boolean changePosition(int xAcceleration, int yAcceleration) {
        boolean isFinished;
        int previousX = xPosition;
        int previousY = yPosition;
        //update y velocity
        if (yVelocity + yAcceleration <= 5) {
            if (yVelocity + yAcceleration >= -5) {
                yVelocity = yVelocity + yAcceleration;
            }
        }
        yPosition = yPosition + yVelocity;
        //update x velocity
        if (xVelocity + xAcceleration <= 5) {
            if (xVelocity + xAcceleration >= -5) {
                xVelocity = xVelocity + xAcceleration;
            }
        }
        xPosition = xPosition + xVelocity;
        isFinished = move(xPosition, yPosition, previousX, previousY);
        return isFinished;
    }
    /*public void restart(int previousX, int previousY, int xCrash, int yCrash){
        xVelocity = 0;
        yVelocity = 0;

        int xNew = xCrash;
        int yNew = yCrash;
        int xDiff = previousX - xNew;
        int yDiff = previousY - yNew;

        if(xDiff != 0){
            if(xDiff > 0){
                if(!checkCrash(xNew + 1, yNew, previousX, previousY)){

                }
            }
        }


    }*/

}
