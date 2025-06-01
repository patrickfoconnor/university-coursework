package wumpus;

import java.util.ArrayList;

import static wumpus.Environment.Direction.*;

public class Explorer {
    // Initialization
    private final World world;
    private Tile tile;
    private int x,y;
    // Holding perceptions and actions
    private final ArrayList<Environment.Perception> perceptions = new ArrayList<>();
    private final ArrayList<Environment.Action> actions = new ArrayList<>();

    // Used for determining validity of actions later
    private boolean isDead = false;
    private boolean hasGold = false;
    // Initialize value for direction and arrow count
    private Environment.Direction direction = Environment.Direction.EAST;
    private int arrowCount = World.getWumpusCount();


    public Explorer(World world) {
        this.world = world;
    }

    /**
     * Reset explorer to original starting location
     */
    public void reset() {
        arrowCount = World.getWumpusCount();
        hasGold = false;
        direction = Environment.Direction.EAST;
        actions.clear();
    }

    /**
     * Get Tile data
     *
     * @return tile
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Set tile location based on index position
     * Reset perceptions and recalculate all perceptions
     * @param index index of Explorer
     */
    public void setTile(int index) {

        if (tile != null) {
            tile.remove(Environment.Element.EXPLORER);
        }

        tile = world.getPosition(index);
        tile.setItem(Environment.Element.EXPLORER);

        x = tile.getX();
        y = tile.getY();

        isDead = (tile.cellContains(Environment.Element.WUMPUS) || tile.cellContains(Environment.Element.PIT)) ;
    }

    /**
     * Get x-coordinate for explorer
     *
     * @return x coordinate
     */
    public int getX() {
        return tile.getX();
    }

    /**
     * Get y-coordinate for explorer
     *
     * @return y coordinate
     */
    public int getY() {
        return tile.getY();
    }

    /**
     * Get direction explorer is facing
     *
     * @return direction (NORTH, EAST, SOUTH, WEST)
     */
    public Environment.Direction getDirection() {
        return direction;
    }

    /**
     * Shoot the arrow if an arrow is available
     * If a wumpus is hit SCREAM and change WUMPUS to DEAD_WUMPUS
     *
     * @return Perception SCREAM if hit and null if miss
     */
    public Environment.Perception shootArrow() {
        if (world.arrowCount >= 1) {
                world.reduceArrows();
                // Index in a North, East, South, West Manner
                int[] neighbors = getTile().getAdjacent();
                ArrayList<Integer> arrowPath = new ArrayList<>();
                Tile neighbor;

                switch (direction) {
                    case NORTH:
                        if (neighbors[0] > -1) {
                            neighbor = world.getPosition(neighbors[0]);
                            arrowPath = neighbor.getArrowPath(NORTH, neighbor);
                        }
                        break;

                    case EAST:
                        if (neighbors[1] > -1) {
                            neighbor = world.getPosition(neighbors[1]);
                            arrowPath = neighbor.getArrowPath(EAST, neighbor);
                        }
                        break;
                    case SOUTH:
                        if (neighbors[2] > -1) {
                            neighbor = world.getPosition(neighbors[2]);
                            arrowPath = neighbor.getArrowPath(SOUTH, neighbor);
                        }
                        break;
                    case WEST:
                        if (neighbors[3] > -1) {
                            neighbor = world.getPosition(neighbors[3]);
                            arrowPath = neighbor.getArrowPath(WEST, neighbor);
                        }
                        break;
                }
                // Return Scream and set Wumpus at that spot to a dead wumpus if arrow is a hit
                // Return nothing and break firing if cellContains Hedge or DeadWumpus

                for (Integer tilePath : arrowPath) {
                    if (world.getPosition(tilePath).cellContains(null) && world.getPosition(tilePath).cellContains(Environment.Element.WUMPUS)) {
                        System.out.println("Wumpus Slayed but carcass remains");
                        world.getPosition(tilePath).remove(Environment.Element.WUMPUS);
                        world.getPosition(tilePath).setItem(Environment.Element.DEAD_WUMPUS);
                        return Environment.Perception.SCREAM;
                    } else if (world.getPosition(tilePath).cellContains(null) && world.getPosition(tilePath).cellContains(Environment.Element.HEDGE)) {
                        break;
                    } else if (world.getPosition(tilePath).cellContains(null) && world.getPosition(tilePath).cellContains(Environment.Element.DEAD_WUMPUS)) {
                        break;
                    }
                }
                return null;
            }
        else {
            return Environment.Perception.NO_ARROWS;
        }
    }

