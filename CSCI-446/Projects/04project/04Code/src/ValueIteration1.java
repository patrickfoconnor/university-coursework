import java.util.*;
import java.util.stream.Collectors;

// Value Iteration with V function

public class ValueIteration1 {
    /**
     * Tune able Parameters
     */
    double gamma = 0.9; // discount factor
    double deltaChange = 0.001; // small enough change to stop iterations
    /**
     * Constant parameters
     */
    int maxX = Runner.row;
    int maxY = Runner.col;
    // 0-7 is success on action
    // 7-9 is failure to act
    int accelerateSuccess = 7;

    double probAccelerationFail = 0.2;
    double probAccelerationSuccess = 1- probAccelerationFail;



    /*
    A range of min velocity to max velocity
     */
    int[] velocityRange = range(Runner.minVelocity, Runner.maxVelocity);

    /*
     All actions A that can be taken
     Of form "acceleration in y,acceleration in x)
     */
    String[] actions = new String[]{
            "-1,-1", "0,-1", "1,-1",
            "-1,0" , "0,0" , "1,0",
            "-1,1" , "0,1" , "1,1"
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
    key is off form "y,x,vY,vX,accelerationY,accelerationX"
    key is off form "x,y,vX,vY,accelerationX,accelerationY"
    value is a double such that applying acceleration aX,aY to vY, vX, at current to position results in an expected value equal to the value
*/
    Hashtable<String, Double> qValuesTable = new Hashtable<>();

        /*
        Hash table to hold policy
            Most desirable action at state "x,y"
            key is off form "x,y"
            value is a String "x,y" such that this is the most desirable action to apply
         */

    Hashtable<String, String> policy = new Hashtable<>();

    // the reward will always be 1 for our specific project
    public ValueIteration1(char[][] track, boolean crashType, double reward, int numOfTrainingIter) {
        int rows = maxX;
        int cols = maxY;


        /*
        Create a table values that will store the optimal Q-value for each state.
        Initialize value table to arbitrary values except terminal state.
            Terminal state will receive a zero value as it is absorbing
            values
         */
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                for (int vy: velocityRange) {
                    for (int vx : velocityRange) {
                        String tempKey = x+","+y+","+vx+","+vy;
                        //String tempKey = y+","+x+","+vy+","+vx;
                        valuesTable.put(tempKey, -1.0);
                        //values[y][x][vy][vx] = -1;
                    }
                }
            }
        }



