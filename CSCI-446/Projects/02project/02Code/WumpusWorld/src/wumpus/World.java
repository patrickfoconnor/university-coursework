package wumpus;

import wumpus.Environment.Action;
import wumpus.Environment.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static wumpus.AgentFOL.Action.*;

public class World {


    private class FOLTile {
        boolean pit = false;
        boolean wumpus = false;
        boolean gold = false;
        boolean breeze = false;
        boolean stench = false;
    }
    public int stepCount;
    private AgentFOL agent;            // The agent
    private int score;            // The agent's score
    private boolean goldLooted;        // True if gold was successfuly looted
    private boolean hasArrow;        // True if the agent can shoot
    private boolean bump;            // Bump percept flag
    private boolean scream;            // Scream percept flag
    private int agentDir;        // The direction the agent is facing: 0 - right, 1 - down, 2 - left, 3 - up
    private int agentX;            // The column where the agent is located ( x-coord = col-coord )
    private int agentY;
    private int colDimension;    // The number of columns the game board has
    private int rowDimension;    // The number of rows the game board has
    private FOLTile[][] board;            // The game board
    private AgentFOL.Action lastaction;
    private Random random;
    private boolean isDead;
    private boolean pitDeath;
    private boolean wumpusDeath;


    private final int width;
    private final int height;
    private final Tile[] tiles;
    private final Explorer explorer;
    private String agentName;
    // SET FOR THE MAX NUMBER OF ITERATIONS OR MOVES THE AGENT SHOULD MAKE
    int maxSteps = 1000;

    // Change this if we want to not recreate board on reset
    private boolean repeatBoard = false;

    // Use to reorganize world to a set map
    private HashMap<Integer, Environment.Element> items = new HashMap<>();
    public int arrowCount = 0;
    private static int wumpusCount = 0;

    int startingIndex;


    /**
     * Check the width and height of board
     * Initialize tile/board and create new explorer
     *
     * @param width  width of board
     * @param height height of board
     * @throws InternalError If the board is not greater than a 3x3 or not square
     */
    public World(int width, int height) throws
            InternalError {
        /*goldLooted   = false;
		hasArrow     = true;
		bump         = false;
		scream       = false;
		score        = 0;
		agentDir     = 0;
		agentX       = 0;
		agentY       = 0;
		lastaction   = AgentFOL.Action.CLIMB;*/

        if (!(width > 3) && (height > 3)) {
            throw new InternalError("The world size must be greater than 3x3.");
        }
        if (width / height != 1) {
            throw new InternalError("The world size must be square.");
        }
        this.width = width;
        this.height = height;

        /*board = new FOLTile[width][height];
        for ( int r = 0; r < rowDimension; ++r )
				for ( int c = 0; c < colDimension; ++c )
					board[c][r] = new FOLTile();*/


        tiles = new Tile[width * height];
        for (int position = 0; position < width * height; position++) {
            tiles[position] = new Tile(position, width, height);
        }

        explorer = new Explorer(this);

    }

