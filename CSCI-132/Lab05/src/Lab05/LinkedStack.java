package Lab05;

/**
 * Author: Patrick O'Connor
 * Date Modified: July 29, 2020
 * Some of the code below was taken from page 233 in Data Structures & Algorithms Ed.5
 */

public class LinkedStack<E> implements Stack<E>{

    private DoublyLinkedList<E> doublyLinkedStack = new DoublyLinkedList<E>();

    /**
     *
     * @return the size of the stack
     */
    public int size()
    {
        return doublyLinkedStack.size();
    }

    public boolean isEmpty()
    /**
     * If the size is 0 return True
     * else return False
     */
    {
        if (size() == 0) {
            return true;
        }
        return false;
    }


    public void push(E element)
    /**
     * Add an element to the top of the list
     */
    {
        doublyLinkedStack.addFirst(element);
    }


    public E peek()
    /**
     * Look at the first element and return it
      */
    {
        return doublyLinkedStack.first();
    }


    public E pop()
    /**
     * Check to see if stack is empty if so, return null
     * Else assign first element to answer
     * remove the answer from the stack and
     * return the the element that was removed
     */
    {
        if (isEmpty()){
            return null;
        }

        E answer = doublyLinkedStack.first();

        doublyLinkedStack.removeFirst();

        return answer;
    }
}
