/**
 * Specifies the order in which nodes are traversed in a binary tree.
 */
public enum Order
{
    /**
     * PREORDER: Nodes are visited before their children (root, left, right).
     */
    PREORDER,
    /**
     * INORDER: Nodes are visited between their left and right children (left, root, right).
     */
    INORDER,
    /**
     * POSTORDER: Nodes are visited after their children (left, right, root).
     */
    POSTORDER;
}