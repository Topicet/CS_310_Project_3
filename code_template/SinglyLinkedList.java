import java.util.Iterator;

/**
    - Implements a singly linked list.
    - You must implement all the public methods in this template plus the method(s) required by the Iterable interface
    - Anything else you add must be private
    - Do not modify the provided signatures
*/
public class SinglyLinkedList<T extends Comparable<T>> implements Iterable<T>
{

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        T value;
        Node next;

        Node(T value){
            this.value = value;
        }
    }


    public SinglyLinkedList()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
        adds a value to the end of the list

        TIME COMPLEXITY REQUIREMENT: O(1)
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
        Inserts a value to the proper location in the list so that the list order is preserved (in descending order)

        TIME COMPLEXITY REQUIREMENT: O(N)
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
        Removes a single item from the list based on its index

        TIME COMPLEXITY REQUIREMENT: O(N)
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
        Returns (without removing) a single item from the list based on its index

        TIME COMPLEXITY REQUIREMENT: O(N)
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
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int size()
    {
        return this.size;
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public boolean isEmpty()
    {
        return this.size == 0;
    }

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
