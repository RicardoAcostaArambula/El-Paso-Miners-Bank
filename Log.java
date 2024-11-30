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
            customer.getName(),
            customer.getLast(),
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
     * @param depositAmount The amount deposited.
     */
    public void logDeposit(Customer customer, int accountType, float depositAmount) {
        bankDataUpdater.processDeposit(customer.getAccountId(), accountType, depositAmount);
        String accountName = getAccountName(accountType);
        int accountNumber = getAccountNumber(customer, accountType);
        float newBalance = getAccountBalance(customer, accountType);
        
        String message = String.format("%s %s, deposited $%.2f to %s-%d. New Balance: $%.2f",
            customer.getName(),
            customer.getLast(),
            depositAmount,
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
     * @param withdrawalAmount The amount withdrawn.
     */
    public void logWithdrawal(Customer customer, int accountType, float withdrawalAmount) {
        bankDataUpdater.processWithdrawal(customer.getAccountId(), accountType, withdrawalAmount);
        String accountName = getAccountName(accountType);
        int accountNumber = getAccountNumber(customer, accountType);
        float newBalance = getAccountBalance(customer, accountType);
        
        String message = String.format("%s %s, withdrew $%.2f from %s-%d. New Balance: $%.2f",
            customer.getName(),
            customer.getLast(),
            withdrawalAmount,
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
     * @param transferAmount The amount transferred.
     */
    public void logTransfer(Customer customer, int sourceType, int destType, float transferAmount) {
        bankDataUpdater.processTransfer(customer.getAccountId(), sourceType, destType, transferAmount);
        String sourceAccount = getAccountName(sourceType);
        String destAccount = getAccountName(destType);
        int sourceNumber = getAccountNumber(customer, sourceType);
        int destNumber = getAccountNumber(customer, destType);
        float sourceBalance = getAccountBalance(customer, sourceType);
        float destBalance = getAccountBalance(customer, destType);
        
        String message = String.format(
            "%s %s, transferred $%.2f from %s-%d to %s-%d. " +
            "Source Balance: $%.2f. Destination Balance: $%.2f",
            customer.getName(),
            customer.getLast(),
            transferAmount,
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
     * @param paymentAmount  The amount paid.
     */
    public void logPayment(Customer customer, float paymentAmount) {
        bankDataUpdater.processPayment(customer.getAccountId(), paymentAmount);
        int creditNumber = customer.getCreditAccountNumber();
        float newBalance = customer.getCreditAccountBalance();
        
        String message = String.format("%s %s, made a payment of $%.2f on Credit-%d. New Balance: $%.2f",
            customer.getName(),
            customer.getLast(),
            paymentAmount,
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
            case 1: return customer.getCheckingAccountNumber();
            case 2: return customer.getSavingAccountNumber();
            case 3: return customer.getCreditAccountNumber();
            default: return 0;
        }
    }
    /**
 * Logs a transfer transaction between two customers' accounts.
 * 
 * @param customer        The customer sending the money.
 * @param recipientCustomer      The customer receiving the money.
 * @param accountType     The type of the sender's account (1 for Checking, 2 for Savings, 3 for Credit).
 * @param recipientAccountType  The type of the recipient's account (1 for Checking, 2 for Savings, 3 for Credit).
 * @param interCustomerTransferAmount The amount transferred.
 */
public void logInterCustomerTransfer(Customer customer, Customer recipientCustomer, int accountType, int recipientAccountType, float interCustomerTransferAmount) {
    // Validate transfer amount
    if (interCustomerTransferAmount <= 0) {
        System.out.println("Transfer amount must be greater than zero.");
        return;
    }
    
    // Process withdrawal and deposit
    bankDataUpdater.processWithdrawal(customer.getAccountId(), accountType, interCustomerTransferAmount);
    bankDataUpdater.processDeposit(recipientCustomer.getAccountId(), recipientAccountType, interCustomerTransferAmount);
    
    // Get account names, numbers, and balances for logging
    String senderAccountName = getAccountName(accountType);
    String recipientAccountName = getAccountName(recipientAccountType);
    int senderAccountNumber = getAccountNumber(customer, accountType);
    int recipientAccountNumber = getAccountNumber(recipientCustomer, recipientAccountType);
    float senderNewBalance = getAccountBalance(customer, accountType);
    float recipientNewBalance = getAccountBalance(recipientCustomer, recipientAccountType);
    
    // Create the log message
    String message = String.format(
        "%s %s transferred $%.2f from %s-%d to %s %s's %s-%d. " +
        "Sender New Balance: $%.2f. Recipient New Balance: $%.2f",
        customer.getName(),
        customer.getLast(),
        interCustomerTransferAmount,
        senderAccountName,
        senderAccountNumber,
        recipientCustomer.getName(),
        recipientCustomer.getLast(),
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
            case 1: return customer.getCheckingAccountBalance();
            case 2: return customer.getSavingAccountBalance();
            case 3: return customer.getCreditAccountBalance();
            default: return 0.0f;
        }
    }
    public void logNewAccountCreation(Customer customer) {
        String message = String.format(
            "%s %s created a new account. Checking Account: %d, Savings Account: %d, Credit Account: %d, Credit Limit: $%.2f",
            customer.getName(),
            customer.getLast(),
            customer.getCheckingAccountNumber(),
            customer.getSavingAccountNumber(),
            customer.getCreditAccountNumber(),
            customer.getCreditAccountMax()
        );
    
        writeToLog(message);
    }
    
}

   

