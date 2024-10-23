public class Account {
    private int accountNumber;
    private float balance;

    // Constructor
    public Account(int accountNumber, float startingBalance) {
        this.accountNumber = accountNumber;
        this.balance = startingBalance;
    }

    public int get_account_number() {
        return accountNumber;
    }

    public float get_balance() {
        return balance;
    }

    public void deposit(float amount) {
        balance += amount; // Add amount to balance
    }

    public void withdraw(float amount) {
        if (balance >= amount) {
            balance -= amount; // Subtract amount from balance
        } else {
            System.out.println("Insufficient funds for withdrawal."); // Handle insufficient funds
        }
    }
}