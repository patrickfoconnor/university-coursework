import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QLearning {

    /**
     * Initializes all rewards to 1
     * While the number of iterations is less than the maxIterations,
     * choose a state, and for each acceleration calculate maximum reward
     * store reward for each action
     * drive car and keep track of moves and crashes
     */
    private String[][] testTrack;
    private String[][] freshTrack;
    private String[][] learnTrack;
    private String restartType;
    private String[][] restartChoice;
    private String trackName;
    private Car learningCar;
    private Car testCars;
    private double gamma = 1;
    private double alpha = 1;
    private int testMoves;
    private int finishers;
    private int maxIterations;
    private
    HashMap<Pair, HashMap<Pair, HashMap<Pair, Double>>> rewards;


    public QLearning(String[][] learnTrack, String restartType){
        this.learnTrack = learnTrack;
        this.restartType = restartType;
        //this.trackName = trackName;
        testTrack = new String[learnTrack.length][];
        this.testTrack = createCopy(learnTrack);
        freshTrack = new String[learnTrack.length][];
        this.freshTrack = createCopy(learnTrack);
        try{
            learningCar = new Car(learnTrack, restartType);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
        maxIterations = 360000;
        rewards = new HashMap<>();
        System.out.println("Starting Training");
        train();
        System.out.println("Training Complete");
        System.out.println("Starting Testing");
        test();
        System.out.println("Testing Complete");
        System.out.println(finishers + " cars finished the race");


    }
    // training class for Q-learning agent
    void train(){
        Random random = new Random();
        int sampleSize = 50;
        int previousDist = -1;
        initialize();
        finishers = 0;
        int[][] availableCells = learningCar.unvisitedCells;
        int[][] numCells = new int[availableCells.length + learningCar.startCells.length][];
        for(int i = 0; i < availableCells.length; i++){
            numCells[i] = availableCells[i];
        }
        for(int i = availableCells.length; i < learningCar.startCells.length + availableCells.length; i++){
            numCells[i] = learningCar.startCells[i - availableCells.length];
        }
        int[][] finishCells = learningCar.finishCells;
        for(int i = 0; i < maxIterations; i++){
            if(alpha > 0.1){
                alpha = alpha - 1 / maxIterations;
            }
            else{
                alpha = 0.1;
            }
            learningCar.isTraining();
            int distance = (int)(sampleSize * Math.floor(i / sampleSize));
            //shuffle array
            Random rand = new Random();
            for (int j = numCells.length - 1; j > 0; j--)
            {
                int index = rand.nextInt(j+1);
                // Simple swap
                int[] x = numCells[index];
                numCells[index] = numCells[j];
                numCells[j] = x;
            }
            Pair position = getPairWithin(distance, finishCells, numCells, previousDist);
            if(i <= numCells.length * sampleSize){
                previousDist = distance;
            }
            else{
                previousDist = 0;
            }
            int xVelocity = random.nextInt(11) - 5;
            int yVelocity = random.nextInt(11) - 5;
            boolean idealHashMap = false;
            //boolean idealHashMap2 = false;
            //boolean idealHashMap3 = false;
            Pair velocity = getVelocityPair(position, xVelocity, yVelocity);
            for(int a = -1; a <= 1; a++){
                for(int b = -1; b <= 1; b++){
                    Pair actionPair = getActionPair(position, velocity, a, b);

                    learningCar.xPosition = position.x;
                    learningCar.yPosition = position.y;
                    learningCar.xVelocity = velocity.x;
                    learningCar.yVelocity = velocity.y;
                    learningCar.xStart = position.x;
                    learningCar.yStart = position.y;
                    drive(learningCar, actionPair.x, actionPair.y);
                    Pair position2 = getPositionPair(learningCar.xPosition, learningCar.yPosition);
                    Pair velocity2 = getVelocityPair(position2, learningCar.xVelocity, learningCar.yVelocity);

                    if(rewards.containsKey(position2)){
                        //getting maximum value
                        double maxReward;
                        HashMap.Entry<Pair, Double> maxEntry = null;
                        for (HashMap.Entry<Pair, Double> entry : rewards.get(position2).get(velocity2).entrySet()) {
                            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                                maxEntry = entry;
                            }
                        }
                        maxReward = maxEntry.getValue();
                        double q = rewards.get(position).get(velocity).get(actionPair);
                        double reward;
                        //getting reward
                        if(learnTrack[position2.y][position2.x].equals("F")){
                            reward = 0.0;
                        }
                        else{
                            reward = -1.0;
                        }
                        double updatedQ = q + (alpha * (reward + (gamma * maxReward) - q));

                        rewards.get(position).get(velocity).put(actionPair, updatedQ);
                    }
                    else{

                    }
                }
            }
            if(i % sampleSize == 0){
                idealHashMap = driveCar(i);
            }


        }
    }
    void test(){
        testMoves = driveCarV2();
    }
    public boolean driveCar(int n){
        String[][] track = createCopy(freshTrack);
        Car testCar;
        try{
            testCar = new Car(track, restartType);
        }
        catch(IOException exception){
            exception.printStackTrace();

            return false;

        }
        testCar.startingLine();

        Pair position = getPositionPair(testCar.xPosition, testCar.yPosition);
        Pair velocity = getVelocityPair(position, testCar.xVelocity, testCar.yVelocity);
        boolean raceDone = false;
        int i = 0;
        int maxTimes = 0;
        while(!raceDone && i < 13000){
            if(rewards.get(position) != null && rewards.get(position).get(velocity) != null){
                Pair maximumAction = getMaximumAction(rewards.get(position).get(velocity));
                if(maximumAction.x == 0 && maximumAction.y == 0){
                    maxTimes++;
                }
                if(maximumAction.x != 0 || maximumAction.y != 0){
                    maxTimes = 0;
                }
                raceDone = drive(testCar, maximumAction.x, maximumAction.y);
                position = getPositionPair(testCar.xPosition, testCar.yPosition);
                velocity = getVelocityPair(position, testCar.xVelocity, testCar.yVelocity);

            }
            else{

            }
            i++;
        }
        
        if(raceDone){
            finishers++;
            return true;
        }
        return false;
    }
    public int driveCarV2(){
        String[][] racetrack = createCopy(testTrack);
        Car testCar;
        try{
            testCar = new Car(racetrack, restartType);
        }
        catch(IOException exception){
            exception.printStackTrace();
            
            return 0;
        }
        testCar.startingLine();

        Pair position = getPositionPair(testCar.xPosition, testCar.yPosition);
        Pair velocity = getVelocityPair(position, testCar.xVelocity, testCar.yVelocity);
        boolean raceDone = false;
        int i = 0;
        int maxTimes = 0;
        while(!raceDone && i < 13000){
            if(rewards.get(position) != null && rewards.get(position).get(velocity) != null){
                Pair maximumAction = getMaximumAction(rewards.get(position).get(velocity));
                if(maximumAction.x == 0 && maximumAction.y == 0){
                    maxTimes++;
                }
                if(maximumAction.x != 0 || maximumAction.y != 0){
                    maxTimes = 0;
                }
                raceDone = drive(testCar, maximumAction.x, maximumAction.y);
                position = getPositionPair(testCar.xPosition, testCar.yPosition);
                velocity = getVelocityPair(position, testCar.xVelocity, testCar.yVelocity);

            }
            else{

            }
            i++;
        }
        if(raceDone){
            finishers++;
            System.out.println("Car finished with " + i + " test moves");
        }
        
        testCars = testCar;
        return i;

    }
    // initialize
    void initialize(){
        int[][] availableCells = learningCar.unvisitedCells;
        for(int i = 0; i < availableCells.length; i++){
            Pair position = new Pair(availableCells[i][0], availableCells[i][1]);
            HashMap<Pair, HashMap<Pair, Double>> medium = new HashMap<Pair, HashMap<Pair, Double>>();
            for(int v = -5; v <= 5; v++){
                for(int z = -5; z <= 5; z++){
                    Pair velocity = new Pair(v, z);
                    HashMap<Pair, Double> third = new HashMap<Pair, Double>();
                    for(int x = -1; x <= 1; x++){
                        for(int y = -1; y <= 1; y++){
                            Pair action = new Pair(x,y);
                            third.put(action, -200.0);
                        }
                    }
                    medium.put(velocity, third);
                }
            }
            rewards.put(position, medium);
        }
        int[][] finishCells = learningCar.finishCells;
        for(int i = 0; i < finishCells.length; i++){
            Pair position = new Pair(finishCells[i][0], finishCells[i][1]);
            HashMap<Pair, HashMap<Pair, Double>> medium = new HashMap<Pair, HashMap<Pair, Double>>();
            for(int v = -5; v <= 5; v++){
                for(int z = -5; z <= 5; z++){
                    Pair velocity = new Pair(v, z);
                    HashMap<Pair, Double> third = new HashMap<Pair, Double>();
                    for(int x = -1; x <= 1; x++){
                        for(int y = -1; y <= 1; y++){
                            Pair action = new Pair(x,y);
                            third.put(action, 0.0);
                        }
                    }
                    medium.put(velocity, third);
                }
            }
            rewards.put(position, medium);
        }
        int[][] startCells = learningCar.startCells;
        for(int i = 0; i < startCells.length; i++) {
            Pair position = new Pair(startCells[i][0], startCells[i][1]);
            HashMap<Pair, HashMap<Pair, Double>> medium = new HashMap<Pair, HashMap<Pair, Double>>();
            for (int v = -5; v <= 5; v++) {
                for (int z = -5; z <= 5; z++) {
                    Pair velocity = new Pair(v, z);
                    HashMap<Pair, Double> third = new HashMap<Pair, Double>();
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            Pair action = new Pair(x, y);
                            third.put(action, -200.0);
                        }
                    }
                    medium.put(velocity, third);
                }
            }
            rewards.put(position, medium);
        }
    }
    // returns a copy of the track
    public String[][] createCopy(String[][] track){
        if(track == null){
            return null;
        }
        String[][] copy = new String[track.length][];
        for(int i = 0; i < track.length; i++){
            copy[i] = track[i].clone();
        }
        return copy;
    }
    // returns the action with the highest reward
    private Pair getMaximumAction(HashMap<Pair, Double> actionMap){
        Map.Entry<Pair, Double> maximumEntry = null;
        for(HashMap.Entry<Pair, Double> entry : actionMap.entrySet()){
            if(maximumEntry == null || entry.getValue() > maximumEntry.getValue()){
                maximumEntry = entry;
            }
        }
        return maximumEntry.getKey();
    }
    private Pair getPairWithin(int distance, int[][] finishCells, int[][] availableCells, int previousDist){
        for(int index = 0; index < availableCells.length; index++) {
            for (int j = 0; j < finishCells.length; j++) {
                double dist = Math.sqrt(Math.pow(availableCells[index][0] - finishCells[j][0], 2) + Math.pow(availableCells[index][1] - finishCells[j][1], 2));

                int d = ((int) Math.floor(dist));
                if (d <= distance && d >= previousDist) {
                    Pair position = getPositionPair(availableCells[index][0], availableCells[index][1]);
                    return position;
                }
            }
        }
        double dist = Math.sqrt(Math.pow(availableCells[0][0] - finishCells[0][0], 2) + Math.pow(availableCells[0][1] - finishCells[0][1], 2));
        int minDistance = ((int)Math.floor(dist));
        int[] closestPair = availableCells[0];

        return getPositionPair(closestPair[0], closestPair[1]);

    }
    // returns the position pair
    private Pair getPositionPair(int x, int y){
        ArrayList<Pair> positions = new ArrayList<Pair>(rewards.keySet());
        Pair testPair = new Pair(x, y);
        for (Pair pair : positions) {
            if (testPair.equals(pair)) {
                return pair;
            }
        }
        return null;
    }
    // returns velocity pair
    private Pair getVelocityPair(Pair pair, int x, int y){
        ArrayList<Pair> velocities = new ArrayList<Pair>(rewards.get(pair).keySet());
        Pair testPair = new Pair(x, y);
        for (Pair velocityPair : velocities) {
            if (testPair.equals(velocityPair)) {
                return velocityPair;
            }
        }
        return null;
    }
    // returns the action pair
    private Pair getActionPair(Pair pair, Pair velocityPair, int x, int y){
        ArrayList<Pair> actions = new ArrayList<Pair>(rewards.get(pair).get(velocityPair).keySet());
        Pair testPair = new Pair(x, y);
        for (Pair actionPair : actions) {
            if (testPair.equals(actionPair)) {
                return actionPair;
            }
        }
        return null;
    }
    // drive the car given acceleration value
    // 20% chance acceleration will fail and velocity will stay the same
    public boolean drive(Car car, int xAcceleration, int yAcceleration){
        Random random = new Random();
        int randomNum = random.nextInt(10);
        //non-determinism
        if (randomNum == 7 && randomNum == 3) {
            xAcceleration = 0;
            yAcceleration = 0;
        }

        boolean raceOver = false;

        raceOver = car.changePosition(xAcceleration, yAcceleration);

        return raceOver;


    }
}
