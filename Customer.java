import java.util.List;

/**
 * The {@code Customer} class extends the {@code Person} class and represents a bank customer.
 * It includes attributes for customer identification, accounts, and personal information.
 * This class provides methods to set and retrieve customer details, including account balances.
 * 
 * @author Sebastian Nares, Ricardo Acosta
 */
public class Customer extends Person {
    /* Attributes */
    private int customer_id;
    private List<Account> accounts;
    private String name;
    private String last;
    private String dob;
    private String address;
    private String phone_number;
    private int checking_account_number;
    private float checking_account_balance;
    private int saving_account_number;
    private float saving_account_balance;
    private int credit_account_number;
    private float credit_account_balance;
    private float credit_account_max;
    private String email;

    /**
     * Default constructor for {@code Customer}.
     */
    public Customer() {
        // Default constructor
    }

    /**
     * Constructs a {@code Customer} with a specified customer ID.
     * 
     * @param customer_id The unique identifier for the customer.
     */
    public Customer(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Constructs a {@code Customer} with a specified customer ID and list of accounts.
     * 
     * @param customer_id The unique identifier for the customer.
     * @param accounts The list of accounts associated with the customer.
     */
    public Customer(int customer_id, List<Account> accounts) {
        this.customer_id = customer_id;
        this.accounts = accounts;
    }

    /* Setters */

    /**
     * Sets the customer ID.
     * 
     * @param customer_id The unique identifier for the customer.
     */
    public void set_account_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Sets the list of accounts associated with the customer.
     * 
     * @param accounts The list of {@code Account} objects.
     */
    public void set_accounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    /**
     * Sets the list of email associated with the customer.
     * @param email The email of user.
     */
    public void set_email(String email){
        this.email = email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set_name(String name) {
        if (!name.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    /**
     * Sets the last name of the customer.
     * 
     * @param last The last name of the customer.
     */
    public void set_last(String last) {
        if (!last.matches("[a-zA-Z\\s\\-]+")) {
            throw new IllegalArgumentException();
        }
        this.last = last;
    }

    /**
     * Sets the date of birth of the customer.
     * 
     * @param dob The date of birth in {@code String} format.
     */
    public void set_dob(String dob) {
        this.dob = dob;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set_address(String address) {
        this.address = address;
    }

    /**
     * Sets the phone number of the customer.
     * 
     * @param phone_number The phone number of the customer.
     */
    public void set_phone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * Sets the checking account number for the customer.
     * 
     * @param checking_account_number The checking account number.
     */
    public void set_checking_account_number(int checking_account_number) {
        this.checking_account_number = checking_account_number;
    }

    /**
     * Sets the checking account balance.
     * 
     * @param checking_account_balance The balance of the checking account.
     */
    public void set_checking_account_balance(float checking_account_balance) {
        this.checking_account_balance = checking_account_balance;
    }

    /**
     * Sets the saving account number for the customer.
     * 
     * @param saving_account_number The saving account number.
     */
    public void set_saving_account_number(int saving_account_number) {
        this.saving_account_number = saving_account_number;
    }

    /**
     * Sets the saving account balance.
     * 
     * @param saving_account_balance The balance of the saving account.
     */
    public void set_saving_account_balance(float saving_account_balance) {
        this.saving_account_balance = saving_account_balance;
    }

    /**
     * Sets the credit account number for the customer.
     * 
     * @param credit_account_number The credit account number.
     */
    public void set_credit_account_number(int credit_account_number) {
        this.credit_account_number = credit_account_number;
    }

    /**
     * Sets the credit account balance.
     * 
     * @param credit_account_balance The balance of the credit account.
     */
    public void set_credit_account_balance(float credit_account_balance) {
        this.credit_account_balance = credit_account_balance;
    }

    /**
     * Sets the maximum credit limit for the credit account.
     * 
     * @param credit_account_max The maximum allowed credit limit.
     */
    public void set_credit_account_max(float credit_account_max) {
        this.credit_account_max = credit_account_max;
    }

    /* Getters */

    /**
     * Retrieves the customer ID.
     * 
     * @return The customer ID as an integer.
     */
    public int get_account_id() {
        return this.customer_id;
    }

    public String get_email(){
        return this.email;
    }

    /**
     * Retrieves the list of accounts associated with the customer.
     * 
     * @return A list of {@code Account} objects.
     */
    public List<Account> get_accounts() {
        return this.accounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String get_name() {
        return this.name;
    }

    /**
     * Retrieves the last name of the customer.
     * 
     * @return The last name as a {@code String}.
     */
    public String get_last() {
        return this.last;
    }

    /**
     * Retrieves the date of birth of the customer.
     * 
     * @return The date of birth as a {@code String}.
     */
    public String get_dob() {
        return this.dob;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String get_address() {
        return this.address;
    }

    /**
     * Retrieves the phone number of the customer.
     * 
     * @return The phone number as a {@code String}.
     */
    public String get_phone_number() {
        return this.phone_number;
    }

    /**
     * Retrieves the checking account number.
     * 
     * @return The checking account number as an integer.
     */
    public int get_checking_account_number() {
        return this.checking_account_number;
    }

    /**
     * Retrieves the checking account balance.
     * 
     * @return The balance of the checking account as a float.
     */
    public float get_checking_account_balance() {
        return this.checking_account_balance;
    }

    /**
     * Retrieves the saving account number.
     * 
     * @return The saving account number as an integer.
     */
    public int get_saving_account_number() {
        return this.saving_account_number;
    }

    /**
     * Retrieves the saving account balance.
     * 
     * @return The balance of the saving account as a float.
     */
    public float get_saving_account_balance() {
        return this.saving_account_balance;
    }

    /**
     * Retrieves the credit account number.
     * 
     * @return The credit account number as an integer.
     */
    public int get_credit_account_number() {
        return this.credit_account_number;
    }

    /**
     * Retrieves the credit account balance.
     * 
     * @return The balance of the credit account as a float.
     */
    public float get_credit_account_balance() {
        return this.credit_account_balance;
    }

    /**
     * Retrieves the maximum credit limit for the credit account.
     * 
     * @return The maximum credit limit as a float.
     */
    public float get_credit_account_max() {
        return this.credit_account_max;
    }
}
