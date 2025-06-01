package wumpus;

public class Environment {

    /*--------- Begin Elements ----------------------  */
    /**
     * Elements that can be present on the board space
     */
    enum Element{
        EXPLORER,
        GOLD,
        PIT,
        HEDGE,
        EMPTY,
        WUMPUS,
        DEAD_WUMPUS
    }

    /**
     * Returns the icon for each element💰
     * @param element element that is present
     * @return icon
     */
    static String getIcon(Element element) {
        return switch (element) {
            case EXPLORER -> "@";
            case GOLD -> "$";
            case PIT -> "P";
            case EMPTY -> "E";
            case HEDGE -> "H";
            case WUMPUS -> "W";
            case DEAD_WUMPUS -> "M";
        };
    }

    /*--------- End Elements ----------------------  */


    /*--------- Begin Perceptions ----------------------  */
    /**
     * Perception that can be felt whether they
     */

    enum Perception{
        // Felt from cells adjacent to elements
        BUMP,
        BREEZE,
        GLITTER,
        STENCH,
        // Perceptions used when firing arrow
        NO_ARROWS,
        SCREAM
    }

    /**
     * Returns the icon for each perception
     * @param perception  Perception that is present
     * @return icon of the perception
     */
    static String getIcon(Perception perception) {
        return switch (perception) {
            case GLITTER -> "*";
            case STENCH -> "~";
            case BREEZE -> "≈";
            case BUMP, SCREAM, NO_ARROWS -> null;
        };
    }
    /*--------- End Perceptions ----------------------  */


    /*--------- Begin Actions ----------------------  */
    /**
     * Available actions of the game
     */
    enum Action {
        GO_FORWARD,
        TURN_LEFT,
        TURN_RIGHT,
        GRAB,
        SHOOT,
        CLIMB,
        EXIT
    }

    /**
     * Result of game
     */

    public enum Result{
        LOSE,
        WIN
    }

    public enum CauseOfDeath {
        EATEN,
        FALL,
        NO_MOVES,
        SAFE
    }

    public static int getScore(Explorer explorer) {
        int score = 0;
        if (explorer.isDead()) score += -10000;
        if (explorer.hasGold()) score += +1000000;
        if (explorer.hasScream()) score += 100;

        for (Action action : explorer.getActions()) {
            switch (action) {
                case GO_FORWARD, TURN_LEFT, TURN_RIGHT, GRAB -> score += -1;
                case SHOOT -> score += -10;
            }
        }
        return score;
    }


    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
    public static String getIcon(Explorer explorer) {
        if (explorer.isDead()) return "†";

        return switch (explorer.getDirection()) {
            case NORTH -> "↑";
            case EAST -> "→";
            case SOUTH -> "↓";
            case WEST -> "←";
        };

    }
    /*--------- End Actions/Scoring ----------------------  */

}
