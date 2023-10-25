import java.util.Iterator;

import javax.swing.SingleSelectionModel;

/**
    - Represents a single block in the blockchain
    - You must implement all the public methods in this template plus the methods required by the Comparable and Iterable interfaces
    - Anything else you add must be private
    - Do not modify the provided signatures
    - Comparison between two blocks is based on the number of transactions they contain. The block that has more transactions is considered larger
*/

public class Block implements Comparable<Block>, Iterable<Transaction>
{
    private SinglyLinkedList<Transaction> blockList;
    private String rootHash;

    public Block()
    {
        blockList = new SinglyLinkedList<>();
        rootHash = "";
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public void addTransaction(Transaction t)
    {
        blockList.add(t);
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int numOfTransactions()
    {
        return this.blockList.size();
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public String getRootHash()
    {
        return rootHash;
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
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
