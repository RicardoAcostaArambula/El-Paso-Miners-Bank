import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static final String LOG_FILE = "Log.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private bankDataUpdater bankDataUpdater;

    public Log() {
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            bankDataUpdater = new bankDataUpdater();
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
        // Update bank data
        bankDataUpdater.processDeposit(customer.get_account_id(), accountType, deposit_amount);
        
        // Log the transaction
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
        
        // Save updates to CSV
        bankDataUpdater.saveUpdates();
    }

    public void logWithdrawal(Customer customer, int accountType, float withdrawal_amount) {
        // Update bank data
        bankDataUpdater.processWithdrawal(customer.get_account_id(), accountType, withdrawal_amount);
        
        // Log the transaction
        String accountName = getAccountName(accountType);
        int accountNumber = getAccountNumber(customer, accountType);
        float newBalance = getAccountBalance(customer, accountType);
        
        String message = String.format("%s %s, withdrew $%.2f from %s-%d. New Balance: $%.2f",
            customer.get_name(),
            customer.get_last(),
            withdrawal_amount,
            accountName,
            accountNumber,
            newBalance
        );
        writeToLog(message);
        
        // Save updates to CSV
        bankDataUpdater.saveUpdates();
    }

    public void logTransfer(Customer customer, int sourceType, int destType, float transfer_amount) {
        // Update bank data
        bankDataUpdater.processTransfer(customer.get_account_id(), sourceType, destType, transfer_amount);
        
        // Log the transaction
        String sourceAccount = getAccountName(sourceType);
        String destAccount = getAccountName(destType);
        int sourceNumber = getAccountNumber(customer, sourceType);
        int destNumber = getAccountNumber(customer, destType);
        float sourceBalance = getAccountBalance(customer, sourceType);
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
        
        // Save updates to CSV
        bankDataUpdater.saveUpdates();
    }

    public void logPayment(Customer customer, float payment_amount) {
        // Update bank data
        bankDataUpdater.processPayment(customer.get_account_id(), payment_amount);
        
        // Log the transaction
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
        
        // Save updates to CSV
        bankDataUpdater.saveUpdates();
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