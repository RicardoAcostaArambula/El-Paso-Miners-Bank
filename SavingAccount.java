/**
 * The {@code SavingAccount} class represents a savings account with attributes such as account ID,
 * account type, account balance, and a savings goal. It provides constructors to create a savings
 * account object and getter/setter methods to access and modify the attributes.
 * 
 * @author Sebastian Nares, Ricardo Acosta
 */
public class SavingAccount extends Account {
    // Attributes
    private String account_id;
    private String account_type;
    private float account_balance;
    private float savings_goal;

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
     * @param routing_number The routing number of the account.
     * @param account_id     The ID of the savings account.
     * @param account_type   The type of the account (e.g., "Savings").
     * @param account_balance The current balance of the savings account.
     * @param savings_goal   The savings goal for the account.
     */
    public SavingAccount(String routing_number, String account_id, String account_type, 
                         float account_balance, float savings_goal) {
        super(routing_number); // Calls the constructor of the Account class
        this.account_id = account_id;
        this.account_type = account_type;
        this.account_balance = account_balance;
        this.savings_goal = savings_goal;
    }

    /* Setters */

    /**
     * Sets the account ID of the savings account.
     * 
     * @param account_id The new account ID of the savings account.
     * @return {@code true} if the account ID is successfully set, {@code false} otherwise.
     */
    public boolean set_account_id(String account_id) {
        if (account_id != null && !account_id.isEmpty()) {
            this.account_id = account_id;
            return true;
        }
        return false;
    }

    /**
     * Sets the account type of the savings account.
     * 
     * @param account_type The new type of the savings account.
     */
    public void set_account_type(String account_type) {
        this.account_type = account_type;
    }

    /**
     * Sets the account balance of the savings account.
     * 
     * @param account_balance The new balance of the savings account.
     * @return {@code true} if the account balance is successfully set (must be non-negative), 
     *         {@code false} otherwise.
     */
    public boolean set_account_balance(float account_balance) {
        if (account_balance >= 0) {
            this.account_balance = account_balance;
            return true;
        }
        return false;
    }

    /**
     * Sets the savings goal of the savings account.
     * 
     * @param savings_goal The new savings goal for the account.
     */
    public void set_savings_goal(float savings_goal) {
        this.savings_goal = savings_goal;
    }

    /* Getters */

    /**
     * Gets the account ID of the savings account.
     * 
     * @return The account ID of the savings account.
     */
    public String get_account_id() {
        return this.account_id;
    }

    /**
     * Gets the account type of the savings account.
     * 
     * @return The account type of the savings account.
     */
    public String get_account_type() {
        return this.account_type;
    }

    /**
     * Gets the current balance of the savings account.
     * 
     * @return The account balance of the savings account.
     */
    public float get_account_balance() {
        return this.account_balance;
    }

    /**
     * Gets the savings goal of the savings account.
     * 
     * @return The savings goal for the account.
     */
    public float get_savings_goal() {
        return this.savings_goal;
    }
}