    /**
     * Will be used to execute the agent made through interface
     *
     */
    public int runFOL() {
        goldLooted = false;
        int stepCount = 0;
        hasArrow = true;
        bump = false;
        scream = false;
        score = 0;
        agentDir = 0;
        agentX = 0;
        agentY = 0;
        isDead = false;
        pitDeath = false;
        wumpusDeath = false;
        lastaction = AgentFOL.Action.CLIMB;
        agent = new TestAgent();
        random = new Random();
        colDimension = height;
        rowDimension = width;
        board = new FOLTile[width][height];
        for (int r = 0; r < rowDimension; ++r) {
            for (int c = 0; c < colDimension; ++c) {
                board[c][r] = new FOLTile();
            }
        }
        addFeatures();

        while (score >= -1000) {
            lastaction = agent.getAction
                    (
                            board[agentX][agentY].stench,
                            board[agentX][agentY].breeze,
                            board[agentX][agentY].gold,
                            bump,
                            scream
                    );

            // Make the move
            --score;
            bump = false;
            scream = false;

            switch (lastaction) {
                case TURN_LEFT:
                    if (--agentDir < 0) agentDir = 3;
                    stepCount++;
                    stepCount++;
                    break;

                case TURN_RIGHT:
                    if (++agentDir > 3) agentDir = 0;
                    stepCount++;
                    break;

                case FORWARD:
                    if (agentDir == 0 && agentX + 1 < colDimension)
                        ++agentX;
                    else if (agentDir == 1 && agentY - 1 >= 0)
                        --agentY;
                    else if (agentDir == 2 && agentX - 1 >= 0)
                        --agentX;
                    else if (agentDir == 3 && agentY + 1 < rowDimension)
                        ++agentY;
                    else
                        bump = true;

                    if (board[agentX][agentY].pit) {
                        pitDeath = true;
                        score -= 1000;
                        isDead = true;

                        //if (debug) printWorldInfo();
                        return score;
                    } else if (board[agentX][agentY].wumpus) {
                        wumpusDeath = true;
                        score -= 1000;
                        isDead = true;
                        return score;
                    }
                    stepCount++;
                    break;

                case SHOOT:
                    if (hasArrow) {
                        hasArrow = false;
                        score -= 10;
                        if (agentDir == 0) {
                            for (int x = agentX; x < colDimension; ++x)
                                if (board[x][agentY].wumpus) {
                                    board[x][agentY].wumpus = false;
                                    board[x][agentY].stench = true;
                                    scream = true;
                                }
                        } else if (agentDir == 1) {
                            for (int y = agentY; y >= 0; --y)
                                if (board[agentX][y].wumpus) {
                                    board[agentX][y].wumpus = false;
                                    board[agentX][y].stench = true;
                                    scream = true;
                                }
                        } else if (agentDir == 2) {
                            for (int x = agentX; x >= 0; --x)
                                if (board[x][agentY].wumpus) {
                                    board[x][agentY].wumpus = false;
                                    board[x][agentY].stench = true;
                                    scream = true;
                                }
                        } else if (agentDir == 3) {
                            for (int y = agentY; y < rowDimension; ++y)
                                if (board[agentX][y].wumpus) {
                                    board[agentX][y].wumpus = false;
                                    board[agentX][y].stench = true;
                                    scream = true;
                                }
                        }
                    }
                    stepCount++;
                    break;

                case GRAB:
                    if (board[agentX][agentY].gold) {
                        board[agentX][agentY].gold = false;
                        goldLooted = true;
                    }
                    stepCount++;
                    break;

                case CLIMB:
                    if (agentX == 0 && agentY == 0) {
                        if (goldLooted)
                            score += 1000;
                        //if (debug) printWorldInfo();
                        return score;
                    }
                    stepCount++;
                    break;
            }
        }
        return score;

    }

    private void addPit(int c, int r) {
        if (isInBounds(c, r)) {
            board[c][r].pit = true;
            addBreeze(c + 1, r);
            addBreeze(c - 1, r);
            addBreeze(c, r + 1);
            addBreeze(c, r - 1);
        }
    }

    private void addWumpus(int c, int r) {
        if (isInBounds(c, r)) {
            board[c][r].wumpus = true;
            addStench(c + 1, r);
            addStench(c - 1, r);
            addStench(c, r + 1);
            addStench(c, r - 1);
        }
    }

    private void addGold(int c, int r) {
        if (isInBounds(c, r))
            board[c][r].gold = true;
    }

    private void addStench(int c, int r) {
        if (isInBounds(c, r))
            board[c][r].stench = true;
    }

    private void addBreeze(int c, int r) {
        if (isInBounds(c, r))
            board[c][r].breeze = true;
    }

    private boolean isInBounds(int c, int r) {
        return (c < colDimension && r < rowDimension && c >= 0 && r >= 0);
    }

    public void execute(Agent agent) {
        agentName = agent.getClass().getName();

        for (Explorer explorer : run()) {
            agent.beforeAction(explorer);

            //debugging prints
            //System.out.println("Board Before:");
            //System.out.println(this.outputBoard());

            Action actions = agent.getAction(explorer);
            explorer.setAction(actions);
            agent.afterAction(explorer);

            //debugging prints
            //System.out.println("Board After:");
            //System.out.println(this.outputBoard());

        }
    }

    public int getArrowCount() {
        return arrowCount;
    }

    public void reduceArrows() {
        arrowCount--;
    }

