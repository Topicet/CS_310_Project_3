import java.util.Iterator;

/**
    - Represents a single block in the blockchain
    - You must implement all the public methods in this template plus the methods required by the Comparable and Iterable interfaces
    - Anything else you add must be private
    - Do not modify the provided signatures
    - Comparison between two blocks is based on the number of transactions they contain. The block that has more transactions is considered larger
*/
public class Block implements Comparable<Block>, Iterable<Transaction>
{

    /**
     * The list of transactions stored in this block.
     */
    private SinglyLinkedList<Transaction> blockList;

    /**
     * The root hash of this block, representing the Merkle tree's root hash.
     */
    private String rootHash;

    /**
     * Constructs an empty block with no transactions and an empty root hash.
     */
    public Block()
    {
        blockList = new SinglyLinkedList<>();
        rootHash = "";
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1).
        @param t The transaction that will be added to this block.
    */
    public void addTransaction(Transaction t)
    {
        blockList.add(t);
    }

    /**
      * Returns the number of transactions in this block.
      * @return The number of transactions in the block.
      */
    public int numOfTransactions()
    {
        return this.blockList.size();
    }

    /**
     * Gets the root hash of this block, representing the Merkle tree's root hash.
     * @return The root hash of the block.
     */
    public String getRootHash()
    {
        return rootHash;
    }

    /**
     * Sets the root hash of this block to the specified hash code.
     * @param hashCode The new root hash to set.
     */
    public void setRootHash(String hashCode)
    {
        this.rootHash = hashCode;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return blockList.iterator();
    }

    @Override
    public int compareTo(Block o) {
        return Integer.compare(this.numOfTransactions(), o.numOfTransactions());
    }
}
