import java.io.*;
import java.util.*;

public class Runner {
        //TODO a lot of stuff is static in here im wondering if this may be an issue later,
        // though its hard to say right now i think


        // Used for notifying Value Iteration Class what crashing method to use
        /**
         * Tune able Parameters
         */
        static Random r;
        static boolean crashType = false;//enable to true for a set of tests when testing R-Track.txt

        static int row;
        static int col;
        // These mean the same thing but for now have them set to different ^^^^
        static char[][] track;



        /**
         * Constant Parameters
         */
        static int minVelocity = -5;
        static int maxVelocity = 5;
        static char startState = 'S';
        static char goalState = 'F';
        static char wall = '#';
        static String restartType = "b";
        static String[][] qTrack;

        /**
         * Main driver for the project
         */
        public static class main {

            public static void main(String[] args) {
                r = new Random();
                // get choice
                String[] userFileChoice = getUserChoice();
                // Initialize Track from txt file in 2d array
                readTrack(userFileChoice[0]);
                readQTrack(userFileChoice[0]);

                // should disable crash type for maps other than R-track if crash type is on
                //can remove if we feel is unneeded
                if (!userFileChoice[0].equals("R-track.txt") && crashType) {
                    crashType = false;
                }




                //Train race car for a certain number of iterations
                // Initialize Instance of ValueIteration2

                ValueIteration1 carVI = new ValueIteration1();
                //carVI.valueIterationTrain(track, crashType, 0.0, 1);
                carVI.initializeTables(track, 0.0);
                carVI.getTrainingData();
                System.out.println("Training Complete.");

                //Initialize Instance of QLearning
                //Run each instance on the track

                new QLearning(qTrack, restartType);


                // Keeping track of
                // - number of training iterations
                // - number of steps to get to finish line
                // Will need to run each algorithm at least ten times on each track txt file
                // so an output file may be our best option for storing data

                // Once data has been collected plot the learning curve for final report

            }
        }




    /**
     * Constructor for runner, currently empty
     */
    public Runner (){

    }

        /**
         * retrieves the user's track choice to feed to readTrack() in main
         */
        private static String[] getUserChoice() {
            // Get filename(index 0)
            String[] userChoice = new String[3];
            System.out.println("""
                Choose the track you want to test on:
                [1] L-track
                [2] O-track
                [3] R-track
                """);
            Scanner reader = new Scanner(System.in);
            int fileChoice = reader.nextInt();
            switch (fileChoice) {
                case (1) -> userChoice[0] = "L-track.txt";
                case (2) -> userChoice[0] = "O-track.txt";
                case (3) -> userChoice[0] = "R-track.txt";

            }

            return userChoice;
        }

        /**
         * parses a track file and saves the data to global variables
         * track[][], row, col
         */
        private static void readTrack(String filename) {
            //TODO check to see if it is reading in the correct orientation for updatePos() and buildPath()
            // i may be iterating though it sideways or upside down in those methods, and i think it would be
            // easier to tweak how its read in to match if that's the case
            try {
                InputStream inputStream = Runner.class.getResourceAsStream("./"+filename);

                assert inputStream != null;
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));  //creates a buffering character input stream
                String line;

                //handles the line that sets the size of the track
                line = br.readLine();
                String[] sizePair = line.split(",");
                row = Integer.parseInt(sizePair[0]);
                col = Integer.parseInt(sizePair[1]);
                track = new char[row][col];

                //handles parsing the track
                for (int i = 0; i < row; i++) {
                    line=br.readLine();
                    char[] curRow = line.toCharArray();
                    track[i] = curRow;
                }
                System.out.println();

            } catch(IOException e) {
                e.printStackTrace();
            }
        }



    public static void readQTrack(String fileName){
        String line;
        int rows;
        int cols;
        //String[][] buildTrack;
        try{

            InputStream inputStream = Runner.class.getResourceAsStream("./"+fileName);
            assert inputStream != null;
            BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));

            line = br.readLine();
            rows = Integer.parseInt(line.substring(0, line.indexOf(",")));
            cols = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.length()));

            qTrack = new String[rows][cols];
            int rowCount = 0;

            while((line = br.readLine()) != null){
                for(int i = 0; i < cols; i++){
                    String charValue = String.valueOf(line.charAt(i));
                    qTrack[rowCount][i] = charValue;
                }
                rowCount++;
            }
            br.close();
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }

    }