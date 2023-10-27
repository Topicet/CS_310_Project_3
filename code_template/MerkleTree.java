import java.util.Iterator;

/**
    - Represents the Merkle Tree of a single Block.
    - You must implement all the public methods in this template.
    - Anything else you add must be private.
    - Do not modify the provided signatures.
*/
public class MerkleTree
{
    /**
     * Reference to the root node.
     */
    private Node root;

    /**
     *  Height of the tree.
     */
    private int height;

    /**
     * Number of inner nodes.
     */
    private int innerNodes;

    /**
     * A node in a binary tree used for constructing a Merkle tree. Each node stores a hash code
     * and has references to its left and right children.
     */
    private static class Node implements Comparable<Node> {
        /**
         * The hash code stored in this node.
         */
        String hashCode;

        /**
         * The left child node.
         */
        Node left;

        /**
         * The right child node.
         */
        Node right;
    
        /**
         * Constructs a Node with the specified hash code and initializes its left and right children to null.
         *
         * @param hashCode The hash code to be stored in this node.
         */
        Node(String hashCode) {
            this.hashCode = hashCode;
            this.left = null;
            this.right = null;
        }
    
        /**
         * Compares this node's hash code with another node's hash code for ordering.
         *
         * @param other The node to compare to.
         * @return A negative integer, zero, or a positive integer as this node's hash code
         *         is less than, equal to, or greater than the other node's hash code.
         */
        public int compareTo(Node other) {
            return this.hashCode.compareTo(other.hashCode);
        }
    }

    /**
        - The constructor first creates the Merkle Tree in memory. This MUST be done recursively; zero points if it's not recursive!        
        - You MUST maintain a pointer to the root because the tree is built only once but it's needed many times (e.g. for traversals).
        - After the tree is constructed, the constructor sends the hash of the root to the block object by invoking the block.setRootHash() method.
        @param block is the Block that the Merkle Tree will be created for.
        TIME COMPLEXITY REQUIREMENT: O(N).
        SPACE COMPLEXITY REQUIREMENT: O(N).
    */
    public MerkleTree(Block block)
    {
        // Initialize innerNodes and height
        this.innerNodes = 0;
        this.height = 0;

        // Create the Merkle Tree and set the root
        this.root = buildTree(block);

        // Set the root hash for the block
        block.setRootHash(this.root.hashCode);
    }

    /**
     * Builds a Merkle tree from the transactions in the given block.
     *
     * @param block The block containing the transactions to build the tree from.
     * @return The root node of the Merkle tree.
     */
    private Node buildTree(Block block) {
        Iterator<Transaction> iter = block.iterator();
        int numOfTransactions = block.numOfTransactions();
        int nextPowerOfTwo = nextPowerOfTwo(numOfTransactions);

        //Quick bit manipulation to calculate the height of tree.
        this.height = 31 - Integer.numberOfLeadingZeros(nextPowerOfTwo); 

        return buildTreeHelper(iter, numOfTransactions, nextPowerOfTwo);
    }

    /**
     * Recursively builds the Merkle tree structure for a given set of transactions.
     * @param iter                 An iterator over the transactions.
     * @param remainingTransactions The number of remaining transactions to process.
     * @param totalNodes           The total number of nodes to build in the subtree.
     * @return The root node of the subtree representing the Merkle tree.
     */
    private Node buildTreeHelper(Iterator<Transaction> iter, int remainingTransactions, int totalNodes) {
        if (totalNodes == 1) {
            String hashCode;
            if (remainingTransactions > 0 && iter.hasNext()) {
                Transaction transaction = iter.next();
                hashCode = Utilities.cryptographicHashFunction(transaction.toString());
            } else {
                hashCode = Utilities.cryptographicHashFunction("DUMMY");
            }
            return new Node(hashCode);
        }        
    
        int halfNodes = totalNodes / 2;
    
        // Calculate how many real transactions should go to the left subtree
        int leftTransactions = Math.min(halfNodes, remainingTransactions);
    
        // The rest go to the right subtree
        int rightTransactions = remainingTransactions - leftTransactions;
    
        Node left = buildTreeHelper(iter, leftTransactions, halfNodes);
        Node right = buildTreeHelper(iter, rightTransactions, halfNodes);
    
        String combinedHashCode = Utilities.cryptographicHashFunction(left.hashCode, right.hashCode);
    
        
        Node parent = new Node(combinedHashCode);
        parent.left = left;
        parent.right = right;
    
        this.innerNodes++;
    
        return parent;
    }
    
    /**
     * Calculates the next power of two greater than or equal to a given integer.
     *
     * @param n The input integer.
     * @return The next power of two greater than or equal to the input integer.
     */
    private int nextPowerOfTwo(int n) {
        int count = 0;
    
        // First n in the below condition is for the case where n is 0
        if (n > 0 && (n & (n - 1)) == 0)
            return n;
    
        while( n != 0) {
            n >>= 1;
            count += 1;
        }
    
        return 1 << count;
    }

