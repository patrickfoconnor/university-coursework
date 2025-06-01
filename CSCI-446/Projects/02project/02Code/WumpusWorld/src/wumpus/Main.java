package wumpus;

import java.io.*;

public class Main {
    AgentFOL agent;

    public static void main(String[] args) throws IOException {
        /*
            // Create a new instances of the Agent type you want
            /*Agent agent = new RandomAgent();
            // Create a new world and execute the agent
            // Enter the board size you would like
            World world = new World(6, 6);
            world.execute(agent);

            System.out.println("Board:");
            System.out.println(world.outputBoard());
            System.out.println("Results:");
            System.out.println(world.outputScore());*/

            Agent FOLagent = new FOLAgent();
            // Create a new world and execute the agent
            // Enter the board size you would like
            World FOLworld = new World(6, 6);
            FOLworld.execute(FOLagent);

            System.out.println("Board:");
            System.out.println(FOLworld.outputBoard());
            System.out.println("Results:");
            System.out.println(reactiveWorld.outputScore());
            
            */
            //Agent FOLAgent = new FOLAgent();
            // Create a new world and execute the agent
            // Enter the board size you would like


            /*Agent reactiveAgent = new ReactiveAgent();
            //Agent reactiveAgent = new ReactiveAgent();
            // Create a new world and execute the agent
            // Enter the board size you would like
            World reactiveWorld = new World(6, 6);
            reactiveWorld.execute(reactiveAgent);

            System.out.println("Board:");
            System.out.println(reactiveWorld.outputBoard());
            System.out.println("Results:");
            System.out.println(testFOLWorld.outputScore());*/
            


        // For running 1000 trials on board size 5x5 Tiles thru 25x25 Tiles
        int boardSize = 5;
        while (boardSize <= 25)   {
            // boardSize BY boardSize World for testing
            int trialNum = 0;
            while (trialNum < 1000) {
                // Create new board
                World testWorld = new World(boardSize, boardSize);
                // execute reactive agent on board
                Agent reactiveAgent = new ReactiveAgent();
                testWorld.execute(reactiveAgent);


                String fileScoreRA = testWorld.outputFileScore();

                PrintStream raWriter = new PrintStream(
                        new FileOutputStream( new File(boardSize+"raTestResults.csv"), true));
                raWriter.println(fileScoreRA);
                raWriter.close();
                // execute on FOL agent on same board
                //World FOLWorld = new World(4, 4);
                testWorld.runFOL();
                String fileScoreFOL = testWorld.outFOLFileScore();

                PrintStream folWriter = new PrintStream(
                        new FileOutputStream(new File(boardSize+"folTestResults.csv"), true));
                folWriter.println(fileScoreFOL);
                folWriter.close();

                trialNum++;
            }
            boardSize = boardSize + 5;
        }

    }
}

