package wumpus;

import java.util.Iterator;

public class Player implements Iterable<Explorer>, Iterator<Explorer> {
    private final World world;
    private int iterations = 0;
    private final int maxIterations;


    public Player(World world) {
        this.world = world;
        this.maxIterations = world.getMaxSteps();
    }

    public Iterator<Explorer> iterator() {
        return this;
    }


    @Override
    public boolean hasNext() {
        Explorer explorer = world.getAgent();
        return ((iterations < maxIterations) && (world.getResults() != Environment.Result.WIN) && (explorer.isAlive()) &&
                (explorer.getLastAction() != Environment.Action.EXIT));
    }

    @Override
    public Explorer next() {
        iterations++;
        return world.getAgent();
    }
}