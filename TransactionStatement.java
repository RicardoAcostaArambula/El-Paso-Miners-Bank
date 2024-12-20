/**
 * The TransactionStatement class is responsible for managing customer transaction sessions,
 * recording transactions, and generating detailed account statements.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionStatement {
    private static HashMap<String, SessionData> activeSessions = new HashMap<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

     /**
     * SessionData is a private inner class that holds data related to a customer's session,
     * including the start time, initial balances, and a list of recorded transactions.
     */
    private static class SessionData {
        LocalDateTime startTime;
        float[] initialBalances;
        List<String> transactions;

         /**
         * Constructs a new SessionData instance.
         *
         * @param checkingBalance Initial checking account balance
         * @param savingsBalance Initial savings account balance
         * @param creditBalance Initial credit account balance
         */
        SessionData(float checkingBalance, float savingsBalance, float creditBalance) {
            this.startTime = LocalDateTime.now();
            this.initialBalances = new float[]{checkingBalance, savingsBalance, creditBalance};
            this.transactions = new ArrayList<>();
        }
    }
    /**
     * Starts a session for the given customer by recording the initial account balances.
     *
     * @param customer The customer for whom the session is being started
     */
    public void startSession(Customer customer) {
        String customerKey = customer.getName() + " " + customer.getLast();
        float[] initialBalances = {
            customer.getCheckingAccountBalance(),
            customer.getSavingAccountBalance(),
            customer.getCreditAccountBalance()
        };
        activeSessions.put(customerKey, new SessionData(initialBalances[0], initialBalances[1], initialBalances[2]));
    }
    /**
     * Records a transaction for the given customer.
     *
     * @param customer The customer for whom the transaction is being recorded
     * @param transaction The description of the transaction
     */
    public void recordTransaction(Customer customer, String transaction) {
        String customerKey = customer.getName() + " " + customer.getLast();
        SessionData session = activeSessions.get(customerKey);
        if (session != null) {
            session.transactions.add(String.format("[%s] %s", 
                LocalDateTime.now().format(formatter), 
                transaction));
        }
    }
    /**
     * Generates a transaction statement for the given customer and writes it to a file.
     * The file includes account details, initial and current balances, and transaction history.
     *
     * @param customer The customer for whom the transaction statement is being generated
     */
    public void generateTransactionStatement(Customer customer) {
        String customerKey = customer.getName() + " " + customer.getLast();
        SessionData session = activeSessions.get(customerKey);
        
        if (session == null) {
            System.out.println("No active session found for this customer.");
            return;
        }

        try {
            String fileName = String.format("statement_%s_%s_%s.txt",
                customer.getName(),
                customer.getLast(),
                session.startTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

            FileWriter writer = new FileWriter(fileName);

            writer.write("     =============================================        \n");
            writer.write("                  EL PASO MINERS BANK                     \n");
            writer.write("                   ACCOUNT STATEMENT                      \n");
            writer.write("     =============================================        \n");
            writer.write("\n");

            // Write account information
            writer.write("      =========== ACCOUNT INFORMATION ============        \n");
            writer.write(String.format("Customer Name: %s %s\n", customer.getName(), customer.getLast()));
            writer.write(String.format("Account ID: %d\n\n", customer.getAccountId()));

            writer.write("Checking Account Number: " + customer.getCheckingAccountNumber() + "\n");
            writer.write("Savings Account Number: " + customer.getSavingAccountNumber() + "\n");
            writer.write("Credit Account Number: " + customer.getCreditAccountNumber() + "\n\n");

            // Write balance summary
            writer.write("      =========== BALANCE SUMMARY ================        \n");
            writer.write(String.format("Checking Account:\n  Starting Balance: $%.2f\n  Current Balance: $%.2f\n\n",
                session.initialBalances[0], customer.getCheckingAccountBalance()));
            writer.write(String.format("Savings Account:\n  Starting Balance: $%.2f\n  Current Balance: $%.2f\n\n",
                session.initialBalances[1], customer.getSavingAccountBalance()));
            writer.write(String.format("Credit Account:\n  Starting Balance: $%.2f\n  Current Balance: $%.2f\n  Credit Limit: $%.2f\n\n",
                session.initialBalances[2], customer.getCreditAccountBalance(), customer.getCreditAccountMax()));

            // Write transactions
            writer.write("    ============= TRANSACTION HISTORY ===========         \n");
            for (String transaction : session.transactions) {
                writer.write(transaction + "\n");
            }

            writer.write("\n");
            writer.write("=================================================\n");
            writer.write("Statement generated on: " + LocalDateTime.now().format(formatter) + System.lineSeparator());
            writer.write("=================================================\n");
            writer.close();

            // Clear the session after generating statement
            activeSessions.remove(customerKey);

        } catch (IOException e) {
            System.err.println("Error generating transaction statement: " + e.getMessage());
        }
    }
}