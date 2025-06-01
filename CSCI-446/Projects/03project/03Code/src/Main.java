import BayesianNetwork.*;
import java.io.*;
import java.util.*;

/**
 * Main file used driving the creation of networks and queries based off assignment requirements
 * The created queries are then ran in a for loop and return the marginalized joint distributions to System Output
 */
public class Main {
    public static void main(String[] args) {
        // get choice
        String[] userFileChoice = getUserChoice();
        // Read in data and return built DAG network
        getDistributions(userFileChoice[0]);
    }

    private static BayesianNetwork createNetwork(String filename) {
        BayesianNetwork network = new BayesianNetwork();

        // read in the file
        try {
            File file=new File(".//networkBIFs//"+filename);    //creates a new file instance
            FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            //StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while((line=br.readLine())!=null)
            {
                // if variable
                if (line.startsWith("variable")){
                    //gets variable name
                    String[] s = line.split(" ");
                    String varName = s[1];

                    //advance to next line
                    line = br.readLine();

                    // gets value string and converts to a String array
                    String valuesString = line.substring(line.lastIndexOf("{")+1, line.lastIndexOf(" }"));
                    valuesString = valuesString.replaceAll(" ","");
                    String[] values = valuesString.split(",");
                    ArrayList<String> parents = new ArrayList<>();
                    ArrayList<String> probabilities = new ArrayList<>();

                    network.addNode(varName, values, parents, probabilities);
                }
                //if probability
                else if (line.startsWith("probability")){
                    //get value name from inside the ()
                    String pName = line.substring(line.lastIndexOf("(")+1, line.lastIndexOf(" )"));
                    pName = pName.replaceAll(" ","");
                    String[] nodes = pName.split("\\|");
                    String curNode = nodes[0];
                    String parentString;
                    ArrayList<String> parents = new ArrayList<>();
                    if (nodes.length>1) {
                        parentString = nodes[1];
                        String[] p = parentString.split("\\,");
                        parents.addAll(Arrays.asList(p));
                    }
                    Variable curVar = network.getNode(curNode);
                    //add any parents the node has
                    for (String parent : parents) {
                        curVar.addParent(parent);
                        network.getNode(parent).addChild(curVar);
                    }

                    //parse table probabilities
                    line = br.readLine();
                    line = line.trim();
                    if (line.startsWith("table")){
                        //gets the substring of the probabilities and splits them into a String[]
                        String probability = line.substring(8, line.lastIndexOf(";"));
                        String[] probabilities = probability.split("\\,+");

                        //Grab node variable domain
                        Set<String> pNameDomain = curVar.getDomainKeys();
                        ArrayList<String> domainArray = new ArrayList<>(pNameDomain);
                        //iterates through the table and adds the probabilities for each case
                        // Constructed Statement below
                        for (int i = 0; i < probabilities.length; i++) {
                            String probInsert = (curNode+"="+ domainArray.get(i) +":"+probabilities[i]);
                            curVar.addProbability(probInsert);
                        }
                    }
                    //parse probabilities with parents
                    else {
                        //handles the first line of the probabilities
                        line = line.replaceAll("\\s|\\;", "");
                        String[] l = line.split("\\)");
                        String parentValue = l[0].replaceAll("\\(", "");
                        String[] parentValues = parentValue.split("\\,");
                        String[] nodeProbabilities = l[1].split("\\,");
                        String parentinsert = "";
                        prepareProbStatement(curNode, parents, curVar, parentValues, nodeProbabilities, parentinsert);
                        //handles every line after till it reaches the closing brace
                        while (!(line=br.readLine()).contains("}")){
                            line = line.replaceAll("\\s|\\;", "");
                            l = line.split("\\)");
                            parentValue = l[0].replaceAll("\\(", "");
                            parentValues = parentValue.split("\\,");
                            nodeProbabilities = l[1].split("\\,");
                            parentinsert = "";
                            prepareProbStatement(curNode, parents, curVar, parentValues, nodeProbabilities, parentinsert);

                            //should be sending a list of strings in the format : "Alarm=True Burglary=True Earthquake=True : 0.95" to addProbability()
                            //so for "True, True) 0.95, 0.05;" it will send
                            //"Alarm=True Burglary=True Earthquake=True : 0.95"
                            //"Alarm=False Burglary=True Earthquake=True : 0.05"

                        }
                    }
                }
            }
            //closes the stream and release the resources
            fr.close();
        } catch(IOException e) {
            e.printStackTrace();
        }


        return network;
    }

    private static void prepareProbStatement(String curNode, ArrayList<String> parents, Variable curVar, String[] parentValues, String[] nodeProbabilities, String parentinsert) {
        StringBuilder parentInsertBuilder = new StringBuilder(parentinsert);
        for (int i = 0; i < parents.size(); i++) {
            parentValues[i] = parents.get(i) + "=" + parentValues[i];
            parentInsertBuilder.append(parentValues[i]).append(",");
        }
        parentinsert = parentInsertBuilder.toString();

        for (int i = 0; i < nodeProbabilities.length; i++) {
            Value[] curValue = curVar.getDomainValues().toArray(new Value[nodeProbabilities.length]);
            String probInsert = (curNode + "=" + curValue[i].name() +"," + parentinsert + ": " + nodeProbabilities[i]);
            curVar.addProbability(probInsert);
        }
    }


