import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public final class Utilities
{
    /**
        Reads the transactions from a text file and adds them to a priority queue
        @param pgmFile is the filename of the text file

        TIME COMPLEXITY REQUIREMENT: O(N^2) # it includes the time-compexity for calling the enqueue method
    */
    public static PriorityLine<Transaction> loadTransactions(String pgmFile)
    {
        PriorityLine<Transaction> priorityLine = new PriorityLine<>();
        
        try{
            File file = new File(pgmFile);
            Scanner scanner = new Scanner(file);


            //Read the file
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] transactionInfo = line.split(" ");

                //Store the transaction information.
                String sender = transactionInfo[0];
                String reciever = transactionInfo[1];
                int amount = Integer.parseInt(transactionInfo[2]);
                int fee = Integer.parseInt(transactionInfo[3]);

                Transaction newTransaction = new Transaction(sender, reciever, amount, fee);

                priorityLine.enqueue(newTransaction);
            }

            scanner.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return priorityLine;
    }

    /**
        @param t is the transaction that we want to verify it's contained in a certain block
        @param blockRootHash is the root hash code stored in the respective block
        @param proof is the list of hashes extracted with the method extractProof
        @return true if the transaction is verified, false otherwise
        
        TIME COMPLEXITY REQUIREMENT: O(logN)
    */
    public static boolean verifyTransaction(Transaction t, SinglyLinkedList<String> proof, String blockRootHash)
    {
        String transactionHash = cryptographicHashFunction(t.toString());

        //Reconstruct the merkle tree
        for(String proofHash : proof){
            String totalHash = transactionHash + proofHash;

            transactionHash = cryptographicHashFunction(totalHash);
        }
                
        return transactionHash.equals(blockRootHash);

    }

    /**
    **************************** DO NOT EDIT BELOW THIS LINE **************************************
    */

    /**
        SHA-256 cryptographic hash function for a single input
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
        SHA-256 cryptographic hash function for a pair of inputs
        It uses the XOR bitwise operator to merge the two hash codes
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
