import java.text.DecimalFormat;
import java.util.*;

// Value Iteration with V function

public class ValueIteration2 {


    /**
     * Tune able Parameters
     */
    double gamma = 0.8; // discount factor
    double noise = .2; // non-deterministic
    double deltaChange = 0.001; // small enough change to stop iterations
    /**
     * Constant parameters
     */
    int iterationCount = 0;
    double biggestChange;
    double returnBiggestChange;
    int minX = 0;
    int minY = 0;
    int maxX = Runner.row;
    int maxY = Runner.col;
    double[][] rewards = new double[Runner.row][Runner.col];
    double[][] values = new double[Runner.row][Runner.col];
    /*
     Hashtable to store all possible actions at state "x,y"
            key is off form "x,y"
            value is an arrayList of Strings "x,y"
     */
    Hashtable<String, ArrayList<String>> actionsAtState = new Hashtable<>();
        /*
        Hash table to hold policy
            Most desirable action at state "x,y"
            key is off form "x,y"
            value is a String "x,y" such that this is the most desirable cell to move to
         */
    Hashtable<String, String> policy = new Hashtable<>();


    /**
     * To delete after testing
     */
    //TODO Delete after testing

    public ValueIteration2(char[][] allStates) {
        /*
         Initialize all rewards for states
         */
        initRewardsForState(allStates);

        /*
        Get all valid moves per state
         */
        getValidMovesFromState();

        /*
        Create random policy to later be improved upon
         */
        setRandPolicy();

        /*
        Define an initial value function
         */
        setInitialValues(allStates);

    /*
    The actual value iteration now
     */

        do {
            biggestChange = -1;
            for (int colIterI = 0; colIterI < Runner.col; colIterI++) {
                for (int rowIterI = 0; rowIterI < Runner.row; rowIterI++) {
                    String tempStateKey = rowIterI + "," + colIterI;
                    ArrayList<String> availActionsAtState = actionsAtState.get(tempStateKey);
                    if (policy.containsKey(tempStateKey)) {
                        double oldValue = values[rowIterI][colIterI];

                        double newValue;
                        String currentPolicyAction = policy.get(tempStateKey);
                        String[] currentActionCoord = currentPolicyAction.split(",");
                        int currentActionXCoord = Integer.parseInt(currentActionCoord[0]);
                        int currentActionYCoord = Integer.parseInt(currentActionCoord[1]);

                        for(String coord : availActionsAtState) {
                            String[] randomActionCoord = coord.split(",");
                            int randomActionXCoord = Integer.parseInt(randomActionCoord[0]);
                            int randomActionYCoord = Integer.parseInt(randomActionCoord[1]);

                        // Iterate through each of the actions available
                                DecimalFormat df = new DecimalFormat("###.######");
//                                double value = (rewards[rowIterI][colIterI] + (gamma * (((1- noise)
//                                        * values[currentActionXCoord][currentActionYCoord]) +
//                                        // This next noise * value should be all available actions total?
//                                        (noise * values[randomActionXCoord][randomActionYCoord]))));
                            double value = (rewards[rowIterI][colIterI] + (gamma * ((values[currentActionXCoord][currentActionYCoord]) *
                                    // This next noise * value should be all available actions total?
                                    (values[randomActionXCoord][randomActionYCoord]))));
                                double roundedValue = Double.parseDouble(df.format(value));
                                // Compare that newValue to the previously store oldValue
                                // If greater set that action to be the preferred action in policy
                                if (roundedValue > oldValue && (oldValue != 1 && oldValue != -1)){
                                    String newBestAction = randomActionXCoord + "," + randomActionYCoord;
                                    newValue = roundedValue;
                                    values[rowIterI][colIterI] = newValue;
                                    policy.put(tempStateKey, newBestAction);
                                    double deltaValue = newValue - oldValue;
                                    if ((deltaValue >= biggestChange && deltaValue > 0)) {
                                        biggestChange = deltaValue;
                                        returnBiggestChange = deltaValue;
                                    }
                                }
                            }
                        }
                    }
                }
            iterationCount++;
//            if (iterationCount % 15 == 0){
                System.out.println("Iteration " + iterationCount + ". With a biggestChange of " + returnBiggestChange);
                //
//            }
        } while (returnBiggestChange > deltaChange);
    }

