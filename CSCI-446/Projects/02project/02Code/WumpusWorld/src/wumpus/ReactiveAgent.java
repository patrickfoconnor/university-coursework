package wumpus;

import wumpus.Environment.Action;
import java.util.Random;

import java.util.ArrayList;

// This is where logic of the reactive agent will go
class ReactiveAgent implements Agent {
    boolean debug = false;
    public final Random random = new Random();

    public Action getAction(Explorer explorer) {

        //agent tries to teleport away with the gold
        if (explorer.hasGold()) {
            System.out.println("The Agent Teleports Away with the gold!");
            return Action.EXIT;
        }
        //it is in the gold cell and will try to grab it
        else if (explorer.hasGlitter()) {
            return Action.GRAB;
        }
        // agent needs to turn and will pick a direction randomly
        else if (explorer.hasBump()) {
            int pDir = random.nextInt(100);
            //50% chance to turn left or turn right
            if (pDir > 50) {
                return Action.TURN_LEFT;
            } else {
                return Action.TURN_RIGHT;
            }
        }
        else {
            //everything nearby is empty and the agent will move in the direction it is looking
            if (!explorer.hasBreeze() && !explorer.hasStench()) {
                //move forward because its safe
                return Action.GO_FORWARD;
            }
            //it is probably safer to move in the direction we shot on the last action if we killed a wumpus
            //unless there are multiple
            else if (explorer.hasScream()) {
                return Action.GO_FORWARD;
            }
            //otherwise handle obstacles
            else {
                //there is a random chance the agent will decide to switch his facing before
                // dealing with danger percepts
                int pToTurn = random.nextInt(100);
                if (pToTurn > 33) {
                    return Action.TURN_LEFT;
                    } else if(pToTurn > 33 && pToTurn <= 66) {
                    return Action.TURN_RIGHT;
                }
                //if he didnt then he will handle stench and breeze percepts
                else {
                    //the agent will shoot if it has arrows and is in a square near a wumpus
                    if (explorer.hasStench() && explorer.hasArrows()) {
                        return Action.SHOOT;
                    }
                    //there is a pit adjacent square
                    //randomly walk in a direction hoping we avoid it
                    else if (explorer.hasBreeze()) {
                        // agent picks a random direction to move
                        int rDir = random.nextInt(100);
                        //1/3 chance for forward left or right
                        if (rDir <= 33) {
                            return Action.TURN_RIGHT;
                        } else if (rDir > 33 && rDir <= 66) {
                            return Action.TURN_LEFT;
                        } else {
                            return Action.GO_FORWARD;
                        }
                    }
                    //there is a pit and a wumpus near randomly decide to shoot or move
                    else {
                        Action decision;
                        int rAct = random.nextInt(100);
                        if (rAct > 50) {
                            decision = Action.SHOOT;
                        } else {
                            // agent picks a random direction to move
                            int rDir = random.nextInt(100);
                            //1/3 chance for forward left or right
                            if (rDir <= 33) {
                                decision = Action.TURN_RIGHT;
                            } else if (rDir > 33 && rDir <= 66) {
                                decision = Action.TURN_LEFT;
                            } else {
                                decision = Action.GO_FORWARD;
                            }
                        }
                        return decision;
                    }
                }
            }
        }
    }


    /**
     * Sets weather to show the debug messages or not.
     * @param value  to display messages
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
        explorer.setPerceptions();
    }

    public void afterAction(Explorer reAgent) {
        if (debug) {
            System.out.println(reAgent.getLastAction());
            //System.out.println(reAgent.getActions());
            if (reAgent.isDead()) {
                System.out.println("GAME OVER!");
            }
        }
        if (reAgent.hasGold())
        {
            System.out.println("The Agent Teleports Away with the gold!");
            reAgent.setAction(Action.EXIT);
        }
        else{
            reAgent.setPerceptions();
            //the agent is alive and will update any flags it needs to
        }
    }
}