        // Set finish line to 0/reward for finishing
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (track[y][x] == Runner.goalState) {
                    for (int vy: velocityRange) {
                        for (int vx: velocityRange) {
                            String tempKey = x+","+y+","+vx+","+vy;
                            //String tempKey = y+","+x+","+vy+","+vx;
                            valuesTable.put(tempKey, reward);
                            //values[y][x][vy][vx] = reward;
                        }
                    }
                }
            }
        }

        /*
        Init all Q(s,a) to arbitrary values, except
            terminal state will be set to a value of 0
         */

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                for (int vy: velocityRange) {
                    for (int vx : velocityRange) {
                        for (int actionIndex : actionsIndex) {
                            String[] actionString = (actions[actionIndex]).split(",");
                            int accelX = Integer.parseInt(actionString[0]);
                            int accelY = Integer.parseInt(actionString[1]);
                            String tempKey = x+","+y+","+vx+","+vy+","+accelX+","+accelY;
                            //String tempKey = y+","+x+","+vy+","+vx+","+accelY+","+accelX;
                            qValuesTable.put(tempKey, -1.0);
                            //qValues[y][x][vy][vx][accelY][accelX] = -1;
                        }
                    }
                }
            }
        }
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (track[x][y] == Runner.goalState) {
                    for (int vy: velocityRange) {
                        for (int vx: velocityRange) {
                            for (int actionIndex: actionsIndex) {
                                // get action with index
                                String[] actionString = (actions[actionIndex]).split(",");
                                int accelX = Integer.parseInt(actionString[0]);
                                int accelY = Integer.parseInt(actionString[1]);
                                String tempKey = x+","+y+","+vx+","+vy+","+accelX+","+accelY;
//                                int accelY = Integer.parseInt(actionString[0]);
//                                int accelX = Integer.parseInt(actionString[1]);
//                                String tempKey = y+","+x+","+vy+","+vx+","+accelY+","+accelX;
                                qValuesTable.put(tempKey, reward);
                                //qValues[y][x][vy][vx][accelY][accelX] = reward;
                            }
                        }
                    }
                }
            }
        }

        /*
        Location for training the agent
            Includes optimizing the values in the tables V(state) and Q(state,action)
         */

        for (int iteration = 0; iteration < numOfTrainingIter; iteration++) {
            // Keep a copy of the old values in order to check for stopping when max change is small enough
            //TODO create another hashTable
            //oldValues = values;
            Hashtable<String, Double> oldValuesTable;
            oldValuesTable = createDeepCopy(valuesTable);
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    for (int vy: velocityRange) {
                        for (int vx: velocityRange) {
                            // If wall is hit value of that should be a large negative value

                            if (track[x][y] ==  Runner.wall){
                                String tempKey = x+","+y+","+vx+","+vy;
                                //String tempKey = y+","+x+","+vy+","+vx;
                                valuesTable.put(tempKey, -9.9);
                                //values[y][x][vy][vx] = -9.9;
                                continue;
                            }

                            for (int actionIndex: actionsIndex) {
                                double tempReward;
                                if (track[x][y] == Runner.goalState){
                                    tempReward = reward;
                                }
                                else { tempReward = -1;}
                                // Get new state sPrime based on the current state and the current action
                                // get the acceleration from actions
                                String accelerationYX = actions[actionIndex];
                                String[] accelYXSplit = accelerationYX.split(",");
                                int accelX = Integer.parseInt(accelYXSplit[0]);
                                int accelY = Integer.parseInt(accelYXSplit[1]);
                                //int[] sPrime = trainingAction(y, x, vy, vx, accelY, accelX, track, crashType);
                                int[] sPrime = trainingAction(x, y, vx, vy, accelX, accelY, track, crashType);
                                int newX = sPrime[0];
                                int newY = sPrime[1];
                                int newVX = sPrime[2];
                                int newVY = sPrime[3];

                                // Look one step ahead
                                String tempKeySuccess = newX+","+newY+","+newVX+","+newVY;
                                double valueOfNewState = oldValuesTable.get(tempKeySuccess);
                                //double valueOfNewState = oldValues[newY][newX][newVY][newVX];

                                // Handle failed acceleration now
                                int[] sPrimeFail = trainingAction(x, y, vx, vy,0,0,track, crashType );
                                int newXFail = sPrimeFail[0];
                                int newYFail = sPrimeFail[1];
                                int newVXFail = sPrimeFail[2];
                                int newVYFail = sPrimeFail[3];
                                String tempKeyFail = newXFail+","+newYFail+","+newVXFail+","+newVYFail;
                                double valueOfNewStateFail = oldValuesTable.get(tempKeyFail);
                                //double valueOfNewStateFail = oldValues[newYFail][newXFail][newVYFail][newVXFail];

                                double expectedValue = (probAccelerationSuccess *valueOfNewState) +
                                        (probAccelerationFail * valueOfNewStateFail);

                                // Update Q value in Q table
                                String tempKey = x+","+y+","+vx+","+vy+","+accelX+","+accelY;
                                qValuesTable.put(tempKey, (tempReward + (gamma * expectedValue)));
                                //qValues[y][x][vy][vx][accelY][accelY] = tempReward + (gamma * expectedValue);
                            }
                            //TODO get the action with the highest value
                            String maxKey = "";
                            double maxValue = 0.0;
                            for (int actionIndex: actionsIndex) {
                                //Loop through the available actions and grab the value associated with it
                                String[] accelXY = actions[actionIndex].split(",");
                                int accelX = Integer.parseInt(accelXY[0]);
                                int accelY = Integer.parseInt(accelXY[1]);
                                String tempMaxQKey = x + "," + y + "," + vx + "," + vy + "," + accelX + "," + accelY;
                                double tempMaxValue = qValuesTable.get(tempMaxQKey);
                                if (tempMaxValue > maxValue) {
                                    maxKey = tempMaxQKey;
                                    maxValue = tempMaxValue;
                                }
                                String tempValueKey = x + "," + y + "," + vx + "," + vy;
                                valuesTable.put(tempValueKey, qValuesTable.get(maxKey));
                                //qValues[y][x][vy][vx][maxAccelY][maxAccelX];
                            }
                        }
                    }
                }
            }
            // Make sure all the rewards set to 0 in the terminal state
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    if (track[x][y] == Runner.goalState){
                        for (int vy: velocityRange) {
                            for (int vx : velocityRange) {
                                String tempKey = x+","+y+","+vx+","+vy;
                                valuesTable.put(tempKey, reward);
                                //values[y][x][vy][vx] = reward;
                            }
                        }
                    }
                }
            }
            //TODO Check out to see if values are stabilizing
            // by finding maximum change of any of the states
            double delta = 0; // method/algorithm will go right here
            for (String key : valuesTable.keySet()) {
                double oldValue = oldValuesTable.get(key);
                double newValue = valuesTable.get(key);
                double tempDelta = Math.abs(newValue-oldValue);
                if (tempDelta > delta){
                    delta = tempDelta;
                }
            }
            if (delta < deltaChange){
                getPolicyFromQValues(qValuesTable);
                break;
            }
        }
    }

    /**
     *
     */
    private void getPolicyFromQValues(Hashtable<String, Double> qValuesTable) {
//        for (int x = 0; x < row; x++) {
//            for (int y = 0; y < col; y++) {
//                for (int vy: velocityRange) {
//                    for (int vx: velocityRange) {
                        // Grab the best action from qValues[y][x][vy][vx]
                        String bestKey = "";
                        double bestValue = 0;
                        for (String key : qValuesTable.keySet()) {
                            double tempBestValue = qValuesTable.get(key);
                            if (tempBestValue > bestValue){
                                bestKey = key;
                                bestValue = tempBestValue;
                            }
                            String[] splitKey = bestKey.split(",");
                            int bestStateX = Integer.parseInt(splitKey[0]);
                            int bestStateY = Integer.parseInt(splitKey[1]);
                            int bestAccelerationX = Integer.parseInt(splitKey[4]);
                            int bestAccelerationY = Integer.parseInt(splitKey[5]);
                            String bestState = bestStateX +","+bestStateY;
                            String bestAcceleration = bestAccelerationX+","+bestAccelerationY;
                            policy.put(bestState, bestAcceleration);
                        }
                    }
