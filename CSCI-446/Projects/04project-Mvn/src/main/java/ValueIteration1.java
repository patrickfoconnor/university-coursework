import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.*;


// Value Iteration with V function

    public class ValueIteration1 {
        /**
         * Tune able Parameters
         */
        double gamma = 0.8; // discount factor
        double deltaChange = 0.0005; // small enough change to stop iterations
        /**
         * Constant parameters
         */
        int maxX = Runner.row;
        int maxY = Runner.col;
        int totalSteps = 0;
        int rows = maxX;
        int cols = maxY;
        // 0-7 is success on action
        // 7-9 is failure to act
        int accelerateSuccess = 7;

        double probAccelerationFail = 0.2;
        double probAccelerationSuccess = 1 - probAccelerationFail;
        int iterationCount;

        /*
        Changeable parameters
         */
        boolean animate = true;
        int maxTrainingIterations = 100;
        int numOfTrainingIterations = 5;

        /*
        A range of min velocity to max velocity
         */
        int[] velocityRange = range(Runner.minVelocity, Runner.maxVelocity+1);

        /*
         All actions A that can be taken
         Of form "acceleration in y,acceleration in x)
         */
        String[] actions = new String[]{
                "-1,1", "0,1", "1,1",
                "-1,0", "0,0", "1,0",
                "-1,-1", "0,-1", "1,-1"
        };

        int[] actionsIndex = new int[]{
                0, 1, 2, 3, 4, 5, 6, 7, 8
        };


        /*
        Hash table to hold values
        key is off form "y,x,vY,vX"
            key is off form "x,y,vX,vY"
        value is a double such that applying vY and vX to position x,y results in an expected value equal to the value
     */
        Hashtable<String, Double> valuesTable = new Hashtable<>();

        /*
        Hash table to hold qValues
        key is off form "x,y,vX,vY,accelerationX,accelerationY"
        value is a double such that applying acceleration aX,aY to vY, vX, at current to position results in an expected value equal to the value
    */
        Hashtable<String, Double> qValuesTable = new Hashtable<>();

        /*

        Hash table to hold policy
            Most desirable acceleration "ax,ay" at state "x,y,vx,vy"
            key is off form "x,y,vx,vy"
            value is a String "accelX,accelY" such that this is the most desirable acceleration to apply
         */

        Hashtable<String, String> policy = new Hashtable<>();

        public ValueIteration1() {

        }

        public void initializeTables(char[][] track, double reward) {

        /*
        Create a table values that will store the optimal Q-value for each state.
        Initialize value table to arbitrary values except terminal state.
            Terminal state will receive a zero value as it is absorbing
            values
         */
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    for (int vy : velocityRange) {
                        for (int vx : velocityRange) {
                            String tempKey = x + "," + y + "," + vx + "," + vy;
                            valuesTable.put(tempKey, 0.01);
                        }
                    }
                }
            }


            // Set finish line to 0/reward for finishing This should be updating not putting
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    if (track[y][x] == Runner.goalState) {
                        for (int vy : velocityRange) {
                            for (int vx : velocityRange) {
                                String tempKey = x + "," + y + "," + vx + "," + vy;
                                //valuesTable.put(tempKey, reward);
                                valuesTable.replace(tempKey, reward);
                            }
                        }
                    }
                }
            }

        /*
        Init all Q(s,a) to arbitrary values, except
            terminal state will be set to a value of 0
         */

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    for (int vy : velocityRange) {
                        for (int vx : velocityRange) {
                            for (int actionIndex : actionsIndex) {
                                String[] actionString = (actions[actionIndex]).split(",");
                                int accelX = Integer.parseInt(actionString[0]);
                                int accelY = Integer.parseInt(actionString[1]);
                                String tempKey = x + "," + y + "," + vx + "," + vy + "," + accelX + "," + accelY;
                                qValuesTable.put(tempKey, 0.1);
                            }
                        }
                    }
                }
            }
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    if (track[x][y] == Runner.goalState) {
                        for (int vy : velocityRange) {
                            for (int vx : velocityRange) {
                                for (int actionIndex : actionsIndex) {
                                    // get action with index
                                    String[] actionString = (actions[actionIndex]).split(",");
                                    int accelX = Integer.parseInt(actionString[0]);
                                    int accelY = Integer.parseInt(actionString[1]);
                                    String tempKey = x + "," + y + "," + vx + "," + vy + "," + accelX + "," + accelY;
                                    qValuesTable.put(tempKey, reward);
                                }
                            }
                        }
                    }
                }
            }
        }

        // the reward will always be 1 for our specific project
        public Hashtable<String, String> valueIterationTrain(char[][] track, boolean crashType, double reward, int numOfTrainingIter) {

            //initializeTables(track, reward);
        /*
        Location for training the agent
            Includes optimizing the values in the tables V(state) and Q(state,action)
         */

            for (int iteration = 1; iteration <= numOfTrainingIter; iteration++) {
                // Keep a copy of the old values in order to check for stopping when max change is small enough
                Hashtable<String, Double> oldValuesTable = createDeepCopy(valuesTable);
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < cols; y++) {
                        for (int vy : velocityRange) {
                            for (int vx : velocityRange) {
                                // If wall is hit value of that should be a large negative value
                                if (track[x][y] == Runner.wall) {
                                    String tempKey = x + "," + y + "," + vx + "," + vy;
                                    valuesTable.replace(tempKey, -10.0);
                                }

                                for (int actionIndex : actionsIndex) {
                                    double tempReward;
                                    if (track[x][y] == Runner.goalState) {
                                        tempReward = reward;
                                    } else {
                                        tempReward = -1;
                                    }
                                    // Get new state sPrime based on the current state and the current action
                                    // get the acceleration from actions
                                    String accelerationYX = actions[actionIndex];
                                    String[] accelYXSplit = accelerationYX.split(",");
                                    int accelX = Integer.parseInt(accelYXSplit[0]);
                                    int accelY = Integer.parseInt(accelYXSplit[1]);
                                    int[] sPrime = trainingAction(x, y, vx, vy, accelX, accelY, track, crashType);
                                    int newX = sPrime[0];
                                    int newY = sPrime[1];
                                    int newVX = sPrime[2];
                                    int newVY = sPrime[3];

                                    // Look one step ahead
                                    String tempKeySuccess = newX + "," + newY + "," + newVX + "," + newVY;
                                    boolean hitsWall = Boolean.valueOf(getNewTrainingPosition(x,y,vx,vy,crashType)[2]);
                                    double valueOfNewState;
                                    if (hitsWall){
                                        valueOfNewState = -5.0;
                                    }
                                    else{
                                        valueOfNewState = oldValuesTable.get(tempKeySuccess);
                                    }


                                    // Handle failed acceleration now
                                    int[] sPrimeFail = trainingAction(x, y, vx, vy, 0, 0, track, crashType);
                                    int newXFail = sPrimeFail[0];
                                    int newYFail = sPrimeFail[1];
                                    int newVXFail = sPrimeFail[2];
                                    int newVYFail = sPrimeFail[3];
                                    String tempKeyFail = newXFail + "," + newYFail + "," + newVXFail + "," + newVYFail;
                                    double valueOfNewStateFail = oldValuesTable.get(tempKeyFail);

                                    double expectedValue = (probAccelerationSuccess * valueOfNewState) +
                                            (probAccelerationFail * valueOfNewStateFail);

                                    // Update Q value in Q table
                                    String tempKey = x + "," + y + "," + vx + "," + vy + "," + accelX + "," + accelY;
                                    qValuesTable.replace(tempKey, (tempReward + (gamma * expectedValue)));
                                }
                                //Get the action with the highest value
                                String maxKey = "";
                                String maxValueKey = "";
                                double maxValue = -10.0;
                                for (int actionIndex : actionsIndex) {
                                    //Loop through the available actions and grab the value associated with it
                                    String[] accelXY = actions[actionIndex].split(",");
                                    int accelX = Integer.parseInt(accelXY[0]);
                                    int accelY = Integer.parseInt(accelXY[1]);
                                   // Check to see
                                    String tempMaxQKey = x + "," + y + "," + vx + "," + vy + "," + accelX + "," + accelY;
                                    double tempMaxValue = qValuesTable.get(tempMaxQKey);
                                    if (tempMaxValue > maxValue) {
                                        maxKey = tempMaxQKey;
                                        maxValue = tempMaxValue;
                                        maxValueKey = x + "," + y + "," + vx + "," + vy;
                                    }
                                }
                                valuesTable.replace(maxValueKey, qValuesTable.get(maxKey));
                            }
                        }
                    }
                }
                // Make sure all the rewards set to 0 in the terminal state
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < cols; y++) {
                        if (track[x][y] == Runner.goalState) {
                            for (int vy : velocityRange) {
                                for (int vx : velocityRange) {
                                    String tempKey = x + "," + y + "," + vx + "," + vy;
                                    valuesTable.replace(tempKey, reward);
                                }
                            }
                        }
                    }
                }
                // Check out to see if values are stabilizing
                // by finding maximum change of the states
                double delta = -1; // method/algorithm will go right here
                for (String key : valuesTable.keySet()) {
                    double oldValue = oldValuesTable.get(key);
                    double newValue = valuesTable.get(key);
                    double tempDelta = Math.abs(newValue - oldValue);
                    if (tempDelta > delta) {
                        delta = tempDelta;
                    }
                }
                iterationCount++;
                if (delta < deltaChange && delta != -1) {
                    return getPolicyFromQValues(qValuesTable);
                }
            }
            // If all the iterations end return the policy by default
            return getPolicyFromQValues(qValuesTable);
        }


        /**
         *
         */
        private Hashtable<String, String> getPolicyFromQValues(Hashtable<String, Double> qValuesTable) {
            // Grab the best action from qValues[x][y][vx][vy][ax][ay]
            Hashtable<String, String> newPolicy = new Hashtable<>();
            for (int x = 0; x < Runner.row; x++) {
                for (int y = 0; y < Runner.col; y++) {
                        for (int vy : velocityRange) {
                            for (int vx : velocityRange) {
                                double bestValue = 0;
                                String bestKey = "";
                                for (int actionIndex : actionsIndex) {
                                    String[] accelXY = actions[actionIndex].split(",");
                                    int accelX = Integer.parseInt(accelXY[0]);
                                    int accelY = Integer.parseInt(accelXY[1]);
                                    String tempKey = x+","+y+","+vx+","+vy+","+accelX+","+accelY;
                                    double tempBestValue = qValuesTable.get(tempKey);

                                    if ((tempBestValue > bestValue || bestValue == 0.0)) {
                                        bestKey = tempKey;
                                        bestValue = tempBestValue;
                                    }
                                }
                                String[] splitKey = bestKey.split(",");
                                int bestStateX = Integer.parseInt(splitKey[0]);
                                int bestStateY = Integer.parseInt(splitKey[1]);
                                int bestVX = Integer.parseInt(splitKey[2]);
                                int bestVY = Integer.parseInt(splitKey[3]);
                                int bestAccelerationX = Integer.parseInt(splitKey[4]);
                                int bestAccelerationY = Integer.parseInt(splitKey[5]);
                                String bestStateVPair = bestStateX + "," + bestStateY+","+bestVX+","+bestVY;
                                String bestAcceleration = bestAccelerationX + "," + bestAccelerationY;

                                newPolicy.put(bestStateVPair, bestAcceleration);
                            }
                        }
                }
            }
            return newPolicy;
        }



        /**
         *
         *Generate new state sPrime (position and velocity from old state s
         * and action taken at state s
         * @param oldY The old y position
         * @param oldX The old x position
         * @return As java doesn't have multiple returns this array will hold each of the following values
         *         - returnMultiple[0] = newY
         *         - returnMultiple[1] = newX
         *         - returnMultiple[2] = newVY
         *         - returnMultiple[3] = newVX
         */

        private int[] trainingAction(int oldX, int oldY, int oldVX, int oldVY, int accelX, int accelY,
                                     char[][] track, boolean badCrash) {
            // As java doesn't have multiple returns this array will hold each of the following values
            // returnMultiple[0] = newX
            // returnMultiple[1] = newY
            // returnMultiple[2] = newVX
            // returnMultiple[3] = newVY
            int[] newCarStats;

            Random randAccel = new Random();
            int accelProb = randAccel.nextInt(10);
            if (accelProb > accelerateSuccess) {
                // if failure to accelerate then old velocity stays the same per project outline
                accelX = 0;
                accelY = 0;
            }
            // Get the new velocity using old velocity and new acceleration values
            String[] newVelocity = getNewTrainingVelocity(oldVX, oldVY, accelX, accelY);
            int newVX = Integer.parseInt(newVelocity[0]);
            int newVY = Integer.parseInt(newVelocity[1]);

            // Using new velocity update position
            String[] newPosition = getNewTrainingPosition(oldX, oldY, newVX, newVY, badCrash);
            int tempX = Integer.parseInt(newPosition[0]);
            int tempY = Integer.parseInt(newPosition[1]);
            boolean crashBool = Boolean.valueOf(newPosition[2]);

            // If crash happened
            if (crashBool) {
                    // Reset Velocity to 0
                    newVX = 0;
                    newVY = 0;
            }

            newCarStats = new int[]{tempX, tempY, newVX, newVY};

            return newCarStats;
        }

        /*
        Locate the nearest open cell in order to handle crash
        If velocity is provided search in the opposite direction to avoid jumping walls
         */
        private String[] getNearestOpenCell(int tempX, int tempY, int newVX, int newVY, char[][] track) {
            int maxRadius = Math.max(Runner.row, Runner.col);

            for (int radius = 1; radius <= maxRadius; radius++) {
                // not moving in x direction
                int[] xRange;
                if (newVX == 0) {
                    xRange = range(-radius, radius+1);
                }
                // moving in negative x direction
                else if (newVX < 0){
                    xRange = range(0, radius+1);
                }
                else{
                    xRange = range(-radius, 1);
                }
                //For each value in y search radius
                for (int xOffset: xRange) {
                    int newX = tempX + xOffset;
                    int yRadius = radius - Math.abs(xOffset);
                    int[] yRange;
                    // not moving in y direction
                    if (newVY == 0) {
                        yRange = range(tempY - yRadius, tempY + yRadius+1);
                    }
                    // moving in negative y direction
                    else if (newVY < 0){
                        yRange = range(tempY, tempY + yRadius+1);

                    }
                    else{
                        yRange = range(tempY - yRadius, tempY+1);
                    }
                    for (int newY: yRange) {
                        if ((newX > 0 && newX < Runner.row) && (newY > 0 && newY < Runner.col)){
                            if (track[newX][newY] == '.' || track[newX][newY] == Runner.startState){
                                String newXString = String.valueOf(newX);
                                String newYString = String.valueOf(newY);
                                return new String[] {newXString, newYString};
                            }
                        }
                    }

                }
            }
            // If no resolution is found return random start position
            return getRandomStartPosition(track);
        }

        /**
         * Get a random position that is a valid spot for the car to start
         * @param track the current environment
         * @return a start position [x][y]
         */
        private String[] getRandomStartPosition(char[][] track) {
            ArrayList<String[]> startPositions = new ArrayList<>();
            for (int x = 0; x < Runner.row; x++) {
                for (int y = 0; y < Runner.col; y++) {
                    if (track[x][y] == 'S'){
                        String startXString = String.valueOf(x);
                        String startYString = String.valueOf(y);
                        String[] startPosition = new String[] {startXString, startYString};
                        startPositions.add(startPosition);
                    }
                }
            }
            Collections.shuffle(startPositions);
            return startPositions.get(0);
        }

        /*
        Using a position and velocity get the new position
         */
        private String[] getNewTrainingPosition(int oldX, int oldY, int newVX,
                                                int newVY, boolean crashType) {
            String crashHappened = "false";
            int newX = oldX + newVX;
            int newY = oldY + newVY;
            if (newX >= Runner.row){
                newX = Runner.row-1;
            }
            if (newX < 0){
                newX = 0;
            }
            if (newY >= Runner.col){
                newY = Runner.col-1;
            }
            if (newY < 0){
                newY = 0;
            }


            // Check to make sure this is a legal path
            Queue<Character> path = new LinkedList<>();
            path = buildPath(path, oldX,oldY, newX, newY, newVX, newVY);

            boolean lastCharWall = false;
            String safeFinish = "false";
            while (!path.isEmpty()){
                char cur = path.remove();

                if (cur == Runner.wall){
                    // If crash is true return to start
                    lastCharWall = true;
                    String[] newXY;
                    if (crashType){
                        newXY = getRandomStartPosition(Runner.track);
                    }
                    // Get nearestOpenCell
                    else{
                        newXY = getNearestOpenCell(newX, newY, newVX, newVY, Runner.track);
                    }
                    newX = Integer.parseInt(newXY[0]);
                    newY = Integer.parseInt(newXY[1]);
                    crashHappened = "true";
                }
                else{
                    crashHappened="false";
                }
                if (cur == Runner.goalState && !lastCharWall){
                    safeFinish = "true";
                }
            }

            String newXString = String.valueOf(newX);
            String newYString = String.valueOf(newY);
            return new String[] {newXString, newYString, crashHappened, safeFinish};

        }

        private Queue<Character> buildPath(Queue<Character> path, int oldX, int oldY, int newX, int newY, int newVX, int newVY) {


            boolean firstWallFlag = false;

            //horizontal straight move
            if (newVX !=0 && newVY == 0){
                //left to right move
                if (newVX > 0){
                    //loop and add chars to the path queue
                    for (int x = oldX; x < newX && x < Runner.col; x++) {
                        path.add(Runner.track[x][oldY]);
                        if (Runner.track[x][oldY] == '#'){
                            break;
                        }
                    }
                }
                //right to left move
                else if (newVX < 0){
                    //loop and add chars to the path queue
                    for (int x = oldX; x > newX && x >= 0 ; x--) {
                        path.add(Runner.track[x][oldY]);
                        if (Runner.track[x][oldY] == '#'){
                            break;
                        }
                    }
                }
            }
            //vertical straight move
            else if (newVX == 0 && newVY != 0){
                //upward Move
                if (newVY > 0){
                    //loop and add chars to the path queue
                    for (int y = oldY; y < newY && y < Runner.col-1; y++) {
                        path.add(Runner.track[oldX][y+1]);
                        if (Runner.track[oldX][y+1] == '#'){
                            break;
                        }
                    }
                }
                //downward move
                else if (newVY < 0) {
                    //loop and add chars to the path queue
                    for (int y = oldY; y > newY && y >= 0; y--) {
                        path.add(Runner.track[oldX][y-1]);
                        if (Runner.track[oldX][y-1] == '#' ){
                           break;
                        }
                    }
                }
            }
            //no acceleration, standstill
            else if (newVX == 0 && newVY ==0){
                //nothing gets added to the queue and the position will remain the same
            }
            //some form of diagonal move
            else {
                //diagonal move where the velocity is the same
                if (Math.abs(newVX) == Math.abs(newVY)){
                    // up right diagonal move
                    if (newVX > 0 && newVY > 0){
                        for (int y = oldY; y < newY && y < Runner.col-1; y++) {
                            for (int x = oldX; x < newX && x < Runner.row-1 ; x++) {
                                if (x == y) {
                                    path.add(Runner.track[x][y+1]);
                                    if (Runner.track[x][y] == '#'){
                                        break;
                                    }
                                    path.add(Runner.track[x][y+1]);
                                    if (Runner.track[x][y] == '#'){
                                        break;
                                    }
                                    path.add(Runner.track[x + 1][y + 1]);
                                    if (Runner.track[x + 1][y + 1] == '#'){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // down right diagonal move
                    else if (newVX > 0 && newVY < 0){
                        for (int y = oldY; y > newY && y >= 0; y--) {
                            for (int x = oldX; x < newX && x <= Runner.row-1; x++) {
                                int i = newY + y;
                                int j = newX - x;
                                if (i == j) {
                                    path.add(Runner.track[x + 1][y]);
                                    if (Runner.track[x + 1][y] == '#'){
                                        break;
                                    }
                                    path.add(Runner.track[x][y - 1]);
                                    if (Runner.track[x][y - 1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x + 1][y - 1]);
                                    if (Runner.track[x + 1][y - 1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // up left diagonal move
                    else if (newVX < 0 && newVY > 0){
                        for (int y = oldY; y < newY && y < Runner.col; y++) {
                            for (int x = oldX; x > newX && x >= 0; x--) {
                                int i = newY - y;
                                int j = newX + x;
                                if (i == j) {
                                    path.add(Runner.track[x - 1][y]);
                                    if (Runner.track[x - 1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x][y + 1]);
                                    if (Runner.track[x][y + 1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x - 1][y + 1]);
                                    if (Runner.track[x - 1][y + 1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // down left diagonal move
                    else if (newVX < 0 && newVY < 0){
                        for (int y = oldY; y > newY && y >= 0; y--) {
                            for (int x = oldX; x > newX && x >= 0; x--) {
                                if (y == x) {
                                    path.add(Runner.track[x - 1][y]);
                                    if (Runner.track[x - 1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x][y - 1]);
                                    if (Runner.track[x][y - 1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x - 1][y - 1]);
                                    if (Runner.track[x - 1][y - 1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                //diagonal move where the x distance is larger than the y distance
                else if (Math.abs(newVX) > Math.abs(newVY)){
                    // up right diagonal move
                    if (newVX > 0 && newVY > 0){
                        for (int y = oldY; y < newY && y < Runner.col; y++) {
                            for (int x = oldX; x < newX && x < Runner.row; x++) {
                                if (x == y) {
                                    path.add(Runner.track[x+1][y]);
                                    if (Runner.track[x+1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x+1][y+1]);
                                    if (Runner.track[x+1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                                else if (x >= oldX+newVX && x < newX-1 && y == newY-1){
                                    path.add(Runner.track[x+1][y]);
                                    if (Runner.track[x+1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x+1][y+1]);
                                    if (Runner.track[x+1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // down right diagonal move
                    else if (newVX > 0 && newVY < 0){
                        for (int y = oldY; y > newY && y >= 0; y--) {
                            for (int x = oldX; x < newX && x < Runner.row; x++) {
                                int i = x+y;
                                //int j = xDest - x;
                                if (i == 10) {
                                    path.add(Runner.track[x+1][y]);
                                    if (Runner.track[x+1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x+1][y-1]);
                                    if (Runner.track[x+1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                                else if (x >= oldX-newVX && x < newX-1 && y == newY+1){
                                    path.add(Runner.track[x+1][y]);
                                    if (Runner.track[x+1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x+1][y-1]);
                                    if (Runner.track[x+1][y-1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // up left diagonal move
                    else if (newVX < 0 && newVY > 0){
                        for (int y = oldY; y < newY && y < Runner.col; y++) {
                            for (int x = oldX; x > newX && x >= 0; x--) {
                                int i = x+y;
                                //int j = xDest - x;
                                if (i == 10) {
                                    path.add(Runner.track[x-1][y]);
                                    if (Runner.track[x-1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x-1][y+1]);
                                    if (Runner.track[x-1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }

                                }
                                else if (x <= oldX-newVX && x > newX+1 && y == newY-1){
                                    path.add(Runner.track[x-1][y]);
                                    if (Runner.track[x-1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x-1][y+1]);
                                    if (Runner.track[x-1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // down left diagonal move
                    else if (newVX < 0 && newVY < 0){
                        for (int y = oldY; y > newY && y >= 0; y--) {
                            for (int x = oldX; x > newX && x >= 0; x--) {
                                if (x == y) {
                                    path.add(Runner.track[x-1][y]);
                                    if (Runner.track[x-1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x-1][y-1]);
                                    if (Runner.track[x-1][y-1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                                else if (x <= oldX+newVX && x > newX+1 && y == newY+1){
                                    path.add(Runner.track[x-1][y]);
                                    if (Runner.track[x-1][y] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x-1][y-1]);
                                    if (Runner.track[x-1][y-1] == '#' && !firstWallFlag){
                                        break;
                                    }

                                }
                            }
                        }
                    }
                }
                //diagonal move where the x distance is less than the y distance
                else if (Math.abs(newVX) < Math.abs(newVY)){
                    // up right diagonal move
                    if (newVX > 0 && newVY > 0){
                        for (int y = oldY; y < newY && y < Runner.col; y++) {
                            for (int x = oldX; x < newX && x < Runner.row; x++) {
                                if (x == y) {
                                    path.add(Runner.track[x][y+1]);
                                    if (Runner.track[x][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x+1][y+1]);
                                    if (Runner.track[x+1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                                else if (y >= oldY+newVX && x == newX-1 && y < newY-1){
                                    path.add(Runner.track[x][y+1]);
                                    if (Runner.track[x][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x+1][y+1]);
                                    if (Runner.track[x+1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // down right diagonal move
                    else if (newVX > 0 && newVY < 0){
                        for (int y = oldY; y > newY && y >= 0; y--) {
                            for (int x = oldX; x < newX && x < Runner.row; x++) {
                                int i = x+y;
                                if (i == 10) {
                                    path.add(Runner.track[x][y-1]);
                                    if (Runner.track[x][y-1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x+1][y-1]);
                                    if (Runner.track[x+1][y-1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                                else if (y <= oldY-newVX && x == newX-1 && y > newY+1){
                                    path.add(Runner.track[x][y-1]);
                                    if (Runner.track[x][y-1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x+1][y-1]);
                                    if (Runner.track[x+1][y-1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    // up left diagonal move
                    else if (newVX < 0 && newVY > 0){
                        for (int y = oldY; y < newY && y < Runner.row; y++) {
                            for (int x = oldX; x > newX && x >= 0; x--) {
                                int i = x+y;
                                if (i == 10) {
                                    path.add(Runner.track[x][y+1]);
                                    if (Runner.track[x][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x-1][y+1]);
                                    if (Runner.track[x-1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }

                                }
                                else if (y >= oldY-newVX && x == newX+1 && y < newY-1){
                                    path.add(Runner.track[x][y+1]);
                                    if (Runner.track[x][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }
                                    path.add(Runner.track[x-1][y+1]);
                                    if (Runner.track[x-1][y+1] == '#' && !firstWallFlag){
                                        break;
                                    }

                                }
                            }
                        }
                    }
                    // down left diagonal move
                    else if (newVX < 0 && newVY < 0){
                        for (int y = oldY; y > newY && y >= 0; y--) {
                            for (int x = oldX; x > newX && x >= 0; x--) {
                                if (x == y) {
                                    path.add(Runner.track[x][y - 1]);
                                    if (Runner.track[x][y - 1] == '#' && !firstWallFlag) {
                                        break;
                                    }
                                    path.add(Runner.track[x - 1][y - 1]);
                                    if (Runner.track[x - 1][y - 1] == '#' && !firstWallFlag) {
                                        break;
                                    }

                                } else if (y <= oldY + newVY && x == newX + 1 && y > newY + 1) {
                                    path.add(Runner.track[x][y - 1]);
                                    if (Runner.track[x][y - 1] == '#' && !firstWallFlag) {
                                        break;
                                    }
                                    path.add(Runner.track[x - 1][y - 1]);
                                    if (Runner.track[x - 1][y - 1] == '#' && !firstWallFlag) {
                                        break;
                                    }

                                }
                            }
                        }

                    }
                }
            }
            path.add(Runner.track[newX][newY]);
            return path;
        }


        /*
        Using the old velocity, and new acceleration get the new velocity
         */
        private String[] getNewTrainingVelocity(int oldVX, int oldVY, int accelX, int accelY){
            int newVX = oldVX + accelX;
            int newVY = oldVY + accelY;
            // Check to make sure we are within acceleration bounds and reduce if needed
            if (newVX <= Runner.minVelocity) {newVX = Runner.minVelocity;}
            if (newVX >= Runner.maxVelocity) {newVX = Runner.maxVelocity;}
            if (newVY <= Runner.minVelocity) {newVY = Runner.minVelocity;}
            if (newVY >= Runner.maxVelocity) {newVY = Runner.maxVelocity;}
            String newVXString = String.valueOf(newVX);
            String newVYString = String.valueOf(newVY);
            // return the new velocity as string to be parsed out in value iteration
            return new String[] {newVXString, newVYString};
        }


        /*
        Create a deep copy of hashtable
         */
        private Hashtable<String, Double> createDeepCopy(Hashtable<String, Double> original) {

            Gson gson = new Gson();
            String jsonString = gson.toJson(original);
            Type type = new TypeToken<Hashtable<String, Double>>(){}.getType();
            Hashtable<String, Double> copy = gson.fromJson(jsonString, type);
            return copy;
        }

        /*
        create an int array that is inclusive in both start and end
         */
        public int[] range(int start, int end) {
            int[] range = new int[end - start ];
            for (int i = start; i < end; i++) {
                range[i - start] = i;
            }
            return range;
        }

        public void getTrainingData(){
            int iterationCount = 0;
            while (iterationCount < maxTrainingIterations) {
                // Keep track of the total steps taken

                int races = 10;

                Hashtable<String, String> newPolicy = valueIterationTrain(Runner.track, Runner.crashType, 0.0, numOfTrainingIterations);
                for (int race = 0; race < races; race++) {
                    int steps = doTrial(newPolicy, Runner.crashType, animate);
                    totalSteps += steps;
                }
                int averageSteps = totalSteps / races;
                String crash;
                if (!Runner.crashType) {
                    crash = "Return to nearest position.";
                } else
                    crash = "Return to starting line.";
                iterationCount += numOfTrainingIterations;
                System.out.println("Number of training iterations: " + iterationCount);
                System.out.println("Type of crash: " + crash);
                System.out.println("Average number of steps needed to complete track " + averageSteps);
                System.out.println("If this number is 500 the track was not completed");
                System.out.println("The race car is training...");
                totalSteps = 0;
            }
        }

        private int doTrial(Hashtable<String, String> policy, boolean crashType, boolean animate) {
            // When no change in velocity for 5 steps assume were stuck
            int carStuck = 0;
            int maxSteps = 500;
//            char[][] deepCopyTrack = new char[Runner.track.length][Runner.track[0].length];
//            for (int i = 0; i < deepCopyTrack.length; i++)
//                deepCopyTrack[i] = Arrays.copyOf(Runner.track[i], Runner.track[i].length);
            // Grab random starting position
            String[] startingPosition = getRandomStartPosition(Runner.track);
            int x = Integer.parseInt(startingPosition[0]);
            int y = Integer.parseInt(startingPosition[1]);
            if (animate){
                System.out.println("New Run: ");
                showTrack(Runner.track, x,y);
            }
        // Init velocity to zero
            int vx = 0;
            int vy = 0;

            for (int step = 0; step < maxSteps; step++) {
                String tempKey =  x+","+y+","+vx+","+vy;
                String accelXY = policy.get(tempKey);
                int accelX = Integer.parseInt(accelXY.split(",")[0]);
                int accelY = Integer.parseInt(accelXY.split(",")[1]);


//TODO
                // Make sure were not at goal state and path does not include a wall
                String[] checkPathForWalls = getNewTrainingPosition(x,y,vx,vy, crashType);
                // Valid if true
                boolean validPathToFinish = (Boolean.valueOf(checkPathForWalls[3]));
                if (Runner.track[x][y] == Runner.goalState && validPathToFinish){
                    return step;
                }
                if (animate){
                    showTrack(Runner.track, x,y);
                    System.out.println("AccelXY: " + accelXY);
                }
                // Return of form
                // returnMultiple[0] = newX
                // returnMultiple[1] = newY
                // returnMultiple[2] = newVX
                // returnMultiple[3] = newVY
                int[] actionResult = trainingAction(x,y,vx,vy,accelX,accelY,Runner.track,crashType);
                x = actionResult[0];
                y = actionResult[1];
                vx = actionResult[2];
                vy = actionResult[3];
                if (vx == 0 && vy == 0){
                    carStuck++;
                }
                else {carStuck = 0;}
                // If we have been stuck for 5 iterations return maxSteps
                if (carStuck == 5){
                    return maxSteps;}
            }
            // 500 steps taken without goal state being reached
            return maxSteps;
        }

        private void showTrack(char[][] track, int xPos, int yPos){
            char currentPosition = track[xPos][yPos];

            track[xPos][yPos] = 'X';

            for (int y = 0; y < Runner.col; y++) {
                StringBuilder line = new StringBuilder();
                for (int x = 0; x < Runner.row; x++) {
                    line.append(track[x][y]);
                }
                System.out.println(line);
            }
            //Reset track to original char
            track[xPos][yPos] = currentPosition;
        }

    }

