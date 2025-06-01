import BayesianNetwork.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * Implementation of Gibbs Sampling with a sample size of 20000
 */
public class GibbsInference {
    Random r; // random value generator
    BayesianNetwork network;
    int nSamples = 20000;
    int steps = 0;

    public GibbsInference(BayesianNetwork network){
        this.network = network;
        this.r = new Random();

    }

    public String evaluate(String cause, List<String> spec) {
        double probability;
        try {
            return String.format("%.6f", network.query(cause, merge(",", spec)));

        } catch (Exception exception) {
            Map<String, String> state = new LinkedHashMap<>();
            String queryValue = null;
            //with evidence
            List<String> evidenceNames = new ArrayList<>();
            for (String e : spec) {
                String name = e.split("=",2)[0];
                if (!name.equals(cause)) {
                    String value = e.split("=",2)[1];
                    state.put(name, value); // the evidence variables are fixed
                    evidenceNames.add(name);
                } else {
                    queryValue = e.split("=",2)[1]; // store the query value, eg. if A=T is the cause, T is query value.
                }
            }
            List<String> noEvidence = new ArrayList<>();
            for (String name : network.nodes.keySet()) {
                if (!evidenceNames.contains(name)) {
                    noEvidence.add(name);
                    Object[] tmp = network.getNode(name).domain.keySet().toArray();
                    state.put(name
                            , (String) tmp[r.nextInt(tmp.length)]); // assign random value for non-evd
                }
            }
            int count = 0;
            for (int i = 0; i < nSamples; ++i) {
                Collections.shuffle(noEvidence);									   // 2. RANDOM
                Variable var = network.getNode(noEvidence.get(0));
                String newVal = getSample(var, state);
                state.put(var.name, newVal);
                if (state.get(cause).equals(queryValue)) {
                    ++count;
                }
            }
            probability = (double) count/ nSamples;
            return String.format("%.6f", probability);
        }
    }
    public String evaluate(String query) {
        query = query.replaceAll("\\s+", "");
        String[] contents = query.split("\\|");
        List<String> evidences = new ArrayList<>();

        String cause = contents[0].substring(0, contents[0].indexOf('='));
        evidences.add(contents[0]);

        if (contents.length > 1) {
            String[] tmp = contents[1].split(",");
            Collections.addAll(evidences, tmp);
        }

        return evaluate(cause, evidences);
    }
    public void getDistribution(Map<String, Double> distribution) {
        double sum = 0.0;
        for (Double d : distribution.values()) sum += d;
        for (Entry<String, Double> entry : distribution.entrySet())

            entry.setValue(100.0 * (entry.getValue() / sum));
    }

    public double getParentProblem(Variable var, String value, Map<String, String> state){

        List<String> cond = new ArrayList<>();
        cond.add(var.name + "=" + value);
        for (Variable parent : var.parents)
            cond.add(parent.name + "=" + state.get(parent.name));
        return network.query(var.name, merge(",", cond));
    }

    public static String merge(String sep, Iterable<? extends CharSequence> list) {
        StringBuilder builder = new StringBuilder();
        Iterator<? extends CharSequence> it = list.iterator();
        while (it.hasNext()) {
            builder.append(it.next());
            if (it.hasNext())
                builder.append(sep);
        }
        return builder.toString();
    }

    public String getSample(Variable var, Map<String, String> state){
        Map<String, Double> distribution = new HashMap<>();
        for (String value : var.domain.keySet()) {
            double p = 0.0;
            p += getParentProblem(var, value, state);
            state.put(var.name, value);
            for (Variable child : var.children) {
                p *= getParentProblem(child, state.get(child.name), state);
            }
            distribution.put(value, p);
            steps++;
        }
        getDistribution(distribution);
        double i = r.nextInt(100) + r.nextDouble();
        Double product = 0.0;
        for (Entry<String, Double> ent : distribution.entrySet()) {
            product += ent.getValue();
            if (product >= i)
                return ent.getKey();
        }
        return null;
    }


}
