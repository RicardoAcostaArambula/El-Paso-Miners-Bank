/**
 * The {@code SavingAccount} class represents a savings account with attributes such as account ID,
 * account type, account balance, and a savings goal. It provides constructors to create a savings
 * account object and getter/setter methods to access and modify the attributes.
 * 
 * @author Sebastian Nares, Ricardo Acosta
 */
public class SavingAccount extends Account {
    // Attributes
    private String accountId;
    private String accountType;
    private float accountBalance;
    private float savingsGoal;

    /**
     * Default constructor that initializes a new {@code SavingAccount} object without any attributes.
     */
    public SavingAccount() {
        super(); // Calls the no-argument constructor of the Account class
    }

    /**
     * Constructs a new {@code SavingAccount} object with the specified routing number, account ID,
     * account type, account balance, and savings goal.
     * 
     * @param routingNumber The routing number of the account.
     * @param accountId     The ID of the savings account.
     * @param accountType   The type of the account (e.g., "Savings").
     * @param accountBalance The current balance of the savings account.
     * @param savingsGoal   The savings goal for the account.
     */
    public SavingAccount(String routingNumber, String accountId, String accountType, 
                         float accountBalance, float savingsGoal) {
        super(routingNumber); // Calls the constructor of the Account class
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.savingsGoal = savingsGoal;
    }

    /* Setters */

    /**
     * Sets the account ID of the savings account.
     * 
     * @param accountId The new account ID of the savings account.
     * @return {@code true} if the account ID is successfully set, {@code false} otherwise.
     */
    public boolean setAccountId(String accountId) {
        if (accountId != null && !accountId.isEmpty()) {
            this.accountId = accountId;
            return true;
        }
        return false;
    }

    /**
     * Sets the account type of the savings account.
     * 
     * @param accountType The new type of the savings account.
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * Sets the account balance of the savings account.
     * 
     * @param accountBalance The new balance of the savings account.
     * @return {@code true} if the account balance is successfully set (must be non-negative), 
     *         {@code false} otherwise.
     */
    public boolean setAccountBalance(float accountBalance) {
        if (accountBalance >= 0) {
            this.accountBalance = accountBalance;
            return true;
        }
        return false;
    }

    /**
     * Sets the savings goal of the savings account.
     * 
     * @param savingsGoal The new savings goal for the account.
     */
    public void setSavingsGoal(float savingsGoal) {
        this.savingsGoal = savingsGoal;
    }

    /* Getters */

    /**
     * Gets the account ID of the savings account.
     * 
     * @return The account ID of the savings account.
     */
    public String getAccountId() {
        return this.accountId;
    }

    /**
     * Gets the account type of the savings account.
     * 
     * @return The account type of the savings account.
     */
    public String getAccountType() {
        return this.accountType;
    }

    /**
     * Gets the current balance of the savings account.
     * 
     * @return The account balance of the savings account.
     */
    public float getAccountBalance() {
        return this.accountBalance;
    }

    /**
     * Gets the savings goal of the savings account.
     * 
     * @return The savings goal for the account.
     */
    public float getSavingsGoal() {
        return this.savingsGoal;
    }
}
