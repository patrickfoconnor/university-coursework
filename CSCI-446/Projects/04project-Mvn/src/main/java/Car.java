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
    //restart at previous position or restart from start
    private final String restartType;
    //coordinates of crash
    private int xCrash = -1;
    private int yCrash = -1;

    //Car.java stores coordinates of available spots on the track, 
    //as well as the starting, finish, and boundary coordinates.
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
    // put car on specified coordinates
    //returns true if car passed finish line
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
                restart();
                //restartPosition(previousX, previousY, xCrash, yCrash);
                


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
    // checks if car passed the finish line
    // returns true if car has crossed
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
    // prompts car to train
    public void isTraining() {
        this.training = true;
    }
    // returns true if car has hit a wall
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
    // returns array of starting line cells
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
    // returns array of available cells
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
    // returns array of finish line cells
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
    // returns array of wall cells
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
    // crash type where car restarts from start line
    // velocity is set to 0,0
    public void restart() {
        xVelocity = 0;
        yVelocity = 0;
        numCrashes++;

        if (!training) {
            racetrack[yStart][xStart] = "C";
        }
        xPosition = xStart;
        yPosition = yStart;
    }
    // uses new velocity value to update the coordinates of the car
    // returns true is car has crossed finish line
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
    // handles crash type where car restarts from previous position
    // velocity is set to 0,0
    public void restartPosition(int previousX, int previousY, int xCrash, int yCrash) {
        xVelocity = 0;
        yVelocity = 0;
        numCrashes++;

        int xNew = xCrash;
        int yNew = yCrash;
        int xDiff = previousX - xNew;
        int yDiff = previousY - yNew;

        if (xDiff != 0) {
            if (xDiff > 0) {
                if (!checkCrash(xNew + 1, yNew, previousX, previousY)) {
                    xNew++;

                }
                else if (!checkCrash(xNew - 1, yNew, previousX, previousY)) {
                    xNew--;
                }
                else if (!checkCrash(xNew, yNew + 1, previousX, previousY)) {
                    yNew++;
                }
                else if (!checkCrash(xNew, yNew - 1, previousX, previousY)) {
                    yNew--;
                }
            }
            else {
                if (!checkCrash(xNew - 1, yNew, previousX, previousY)) {
                    xNew--;

                }
                else if (!checkCrash(xNew + 1, yNew, previousX, previousY)) {
                    xNew++;
                }
                else if (!checkCrash(xNew, yNew + 1, previousX, previousY)) {
                    yNew++;
                }
                else if (!checkCrash(xNew, yNew - 1, previousX, previousY)) {
                    yNew--;
                }
            }
        }
        else {
            if (yDiff > 0) {
                if (!checkCrash(xNew, yNew + 1, previousX, previousY)) {
                    yNew++;
                }
                else if (!checkCrash(xNew, yNew - 1, previousX, previousY)) {
                    yNew--;
                }
                else if (!checkCrash(xNew + 1, yNew, previousX, previousY)) {
                    xNew++;
                }
                else if (!checkCrash(xNew - 1, yNew, previousX, previousY)) {
                    xNew--;
                }
            }
            else {
                if (!checkCrash(xNew, yNew - 1, previousX, previousY)) {
                    yNew--;
                }
                else if (!checkCrash(xNew, yNew + 1, previousX, previousY)) {
                    yNew++;
                }
                else if (!checkCrash(xNew + 1, yNew, previousX, previousY)) {
                    xNew++;
                }
                else if (!checkCrash(xNew - 1, yNew, previousX, previousY)) {
                    xNew--;
                }
            }
        }
        if(!training){
            racetrack[yNew][xNew] = "C";
        }
        xPosition = xNew;
        yPosition = yNew;


    }

}
