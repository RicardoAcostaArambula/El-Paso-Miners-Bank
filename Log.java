import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static final String LOG_FILE = "transactions.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Log() {
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating log file: " + e.getMessage());
        }
    }

    private void writeToLog(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(formatter);
            fw.write("[" + timestamp + "] " + message + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public void logBalanceInquiry(Customer customer, int accountType) {
        String accountName = getAccountName(accountType);
        int accountNumber = getAccountNumber(customer, accountType);
        float balance = getAccountBalance(customer, accountType);
        
        String message = String.format("%s %s, made a balance inquiry on %s-%d. Balance: $%.2f",
            customer.get_name(),
            customer.get_last(),
            accountName,
            accountNumber,
            balance
        );
        writeToLog(message);
    }

    public void logDeposit(Customer customer, int accountType, float deposit_amount) {
        String accountName = getAccountName(accountType);
        int accountNumber = getAccountNumber(customer, accountType);
        float newBalance = getAccountBalance(customer, accountType);
        
        String message = String.format("%s %s, deposited $%.2f to %s-%d. New Balance: $%.2f",
            customer.get_name(),
            customer.get_last(),
            deposit_amount,
            accountName,
            accountNumber,
            newBalance
        );
        writeToLog(message);
    }

    public void logWithdrawal(Customer customer, int account_type, float withdrawal_amount) {
        String accountName = getAccountName(account_type);
        int accountNumber = getAccountNumber(customer, account_type);
        float newBalance = getAccountBalance(customer, account_type);
        
        String message = String.format("%s %s, withdrew $%.2f from %s-%d. New Balance: $%.2f",
            customer.get_name(),
            customer.get_last(),
            withdrawal_amount,
            accountName,
            accountNumber,
            newBalance
        );
        writeToLog(message);
    }

    public void logTransfer(Customer customer, int source_type, int destType, float transfer_amount) {
        String sourceAccount = getAccountName(source_type);
        String destAccount = getAccountName(destType);
        int sourceNumber = getAccountNumber(customer, source_type);
        int destNumber = getAccountNumber(customer, destType);
        float sourceBalance = getAccountBalance(customer, source_type);
        float destBalance = getAccountBalance(customer, destType);
        
        String message = String.format(
            "%s %s, transferred $%.2f from %s-%d to %s-%d. " +
            "Source Balance: $%.2f. Destination Balance: $%.2f",
            customer.get_name(),
            customer.get_last(),
            transfer_amount,
            sourceAccount,
            sourceNumber,
            destAccount,
            destNumber,
            sourceBalance,
            destBalance
        );
        writeToLog(message);
    }

    public void logPayment(Customer customer, float payment_amount) {
        int creditNumber = customer.get_credit_account_number();
        float newBalance = customer.get_credit_account_balance();
        
        String message = String.format("%s %s, made a payment of $%.2f on Credit-%d. New Balance: $%.2f",
            customer.get_name(),
            customer.get_last(),
            payment_amount,
            creditNumber,
            newBalance
        );
        writeToLog(message);
    }

    private String getAccountName(int accountType) {
        switch (accountType) {
            case 1: return "Checking";
            case 2: return "Savings";
            case 3: return "Credit";
            default: return "Unknown";
        }
    }

    private int getAccountNumber(Customer customer, int accountType) {
        switch (accountType) {
            case 1: return customer.get_checking_account_number();
            case 2: return customer.get_saving_account_number();
            case 3: return customer.get_credit_account_number();
            default: return 0;
        }
    }

    private float getAccountBalance(Customer customer, int accountType) {
        switch (accountType) {
            case 1: return customer.get_checking_account_balance();
            case 2: return customer.get_saving_account_balance();
            case 3: return customer.get_credit_account_balance();
            default: return 0.0f;
        }
    }
}