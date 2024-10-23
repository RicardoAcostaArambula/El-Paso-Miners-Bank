import java.util.Date;

/**
 * The {@code CreditAccount} class extends the {@code Account} class and represents a specific type of bank account
 * with a credit limit, good standing status, and payment date attributes.
 * It provides methods to set and get the details of the credit account, as well as constructors for initialization.
 * 
 * @author Sebastian Nares, Ricardo Acosta
 * */
public class CreditAccount extends Account {
    // Attributes
    private float CreditAccountLimit;
    private String goodStanding;
    private Date paymentDate;

    /**
     * Default constructor for {@code CreditAccount}, calls the no-argument constructor of the {@code Account} class.
     */
    public CreditAccount() {
        super(); 
    }

    /**
     * Constructs a {@code CreditAccount} with specified routing number, credit limit, good standing status, and payment date.
     * 
     * @param routing_number The routing number associated with the account.
     * @param CreditAccountLimit The credit limit of the account.
     * @param goodStanding The status indicating whether the account is in good standing.
     * @param paymentDate The next payment due date.
     */
    public CreditAccount(String routing_number, float CreditAccountLimit, String goodStanding, Date paymentDate) {
        // Calls the constructor of the Account class
        super(routing_number); 
        this.CreditAccountLimit = CreditAccountLimit;
        this.goodStanding = goodStanding;
        this.paymentDate = paymentDate;
    }

    /**
     * Sets the credit limit for the credit account.
     * 
     * @param CreditAccountLimit The credit limit to be set.
     */
    public void set_CreditAccount_limit(float CreditAccountLimit) {
        if (CreditAccountLimit >= 0) {
            this.CreditAccountLimit = CreditAccountLimit;
        }
    }

    /**
     * Sets the good standing status for the credit account.
     * 
     * @param goodStanding The status to be set (e.g., "Good" or "Not Good").
     * @return {@code true} if the good standing status is valid and successfully set; {@code false} otherwise.
     */
    public boolean set_good_standing(String goodStanding) {
        if (goodStanding != null && !goodStanding.isEmpty()) {
            this.goodStanding = goodStanding;
            return true;
        }
        return false;
    }

    /**
     * Sets the payment due date for the credit account.
     * 
     * @param paymentDate The new payment date to be set.
     */
    public void set_payment_date(Date paymentDate) {
        if (paymentDate != null) {
            this.paymentDate = paymentDate;
        }
    }

    /**
     * Gets the credit limit of the credit account.
     * 
     * @return The credit limit as a {@code float}.
     */
    public float get_CreditAccount_limit() {
        return this.CreditAccountLimit;
    }

    /**
     * Gets the good standing status of the credit account.
     * 
     * @return The good standing status as a {@code String}.
     */
    public String get_good_standing() {
        return this.goodStanding;
    }

    /**
     * Gets the payment due date of the credit account.
     * 
     * @return The current payment date as a {@code Date} object.
     */
    public Date get_payment_date() {
        return this.paymentDate;
    }
}
