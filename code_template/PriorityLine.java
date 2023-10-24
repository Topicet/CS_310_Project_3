import java.util.Iterator;

/**
 * Implements a priority queue.
 * You must implement all the public methods in this template plus the method(s) required by the Iterable interface.
 * Anything else you add must be private.
 * Do not modify the provided signatures.
 *
 * @param <T> The type of elements stored in the priority queue, must implement Comparable.
 */
public class PriorityLine<T extends Comparable<T>> implements Iterable<T>
{

    private SinglyLinkedList<T> priorityQueue;


    /**
     * Initializes an empty priority queue.
     */
    public PriorityLine()
    {
        this.priorityQueue = new SinglyLinkedList<T>();
    }

    /**
     * Inserts an element into the priority queue.
     *
     * @param element The element to be inserted.
     * @implNote This operation has a time complexity of O(N).
     */
    public void enqueue(T element)
    {
        if(element!=null)   this.priorityQueue.insert(element);
    }


    /**
     * Removes and returns the highest-priority element from the priority queue.
     *
     * @return The highest-priority element, or null if the priority queue is empty.
     * @implNote This operation has a time complexity of O(1).
     */
    public T dequeue()
    {
        if(this.priorityQueue.isEmpty()) return null;

        return this.priorityQueue.remove(0);
    }

    /**
     * Returns the number of elements in the priority queue.
     *
     * @return The number of elements.
     * @implNote This operation has a time complexity of O(1).
     */
    public int size()
    {
        return this.priorityQueue.size();
    }

    /**
     * Checks if the priority queue is empty.
     *
     * @return True if the priority queue is empty, false otherwise.
     * @implNote This operation has a time complexity of O(1).
     */
    public boolean isEmpty()
    {
        return this.priorityQueue.size() == 0;
    }

    /**
     * Returns the highest-priority element in the priority queue without removing it.
     *
     * @return The highest-priority element, or null if the priority queue is empty.
     * @implNote This operation has a time complexity of O(1).
     */
    public T peek()
    {
        if (this.priorityQueue.isEmpty()) {
            return null;  // or throw an exception, depending on your needs
        }
        return this.priorityQueue.get(0);
    }

    /**
     * Returns an iterator over the elements in the priority queue.
     *
     * @return An iterator over the elements.
     */
    @Override
    public Iterator<T> iterator() {
        return this.priorityQueue.iterator();
    }

}
