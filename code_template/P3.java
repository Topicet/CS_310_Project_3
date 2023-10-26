/**
    DO NOT SUBMIT THIS FILE
    
    THIS IS NOT A TESTER
*/
public class P3
{
    public static void main(String[] args)
    {
        String[] args1 = {"code_template\\transactions_example____sender_receiver_amount_fee.txt","20"};

        if(args1.length != 2)
        {
            System.err.println("Usage: java P3 <filename> <cumulative fee threshold>");
            return;
        }

        PriorityLine<Transaction> pq = Utilities.loadTransactions(args1[0]);

        System.out.println("\nContents of the priority queue:");
        for(Transaction t : pq)
            System.out.println(t);

        Blockchain chain = new Blockchain(pq, Integer.parseInt(args1[1]));

        int count = 1;
        for(Block b : chain)
        {
            System.out.println(String.format("\nBlock %d:", count++));
            for (Transaction t : b)
                System.out.println(t);

            System.out.println("\nMerkle Tree:");
            MerkleTree mt = new MerkleTree(b);
            System.out.println(String.format("\nHeight: %d\nInnerNodes: %d\nRoot Hash Code: %s", mt.height(), mt.innerNodes(), b.getRootHash()));

            SinglyLinkedList<String> walk = mt.depthFirstTraversal(Order.INORDER);

            System.out.println("\nIn-order traversal of Merkle tree");
            for(String s : walk)
                System.out.println(s);

            Transaction lookup_existing = new Transaction("sender8", "receiver8", 12305, 4);
            Transaction lookup_non_existing = new Transaction("sender8", "receiver8", 12305, 5);

            System.out.println("\nExisting transaction for lookup: " + lookup_existing);
            System.out.println("\nNon-existing transaction for lookup: " + lookup_non_existing);
            
            SinglyLinkedList<String> proof = mt.extractProof(lookup_existing);
            System.out.println("\nExtracted proof of the existing transaction:");
            for(String s : proof)
                System.out.println(s);

            System.out.println("\nVerification of the existing transaction: "+Utilities.verifyTransaction(lookup_existing, proof, b.getRootHash()));
            System.out.println("\nVerification of the non-existing transaction: "+Utilities.verifyTransaction(lookup_non_existing, proof, b.getRootHash()));
        }

    }
}
