public class SavingAccount extends Account {
    // Attributes
    private String account_id;
    private String account_type;
    private float account_balance;
    private float savings_goal;

    // Constructors
    public SavingAccount() {
        super(); // Calls the no-argument constructor of the Account class
    }

    public SavingAccount(String routing_number, String account_id, String account_type, 
                         float account_balance, float savings_goal) {
        super(routing_number); // Calls the constructor of the Account class
        this.account_id = account_id;
        this.account_type = account_type;
        this.account_balance = account_balance;
        this.savings_goal = savings_goal;
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

    public void set_savings_goal(float savings_goal) {
        this.savings_goal = savings_goal;
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

    public float get_savings_goal() {
        return this.savings_goal;
    }
}
