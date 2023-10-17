/**
    - Represents the Merkle Tree of a single Block
    - You must implement all the public methods in this template
    - Anything else you add must be private
    - Do not modify the provided signatures
*/

public class MerkleTree
{
    /**
        @param block is the Block that the Merkle Tree will be created for
        
        - The constructor first creates the Merkle Tree in memory. This MUST be done recursively; zero points if it's not recursive!
        
        - You MUST maintain a pointer to the root because the tree is built only once but it's needed many times (e.g. for traversals)

        - After the tree is constructed, the constructor sends the hash of the root to the block object by invoking the block.setRootHash() method

        TIME COMPLEXITY REQUIREMENT: O(N)
        SPACE COMPLEXITY REQUIREMENT: O(N)        
    */
    public MerkleTree(Block block)
    {
    }

    /**
        @return the height of the tree

        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int height()
    {
    }

    /**
        @return the number of inner nodes in the tree

        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int innerNodes()
    {
    }

    /**
        @return a list of the hash codes contained in the tree by walking the tree in a level-order

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public SinglyLinkedList<String> breadthFirstTraversal()
    {
    }

    /**
        @return a list of the hash codes contained in the tree by walking the tree in a certain order
        
        @param order is an enumeration representing the three possible depth-first traversals
        
        You MUST use recursion for this method; zero points if it's not recursive!

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public SinglyLinkedList<String> depthFirstTraversal(Order order)
    {
    }

    /**
        @return a list of the hash codes that are required to prove that a transaction is contained in the block that this Merkle Tree encodes.
        In the example depicted in Figure 4 of the project description, the content of this list would be [F] -> [L] -> [M]
        The head of the list is the deepest hash code and the tail of the list is the top-most hash code required for the proof.
        The root hash code must NOT be added to this list because it's already stored inside each Block
        
        You MUST use recursion for this method; zero points if it's not recursive!

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public SinglyLinkedList<String> extractProof(Transaction t)
    {
    }

}
