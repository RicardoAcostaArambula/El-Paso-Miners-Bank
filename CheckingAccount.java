/**
 * The {@code CheckingAccount} class extends the {@code Account} class and represents a specific type of bank account.
 * It includes attributes such as account ID, account type, and account balance.
 * This class provides methods to set and get the account details, as well as constructors for initialization.
 * @author Sebastian Nares, Ricardo Acosta
 */
public class CheckingAccount extends Account {
    // Attributes
    private String account_id;
    private String account_type;
    private float account_balance;

    /**
     * Default constructor for {@code CheckingAccount}, calls the no-argument constructor of the {@code Account} class.
     */
    public CheckingAccount() {
        super(); 
    }

    /**
     * Constructs a {@code CheckingAccount} with specified routing number, account ID, account type, and balance.
     * 
     * @param routing_number The routing number associated with the account.
     * @param account_id The unique identifier for the checking account.
     * @param account_type The type of the account (e.g., "checking").
     * @param account_balance The initial balance of the account.
     */
    public CheckingAccount(String routing_number, String account_id, String account_type, float account_balance) {
        super(routing_number); 
        this.account_id = account_id;
        this.account_type = account_type;
        this.account_balance = account_balance;
    }

    /**
     * Sets the account ID for the checking account.
     * 
     * @param account_id The account ID to be set.
     * @return {@code true} if the account ID is valid and successfully set; {@code false} otherwise.
     */
    public boolean set_account_id(String account_id) {
        if (account_id != null && !account_id.isEmpty()) {
            this.account_id = account_id;
            return true;
        }
        return false;
    }

    /**
     * Sets the account type for the checking account.
     * 
     * @param account_type The type of the account (e.g., "checking").
     */
    public void set_account_type(String account_type) {
        this.account_type = account_type;
    }

    /**
     * Sets the account balance for the checking account.
     * 
     * @param account_balance The new balance to be set.
     * @return {@code true} if the balance is non-negative and successfully set; {@code false} otherwise.
     */
    public boolean set_account_balance(float account_balance) {
        if (account_balance >= 0) {
            this.account_balance = account_balance;
            return true;
        }
        return false;
    }

    /**
     * Gets the account ID of the checking account.
     * 
     * @return The account ID as a {@code String}.
     */
    public String get_account_id() {
        return this.account_id;
    }

    /**
     * Gets the account type of the checking account.
     * 
     * @return The account type as a {@code String}.
     */
    public String get_account_type() {
        return this.account_type;
    }

    /**
     * Gets the account balance of the checking account.
     * 
     * @return The current balance as a {@code float}.
     */
    public float get_account_balance() {
        return this.account_balance;
    }
}
