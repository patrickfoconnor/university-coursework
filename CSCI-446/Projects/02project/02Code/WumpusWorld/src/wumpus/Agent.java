package wumpus;

import wumpus.Environment.Action;

/**
 * Used for creating new agents whether they be Reactive or FOLAgents
 */
public interface Agent {
    Action getAction(Explorer explorer);

    void beforeAction(Explorer explorer);

    void afterAction(Explorer explorer);
}
