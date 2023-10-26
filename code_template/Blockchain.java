import java.util.Iterator;

/**
    - The class represents the entire blockchain
    - You must implement all the public methods in this template plus the method(s) required by the Iterable interface
    - Anything else you add must be private
    - Do not modify the provided signatures
*/

public class Blockchain implements Iterable<Block>
{

    private SinglyLinkedList<Block> blockchainList;

    /**
        The concstructor takes a priority queue and creates the linked list of blocks

        @param threshold is the minimum amount of cumulative fees that is required to create a new block.
        The block must contain the minimum number of transactions that satisfy the threshold criterion

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public Blockchain(PriorityLine<Transaction> queue, int threshold)
    {
        blockchainList = new SinglyLinkedList<>();
        int cumulativeFees = 0;
        Block currentBlockTransactions = new Block();

        while (!queue.isEmpty()) {
            Transaction currentTransaction = queue.dequeue();
            cumulativeFees += currentTransaction.getFee();
            currentBlockTransactions.addTransaction(currentTransaction);
    
            if (cumulativeFees >= threshold) {
                blockchainList.add(currentBlockTransactions);
    
                // Reset for next block
                cumulativeFees = 0;
                currentBlockTransactions = new Block();
            }
        }
    }

    @Override
    public Iterator<Block> iterator() {
        return blockchainList.iterator();
    }

}
