import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * The {@code Log} class is responsible for logging various banking transactions including balance inquiries, 
 * deposits, withdrawals, transfers, and payments. It writes transaction details to a log file and updates 
 * bank data accordingly.
 * 
 * This class also provides helper methods to determine account names, numbers, and balances based on 
 * account types.
 * 
 * @author Sebastian Nares, Ricardo Acosta
 */
public class Log {
    private static final String LOG_FILE = "Log.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private bankDataUpdater bankDataUpdater;

    /**
     * Constructs a new {@code Log} object. Initializes the log file and the {@code bankDataUpdater} object.
     */
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

    /**
     * Writes a message to the log file with a timestamp.
     * 
     * @param message The message to write to the log.
     */
    private void writeToLog(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(formatter);
            fw.write("[" + timestamp + "] " + message + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Logs a balance inquiry for the specified customer and account type.
     * 
     * @param customer   The customer making the balance inquiry.
     * @param accountType The type of account (1 for Checking, 2 for Savings, 3 for Credit).
     */
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

    /**
     * Logs a deposit transaction for the specified customer and account type.
     * 
     * @param customer      The customer making the deposit.
     * @param accountType   The type of account (1 for Checking, 2 for Savings, 3 for Credit).
     * @param deposit_amount The amount deposited.
     */
    public void logDeposit(Customer customer, int accountType, float deposit_amount) {
        bankDataUpdater.processDeposit(customer.get_account_id(), accountType, deposit_amount);
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
        
        bankDataUpdater.saveUpdates();
    }

    /**
     * Logs a withdrawal transaction for the specified customer and account type.
     * 
     * @param customer          The customer making the withdrawal.
     * @param accountType       The type of account (1 for Checking, 2 for Savings, 3 for Credit).
     * @param withdrawal_amount The amount withdrawn.
     */
    public void logWithdrawal(Customer customer, int accountType, float withdrawal_amount) {
        bankDataUpdater.processWithdrawal(customer.get_account_id(), accountType, withdrawal_amount);
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
        
        bankDataUpdater.saveUpdates();
    }

    /**
     * Logs a transfer transaction between two accounts for the specified customer.
     * 
     * @param customer       The customer making the transfer.
     * @param sourceType     The type of the source account (1 for Checking, 2 for Savings, 3 for Credit).
     * @param destType       The type of the destination account (1 for Checking, 2 for Savings, 3 for Credit).
     * @param transfer_amount The amount transferred.
     */
    public void logTransfer(Customer customer, int sourceType, int destType, float transfer_amount) {
        bankDataUpdater.processTransfer(customer.get_account_id(), sourceType, destType, transfer_amount);
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
        
        bankDataUpdater.saveUpdates();
    }

    /**
     * Logs a payment transaction for the specified customer.
     * 
     * @param customer        The customer making the payment.
     * @param payment_amount  The amount paid.
     */
    public void logPayment(Customer customer, float payment_amount) {
        bankDataUpdater.processPayment(customer.get_account_id(), payment_amount);
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
        
        bankDataUpdater.saveUpdates();
    }

    /**
     * Gets the name of the account based on the account type.
     * 
     * @param accountType The type of account (1 for Checking, 2 for Savings, 3 for Credit).
     * @return The account name as a string.
     */
    private String getAccountName(int accountType) {
        switch (accountType) {
            case 1: return "Checking";
            case 2: return "Savings";
            case 3: return "Credit";
            default: return "Unknown";
        }
    }

    /**
     * Retrieves the account number of the specified account type for the customer.
     * 
     * @param customer    The customer whose account number is being retrieved.
     * @param accountType The type of account (1 for Checking, 2 for Savings, 3 for Credit).
     * @return The account number as an integer.
     */
    private int getAccountNumber(Customer customer, int accountType) {
        switch (accountType) {
            case 1: return customer.get_checking_account_number();
            case 2: return customer.get_saving_account_number();
            case 3: return customer.get_credit_account_number();
            default: return 0;
        }
    }
    /**
 * Logs a transfer transaction between two customers' accounts.
 * 
 * @param sender         The customer sending the money.
 * @param recipient      The customer receiving the money.
 * @param senderType     The type of the sender's account (1 for Checking, 2 for Savings, 3 for Credit).
 * @param recipientType  The type of the recipient's account (1 for Checking, 2 for Savings, 3 for Credit).
 * @param transferAmount The amount transferred.
 */
public void logInterCustomerTransfer(Customer customer, Customer recipientCustomer, int account_type, int recipientAccountType, float interCustomerTransferAmount) {
    // Validate transfer amount
    if (interCustomerTransferAmount <= 0) {
        System.out.println("Transfer amount must be greater than zero.");
        return;
    }
    
    // Process withdrawal and deposit
    bankDataUpdater.processWithdrawal(customer.get_account_id(), account_type, interCustomerTransferAmount);
    bankDataUpdater.processDeposit(recipientCustomer.get_account_id(), recipientAccountType, interCustomerTransferAmount);
    
    // Get account names, numbers, and balances for logging
    String senderAccountName = getAccountName(account_type);
    String recipientAccountName = getAccountName(recipientAccountType);
    int senderAccountNumber = getAccountNumber(customer, account_type);
    int recipientAccountNumber = getAccountNumber(recipientCustomer, recipientAccountType);
    float senderNewBalance = getAccountBalance(customer, account_type);
    float recipientNewBalance = getAccountBalance(recipientCustomer, recipientAccountType);
    
    // Create the log message
    String message = String.format(
        "%s %s transferred $%.2f from %s-%d to %s %s's %s-%d. " +
        "Sender New Balance: $%.2f. Recipient New Balance: $%.2f",
        customer.get_name(),
        customer.get_last(),
        interCustomerTransferAmount,
        senderAccountName,
        senderAccountNumber,
        recipientCustomer.get_name(),
        recipientCustomer.get_last(),
        recipientAccountName,
        recipientAccountNumber,
        senderNewBalance,
        recipientNewBalance
    );
    
    // Write the transaction to the log file
    writeToLog(message);
    
    // Save the updated bank data
    bankDataUpdater.saveUpdates();
}


    /**
     * Retrieves the account balance of the specified account type for the customer.
     * 
     * @param customer    The customer whose account balance is being retrieved.
     * @param accountType The type of account (1 for Checking, 2 for Savings, 3 for Credit).
     * @return The account balance as a float.
     */
    private float getAccountBalance(Customer customer, int accountType) {
        switch (accountType) {
            case 1: return customer.get_checking_account_balance();
            case 2: return customer.get_saving_account_balance();
            case 3: return customer.get_credit_account_balance();
            default: return 0.0f;
        }
    }
    public void logNewAccountCreation(Customer customer) {
        String message = String.format(
            "%s %s created a new account. Checking Account: %d, Savings Account: %d, Credit Account: %d, Credit Limit: $%.2f",
            customer.get_name(),
            customer.get_last(),
            customer.get_checking_account_number(),
            customer.get_saving_account_number(),
            customer.get_credit_account_number(),
            customer.get_credit_account_max()
        );
    
        writeToLog(message);
    }
    
}

   

