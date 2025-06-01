package BayesianNetwork;

/**
 * Represent the assignment of variable and the outcome
 */
public class Event implements Comparable<Event>{
    public Value value;
    public Variable node;

    public Event(Variable node, String outcome) {
        if (node.domain.containsKey(outcome)) {
            this.node = node;
            this.value = node.domain.get(outcome);
        }
    }

    public Event(Variable node, Value outcome) {
        if (node.domain.containsValue(outcome)) {
            this.node = node;
            this.value = outcome;}

    }

    public boolean equals(Event evt) {
        return evt.node == node && evt.value == value;
    }
    public boolean equals(Object other) {
        if (other instanceof Event)
            return equals((Event) other);
        return false;
    }
    @Override
    public int compareTo(Event evt) {
        if (node == evt.node) {
            return value.compareTo(evt.value);
        }
        else {
            return node.name.compareTo(evt.node.name);
        }
    }

    @Override
    public int hashCode() {
        return node.hashCode() * value.hashCode();
    }

}