    private static String[] getUserChoice() {
        // Get filename(index 0)
        String[] userChoice = new String[3];
        System.out.println("""
                Choose the file you would like to see the marginal distribution of:
                [1] alarm.bif (Size: Medium)
                [2] child.bif (Size: Small to Medium)
                [3] hailfinder.bif (Size: Large)
                [4] insurance.bif (Size: Medium)
                [5] Win95pts.bif (Size: Large)
                """);
        Scanner reader = new Scanner(System.in);
        int fileChoice = reader.nextInt();
        switch (fileChoice) {
            case (1) -> userChoice[0] = "alarm.bif";
            case (2) -> userChoice[0] = "child.bif";
            case (3) -> userChoice[0] = "hailfinder.bif";
            case (4) -> userChoice[0] = "insurance.bif";
            case (5) -> userChoice[0] = "Win95pts.bif";
        }

        return userChoice;
    }

    private static void getDistributions(String fileName){
        //Output the distributions

        BayesianNetwork network = createNetwork(fileName);
        // Start by grabbing the appropiate report, little evidence and moderate evidence
        String[] fullScenarios = getScenarios(fileName);


        String unParsedReport = fullScenarios[0];
        // have grab the evidence and make them into string ready for parsing

        String[] reportList = unParsedReport.split(";");
        Map<String, Collection<Value>> reportDomain = new HashMap<>();
        for (String report: reportList) {

            reportDomain.put(report, network.nodes.get(report).domain.values());
        }

        List<String> evidence = new ArrayList<>(Arrays.asList(fullScenarios).subList(1, fullScenarios.length));

        // iterate through each of the report variables
        // Using network grab the domain values add them to list/array
        // Add them to array of ready to go report

        List<String> queryList = new ArrayList<>();

        for (String evid: evidence) {
            for (String reportValue: reportDomain.keySet()) {
                for (Value reportOption: reportDomain.get(reportValue)) {

                    String query = reportValue+"="+reportOption.name()+"|"+evid;
                    queryList.add(query);
                }
            }
        }

         //Iterate through and prepend them to the evidence seperated by |
        for (String indQuery:queryList) {
            System.out.println(indQuery);
            VariableElimination veInferenceMethod = new VariableElimination(network);
            String veAnswer = (veInferenceMethod.evaluate(indQuery));
            System.out.println("VE: " + veAnswer);
            System.out.println("VE steps: " + VariableElimination.steps);

            GibbsInference  gsInferenceMethod = new GibbsInference(network);
            String gSAnswer = (gsInferenceMethod.evaluate(indQuery));
            System.out.println("GS: " + gSAnswer);
            System.out.println("GS steps: " + gsInferenceMethod.steps);

            System.out.println();
        }

    }


    private static String[] getScenarios(String networkName) {
        networkName = networkName.replace(".bif", "");
        String report;
        String littleEvidence;
        String moderateEvidence;
        String[] scenarios;
        
        switch (networkName) {
            //case "earthquake" -> System.out.println("Testing on earthquake...");
                    
            case "alarm" -> {
                report = "HYPOVOLEMIA;LVFAILURE;ERRLOWOUTPUT";
                littleEvidence = "HRBP=HIGH,CO=LOW,BP=HIGH";
                moderateEvidence = "HRBP=HIGH,CO=LOW,BP=HIGH,HRSAT=LOW,HREKG=LOW,HISTORY=TRUE";
                scenarios = new String[]{report, littleEvidence, moderateEvidence};
                return scenarios;
            }

            case "child" -> {
                report = "Disease";
                littleEvidence = "LowerBodyO2=<5,RUQO2=12+,CO2Report=>=7.5,XrayReport=Asy/Patchy";
                moderateEvidence = "LowerBodyO2=<5,RUQO2=12+,CO2Report=>=7.5,XrayReport=Asy/Patchy,GruntingReport=yes,LVHreport=yes,Age=11-30_days";
                scenarios = new String[]{report, littleEvidence, moderateEvidence};
                return scenarios;
            }
            case "hailfinder" -> {
                report = "SatContMoist;LLIW";
                littleEvidence = "R5Fcst=XNIL,N34StarFcst=XNIL,MountainFcst=XNIL,AreaMoDryAir=VeryWet";
                moderateEvidence = "R5Fcst=XNIL,N34StarFcst=XNIL,MountainFcst=XNIL,AreaMoDryAir=VeryWet,CombVerMo=Down,AreaMeso_ALS=Down,CurPropConv=Strong";
                scenarios = new String[]{report, littleEvidence, moderateEvidence};
                return scenarios;
            }
            case "insurance" -> {
                report = "MedCost;ILiCost;PropCost";
                littleEvidence = "Age=Adolescent,GoodStudent=False,SeniorTrain=False,DrivQuality=Poor";
                moderateEvidence = "Age=Adolescent,GoodStudent=False,SeniorTrain=False,DrivQuality=Poor,MakeModel=Luxury,CarValue=FiftyThou,DrivHist=Zero";
                scenarios = new String[]{report, littleEvidence, moderateEvidence};
                return scenarios;
            }
            case "Win95pts" -> {
                report = "Problem1;Problem2;Problem3;Problem4;Problem5;Problem6";

                String evidence01 = "Problem1=No_Output";
                String evidence02 = "Problem2=Too_Long";
                String evidence03 = "Problem3=No";
                String evidence04 = "Problem4=No";
                String evidence05 = "Problem5=No";
                String evidence06 = "Problem6=Yes";

                scenarios = new String[]{report, evidence01, evidence02, evidence03, evidence04, evidence05, evidence06};
                return scenarios;
            }
            default -> throw new IllegalStateException("Unexpected fileName: " + networkName);
        }
    }

}