    public static int getWumpusCount() {
        return wumpusCount;
    }

    private Player run() {
        reset();
        return new Player(this);
    }

    private void reset() {

        for (Tile tile : tiles) {
            tile.clear();
        }
        // Using an if statement decide if we would like to
        // reset set board completely or just return to explorer home,
        // Wumpus Alive, Arrows...
        // The location of items is are stored in items.keySet() for easy
        // remaking of the board
        explorer.setTile(startingIndex);
        explorer.reset();

        if (repeatBoard) {
            for (int index : items.keySet()) {
                Tile tile = getPosition(index);
                tile.setItem(items.get(index));
            }
        } else {
            wumpusCount = 0;
            setRandomBoard();
        }


    }

    /**
     * Get results of the game WIN or LOSE
     *
     * @return WIN or LOSE
     */
    public Environment.Result getFOLResults() {
        if (goldLooted == true) {
            return Environment.Result.WIN;
        }
        return Environment.Result.LOSE;
    }

    public Environment.CauseOfDeath getCauseOfFOLDeath() {
        if ((isDead == false) && (goldLooted == true)) {
            return Environment.CauseOfDeath.SAFE;
        } else if (isDead == true && wumpusDeath == true) {
            return Environment.CauseOfDeath.EATEN;
        } else if (isDead == true && pitDeath == true) {
            return Environment.CauseOfDeath.FALL;
        } else {
            return Environment.CauseOfDeath.NO_MOVES;
        }
    }

    public Environment.Result getResults() {
        if (explorer.isAlive() && explorer.hasGold()) {
            return Environment.Result.WIN;
        }
        return Environment.Result.LOSE;
    }

    public Environment.CauseOfDeath getCauseOfDeath() {
        if (explorer.isAlive() && explorer.hasGold()) {
            return Environment.CauseOfDeath.SAFE;
        } else if (explorer.isDead() && explorer.getTile().cellContains(Environment.Element.WUMPUS)) {
            return Environment.CauseOfDeath.EATEN;
        } else if (explorer.isDead() && explorer.getTile().cellContains(Element.PIT)) {
            return Environment.CauseOfDeath.FALL;
        } else {
            return Environment.CauseOfDeath.NO_MOVES;
        }
    }
    /*public ArrayList<AgentFOL.Action> getStepsTaken(){
        return stepsTaken;
    }*/
    public String outFOLFileScore(){
        String resultFOL = getFOLResults().toString();
        int folScore = runFOL();

        String results = (getFOLResults() + "," + folScore + "," + folCount + "," + getCauseOfFOLDeath());

        
        return results;
        
    }

    public int getStepsTaken() {
        return stepCount;
    }

