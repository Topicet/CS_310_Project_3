import java.util.Iterator;

/**
    - Represents the Merkle Tree of a single Block
    - You must implement all the public methods in this template
    - Anything else you add must be private
    - Do not modify the provided signatures
*/

public class MerkleTree
{

    private Node root; // Reference to the root node
    private int height; // Height of the tree
    private int innerNodes; // Number of inner nodes

    private static class Node implements Comparable<Node>  {
        String hashCode; // The hash code stored in this node
        Node left; // Left child
        Node right; // Right child

        Node(String hashCode) {
            this.hashCode = hashCode;
            this.left = null;
            this.right = null;
        }

        public int compareTo(Node other) {
            return this.hashCode.compareTo(other.hashCode);
        }
    }

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
            // Initialize innerNodes and height
            this.innerNodes = 0;
            this.height = 0;
    
            // Create the Merkle Tree and set the root
            this.root = buildTree(block);
    
            // Set the root hash for the block
            block.setRootHash(this.root.hashCode);
    }

    private Node buildTree(Block block) {
        Iterator<Transaction> iter = block.iterator();
        int numOfTransactions = block.numOfTransactions();
        int nextPowerOfTwo = nextPowerOfTwo(numOfTransactions);

        //Quick bit manipulation to calculate the height of tree.
        this.height = 31 - Integer.numberOfLeadingZeros(nextPowerOfTwo); 

        return buildTreeHelper(iter, numOfTransactions, nextPowerOfTwo);
    }

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
    
    // Function to find the next power of two greater than or equal to n
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
        @return the height of the tree
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int height()
    {
        return this.height;
    }

    /**
        @return the number of inner nodes in the tree
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int innerNodes()
    {
        return this.innerNodes;
    }

    /**
        @return a list of the hash codes contained in the tree by walking the tree in a level-order
        TIME COMPLEXITY REQUIREMENT: O(N)
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
        @return a list of the hash codes contained in the tree by walking the tree in a certain order        
        @param order is an enumeration representing the three possible depth-first traversals        
        You MUST use recursion for this method; zero points if it's not recursive!
        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public SinglyLinkedList<String> depthFirstTraversal(Order order) {
        SinglyLinkedList<String> hashCodes = new SinglyLinkedList<>();
        depthFirstTraversalHelper(root, hashCodes, order);
        return hashCodes;
    }
    
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
        @return a list of the hash codes that are required to prove that a transaction is contained in the block that this Merkle Tree encodes.
        In the example depicted in Figure 4 of the project description, the content of this list would be [F] -> [L] -> [M]
        The head of the list is the deepest hash code and the tail of the list is the top-most hash code required for the proof.
        The root hash code must NOT be added to this list because it's already stored inside each Block
        
        You MUST use recursion for this method; zero points if it's not recursive!

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public SinglyLinkedList<String> extractProof(Transaction t) {
        SinglyLinkedList<String> proofList = new SinglyLinkedList<>();
        extractProofHelper(root, Utilities.cryptographicHashFunction(t.toString()), proofList);
        return proofList;
    }
    
    String hashCodeLeft = "";
    String hashCodeRight = "";
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