//                }
//            }
//        }
//    }

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
        String[] newPosition = getNewTrainingPosition(oldX, oldY, newVX, newVY);
        int tempX = Integer.parseInt(newPosition[0]);
        int tempY = Integer.parseInt(newPosition[1]);

        // Using new position find the nearest playable cell
        String[] playableNewPosition = getNearestOpenCell(tempX, tempY, newVX, newVY, track);
        int newX = Integer.parseInt(playableNewPosition[0]);
        int newY = Integer.parseInt(playableNewPosition[1]);

            // If crash happened then new position does not equal old position
        if (newX != tempX || newY != tempY) {
                // If badCrash is true return to random start position
            if (badCrash && track[newX][newX] != Runner.goalState) {
                String[] newCrashPosition = getRandomStartPosition(track);
                newX = Integer.parseInt(newCrashPosition[0]);
                newY = Integer.parseInt(newCrashPosition[1]);
                }
            }
            // Reset Velocity to 0
        newVX = 0;
        newVY = 0;

            newCarStats = new int[]{newX, newY, newVX, newVY};

        return newCarStats;
    }

    /*
    Locate the nearest open cell in order to handle crash
    If velocity is provided search in the opposite direction to avoid jumping walls
     */
    private String[] getNearestOpenCell(int tempX, int tempY, int newVX, int newVY, char[][] track) {
        int maxRadius = Math.max(Runner.row, Runner.col);

        for (int radius = 1; radius <= maxRadius; radius++) {
            // not moving in y direction
            int[] xRange;
            if (newVX == 0) {
                xRange = range(-radius, radius);
            }
            // moving in negative y direction
            if (newVX < 0){
                xRange = range(0, radius);
            }
            else{
                xRange = range(-radius, 0);
            }
            //For each value in y search radius
            for (int xOffset: xRange) {
                int newX = tempX + xOffset;
                int yRadius = radius - Math.abs(xOffset);
                int[] yRange;
                // not moving in x direction
                if (newVY == 0) {
                    yRange = range(tempY - yRadius, tempY + yRadius);
                }
                // moving in negative x direction
                if (newVY < 0){
                    yRange = range(tempY, tempY + yRadius);

                }
                else{
                    yRange = range(tempY - yRadius, tempY);
                }
                for (int newY: yRange) {
                    //if ((newX < 0 || newX >= Runner.row) && (newY < 0 || newY >= Runner.col)){
                    if ((newX > 0 && newX <= Runner.row) && (newY > 0 && newY <= Runner.col)){
                        if (track[newX][newY] == '.' || track[newX][newY] == 'S' || track[newX][newY] == 'F'){
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
    private String[] getNewTrainingPosition(int oldX, int oldY, int newVX, int newVY) {
        int newX = oldX + newVX;
        int newY = oldY + newVY;
        String newXString = String.valueOf(newX);
        String newYString = String.valueOf(newY);
        return new String[] {newXString, newYString};
    }


    /*
    Using the old velocity, and new acceleration get the new velocity
     */
    private String[] getNewTrainingVelocity(int oldVX, int oldVY, int accelX, int accelY){
        int newVX = oldVX + accelX;
        int newVY = oldVY + accelY;
        // Check to make sure we are within acceleration bounds and reduce if needed
        if (newVX < Runner.minVelocity) {newVX = Runner.minVelocity;}
        if (newVX > Runner.maxVelocity) {newVX = Runner.maxVelocity;}
        if (newVY < Runner.minVelocity) {newVY = Runner.minVelocity;}
        if (newVY > Runner.maxVelocity) {newVY = Runner.maxVelocity;}
        String newVXString = String.valueOf(newVX);
        String newVYString = String.valueOf(newVY);
        // return the new velocity as string to be parsed out in value iteration
        return new String[] {newVXString, newVYString};
    }


    /*
    Create a deep copy of hashtable
     */
    private Hashtable<String, Double> createDeepCopy(Hashtable<String, Double> original) {
        Hashtable<String, Double> copy = new Hashtable<>();
        for (Map.Entry<String, Double> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
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
}
