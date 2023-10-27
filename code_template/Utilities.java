import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for various operations, including reading transactions from a file, verifying transactions using Merkle proofs,
 * and cryptographic hash functions.
 */
public final class Utilities
{
    /**
     * Reads the transactions from a text file and adds them to a priority queue.
     *
     * @param pgmFile is the filename of the text file.
     * @return a PriorityLine containing the transactions.
     *
     * @TimeComplexityRequirement O(N^2).
     */
    public static PriorityLine<Transaction> loadTransactions(String pgmFile)
    {
        PriorityLine<Transaction> priorityLine = new PriorityLine<>();
        
        try{
            File file = new File(pgmFile);
            Scanner scanner = new Scanner(file);
    
            // Read the file line by line
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                String[] transactionInfo = line.split(" ");
    
                // Store the transaction information.
                String sender = transactionInfo[0];
                String receiver = transactionInfo[1];
                int amount = Integer.parseInt(transactionInfo[2]);
                int fee = Integer.parseInt(transactionInfo[3]);
    
                Transaction newTransaction = new Transaction(sender, receiver, amount, fee);
    
                priorityLine.enqueue(newTransaction);
            }
    
            scanner.close();
        }
        catch(FileNotFoundException e){            
            System.out.printf("Desired file %f not found!", pgmFile);
            return null;
        }
    
        return priorityLine;
    }
    
    /**
        Verifies if a transaction is contained in a certain block using a Merkle proof.
        
        @param t is the transaction that we want to verify its presence in the block
        @param proof is the list of hashes extracted with the method extractProof
        @param blockRootHash is the root hash code stored in the respective block
        @return true if the transaction is verified, false otherwise
        
        TIME COMPLEXITY REQUIREMENT: O(logN)
    */
    public static boolean verifyTransaction(Transaction t, SinglyLinkedList<String> proof, String blockRootHash)
    {
        String transactionHash = cryptographicHashFunction(t.toString());
    
        // Reconstruct the Merkle tree using the proof
        for(String proofHash : proof){
            String totalHash = cryptographicHashFunction(transactionHash, proofHash);
            transactionHash = totalHash;
        }
                
        return transactionHash.equals(blockRootHash);
    }


    //**************************** DO NOT EDIT BELOW THIS LINE **************************************
  

    /**
     * Calculates the SHA-256 cryptographic hash for a given input string.
     *
     * @param input is the input string for which the hash will be calculated.
     * @return a hexadecimal string representing the SHA-256 hash of the input.
     */
    public static String cryptographicHashFunction(String input)
    {
        StringBuilder hexString = null;

        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            hexString = new StringBuilder(2 * encodedhash.length);
            for (int i = 0; i < encodedhash.length; i++)
            {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }

        return hexString.toString();
    }

    /**
     * Calculates the SHA-256 cryptographic hash for a pair of input strings.
     * It uses the XOR bitwise operation to merge the two hash codes.
     *
     * @param input1 is the first input string for hash calculation.
     * @param input2 is the second input string for hash calculation.
     * @return a hexadecimal string representing the SHA-256 hash of the combined inputs.
     */
    public static String cryptographicHashFunction(String input1, String input2)
    {
        StringBuilder hexString = null;

        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash1 = digest.digest(input1.getBytes(StandardCharsets.UTF_8));
            byte[] encodedhash2 = digest.digest(input2.getBytes(StandardCharsets.UTF_8));
            hexString = new StringBuilder(2 * encodedhash1.length);
            for (int i = 0; i < encodedhash1.length; i++)
            {
                String hex = Integer.toHexString(0xff & (encodedhash1[i] ^ encodedhash2[i]) );
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }

        return hexString.toString();
    }

}
