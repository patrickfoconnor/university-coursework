package lab04;
/**
 * Author: Patrick O'Connor
 * Modified: July 22, 2020
 */

/**
 * Created get method starts on line 88
 */
public class SinglyLinkedList<E> {
	
	// ----------- nested Node class -----------
	private static class Node<E> {
		
		// fields
		private E element;		// reference to the element stored at this node
		private Node<E> next;	// reference to the subsequent node in the list
		
		// constructor
		public Node(E e, Node<E> n) {
			this.element = e;
			this.next = n;
		}
		
		// methods
		public E getElement() {
			return this.element;
		}
		public Node<E> getNext() {
			return this.next;
		}
		public void setNext(Node<E> n) {
			this.next = n;
		}

	} // ----------- end of nested Node class -----------
	
	// fields 
	private Node<E> head = null;		// head node of the list (or null if empty)
	private Node<E> tail = null;		// last node of the list (or null if empty)
	private int size = 0;				// number of nodes in the list
	
	// constructor
	SinglyLinkedList() { }				// constructs an initially empty list
	
	// methods
	public int size () {
		return this.size;
	}
	public boolean isEmpty() {
		return this.size == 0;
	}
	public E first() {						// returns (but does not remove) the first element
		if (this.isEmpty()) return null;
		return this.head.getElement();
	}
	public E last() {						// returns (but does not remove) the last element
		if (this.isEmpty()) return null;
		return this.tail.getElement();
	}
	public void addFirst(E e) {				// adds element e to the front of the list
		this.head = new Node<>(e, head);	// create and link a new node
		if (this.size == 0)
			this.tail = this.head;
		this.size ++;
	}
	public void addLast(E e) {
		Node<E> newest = new Node<>(e, null);
		if (this.isEmpty())
			this.head = newest;
		else
			this.tail.setNext(newest);
		this.tail = newest;
		this.size++;
		}

	public E removeFirst() {
		if (this.isEmpty()) return null;
		E answer = this.head.getElement();
		this.head = this.head.getNext();
		this.size--;
		if (this.size == 0)
			this.tail = null;
		return answer;
	}


	/**
	 * Set current node to the head
	 * Because we are starting at head and want to start index at 0: set count to 0
	 * Make sure we are not at the tail where are value would be null
	 * If the count == index return formatted statement
	 * 		If the length is greater than 3 presumably a movie in this project return with parenthesis
	 * 		If the length is 3 / an airport code return string without parenthesis
	 */
	public String get(int index){
		Node current = head;
		int count = 0;
		while (current != null) {
			if (count == index)

				if (current.element.toString().length() == 3)
					return current.element.toString();

				else
					return ("(" + current.element + ")");


			if (count != index){
				count++;
				current = current.next;}
			}

		return "Index out of range";
		}


	/**
	 * Create new string that starts with "("
	 * Go through each element starting with the head appending one after the other with
	 * a comma separating the list until the tail is reached
 	 * Once the tail is reached append a closing parenthesis and return string builder as String
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder("(");
		Node<E> walk = this.head;
		while (walk != null) {
			sb.append(walk.getElement());
			if (walk != this.tail)
				sb.append(", ");
			walk = walk.getNext();
		}
		sb.append(")");
		return sb.toString();
	}
}
