import java.util.Iterator;

/**
    - Implements a singly linked list.
    - You must implement all the public methods in this template plus the method(s) required by the Iterable interface
    - Anything else you add must be private
    - Do not modify the provided signatures
    @param <T> The generic type that defines this singlyLinkedList.
*/
public class SinglyLinkedList<T extends Comparable<T>> implements Iterable<T>
{

    /**
     * The head of the singly linked list, pointing to the first element.
     */
    private Node head;

    /**
     * The tail of the singly linked list, pointing to the last element.
     */
    private Node tail;

    /**
     * The number of elements in the singly linked list.
     */
    private int size;

    /**
     * Represents a node in the singly linked list.
     */
    private class Node {
        /**
         * Generic value that the node will store.
         */
        T value;

        /**
         * The next node in the singlyLinkedList connection.
         */
        Node next;

        /**
         * Given a node a specified generic value.
         * @param value The value desired.
         */
        Node(T value){
            this.value = value;
        }
    }

    /**
     * Constructs an empty singly linked list.
     */
    public SinglyLinkedList()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds a value to the end of the list.
     *
     * @param value The value to be added.
     *
     * @TimeComplexityRequirement O(1)
     */
    public void add(T value)
    {
        Node newNode = new Node(value);
        if(tail != null){
            tail.next = newNode;
            tail = newNode;
        }
        else{
            head = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Inserts a value into the list in descending order.
     * The list order is preserved.
     *
     * @param newValue The value to be inserted.
     *
     * @TimeComplexityRequirement O(N)
     */
    public void insert(T newValue)
    {
        Node newNode = new Node(newValue);
        if (head == null || head.value.compareTo(newValue) <= 0) {
            newNode.next = head;
            head = newNode;
            if (tail == null) {  // Update the tail if this is the first node
                tail = head;
            }
        } 

        else {
            Node temp = head;
            while (temp.next != null && temp.next.value.compareTo(newValue) > 0) {
                temp = temp.next;
            }

            if(temp.next == null){
                temp.next = newNode;
                tail = newNode;
            }
            else{
                newNode.next = temp.next;
                temp.next = newNode;
            }

        }
        size++;
    }

    /**
     * Removes a single item from the list based on its index.
     *
     * @param index The index of the item to be removed.
     * @return The removed item.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     *
     * @TimeComplexityRequirement O(N)
     */
    public T remove(int index)
    {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node temp = head;
        if (index == 0) {
            if(this.size == 1){
                T returnVal = temp.value;
                this.head = null;
                this.tail = null;
                size--;
                return returnVal;
            }
            else{
                head = head.next;
                size--;
                return temp.value;
            }
        }

        for (int i = 1; i < index; i++) {
            temp = temp.next;
        }

        if(temp.next == tail){
            T returnVal = tail.value;
            temp.next = null;
            tail = temp;
            size--;
            return returnVal;
        }
        else{
            T returnVal = temp.next.value;
            temp.next = temp.next.next;
            size--;
            return returnVal;
        }
    }

    /**
     * Returns (without removing) a single item from the list based on its index.
     *
     * @param index The index of the item to be retrieved.
     * @return The item at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     *
     * @TimeComplexityRequirement O(N)
     */
    public T get(int index)
    {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.value;
    }

    /**
     * Gets the size of the list.
     *
     * @return The number of elements in the list.
     *
     * @TimeComplexityRequirement O(1)
     */
    public int size()
    {
        return this.size;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise.
     *
     * @TimeComplexityRequirement O(1)
     */
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    /**
     * Returns an iterator to traverse the elements in the list.
     *
     * @return An iterator for the list.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
