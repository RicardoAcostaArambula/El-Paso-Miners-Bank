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
    private int customerId;
    private List<Account> accounts;
    private String name;
    private String last;
    private String dob;
    private String address;
    private String phoneNumber;
    private int checkingAccountNumber;
    private float checkingAccountBalance;
    private int savingAccountNumber;
    private float savingAccountBalance;
    private int creditAccountNumber;
    private float creditAccountBalance;
    private float creditAccountMax;
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
     * @param customerId The unique identifier for the customer.
     */
    public Customer(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Constructs a {@code Customer} with a specified customer ID and list of accounts.
     * 
     * @param customerId The unique identifier for the customer.
     * @param accounts The list of accounts associated with the customer.
     */
    public Customer(int customerId, List<Account> accounts) {
        this.customerId = customerId;
        this.accounts = accounts;
    }

    /* Setters */

    /**
     * Sets the customer ID.
     * 
     * @param customerId The unique identifier for the customer.
     */
    public void setAccountId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Sets the list of accounts associated with the customer.
     * 
     * @param accounts The list of {@code Account} objects.
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    /**
     * Sets the list of email associated with the customer.
     * @param email The email of user.
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(String name) {
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
    public void setLast(String last) {
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
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the phone number of the customer.
     * 
     * @param phoneNumber The phone number of the customer.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the checking account number for the customer.
     * 
     * @param checkingAccountNumber The checking account number.
     */
    public void setCheckingAccountNumber(int checkingAccountNumber) {
        this.checkingAccountNumber = checkingAccountNumber;
    }

    /**
     * Sets the checking account balance.
     * 
     * @param checkingAccountBalance The balance of the checking account.
     */
    public void setCheckingAccountBalance(float checkingAccountBalance) {
        this.checkingAccountBalance = checkingAccountBalance;
    }

    /**
     * Sets the saving account number for the customer.
     * 
     * @param savingAccountNumber The saving account number.
     */
    public void setSavingAccountNumber(int savingAccountNumber) {
        this.savingAccountNumber = savingAccountNumber;
    }

    /**
     * Sets the saving account balance.
     * 
     * @param saving_account_balance The balance of the saving account.
     */
    public void setSavingAccountBalance(float savingAccountBalance) {
        this.savingAccountBalance = savingAccountBalance;
    }

    /**
     * Sets the credit account number for the customer.
     * 
     * @param creditAccountNumber The credit account number.
     */
    public void setCreditAccountNumber(int creditAccountNumber) {
        this.creditAccountNumber = creditAccountNumber;
    }

    /**
     * Sets the credit account balance.
     * 
     * @param creditAccountBalance The balance of the credit account.
     */
    public void setCreditAccountBalance(float creditAccountBalance) {
        this.creditAccountBalance = creditAccountBalance;
    }

    /**
     * Sets the maximum credit limit for the credit account.
     * 
     * @param creditAccountMax The maximum allowed credit limit.
     */
    public void setCreditAccountMax(float creditAccountMax) {
        this.creditAccountMax = creditAccountMax;
    }

    /* Getters */

    /**
     * Retrieves the customer ID.
     * 
     * @return The customer ID as an integer.
     */
    public int getAccountId() {
        return this.customerId;
    }

    public String getEmail(){
        return this.email;
    }

    /**
     * Retrieves the list of accounts associated with the customer.
     * 
     * @return A list of {@code Account} objects.
     */
    public List<Account> getAccounts() {
        return this.accounts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the last name of the customer.
     * 
     * @return The last name as a {@code String}.
     */
    public String getLast() {
        return this.last;
    }

    /**
     * Retrieves the date of birth of the customer.
     * 
     * @return The date of birth as a {@code String}.
     */
    public String getDob() {
        return this.dob;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAddress() {
        return this.address;
    }

    /**
     * Retrieves the phone number of the customer.
     * 
     * @return The phone number as a {@code String}.
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Retrieves the checking account number.
     * 
     * @return The checking account number as an integer.
     */
    public int getCheckingAccountNumber() {
        return this.checkingAccountNumber;
    }

    /**
     * Retrieves the checking account balance.
     * 
     * @return The balance of the checking account as a float.
     */
    public float getCheckingAccountBalance() {
        return this.checkingAccountBalance;
    }

    /**
     * Retrieves the saving account number.
     * 
     * @return The saving account number as an integer.
     */
    public int getSavingAccountNumber() {
        return this.savingAccountNumber;
    }

    /**
     * Retrieves the saving account balance.
     * 
     * @return The balance of the saving account as a float.
     */
    public float getSavingAccountBalance() {
        return this.savingAccountBalance;
    }

    /**
     * Retrieves the credit account number.
     * 
     * @return The credit account number as an integer.
     */
    public int getCreditAccountNumber() {
        return this.creditAccountNumber;
    }

    /**
     * Retrieves the credit account balance.
     * 
     * @return The balance of the credit account as a float.
     */
    public float getCreditAccountBalance() {
        return this.creditAccountBalance;
    }

    /**
     * Retrieves the maximum credit limit for the credit account.
     * 
     * @return The maximum credit limit as a float.
     */
    public float getCreditAccountMax() {
        return this.creditAccountMax;
    }
}
