/**
* The {@code Account} class represents a bank account with an account number and a balance.
* It provides methods to deposit and withdraw funds from the account.
* 
* <p>This class includes basic functionalities like adding to the account balance through deposits
* and subtracting from the balance through withdrawals, with a check for sufficient funds.</p>
* 
* @authors Sebastian Nares, Ricardo Acosta
*/
public class Account {
    private int accountNumber;
    private float balance;
    private String routing_number;
    /**
     * 
     * Constructs an {@code Account} with the specified account number and starting balance.
     *
     * @param accountNumber the unique account number associated with the account
     * @param startingBalance the initial balance of the account
     */
    // Constructor
    public Account(int accountNumber, float startingBalance, String routing_number) {
        this.accountNumber = accountNumber;
        this.balance = startingBalance;
        this.routing_number = routing_number;
    }
    public void set_routing_number(String routing_number){
        this.routing_number = routing_number;
    }
    /**
     * Returns the account number of the account.
     *
     * @return the account number
     */
    public int get_account_number() {
        return accountNumber;
    }
     /**
     * Returns the current balance of the account.
     *
     * @return the current balance
     */
    public float get_balance() {
        return balance;
    }
     /**
     * Deposits the specified amount into the account.
     *
     * @param amount the amount to deposit
     */
    public String get_routing_number(){
        return this.routing_number;
    }

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