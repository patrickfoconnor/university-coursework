package BayesianNetwork;

import java.util.*;

/**
 * The main location for creating our Bayesian Network object and the helper
 * classes associated with networks
 */
public class BayesianNetwork {
    // Map of all nodes/variables
    public Map<String, Variable> nodes;

    public BayesianNetwork() {
        nodes = new LinkedHashMap<>();
    }


    public double query(String name, String condition){
        return getNode(name).getProbability(condition);
    }


    public void addNode(String name, String[] values, ArrayList<String> parents, ArrayList<String> probabilities) {
        Variable var = new Variable(this, name);
        nodes.put(name, var);
        for (String val : values )
            var.addValue(val);
        for (String par : parents )
            var.addParent(par);
        for (String prob : probabilities )
            var.addProbability(prob);
        for (Variable child : var.parents )
            child.children.add(var);
    }

    public Variable getNode(String name) {
        if (hasNode(name)){
            return nodes.get(name);
        }
        else {
            throw new RuntimeException(name + "does not exist");
        }
    }


    public boolean hasNode(String name) {
        return nodes.containsKey(name);
    }


    public Condition parseCondition(String conditions) {
        conditions.replaceAll("\\s+", "");
        String[] cond = conditions.split(",");
        ArrayList<Event> conditionList = new ArrayList<>();
        for (String event : cond) {
            conditionList.add(parseEvent(event));
        }
        return new Condition(conditionList);
    }

    public Event parseEvent(String event) {
        event.replaceAll("//s+", "");
        String[] events = event.split("=",2);
        if (nodes.containsKey(events[0]))
            return nodes.get(events[0]).parseEvent(event);
        else {
            throw new RuntimeException(events[0] + "does not exist");
        }
    }
}
