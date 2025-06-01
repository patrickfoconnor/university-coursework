package BayesianNetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *  Used as a way of storing values in nodes
 */
public final class Value implements Comparable<Value>, Iterable<Value> {
    private final List<Value> valueIter = new ArrayList<>();
    private final String name;
    private final Variable variable;

    public Value(String name, Variable variable) {
        this.name = name;
        this.variable = variable;
    }

    @Override
    public int compareTo(Value val) {
        return val.name.compareTo(name);
    }

    public String name() {
        return name;
    }

    public Variable variable() {
        return variable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Value) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.variable, that.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, variable);
    }

    @Override
    public String toString() {
        return "Value[" +
                "name=" + name + ", " +
                "variable=" + variable + ']';
    }


    @Override
    public Iterator<Value> iterator() {
        return new Iterator<> () {
            private final Iterator<Value> iter = valueIter.iterator();

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public Value next() {
                return iter.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("no changes allowed");
            }
        };
    }
}