    /**
     * Complete the correct action based on action inputted and which direction you are facing
     */
    void setAction(Environment.Action action) {
        actions.add(action);
        switch (action) {
            case GO_FORWARD: {
                int[] neighbors = tile.getAdjacent();
                switch (direction) {
                    case NORTH:
                        if (neighbors[0] > -1) {
                            setTile(neighbors[0]);
                        }
                        //break;

                    case EAST:
                        if (neighbors[1] > -1) {
                            setTile(neighbors[1]);
                        }
                        //break;
                    case SOUTH:
                        if (neighbors[2] > -1) {
                            setTile(neighbors[2]);
                        }
                        //break;
                    case WEST:
                        if (neighbors[3] > -1) {
                            setTile(neighbors[3]);
                        }
                        //break;
                }
                break;
            }
            case TURN_LEFT:
                switch (direction) {
                    case NORTH -> direction = Environment.Direction.WEST;
                    case EAST -> direction = Environment.Direction.NORTH;
                    case SOUTH -> direction = Environment.Direction.EAST;
                    case WEST -> direction = Environment.Direction.SOUTH;
                }
                break;
            case TURN_RIGHT:
                switch (direction) {
                    case NORTH -> direction = Environment.Direction.EAST;
                    case EAST -> direction = Environment.Direction.SOUTH;
                    case SOUTH -> direction = Environment.Direction.WEST;
                    case WEST -> direction = NORTH;
                }
                break;
            case GRAB:
                if (tile.cellContains(Environment.Element.GOLD)) {
                    tile.remove(Environment.Element.GOLD);
                    hasGold = true;
                }
            case SHOOT:
                Environment.Perception perception = shootArrow();
                if (perception != null) {
                    setPerceptions(perception);
                    break;
                }
        }
    }

    /**
     * Add perception when you know what it is
     * @param perception a perception that is felt from surrounding tiles from elements
     */
    void setPerceptions (Environment.Perception perception) {
        setPerceptions();
        perceptions.add(perception);
    }

    /**
     * Clear perceptions ArrayList
     * Check adjacent cells for elements.
     * Add perception to ArrayList perceptions if the cell adjacent contains element
     *
     */
    void setPerceptions(){
        perceptions.clear();

        if (tile.cellContains(Environment.Element.GOLD)){
            perceptions.add(Environment.Perception.GLITTER);
        }
        int[] neighbors = tile.getAdjacent();
        for (int i = 0; i < neighbors.length; i++){
            if (neighbors[i] == -1){
                if (    (i == 0 && direction == NORTH) ||
                        (i == 1 && direction == EAST) ||
                        (i == 2 && direction == SOUTH) ||
                        (i == 3 && direction == WEST)) {
                    perceptions.add(Environment.Perception.BUMP);
                }
            }   else {
                Tile neighbor = world.getPosition(neighbors[i]);
                if (neighbor.cellContains(Environment.Element.PIT)) {
                    perceptions.add(Environment.Perception.BREEZE);
                }
                else if (neighbor.cellContains(Environment.Element.WUMPUS)) {
                    perceptions.add(Environment.Perception.STENCH);
                }
            }
        }
    }


    ArrayList<Environment.Perception> getPerceptions() {
        return perceptions;
    }

    /**
     *  Check if the explorer is alive using the boolean isDead
     * @return returns true if the explorer is alive
     */
    public boolean isAlive() {
        return !isDead;
    }

    /**
     * Check if the explorer has died
     * @return returns true when explorer is dead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Check to see if the explorer has gold
     * @return true if gold has been found
     */
    public boolean hasGold() {
        return hasGold;
    }

    /**
     * check to see if the scream has been heard in cave
     * @return true if scream can be found in perceptions
     */
    public boolean hasScream() {
        return perceptions.contains(Environment.Perception.SCREAM);
    }

    /**
     * Check if the explorer has an arrow to shoot
     * @return true if there is greater than one arrow
     */
    public boolean hasArrows() {
        return (arrowCount > 0);
    }


    /**
     * Check perception ArrayList for BREEZE
     * @return true if BREEZE is "felt"
     */
    public boolean hasBreeze() {
        return perceptions.contains(Environment.Perception.BREEZE);
    }
    /**
     * Check perception ArrayList for STENCH
     * @return true if STENCH is "smelled"
     */
    public boolean hasStench() {
        return perceptions.contains(Environment.Perception.STENCH);
    }
    /**
     * Check perception ArrayList for GLITTER
     * @return true if GLITTER is "seen"
     */
    public boolean hasGlitter() {
        return perceptions.contains(Environment.Perception.GLITTER);
    }
    public boolean hasBump() {
        return perceptions.contains(Environment.Perception.BUMP);
    }

    /**
     * get the ArrayList of previous actions
     * @return ArrayList of previous actions
     */
    public ArrayList<Environment.Action> getActions() {
        return actions;
    }

    /**
     * if actions ArrayList is not empty get the last action pushed to it
     * @return the last action by explorer
     */
    public Environment.Action getLastAction() {
        if (actions.size() == 0) {
            return null;
        }
        else{
        return actions.get(actions.size()-1);
        }
    
    }

    /**
     * Get current score that has been calculated in Environment
     * @return Current score from environment
     */
    public int getScore() {
        return Environment.getScore(this);
    }

    public String outputBoard() {
        return world.outputBoard();
    }

    public String debug() {
        //Location and direction
        ArrayList<Environment.Perception> perceptionsList = getPerceptions();
        return "Index:"+this.getTile().getIndex()+ "," + this.getDirection() + ") \n" +
                // Perceptions
                "Perceptions: " + perceptionsList.toString() + "\n" +
                // Check ArrowCount
                "ArrowCount: " + world.getArrowCount() + "\n" +
                // Score
                "Score: " + getScore();
    }

}