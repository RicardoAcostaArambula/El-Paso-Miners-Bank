public class CheckingAccount extends Account {
    // Attributes
    private String account_id;
    private String account_type;
    private float account_balance;

    // Constructor, no-argument constructor of the Account class
    public CheckingAccount() {
        super(); 
    }

    public CheckingAccount(String routing_number, String account_id, String account_type, float account_balance) {
        super(routing_number); 
        this.account_id = account_id;
        this.account_type = account_type;
        this.account_balance = account_balance;
    }

    // Setters
    public boolean set_account_id(String account_id) {
        if (account_id != null && !account_id.isEmpty()) {
            this.account_id = account_id;
            return true;
        }
        return false;
    }

    public void set_account_type(String account_type) {
        this.account_type = account_type;
    }

    public boolean set_account_balance(float account_balance) {
        if (account_balance >= 0) {
            this.account_balance = account_balance;
            return true;
        }
        return false;
    }

    // Getters
    public String get_account_id() {
        return this.account_id;
    }

    public String get_account_type() {
        return this.account_type;
    }

    public float get_account_balance() {
        return this.account_balance;
    }
}
