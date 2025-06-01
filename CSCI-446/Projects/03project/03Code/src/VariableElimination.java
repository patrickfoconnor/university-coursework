import java.util.*;

import BayesianNetwork.*;

/**
 * Variable Elimination using a reverse topological order
 */
public class VariableElimination {
    BayesianNetwork network;
    static int steps = 0;


    public VariableElimination(BayesianNetwork network){
        this.network = network;

    }

    public String evaluate(String fullQuery){
        fullQuery = fullQuery.replaceAll("\\s", "");
        String[] splitQ = fullQuery.split("\\|");
        //TODO make it so it accepts no evidence
        return evaluate(splitQ[0], splitQ[1]);
    }

    public String evaluate(String currVariable, String currCondition){
        Event goal;
        Condition condition;
        List<Variable> variableOrder;
        List<Factor> factorList;
        variableOrder = new ArrayList<>();
        factorList = new ArrayList<>();
        Factor newFactor;
        Factor resultFactor;
        Collection<Variable> variableCollection = network.nodes.values();

        // Location for setting the order of elimination
        // Something going on herre
        for(Variable variable:variableCollection){
            variableOrder.add(0, variable);
        }


        goal = network.parseEvent(currVariable);
        condition = network.parseCondition(currCondition);

        for (Variable variable : variableOrder) {
            newFactor = new Factor(variable, condition);
            factorList.add(newFactor);
            if (!(condition.include(variable)) && !(goal.node.equals(variable))) {
                Factor tempFactor = factorList.get(0);
                // if more than one fact merge on others
                for (int i = 1; i < factorList.size(); i++) {
                    tempFactor = tempFactor.mergeTable(factorList.get(i));
                }
//                long startTime = System.nanoTime();
                tempFactor.eliminateVariable(variable);
//                long endTime = System.nanoTime();
//                System.out.println("Elimination Speed"+(endTime- startTime));
//                System.out.println(variable.name);
//                long clearStartTime = System.nanoTime();
                factorList.clear();
//                System.out.println("Clearing" + variable.name);
//                long clearEndTime = System.nanoTime();
//                System.out.println("Clearing Speed"+(clearEndTime- clearStartTime));
                factorList.add(tempFactor);

            }
        }
        resultFactor = factorList.get(0);

        for(int i = 1; i < factorList.size(); i++){
            resultFactor = resultFactor.mergeTable(factorList.get(i));
        }
        resultFactor.sumToOne();
        Condition goalCondition = new Condition(Arrays.asList(goal));
        Double finalSolution = resultFactor.probability.get(goalCondition);
        return String.format("%.6f", finalSolution);
    }
}
