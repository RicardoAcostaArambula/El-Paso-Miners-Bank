/**
 * The {@code CheckingAccount} class extends the {@code Account} class and represents a specific type of bank account.
 * It includes attributes such as account ID, account type, and account balance.
 * This class provides methods to set and get the account details, as well as constructors for initialization.
 * @author Sebastian Nares, Ricardo Acosta
 */
public class CheckingAccount extends Account {
    // Attributes
    private String accountId;
    private String accountType;
    private float accountBalance;

    /**
     * Default constructor for {@code CheckingAccount}, calls the no-argument constructor of the {@code Account} class.
     */
    public CheckingAccount() {
        super(); 
    }

    /**
     * Constructs a {@code CheckingAccount} with specified routing number, account ID, account type, and balance.
     * 
     * @param routingNumber The routing number associated with the account.
     * @param accountId The unique identifier for the checking account.
     * @param accountType The type of the account (e.g., "checking").
     * @param accountBalance The initial balance of the account.
     */
    public CheckingAccount(String routingNumber, String accountId, String accountType, float accountBalance) {
        super(routingNumber); 
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    /**
     * Sets the account ID for the checking account.
     * 
     * @param accountId The account ID to be set.
     * @return {@code true} if the account ID is valid and successfully set; {@code false} otherwise.
     */
    public boolean setAccountId(String accountId) {
        if (accountId != null && !accountId.isEmpty()) {
            this.accountId = accountId;
            return true;
        }
        return false;
    }

    /**
     * Sets the account type for the checking account.
     * 
     * @param accountType The type of the account (e.g., "checking").
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * Sets the account balance for the checking account.
     * 
     * @param accountBalance The new balance to be set.
     * @return {@code true} if the balance is non-negative and successfully set; {@code false} otherwise.
     */
    public boolean setAccountBalance(float accountBalance) {
        if (accountBalance >= 0) {
            this.accountBalance = accountBalance;
            return true;
        }
        return false;
    }

    /**
     * Gets the account ID of the checking account.
     * 
     * @return The account ID as a {@code String}.
     */
    public String getAccountId() {
        return this.accountId;
    }

    /**
     * Gets the account type of the checking account.
     * 
     * @return The account type as a {@code String}.
     */
    public String getAccountType() {
        return this.accountType;
    }

    /**
     * Gets the account balance of the checking account.
     * 
     * @return The current balance as a {@code float}.
     */
    public float getAccountBalance() {
        return this.accountBalance;
    }
}