    public String outFOLFileScore() {

        String resultFOL = getFOLResults().toString();

        int folScore = runFOL();
        int stepsTaken = getStepsTaken();

        String results = (resultFOL + "," +getCauseOfFOLDeath().toString() +","+ folScore + "," + stepsTaken);

        return results;
    }



/**
 * Set random board based on prob and random number generation
 * After wumpus,pit, and hedges are set go back through and
 * set a single gold somewhere randomly among gold cells
 * After this set the start position random
 */
private int randomInt ( int limit )
        {
        return random.nextInt(limit);
        }
private void addFeatures( )
        {
        // Generate pits
        int row;
        int col;
        for ( int r = 0; r < rowDimension; ++r )
        for ( int c = 0; c < colDimension; ++c )
        if ( (c != 0 || r != 0) && randomInt(10) < 2 )
        addPit ( c, r );
        //setPit(c,r);

        // Generate wumpus
        int wc = randomInt(colDimension);
        int wr = randomInt(rowDimension);

        while ( wc == 0 && wr == 0 )
        {
        wc = randomInt(colDimension);
        wr = randomInt(rowDimension);
        }

        addWumpus ( wc, wr );
        //setWumpus(wc, wr);

        // Generate gold
        int gc = randomInt(colDimension);
        int gr = randomInt(rowDimension);

        while ( gc == 0 && gr == 0 )
        {
        gc = randomInt(colDimension);
        gr = randomInt(rowDimension);
        }

        addGold ( gc, gr );
        //setGold(gc, gr);
        }

public void setRandomBoard() {
        System.out.println("Creating Random Board");

        int wumpusProb = 5;
        int pitProb = 5;
        int randPitProb = wumpusProb + pitProb;
        int hedgeProb = 3;
        int randHedgeProb = hedgeProb + randPitProb;
        ArrayList<Tile> emptyCells = new ArrayList<>();
        // Used for setting board with random int that will be used for the


        int topIndex = height * width;
        // On first arrival each cell should be empty
        // Using the proportions set above and a random gen int
        // Set tile to correct item
        // Keep count of each item placed for correct arrow count
        for (int tileIndex = 0; tileIndex < topIndex; tileIndex++) {

        Random random = new Random();
        int elementDecider = random.nextInt(100);
        if (elementDecider < wumpusProb) {
        System.out.println("Setting Wumpus at index: "  + tileIndex);
        setWumpus(tileIndex);
        arrowCount++;
        wumpusCount++;

        }
        else if  (elementDecider >  wumpusProb && elementDecider < randPitProb) {

        System.out.println("Setting Pit at index: " + tileIndex);
        setPit(tileIndex);

        }
        else if (randPitProb <= elementDecider && elementDecider < randHedgeProb) {

        System.out.println("Setting Hedge at: " + tileIndex);
        setHedge(tileIndex);


        }
        else if (randHedgeProb < elementDecider){
        setEmpty(tileIndex);
        emptyCells.add(tiles[tileIndex]);

        }

        }

        // After board is set
        // Go through empty cells and place a single gold somewhere
        Random randomEmptyGold = new Random();
        int indexRandomGold = randomEmptyGold.nextInt(emptyCells.size()-1);
        System.out.println("Setting Gold at index: " + indexRandomGold);
        setGold(indexRandomGold);
        emptyCells.remove(indexRandomGold);


        // Once gold is placed randomly select a starting point for the Agent
        Random randomEmptyStart = new Random();

        int indexRandomStart = randomEmptyStart.nextInt(emptyCells.size()-1);
        // Grab whatever tile from emptyCells
        startingIndex = emptyCells.get(indexRandomStart).getIndex();
        System.out.println("Setting Explorer at index: "  + startingIndex);

        explorer.setTile(startingIndex);
        System.out.println("Beginning ArrowCount = "+ arrowCount);
        System.out.println("Beginning Wumpus = "+ wumpusCount);
        //emptyCells.remove(indexRandomStart);
        }


/**
 * Used to set the location of a pit on the current world
 * @param x x coordinate
 * @param y y coordinate
 */
public void setPit(int x, int y) {
        setItem(Element.PIT, x, y);
        }
/**
 * Used to set the location of a wumpus on the current world
 * @param x x coordinate
 * @param y y coordinate
 */
public void setWumpus(int x, int y) {
        setItem(Element.WUMPUS, x, y);
        }
/**
 * Used to set the location of a wumpus on the current world
 * @param x x coordinate
 * @param y y coordinate
 */
public void setHedge(int x, int y) {
        setItem(Element.HEDGE, x, y);
        }

public void setEmpty(int x, int y) {
        setItem(Element.EMPTY, x, y);
        }
/**
 * Used to set the location of gold on the current world
 * @param x x coordinate
 * @param y y coordinate
 */
public void setGold(int x, int y) {
        setItem(Element.GOLD, x, y);
        }

public void setPit(int index) {
        setItem(Element.PIT, index);
        }

public void setWumpus(int index) {
        setItem(Element.WUMPUS, index);
        }

/**
 * Used to set the location of a wumpus on the current world
 * @param index index of location to set
 */
public void setHedge(int index) {
        setItem(Element.HEDGE, index);
        }

public void setEmpty(int index) {
        setItem(Element.EMPTY, index);
        }
/**
 * Used to set the location of gold on the current world
 * @param index Index value of location youd like element
 */
public void setGold(int index) {
        setItem(Element.GOLD, index);
        }
/**
 * Used to set the location of an item on the current world
 * Also updates items in order to create easy copying for recreating board
 * @param x x coordinate
 * @param y y coordinate
 */
public void setItem(Element element, int x, int y) {
        Tile tile = getPosition(x, y);
        tile.setItem(element);
        items.put(tile.getIndex(), element);
        }

/**
 * Used to set the location of an item on the current world
 * Also updates items in order to create easy copying for recreating board
 * @param index Index value of location you'd like element
 */
public void setItem(Element element, int index) {
        Tile tile = getPosition(index);
        tile.setItem(element);
        items.put(tile.getIndex(), element);
        }

/**
 * Get index on grid for x,y coordinates
 * @param x x coordinate
 * @param y y coordinate
 * @return Index
 */
public int getIndex(int x, int y) {
        return (x + y * width);
        }

/**
 * Get Position on grid for x,y coordinates of Tile
 * @param x x coordinate
 * @param y y coordinate
 * @return Position
 */
public Tile getPosition(int x, int y) {
        int index = getIndex(x,y);
        return tiles[index];
        }

/**
 * Get Position on grid for x,y coordinates of Tile
 * @param index The index of the tile you would like
 * @return Position
 */
public Tile getPosition(int index) {
        return tiles[index];
        }

/**
 * get the max steps available
 * @return max steps available
 */
public int getMaxSteps() {
        return maxSteps;
        }

/**
 * Return the explorer
 * @return Return the explorer
 */
public Explorer getAgent() {
        return explorer;
        }

public Environment.Result getResult() {
        if (explorer.isAlive() && explorer.hasGold()) {
        return Environment.Result.WIN;
        }
        return Environment.Result.LOSE;
        }

public String outputBoard() {
        StringBuilder render = new StringBuilder();

        for(int y = 0; y < height; y++) {
        for(int z = 0; z < 3; z++) {
        for (int x = 0; x < width; x++) {
        if (z == 0) {
        if (x == 0) render.append("+");
        render.append("-----+");
        } else {
        Tile tile = getPosition(x, y);
        String line = " 1 2 |";
        if (z == 1) {
        // Renders the second line
        if (tile.cellContains(Element.GOLD)) {
        line = line.replace("2", Environment.getIcon(Element.GOLD));
        }
        else if (tile.cellContains(Element.WUMPUS)) {
        line = line.replace("2", Environment.getIcon(Element.WUMPUS));
        }
        else if (tile.cellContains(Element.PIT)) {
        line = line.replace("2", Environment.getIcon(Element.PIT));
        }
        else if (tile.cellContains(Element.HEDGE)) {
        line = line.replace("2", Environment.getIcon(Element.HEDGE));
        }

        } else {
        if (tile.cellContains(Element.EXPLORER)) {
        line = line.replace("1", Environment.getIcon(explorer));
        }
        if (tile.cellContains(Element.GOLD)) {
        line = line.replace("2",
        Environment.getIcon(Environment.Perception.GLITTER));
        }
        // Mark this tile if some of their neighbor has some danger
        int[] neighbors = tile.getAdjacent();
        for (int i : neighbors) {
        if (i == -1) continue;
        Tile neighbor = getPosition(i);
        if (neighbor.cellContains(Element.WUMPUS)) {
        line = line.replace("2",
        Environment.getIcon(Environment.Perception.STENCH));
        }
        if (neighbor.cellContains(Element.PIT)) {
        line = line.replace("2",
        Environment.getIcon(Environment.Perception.BREEZE));
        }
        }
        }
        // Erase any non-replaced items
        line = line.replace("1", " ").replace("2", " ");
        // Draw
        if (x == 0) render.append("|");
        render.append(line);
        }
        }
        render.append("\n");
        }
        }
        for (int i = 0; i < width; i++) {
        if (i == 0) render.append("+");
        render.append("-----+");
        }
        return render.toString();
        }

public String outputScore() {
        return String.format(
        "+----------------------------+%n" +
        "| Outcome | Score    | Steps |%n" +
        "| ------- | -------- | ----- |%n" +
        "| %-7s | %8d | %5d |%n" +
        "+----------------------------+%n",
        getResult().toString(), explorer.getScore(), explorer.getActions().size()
        );
        }
public String outputFileScore() {
        //String columnNamesList = "Outcome,Score,Steps";
        //results.append(columnNamesList);
        //results.append("\n");

        return getResults().toString() +
        "," +
        getCauseOfDeath().toString() +
        "," +
        explorer.getScore() +
        "," +
        explorer.getActions().size();
        }


        }