    private void setInitialValues(char[][] allStates) {
        for (int colIterV = 0; colIterV < Runner.col; colIterV++) {
            for (int rowIterV = 0; rowIterV < Runner.row; rowIterV++) {
                String tempKey = rowIterV + "," + colIterV;
                if (actionsAtState.containsKey(tempKey)) {
                    values[rowIterV][colIterV] = 0;
                }
                char currentChar = allStates[rowIterV][colIterV];
                switch (currentChar) {
                    case ('#'), ('S') -> values[rowIterV][colIterV] = -1; // -1 value for being in wall spot
                    case ('F') -> values[rowIterV][colIterV] = 1; // 1 for being at finish line
                    default -> values[rowIterV][colIterV] = 0;

                    //throw new IllegalStateException("Unexpected value: " + currentChar);
                }
            }
        }
    }

    /*
    create random policy
     */
    private void setRandPolicy() {
        for (String action : actionsAtState.keySet()) {
            int availActionsLength = (actionsAtState.get(action)).size();
            Random rand = new Random();
            int randIndex = rand.nextInt(availActionsLength);
            //TODO need to make this select a random move
            ArrayList<String> availableActions = actionsAtState.get(action);
            String randMove = availableActions.get(randIndex);

            policy.put(action, randMove);
        }
    }

    private void getValidMovesFromState() {
        for (int colIter = 0; colIter < Runner.col; colIter++) {
            for (int rowIter = 0; rowIter < Runner.row; rowIter++) {
                // Iterate through each of the possible cells
                // Check to make sure they are on the board
                ArrayList<String> possibleActionsTemp = new ArrayList<>();
                String currentCoord = colIter + "," + rowIter;
                for (int possX = -5; possX <= 5; possX++) {
                    for (int possY = -5; possY <= 5; possY++) {
//                for (int possX = -1; possX <= 1; possX++) {
//                    for (int possY = -1; possY <= 1; possY++) {
                        boolean validX = ((rowIter + possX >= minX) && (rowIter + possX < maxX));
                        boolean validY = ((colIter + possY >= minY) && (colIter + possY < maxY));
                        boolean validCoord = (validX && validY);
                        int newX = rowIter + possX;
                        int newY = colIter + possY;
                        String tempCoord = newX + "," + newY;
                        boolean notItselfCoord = (!currentCoord.equals(tempCoord));
                        if (validCoord && notItselfCoord){
                            possibleActionsTemp.add(newX + "," + newY);
                        }
                    }
                }
                String currentCellKey = rowIter + "," + colIter;
                actionsAtState.put(currentCellKey, possibleActionsTemp);
            }
        }
    }

    private void initRewardsForState(char[][] allStates) {
        // Initialize all rewards for each state
        for (int colIter = 0; colIter < Runner.col; colIter++) {
            for (int rowIter = 0; rowIter < Runner.row; rowIter++) {
                char currentChar = allStates[rowIter][colIter];
                switch (currentChar) {
                    case ('#'):
                        rewards[rowIter][colIter] = -1;
                        break;
                    // -1 for being in start as we don't want to be there
                    case ('S'):
                        rewards[rowIter][colIter] = 0;
                    case ('F'):
                        rewards[rowIter][colIter] = 1; // 1 for being at finish line
                        break;
                    default:
                        rewards[rowIter][colIter] = 0; // 0 for being in random play-able board spot
                        //throw new IllegalStateException("Unexpected value: " + currentCharString);
                }
            }
        }
    }
}
