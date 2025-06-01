import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Runner {
    //TODO a lot of stuff is static in here im wondering if this may be an issue later,
    // though its hard to say right now i think

    /**
     * Try and use java-docs and inline when writing code
     */
    // Used for notifying Value Iteration Class what crashing method to use
    /**
     * Tune able Parameters
     */
    static int numOfTests = 10;
    static boolean crashType = false;//enable to true for a set of tests when testing R-Track.txt
    // These mean the same thing but for now have them set to different vvvv
    static int row;
    static int col;
    // These mean the same thing but for now have them set to different ^^^^
    static char[][] track;
    static String[][] qTrack;
    static double accelerationProb = 0.8;

    /**
     * Constant Parameters
     */
    static int time = 0;
    static int xT;
    static int yT;
    static int aX = 0;
    static int aY = 0;
    static int cost = 0;

    static int startX;
    static int startY;
    static Random r;
    static int firstWallx;
    static int firstWally;
    static boolean gameOver = false;
    static int minVelocity = -5;
    static int maxVelocity = 5;
    /*

     */
    static char startState = 'S';
    static char goalState = 'F';
    static char wall = '#';

    /**
     * Main driver for the project
     */
    public static void main(String[] args) {
        r = new Random();
        // get choice
        String[] userFileChoice = getUserChoice();
        // Initialize Track from txt file in 2d array
        readTrack(userFileChoice[0]);
        readQTrack(userFileChoice[0]);

        // should disable crash type for maps other than R-track if crash type is on
        //can remove if we feel is unneeded
        if (!userFileChoice[0].equals("R-track.txt") && crashType == true){
            crashType = false;
        }
        String restartType = "b";
        for(int i = 0; i < qTrack.length; i++){
            for(int j = 0; j < qTrack[i].length; j++){
                System.out.print(qTrack[i][j] + " ");
            }
            System.out.println();

        }
        QLearning qLearning = new QLearning(qTrack, restartType);

        //Initialize Instance of ValueIteration2
        ValueIteration1 carVI = new ValueIteration1(track, false, 0.0, 50);
        //Initialize Instance of QLearning
        //Run each instance on the track

        //TODO i think this will just grab the last 's' it iterates over and uses that as its starting cell
        // may want to change so it randomly picks from any of the potential starting cells
        //finds the starting line
        setStartCells();
        //sets the initial position to the starting coordinates
        xT = startX;
        yT = startY;
        for (int i = 1; i < numOfTests+1; i++) {
            while (gameOver != true){
                //have the agent decide to attempt to acelerate
                //TODO have the agent call accelerate to update aX and aY

                //update the agent's curent position (xT and yT) and check for collisions in its path
                updatePos();
                //iterate the time-step should happen last i think?
                time++;
            }
            //print the time spilt for the current test and then reset the time to 0 and position parameters to starting values
            System.out.println("Time Split for run " + i + ": " + time);
            System.out.println("Cost for run " + i + ": " + cost);
            time = 0;
            xT = startX;
            yT = startY;
            aX = 0;
            aY = 0;
            gameOver = false;
        }

        // Keeping track of
        // - number of training iterations
        // - number of steps to get to finish line
        // Will need to run each algorithm at least ten times on each track txt file
        // so an output file may be our best option for storing data

        // Once data has been collected plot the learning curve for final report

    }

    /**
     * retrieves the user's track choice to feed to readTrack() in main
     */
    private static String[] getUserChoice() {
        // Get filename(index 0)
        String[] userChoice = new String[3];
        System.out.println("""
                Choose the track you want to test on:
                [1] L-track
                [2] O-track
                [3] R-track
                [4] test-track
                [5] Simple-track
                """);
        Scanner reader = new Scanner(System.in);
        int fileChoice = reader.nextInt();
        switch (fileChoice) {
            case (1) -> userChoice[0] = "L-track.txt";
            case (2) -> userChoice[0] = "O-track.txt";
            case (3) -> userChoice[0] = "R-track.txt";
            case (4) -> userChoice[0] = "test.txt";
            case (5) -> userChoice[0] = "Simple-track.txt";
        }

        return userChoice;
    }

    /**
     * parses a track file and saves the data to global variables
     * track[][], row, col
     */
    private static void readTrack(String filename){
        //TODO check to see if it is reading in the correct orientation for updatePos() and buildPath()
        // i may be iterating though it sideways or upside down in those methods, and i think it would be
        // easier to tweak how its read in to match if that's the case
        try {
            File file=new File(".//tracks//"+filename);    //creates a new file instance
            FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            //StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters
            String line;

            //handles the line that sets the size of the track
            line = br.readLine();
            String[] sizePair = line.split(",");
            row = Integer.parseInt(sizePair[0]);
            col = Integer.parseInt(sizePair[1]);
            track = new char[row][col];

            //handles parsing the track
            for (int i = 0; i < row; i++) {
                line=br.readLine();
                char[] curRow = line.toCharArray();
                track[i] = curRow;
            }
            System.out.println();
            //closes the stream and release the resources
            fr.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public static void readQTrack(String fileName){
        String line = null;
        int rows = 0;
        int cols = 0;
        //String[][] buildTrack;
        try{
            //BufferedReader br = new BufferedReader(new FileReader(new File(".//tracks//"+fileName)));
            File file=new File(".//tracks//"+fileName);    //creates a new file instance
            FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            line = br.readLine();
            rows = Integer.parseInt(line.substring(0, line.indexOf(",")));
            cols = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.length()));

            qTrack = new String[rows][cols];
            int rowCount = 0;

            while((line = br.readLine()) != null){
                for(int i = 0; i < cols; i++){
                    String charValue = String.valueOf(line.charAt(i));
                    qTrack[rowCount][i] = charValue;
                }
                rowCount++;
            }
            br.close();
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }

    /**
     * resolves an agents attempt to accelerate
     */
    public void accelerate(int newAX, int newAY){
        //iterate cost here?
        cost++;
        //test to see if accelerate was successful
        if (r.nextDouble() <= accelerationProb){
            //accelerate was successful
            if (!(newAX > 5 || newAX < -5 || newAY > 5 || newAY < -5)){
                //update accelerate values if the new values are within the bounds
                aX = newAX;
                aY = newAY;
            }
        }
    }

    /**
     * updates the car's position on the track
     * also checks for collisions and calls resolveCrash if needed
     */
    public static void updatePos(){
        Queue<Character> path = new LinkedList<>();
        buildPath(path);

        boolean crashed = false;
        while (!path.isEmpty()){
            char cur = path.remove();

            //hit a wall '#'
            if (cur == '#'){
                resolveCrash();
                crashed = true;
            }
            //has reached the goal state of 'F'
            //maybe use atGoalState() instead?
            else if (cur == 'F') {
                atGoalState();
                cost--;
            }
            //is safe to proceed
        }
        if (crashed!=true){
            int xDest = xT + aX;
            int yDest = yT + aY;

            if (xDest >= row){
                xDest = row-1;
            }
            if (yDest >= col){
                yDest = col-1;
            }
            if (xDest >= row){
                xDest = 0;
            }
            if (xDest >= row){
                yDest = 0;
            }

            xT = xDest;
            yT = yDest;
        }
    }

    /**
     * builds the path that from xT,yT -> aX,aY that will be resolved in updatePos()
     */
    private static void buildPath(Queue<Character> path) {

        boolean firstWallFlag = false;

        int xDest = xT + aX;
        int yDest = yT + aY;

        if (xDest >= row){
            xDest = row-1;
        }

        if (yDest >= col){
            yDest = col-1;
        }

        if (xDest >= row){
            xDest = 0;
        }

        if (xDest >= row){
            yDest = 0;
        }


        //horizontal straight move
        if (aX !=0 && aY == 0){
            //left to right move
            if (aX > 0){
                //loop and add chars to the path queue
                for (int x = xT; x < xDest; x++) {
                    path.add(track[x+1][yT]);
                    //System.out.println("Adding: " + (x+1) + ", " + (yT) + " to the queue.");
                    if (track[x+1][yT] == '#' && !firstWallFlag){
                        firstWallx = x;
                        firstWally = yT;
                        firstWallFlag = true;
                    }
                }
            }
            //right to left move
            else if (aX < 0){
                //loop and add chars to the path queue
                for (int x = xT; x > xDest; x--) {
                    path.add(track[x-1][yT]);
                    //System.out.println("Adding: " + (x-1) + ", " + (yT) + " to the queue.");
                    if (track[x-1][yT] == '#' && !firstWallFlag){
                        firstWallx = x;
                        firstWally = yT;
                        firstWallFlag = true;
                    }
                }
            }
        }
        //vertical straight move
        else if (aX == 0 && aY != 0){
            //upward Move
            if (aY > 0){
                //loop and add chars to the path queue
                for (int y = xT; y < yDest; y++) {
                    path.add(track[xT][y+1]);
                    //System.out.println("Adding: " + (xT) + ", " + (y+1) + " to the queue.");
                    if (track[xT][y+1] == '#' && !firstWallFlag){
                        firstWallx = xT;
                        firstWally = y;
                        firstWallFlag = true;
                    }
                }
            }
            //downward move
            else if (aY < 0) {
                //loop and add chars to the path queue
                for (int y = xT; y > yDest; y--) {
                    path.add(track[xT][y-1]);
                    //System.out.println("Adding: " + (xT) + ", " + (y-1) + " to the queue.");
                    if (track[xT][y-1] == '#' && !firstWallFlag){
                        firstWallx = xT;
                        firstWally = y;
                        firstWallFlag = true;
                    }
                }
            }
        }
        //no acceleration, standstill
        else if (aX == 0 && aY ==0){
            //nothing gets added to the queue and the position will remain the same
        }
        //some form of diagonal move
        else {
            //diagonal move where the distances are the same
            if (Math.abs(aX) == Math.abs(aY)){
                // up right diagonal move
                if (aX > 0 && aY > 0){
                    for (int y = yT; y < yDest; y++) {
                        for (int x = xT; x < xDest; x++) {
                            if (x == y) {
                                path.add(track[x + 1][y]);
                                if (track[x + 1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x][y + 1]);
                                if (track[x][y + 1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x + 1][y + 1]);
                                if (track[x + 1][y + 1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }

//                                System.out.println("Adding: " + (x+1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x) + ", " + (y+1) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                }
                // down right diagonal move
                else if (aX > 0 && aY < 0){
                    for (int y = yT; y > yDest; y--) {
                        for (int x = xT; x < xDest; x++) {
                            int i = yDest + y;
                            int j = xDest - x;
                            if (i == j) {
                                path.add(track[x + 1][y]);
                                if (track[x + 1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x][y - 1]);
                                if (track[x][y - 1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x + 1][y - 1]);
                                if (track[x + 1][y - 1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }

//                                System.out.println("Adding: " + (x+1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x) + ", " + (y-1) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y-1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                }
                // up left diagonal move
                else if (aX < 0 && aY > 0){
                    for (int y = yT; y < yDest; y++) {
                        for (int x = xT; x > xDest; x--) {
                            int i = yDest - y;
                            int j = xDest + x;
                            if (i == j) {
                                path.add(track[x - 1][y]);
                                if (track[x - 1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x][y + 1]);
                                if (track[x][y + 1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x - 1][y + 1]);
                                if (track[x - 1][y + 1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x-1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x) + ", " + (y+1) + " to the queue.");
//                                System.out.println("Adding: " + (x-1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                }
                // down left diagonal move
                else if (aX < 0 && aY < 0){
                    for (int y = yT; y > yDest; y--) {
                        for (int x = xT; x > xDest; x--) {
                            if (y == x) {
                                path.add(track[x - 1][y]);
                                if (track[x - 1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x][y - 1]);
                                if (track[x][y - 1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x - 1][y - 1]);
                                if (track[x - 1][y - 1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x-1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x) + ", " + (y-1) + " to the queue.");
//                                System.out.println("Adding: " + (x-1) + ", " + (y-1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                }
            }
            //diagonal move where the x distance is larger then the y distance
            else if (Math.abs(aX) > Math.abs(aY)){
                // up right diagonal move
                if (aX > 0 && aY > 0){
                    for (int y = yT; y < yDest; y++) {
                        for (int x = xT; x < xDest; x++) {
                            if (x == y) {
                                path.add(track[x+1][y]);
                                if (track[x+1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x+1][y+1]);
                                if (track[x+1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x+1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                            else if (x >= xT+aY && x < xDest-1 && y == yDest-1){
                                path.add(track[x+1][y]);
                                if (track[x+1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x+1][y+1]);
                                if (track[x+1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x+1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                    path.add(track[xDest][yDest]);
//                    System.out.println("Adding: " + (xDest) + ", " + (yDest) + " to the queue.");
                    if (track[xDest][yDest] == '#'){
                        firstWallx = xDest;
                        firstWally = yDest;
                    }
                }
                // down right diagonal move
                else if (aX > 0 && aY < 0){
                    for (int y = yT; y > yDest; y--) {
                        for (int x = xT; x < xDest; x++) {
                            int i = x+y;
                            //int j = xDest - x;
                            if (i == 10) {
                                path.add(track[x+1][y]);
                                if (track[x+1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x+1][y-1]);
                                if (track[x+1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x+1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y-1) + " to the queue.");
//                                System.out.println();
                            }
                            else if (x >= xT-aY && x < xDest-1 && y == yDest+1){
                                path.add(track[x+1][y]);
                                if (track[x+1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x+1][y-1]);
                                if (track[x+1][y-1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x+1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y-1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                    path.add(track[xDest][yDest]);
                    //System.out.println("Adding: " + (xDest) + ", " + (yDest) + " to the queue.");
                    if (track[xDest][yDest] == '#'){
                        firstWallx = xDest;
                        firstWally = yDest;
                    }
                }
                // up left diagonal move
                else if (aX < 0 && aY > 0){
                    for (int y = yT; y < yDest; y++) {
                        for (int x = xT; x > xDest; x--) {
                            int i = x+y;
                            //int j = xDest - x;
                            if (i == 10) {
                                path.add(track[x-1][y]);
                                if (track[x-1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x-1][y+1]);
                                if (track[x-1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x-1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x-1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                            else if (x <= xT-aY && x > xDest+1 && y == yDest-1){
                                path.add(track[x-1][y]);
                                if (track[x-1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x-1][y+1]);
                                if (track[x-1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x-1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x-1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                    path.add(track[xDest][yDest]);
                    //System.out.println("Adding: " + (xDest) + ", " + (yDest) + " to the queue.");
                    if (track[xDest][yDest] == '#'){
                        firstWallx = xDest;
                        firstWally = yDest;
                    }
                }
                // down left diagonal move
                else if (aX < 0 && aY < 0){
                    for (int y = yT; y > yDest; y--) {
                        for (int x = xT; x > xDest; x--) {
                            if (x == y) {
                                path.add(track[x-1][y]);
                                if (track[x-1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x-1][y-1]);
                                if (track[x-1][y-1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x-1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x-1) + ", " + (y-1) + " to the queue.");
//                                System.out.println();
                            }
                            else if (x <= xT+aY && x > xDest+1 && y == yDest+1){
                                path.add(track[x-1][y]);
                                if (track[x-1][y] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x-1][y-1]);
                                if (track[x-1][y-1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x-1) + ", " + (y) + " to the queue.");
//                                System.out.println("Adding: " + (x-1) + ", " + (y-1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                    path.add(track[xDest][yDest]);
                    //System.out.println("Adding: " + (xDest) + ", " + (yDest) + " to the queue.");
                    if (track[xDest][yDest] == '#'){
                        firstWallx = xDest;
                        firstWally = yDest;
                    }
                }
            }
            //diagonal move where the x distance is less than the y distance
            else if (Math.abs(aX) < Math.abs(aY)){
                // up right diagonal move
                if (aX > 0 && aY > 0){
                    for (int y = yT; y < yDest; y++) {
                        for (int x = xT; x < xDest; x++) {
                            if (x == y) {
                                path.add(track[x][y+1]);
                                if (track[x][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x+1][y+1]);
                                if (track[x+1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x) + ", " + (y+1) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                            else if (y >= yT+aX && x == xDest-1 && y < yDest-1){
                                path.add(track[x][y+1]);
                                if (track[x][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x+1][y+1]);
                                if (track[x+1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x) + ", " + (y+1) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                    path.add(track[xDest][yDest]);
                    //System.out.println("Adding: " + (xDest) + ", " + (yDest) + " to the queue.");
                    if (track[xDest][yDest] == '#'){
                        firstWallx = xDest;
                        firstWally = yDest;
                    }
                }
                // down right diagonal move
                else if (aX > 0 && aY < 0){
                    for (int y = yT; y > yDest; y--) {
                        for (int x = xT; x < xDest; x++) {
                            int i = x+y;
                            if (i == 10) {
                                path.add(track[x][y-1]);
                                if (track[x][y-1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x+1][y-1]);
                                if (track[x+1][y-1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x) + ", " + (y-1) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y-1) + " to the queue.");
//                                System.out.println();
                            }
                            else if (y <= yT-aX && x == xDest-1 && y > yDest+1){
                                path.add(track[x][y-1]);
                                if (track[x][y-1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x+1][y-1]);
                                if (track[x+1][y-1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x) + ", " + (y-1) + " to the queue.");
//                                System.out.println("Adding: " + (x+1) + ", " + (y-1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                    path.add(track[xDest][yDest]);
                    //System.out.println("Adding: " + (xDest) + ", " + (yDest) + " to the queue.");
                    if (track[xDest][yDest] == '#'){
                        firstWallx = xDest;
                        firstWally = yDest;
                    }
                }
                // up left diagonal move
                else if (aX < 0 && aY > 0){
                    for (int y = yT; y < yDest; y++) {
                        for (int x = xT; x > xDest; x--) {
                            int i = x+y;
                            if (i == 10) {
                                path.add(track[x][y+1]);
                                if (track[x][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x-1][y+1]);
                                if (track[x-1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x) + ", " + (y+1) + " to the queue.");
//                                System.out.println("Adding: " + (x-1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                            else if (y >= yT-aX && x == xDest+1 && y < yDest-1){
                                path.add(track[x][y+1]);
                                if (track[x][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x-1][y+1]);
                                if (track[x-1][y+1] == '#' && !firstWallFlag){
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x) + ", " + (y+1) + " to the queue.");
//                                System.out.println("Adding: " + (x-1) + ", " + (y+1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                    path.add(track[xDest][yDest]);
                    //System.out.println("Adding: " + (xDest) + ", " + (yDest) + " to the queue.");
                    if (track[xDest][yDest] == '#'){
                        firstWallx = xDest;
                        firstWally = yDest;
                    }
                }
                // down left diagonal move
                else if (aX < 0 && aY < 0){
                    for (int y = yT; y > yDest; y--) {
                        for (int x = xT; x > xDest; x--) {
                            if (x == y) {
                                path.add(track[x][y - 1]);
                                if (track[x][y - 1] == '#' && !firstWallFlag) {
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x - 1][y - 1]);
                                if (track[x - 1][y - 1] == '#' && !firstWallFlag) {
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x) + ", " + (y - 1) + " to the queue.");
//                                System.out.println("Adding: " + (x - 1) + ", " + (y - 1) + " to the queue.");
//                                System.out.println();
                            } else if (y <= yT + aX && x == xDest + 1 && y > yDest + 1) {
                                path.add(track[x][y - 1]);
                                if (track[x][y - 1] == '#' && !firstWallFlag) {
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
                                path.add(track[x - 1][y - 1]);
                                if (track[x - 1][y - 1] == '#' && !firstWallFlag) {
                                    firstWallx = x;
                                    firstWally = y;
                                    firstWallFlag = true;
                                }
//                                System.out.println("Adding: " + (x) + ", " + (y - 1) + " to the queue.");
//                                System.out.println("Adding: " + (x - 1) + ", " + (y - 1) + " to the queue.");
//                                System.out.println();
                            }
                        }
                    }
                    path.add(track[xDest][yDest]);
                    //System.out.println("Adding: " + (xDest) + ", " + (yDest) + " to the queue.");
                    if (track[xDest][yDest] == '#'){
                        firstWallx = xDest;
                        firstWally = yDest;
                    }
                }
            }
        }
    }

    /**
     * resolves a crash, if the crashType flag is true it will reset the player to the beginning
     * called in updatePos if the car hits a wall on its way to its new position
     */
    public static void resolveCrash(){
        if (crashType == true){
            //set position to the start line
            xT = startX;
            yT = startY;
        }
        else {
            //set position to where it collided with the wall
            xT = firstWallx;
            yT = firstWally;
        }
    }

    /**
     * is called when the car is at the goal state
     * sets the gameOver flag to true
     */
    public static void atGoalState(){
        gameOver = true;
    }

    //TODO maybe remove idk if we need this, unless im forgetting what it was planned on being used for
    /**
     * checks to see if the car is at the start state
     */
    public static boolean atStartState(){
        return true;
    }

    /**
     * sets the startX and startY for the track we read in
     */
    public static void setStartCells(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (track[i][j] == 'S'){
                    startX = i;
                    startY = j;
                }
            }
        }
    }
}
