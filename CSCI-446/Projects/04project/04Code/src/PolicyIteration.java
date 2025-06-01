import java.util.Random;


public class PolicyIteration {
    /**
     * Tune able Parameters
     */
    double gamma = 0.8;
    double rewardsGoal = 0.7;
    int maxEpisodes = 20;
    //TODO Establish nSteps for the iterateRandomly
    int nSteps = 0;
    /**
     * Constant Parameters
     */
    int trainingStep = 0;
    int steps = 0;
    int newAx = 0;
    int newAy = 0;
    int startPositionX;
    int startPositionY;
    int currentStateX;
    int currentStateY;

    /**
     * Initialize arbitary arrays
     */
    double[][] rewards;
    double[][] uOriginal;        // utility values of the environment
    String[][] optimalPolicy;   //  init arbitary initial policy

    public String[][] VI() {
        uOriginal = new double[Runner.row][Runner.col];
        for (int colIter = 0; colIter < Runner.col; colIter++) {
            for (int rowIter = 0; rowIter < Runner.row; rowIter++) {
                uOriginal[rowIter][colIter] = 0;
            }
        }
        // Temporary utility array
        double[][] uPrime = new double[Runner.row][Runner.col];
        for (int colIter = 0; colIter < Runner.col; colIter++) {
            for (int rowIter = 0; rowIter < Runner.row; rowIter++) {
                uPrime[rowIter][colIter] = 0;
            }
        }
        /**
         * Tune able Parameters
         */
        double minDelta = 0.05;
        /**
         * Constant Parameters
         */
        int iterationCount = 1;
        double maxUtilityDelta = 0;


        do {
            for (int colIter = 0; colIter < Runner.col; colIter++) {
                for (int rowIter = 0; rowIter < Runner.row; rowIter++) {
                    uOriginal[rowIter][colIter]  = uPrime[rowIter][colIter];
                }
            }

            double utilityDelta = 0;

            for (int colIter = 0; colIter < Runner.col; colIter++) {
                for (int rowIter = 0; rowIter < Runner.row; rowIter++) {
                    if (rewards[rowIter][colIter] == 0){
                        break;
                    }
                    else {
                        double maxUtilityOfAction = getMaxUtility(rowIter, colIter);
                        double updatedUtil = 0;

                        updatedUtil = rewards[rowIter][colIter] + (gamma * maxUtilityOfAction);
                        optimalPolicy[rowIter][colIter] = getActionOfMaxUtility(rowIter, colIter);

                        uPrime[rowIter][colIter] = updatedUtil;

                        utilityDelta = Math.abs(uOriginal[rowIter][colIter] - uPrime[rowIter][colIter]);

                        if (utilityDelta > maxUtilityDelta) {
                            maxUtilityDelta = utilityDelta;
                        }
                    }
                }
            }

            if (iterationCount % 20 == 0){
                System.out.println("Finishing iteration " + iterationCount +
                        " with a max utility delta of " + maxUtilityDelta);
            }
            iterationCount++;

        } while (maxUtilityDelta >= minDelta);

        return optimalPolicy;

    }

    //TODO calculate the maximum utility of the state based on all 7 options
    private double getMaxUtility(int rowIter, int colIter) {
        return 0;
    }
    //TODO calculate the action of maximum utility of the state based on all 7 options
    private String getActionOfMaxUtility(int rowIter, int colIter) {
        return "0";
    }

    public void iterateRandomly() {
        for (int colIter = 0; colIter < Runner.col; colIter++) {
            for (int rowIter = 0; rowIter < Runner.row; rowIter++) {
                Random rand = new Random();
                switch(rand.nextInt(7)){
                    // Move at random either of the 7 following directions
                    // REPLACE WITH JUST randomX -5 through 5
                    //      and  randomY -5 through 5
                    // Move north
                    case(0):

                    // Move north/east
                    case(1):

                    // Move east
                    case(2):

                        // Move south/east
                    case(3):

                        // Move south
                    case(4):

                        // Move south/west
                    case(5):

                        // Move west
                    case(6):

                        // Move north/west
                    case(7):

                }
            }
        }
    }

    public double giveReward(int currentStateX, int currentStateY){
        char currentChar = Runner.track[currentStateX][currentStateY];
        if (currentChar == ('F'))
            return 1;
        else
            return 0;
    }
    public boolean trackFinished(int currentStateX, int currentStateY){
        char currentChar = Runner.track[currentStateX][currentStateY];
        if (currentChar == ('F'))
            return true;
        else
            return false;
    }



}
