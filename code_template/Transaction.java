/**
    - This class represents a single transaction
    - Do NOT edit the provided methods
    - The only thing you MUST add in this class is the method required by the Comparable interface
    - Comparison between two transactions is based on the fee. The higher the fee the larger the transaction (i.e. it has higher priority)
*/
public class Transaction implements Comparable<Transaction>
{
    private String sender;
    private String receiver;
    private int amount;
    private int fee;
    

    /**
     * Constructs a Transaction object with the specified attributes.
     *
     * @param sender   The sender's name or identifier.
     * @param receiver The receiver's name or identifier.
     * @param amount   The amount of the transaction.
     * @param fee      The fee associated with the transaction.
     */
    public Transaction(String sender, String receiver, int amount, int fee)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.fee = fee;
    }

    /**
     * Returns a formatted string representation of the Transaction object.
     *
     * @return A string containing sender, receiver, amount, and fee.
     */
    public String toString()
    {
        return String.format("%s %s %d %d", sender, receiver, amount, fee);
    }

    /**
     * Gets the fee associated with the transaction.
     *
     * @return The fee value.
     */
    public int getFee()
    {
        return fee;
    }
    
    /**
     * Compares this Transaction with another Transaction based on their fees.
     *
     * @param otherTransaction The Transaction to compare to.
     * @return 1 if this transaction has a higher fee, -1 if the other transaction has a higher fee, 0 if they have the same fee.
     */
    @Override
    public int compareTo(Transaction otherTransaction) {

        if(this.fee > otherTransaction.getFee())  return 1;           
        
        if(this.fee < otherTransaction.getFee()) return -1;

        return 0;        
    }
}
