import java.util.List;

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

    /* Constructors */
    public Customer() {
        // Default constructor
    }

    public Customer(int customer_id) {
        this.customer_id = customer_id;
    }

    public Customer(int customer_id, List<Account> accounts) {
        this.customer_id = customer_id;
        this.accounts = accounts;
    }

    /* Setters */
    public void set_account_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void set_accounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void set_name(String name) {
        this.name = name;
    }

    public void set_last(String last) {
        this.last = last;
    }

    public void set_dob(String dob) {
        this.dob = dob;
    }

    @Override
    public void set_address(String address) {
        this.address = address;
    }

    public void set_phone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void set_checking_account_number(int checking_account_number) {
        this.checking_account_number = checking_account_number;
    }

    public void set_checking_account_balance(float checking_account_balance) {
        this.checking_account_balance = checking_account_balance;
    }

    public void set_saving_account_number(int saving_account_number) {
        this.saving_account_number = saving_account_number;
    }

    public void set_saving_account_balance(float saving_account_balance) {
        this.saving_account_balance = saving_account_balance;
    }

    public void set_credit_account_number(int credit_account_number) {
        this.credit_account_number = credit_account_number;
    }

    public void set_credit_account_balance(float credit_account_balance) {
        this.credit_account_balance = credit_account_balance;
    }

    public void set_credit_account_max(float credit_account_max) {
        this.credit_account_max = credit_account_max;
    }

    /* Getters */
    public int get_account_id() {
        return this.customer_id;
    }

    public List<Account> get_accounts() {
        return this.accounts;
    }

    @Override
    public String get_name() {
        return this.name;
    }

    public String get_last() {
        return this.last;
    }

    public String get_dob() {
        return this.dob;
    }

    @Override
    public String get_address() {
        return this.address;
    }

    public String get_phone_number() {
        return this.phone_number;
    }

    public int get_checking_account_number() {
        return this.checking_account_number;
    }

    public float get_checking_account_balance() {
        return this.checking_account_balance;
    }

    public int get_saving_account_number() {
        return this.saving_account_number;
    }

    public float get_saving_account_balance() {
        return this.saving_account_balance;
    }

    public int get_credit_account_number() {
        return this.credit_account_number;
    }

    public float get_credit_account_balance() {
        return this.credit_account_balance;
    }

    public float get_credit_account_max() {
        return this.credit_account_max;
    }
}
