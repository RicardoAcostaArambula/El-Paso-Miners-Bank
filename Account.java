/**
* The {@code Account} class represents a bank account with an account number and a balance.
* It provides methods to deposit and withdraw funds from the account.
* 
* <p>This class includes basic functionalities like adding to the account balance through deposits
* and subtracting from the balance through withdrawals, with a check for sufficient funds.</p>
* 
* @author Sebastian Nares, Ricardo Acosta
*/
abstract class Account {
    private int accountNumber;
    private float balance;
    private String routingNumber;
    /**
     * 
     * Constructs an {@code Account} with the specified account number and starting balance.
     *
     * @param accountNumber the unique account number associated with the account
     * @param startingBalance the initial balance of the account
     * @param routingNumber the routing number of the account
     */
    // Constructor
    public Account() {
    	
    }
    public Account(String routingNumber) {
    	this.routingNumber = routingNumber;
    }
    public Account(int accountNumber, float startingBalance, String routingNumber) {
        this.accountNumber = accountNumber;
        this.balance = startingBalance;
        this.routingNumber = routingNumber;
    }
    public void setRoutingNumber(String routingNumber){
        this.routingNumber = routingNumber;
    }
    /**
     * Returns the account number of the account.
     *
     * @return the account number
     */
    public int getAccountNumber() {
        return accountNumber;
    }
     /**
     * Returns the current balance of the account.
     *
     * @return the current balance
     */
    public float getBalance() {
        return balance;
    }
     /**
     * Gets the routing number.
     * @return Returns the routing number
     */
    public String getRoutingNumber(){
        return this.routingNumber;
    }
    /**
     * 
     * @param amount amount being deposited to account.
     */
    public void deposit(float amount) {
        balance += amount; // Add amount to balance
    }
    /**
     * Withdraws the specified amount from the account if there are sufficient funds.
     * If the balance is less than the withdrawal amount, the operation is not performed
     * and a message is printed to indicate insufficient funds.
     *
     * @param amount the amount to withdraw
     */
    public void withdraw(float amount) {
        if (balance >= amount) {
            balance -= amount; // Subtract amount from balance
        } else {
            System.out.println("Insufficient funds for withdrawal."); // Handle insufficient funds
        }
    }
}