    /**
        Returns the height of the tree.
        @return the height of the tree.
        TIME COMPLEXITY REQUIREMENT: O(1).
    */
    public int height()
    {
        return this.height;
    }

    /**
        Returns the number of inner nodes.
        @return the number of inner nodes in the tree.
        TIME COMPLEXITY REQUIREMENT: O(1).
    */
    public int innerNodes()
    {
        return this.innerNodes;
    }

    /**
        Traverses the tree breadth first and returns the hash codes respectively.
        @return a list of the hash codes contained in the tree by walking the tree in a level-order.
        TIME COMPLEXITY REQUIREMENT: O(N).
    */
    public SinglyLinkedList<String> breadthFirstTraversal() {
        SinglyLinkedList<String> hashCodes = new SinglyLinkedList<>();
        SinglyLinkedList<Node> nodeQueue = new SinglyLinkedList<>(); // Using it like a queue
        
        if (root != null) {
            nodeQueue.add(root);
        }
        
        while (nodeQueue.size() > 0) {
            Node currentNode = nodeQueue.remove(0); // Dequeue the front node
            hashCodes.add(currentNode.hashCode);
            
            if (currentNode.left != null) {
                nodeQueue.add(currentNode.left); // Enqueue left child
            }
            
            if (currentNode.right != null) {
                nodeQueue.add(currentNode.right); // Enqueue right child
            }
        }
        
        return hashCodes;
    }

    /**
        Traverses the tree depth first and returns the hash codes respectively.
        @param order is an enumeration representing the three possible depth-first traversals   
        @return a list of the hash codes contained in the tree by walking the tree in a certain order             
        You MUST use recursion for this method; zero points if it's not recursive!
        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public SinglyLinkedList<String> depthFirstTraversal(Order order) {
        SinglyLinkedList<String> hashCodes = new SinglyLinkedList<>();
        depthFirstTraversalHelper(root, hashCodes, order);
        return hashCodes;
    }
    
    /**
     * Performs a depth-first traversal of the binary tree starting from the given node and
     * appends the hash codes of nodes to the provided singly-linked list based on the specified order.
     *
     * @param node  The starting node for the traversal.
     * @param list  The singly-linked list where hash codes are appended.
     * @param order The order in which nodes are visited (PREORDER, INORDER, POSTORDER).
     * 
     */
    private void depthFirstTraversalHelper(Node node, SinglyLinkedList<String> list, Order order) {
        if (node == null) {
            return;
        }
        
        switch (order) {
            case PREORDER:
                list.add(node.hashCode);
                depthFirstTraversalHelper(node.left, list, order);
                depthFirstTraversalHelper(node.right, list, order);
                break;
            case INORDER:
                depthFirstTraversalHelper(node.left, list, order);
                list.add(node.hashCode);
                depthFirstTraversalHelper(node.right, list, order);
                break;
            case POSTORDER:
                depthFirstTraversalHelper(node.left, list, order);
                depthFirstTraversalHelper(node.right, list, order);
                list.add(node.hashCode);
                break;
        }
    }

    /**
        In the example depicted in Figure 4 of the project description, the content of this list would be [F] -> [L] -> [M]
        The head of the list is the deepest hash code and the tail of the list is the top-most hash code required for the proof.
        The root hash code must NOT be added to this list because it's already stored inside each Block
        @param t The transaction we want to verify exists within the merkle tree.
        @return a list of the hash codes that are required to prove that a transaction is contained in the block that this Merkle Tree encodes.        
        You MUST use recursion for this method; zero points if it's not recursive!
        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public SinglyLinkedList<String> extractProof(Transaction t) {
        SinglyLinkedList<String> proofList = new SinglyLinkedList<>();
        extractProofHelper(root, Utilities.cryptographicHashFunction(t.toString()), proofList);
        return proofList;
    }

    /**
     * Recursively searches for a transaction hash in the binary tree starting from the given node.
     * If found, it adds the hash codes of sibling nodes along the path to the provided singly-linked list as a Merkle proof.
     *
     * @param node            The starting node for the search.
     * @param transactionHash The hash code of the transaction to search for.
     * @param proofList       The singly-linked list where sibling hash codes are added as proof.
     * @return True if the transaction hash is found, false otherwise.
     */
    private boolean extractProofHelper(Node node, String transactionHash, SinglyLinkedList<String> proofList) {
        if (node == null) {
            return false;
        }
    
        if (node.hashCode.equals(transactionHash)) {
            return true;
        }
    
        // Check the left subtree first
        if (extractProofHelper(node.left, transactionHash, proofList)) {
            if (node.right != null) {
                proofList.add(node.right.hashCode);
            }
            return true;
        }
    
        // If not found in the left, check the right subtree
        if (extractProofHelper(node.right, transactionHash, proofList)) {
            if (node.left != null) {
                proofList.add(node.left.hashCode);
            }
            return true;
        }
    
        return false;
    }

}
