package BayesianNetwork;

import java.util.*;

/**
 * Object for Random node/variable
 */
public class Variable {
    public BayesianNetwork network;
    public String name;
    public ArrayList<Variable> parents;
    public ArrayList<Variable> children;
    public HashMap<String, Value> domain;
    public HashMap<Condition, Double> probabilities;

    public Variable(String name){
        this.name = name;
        parents = new ArrayList<>();
        children = new ArrayList<>();
        domain = new HashMap<>();
        probabilities = null;
    }

    public Variable(BayesianNetwork net, String name) {
        this(name);
        network = net;
    }


    public double getProbability(String condition) {
        return probabilities.get(parseCondition(condition));
    }

    public Event parseEvent(String event){
        event = event.replaceAll("\\s+", "");
        String[] events = event.split("=",2);
        if (events.length != 2)
            System.out.println("Expected \"variable=value\", received " + event);
        if (network.hasNode(events[0]))
            return new Event(network.getNode(events[0]), events[1]);
        else
            return null;
    }

    public Condition parseCondition(String conditions) {
        conditions = conditions.replaceAll("\\s+", "");
        String[] cond = conditions.split(",");
        List<Event> conditionList = new ArrayList<>();
        for (String event : cond) {
            conditionList.add(parseEvent(event));
        }
        return new Condition(conditionList);
    }


    public void addValue(String name) {
        if (domain.containsKey(name))
            throw new RuntimeException("Value already exist");
        domain.put(name, new Value(name, this));
    }

    public void addChild(Variable child) {
        children.add(child);
    }

    public void addParent(Variable parent) {
        parents.add(parent);
    }

    public void addParent(String name) {
        addParent(network.getNode(name));
    }

    public void addProbability(String prob) {
        if (probabilities == null) {
            probabilities = new HashMap<>();

            List<Variable> varList = new ArrayList<>(parents);
            varList.add(this);

        }


        prob = prob.replaceAll("\\s+", "");

        String[] desc = prob.split(":");

        Condition condition = parseCondition(desc[0]);

        Double probability = Double.parseDouble(desc[1]);

        probabilities.put(condition, probability);
    }

    public Set<String> getDomainKeys() {
        return domain.keySet();
    }
    public Collection<Value> getDomainValues() {
        return domain.values();
    }

    public Value getValue(String name) {
        return domain.get(name);
    }


    public static List<Condition> getAllConditions(List<Variable> varList) {
        List<Condition> allConditions = new ArrayList<>();
        fillConditions(varList, allConditions, new ArrayList<>());
        return allConditions;
    }

    private static void fillConditions(List<Variable> varList, List<Condition> conditionList, List<Event> parsed) {
        if (varList.size() == parsed.size()) {
            conditionList.add(new Condition(parsed));
            return;
        }
        Variable current = varList.get(parsed.size());
        for (Value value : current.domain.values()) {
            List<Event> eventParse = new ArrayList<>(parsed);
            eventParse.add(new Event(current, value));
            fillConditions(varList, conditionList, eventParse);
        }
    }

}
