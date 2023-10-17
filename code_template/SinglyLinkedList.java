import java.util.Iterator;

/**
    - Implements a singly linked list.
    - You must implement all the public methods in this template plus the method(s) required by the Iterable interface
    - Anything else you add must be private
    - Do not modify the provided signatures
*/
public class SinglyLinkedList<T extends Comparable<T>> implements Iterable<T>
{
    public SinglyLinkedList()
    {
    }

    /**
        adds a value to the end of the list

        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public void add(T value)
    {
    }

    /**
        Inserts a value to the proper location in the list so that the list order is preserved (in descending order)

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public void insert(T newValue)
    {
    }

    /**
        Removes a single item from the list based on its index

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public T remove(int index)
    {
    }

    /**
        Returns (without removing) a single item from the list based on its index

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public T get(int index)
    {
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int size()
    {
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public boolean isEmpty()
    {
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
}
