package BayesianNetwork;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Condition class that is used to store conditions in order to easily get probabilities
 * Utilizes the event class in list form
 */
public class Condition implements Iterable<Event>{
    private Event[] events;

    public Condition(List<Event> list) {
        Collections.sort(list);
        events = list.toArray(new Event[0]);
    }
    public boolean equals(Condition other) {
        if (events.length != other.events.length)
            return false;
        for (int i = 0; i < events.length; i++)
            if (!events[i].equals(other.events[i]))
                return false;
        return true;
    }

    public boolean equals(Object other) {
        if (other instanceof Condition)
            return equals((Condition) other);
        return false;
    }


    public boolean contains(Condition cond) {
        for (Event evt : cond.events)
            if (!contains(evt))
                return false;
        return true;
    }

    public boolean contains(Event event) {
        for (Event evt : events) {
            if (evt.equals(event))
                return true;
        }
        return false;
    }
    public boolean include(Variable variable){
        for(Event event: events){
            if(event.node.equals(variable)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int ret = 1;
        for (Event e : events)
            ret *= e.hashCode();
        return ret;

    }

    @Override
    public Iterator<Event> iterator() {
        return new ConditionIterator();
    }

    public class ConditionIterator implements Iterator<Event> {
        private int index;
        public ConditionIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < events.length;
        }

        @Override
        public Event next() {
            index++;
            return events[index - 1];
        }
    }
}
