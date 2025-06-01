package wumpus;

import java.util.Random;

import wumpus.Environment.Action;

/**
 * Random agent
 */
public class RandomAgent implements Agent {

    public boolean debug = false;
    public final Random random = new Random();
    public final Action[] actions = {
            Action.GO_FORWARD,
            Action.GO_FORWARD,
            Action.GO_FORWARD,
            Action.GO_FORWARD,
            Action.TURN_LEFT,
            Action.TURN_RIGHT,
            Action.SHOOT,
            Action.GRAB
    };

    /**
     * Sets whether to show the debug messages or not.
     * @param value true to display messages
     */
    public void setDebug(boolean value) {
        debug = value;
    }

    /**
     * Prints the explorer board and debug message.
     * @param explorer The explorer instance
     */
    public void beforeAction(Explorer explorer) {
        if (debug) {
            System.out.println(explorer.debug());
        }
    }

    /**
     * Prints the last action taken.
     * @param explorer The explorer instance
     */
    public void afterAction(Explorer explorer) {
        if (debug) {
            System.out.println(explorer.getLastAction());
            if (explorer.isDead()) {
                System.out.println("GAME OVER!");
            }
        }
    }

    /**
     * Implements a strategy that takes any random action.
     * @param explorer The explorer instance
     * @return The next action
     */
    public Action getAction(Explorer explorer) {
        int x = explorer.getX();
        int y = explorer.getY();
        // Feel the perceptions
        boolean bump = explorer.hasBump();
        boolean breeze = explorer.hasBreeze();
        boolean stench = explorer.hasStench();
        boolean scream = explorer.hasScream();
        boolean glitter = explorer.hasGlitter();


        return actions[random.nextInt(actions.length - 1)];
    }
}