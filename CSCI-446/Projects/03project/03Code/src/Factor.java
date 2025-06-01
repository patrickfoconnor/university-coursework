import BayesianNetwork.*;

import java.util.*;

/**
 * Helper class for variable elimination
 */
public class Factor {
    public List<Variable> vars;
    public Map<Condition, Double> probability;

    private Factor(List<Variable> variable, Map<Condition, Double> probability){
        vars = variable;
        this.probability = probability;

    }

    public Factor(Variable variable, Condition evidence){
        vars = new ArrayList<>(variable.parents);

        vars.add(variable);
        probability = new HashMap<>(variable.probabilities);

        for(Event event:evidence){
            if(vars.contains(event.node)){
                Map<Condition, Double> probNew = new HashMap<>();

                for(Condition cond:probability.keySet()){
                    if(cond.contains(event)) {
                        probNew.put(cond, probability.get(cond));
                    }
                }
                probability = probNew;
                eliminateVariable(event.node);
            }
        }
    }
    public Double getProbability(Condition condition){
        return probability.get(condition);
    }

    public void eliminateVariable(Variable variable){
        if (!vars.remove(variable)) {
            throw new RuntimeException("This factor does not contain " + variable.name + " to eliminate.");
        }
        Map<Condition, Double> newProbability = new HashMap<>();

        for(Condition condition:Variable.getAllConditions(vars)){
            newProbability.put(condition, 0.0);
        }
        Set<Condition> conditionSet = newProbability.keySet();
        Set<Condition> pastConditionSet = probability.keySet();

        for(Condition condition: conditionSet){
            for(Condition pastCondition:pastConditionSet){
                if(pastCondition.contains(condition)){
                    newProbability.put(condition, newProbability.get(condition)+probability.get(pastCondition));
                    VariableElimination.steps++;
                }
            }
        }
        probability = newProbability;
    }
    public Factor mergeTable(Factor factor){
        //get variables
        List<Variable> newVariables;

        newVariables = new ArrayList<>(vars);
        newVariables.addAll(factor.vars);

        newVariables =new ArrayList<>(new HashSet<>(newVariables));

        //calculate joint probability
        Map<Condition,Double> newProbability;
        newProbability = new HashMap<>();
        //for(Condition condition:Variable.getAllConditions(newVariables)){
        for (Condition condition : Variable.getAllConditions(newVariables)){
            Double probabilityNum = 1.0;
            Set<Condition> conditionKeySet = factor.probability.keySet();

            for(Condition cond:conditionKeySet){
                if(condition.contains(cond)){
                    probabilityNum *= factor.getProbability(cond);
                }
            }
            Set<Condition> condKeySet = probability.keySet();
            for(Condition cond:condKeySet){
                if(condition.contains(cond)){
                    probabilityNum *= getProbability(cond);
                }
            }
            newProbability.put(condition, probabilityNum);
        }
        return new Factor(newVariables, newProbability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factor factor = (Factor) o;
        return vars.equals(factor.vars) && probability.equals(factor.probability);
    }

    public void sumToOne(){
        Double probabilitySum = 0.0;
        Collection<Double> valueList = probability.values();

        for(Double probabilityNum:valueList ){
            probabilitySum = probabilitySum + probabilityNum;
        }
        Map<Condition, Double> newProbability = new HashMap<>();

        Set<Condition> conditionKeySet = probability.keySet();

        for(Condition condition:conditionKeySet){
            Double probNum = probability.get(condition);
            Double finalNum = probNum / probabilitySum;
            newProbability.put(condition, finalNum);
        }
        probability = newProbability;
    }

}
