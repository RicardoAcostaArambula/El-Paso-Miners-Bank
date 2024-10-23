import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * The {@code bankDataUpdater} class handles operations related to banking transactions,
 * such as deposits, withdrawals, transfers, and payments. It reads user account information
 * from a CSV file, processes updates to account balances, and saves changes back to the file.
 * This class uses account data indexed by customer ID and account types.
 * 
 * @author Sebastian Nares, Ricardo Acosta
 */

public class bankDataUpdater {
    private static final String CSV_FILE = "bank_users.csv";
    private List<String[]> userData;

    /**
     * Constructor to initialize and load bank user data from the CSV file.
     */
    public bankDataUpdater() {
        userData = new ArrayList<>();
        loadData();
    }

    /**
     * Loads data from the CSV file and stores it in userData list.
     */
    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                userData.add(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("Error loading bank data: " + e.getMessage());
        }
    }

    /**
     * Finds the row index of a customer based on their ID.
     *
     * @param customerId The ID of the customer.
     * @return The row index of the customer if found, -1 otherwise.
     */
    private int findCustomerRow(int customerId) {
        for (int i = 1; i < userData.size(); i++) { 
            if (Integer.parseInt(userData.get(i)[0]) == customerId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the balance column based on the account type.
     *
     * @param accountType The type of account (1 = Checking, 2 = Savings, 3 = Credit).
     * @return The index of the balance column.
     */
    private int getBalanceColumnIndex(int accountType) {
        switch (accountType) {
            case 1: return 9;  // Checking Starting Balance
            case 2: return 11; // Savings Starting Balance
            case 3: return 14; // Credit Starting Balance
            default: throw new IllegalArgumentException("Invalid account type");
        }
    }

    /**
     * Updates the balance for a specific customer and account type.
     *
     * @param customerId The ID of the customer.
     * @param accountType The type of account.
     * @param newBalance The new balance to set.
     */
    private void updateBalance(int customerId, int accountType, float newBalance) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }

        int columnIndex = getBalanceColumnIndex(accountType);
        String[] row = userData.get(rowIndex);
        row[columnIndex] = String.format("%.2f", newBalance);
    }

    /**
     * Processes a deposit by adding the deposit amount to the current balance.
     *
     * @param customerId The ID of the customer.
     * @param accountType The type of account.
     * @param depositAmount The amount to deposit.
     */
    public void processDeposit(int customerId, int accountType, float depositAmount) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) {
            System.err.println("Customer not found: " + customerId);
            return;
        }

        int columnIndex = getBalanceColumnIndex(accountType);
        try {
            float currentBalance = Float.parseFloat(userData.get(rowIndex)[columnIndex]);
            float newBalance = currentBalance + depositAmount;
            updateBalance(customerId, accountType, newBalance);
        } catch (NumberFormatException e) {
            System.err.println("Invalid balance format for customer ID " + customerId + ": " + userData.get(rowIndex)[columnIndex]);
        }
    }

    /**
     * Processes a withdrawal by subtracting the withdrawal amount from the current balance.
     *
     * @param customerId The ID of the customer.
     * @param accountType The type of account.
     * @param withdrawalAmount The amount to withdraw.
     */
    public void processWithdrawal(int customerId, int accountType, float withdrawalAmount) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) return;

        int columnIndex = getBalanceColumnIndex(accountType);
        try {
            float currentBalance = Float.parseFloat(userData.get(rowIndex)[columnIndex]);
            float newBalance = currentBalance - withdrawalAmount;
            updateBalance(customerId, accountType, newBalance);
        } catch (NumberFormatException e) {
            // Suppress the error message
        }
    }

    /**
     * Processes a transfer between two accounts for a customer.
     *
     * @param customerId The ID of the customer.
     * @param sourceAccount The source account type.
     * @param destAccount The destination account type.
     * @param transferAmount The amount to transfer.
     */
    public void processTransfer(int customerId, int sourceAccount, int destAccount, float transferAmount) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) return;

        int sourceColumnIndex = getBalanceColumnIndex(sourceAccount);
        int destColumnIndex = getBalanceColumnIndex(destAccount);

        try {
            float sourceBalance = Float.parseFloat(userData.get(rowIndex)[sourceColumnIndex]);
            float destBalance = Float.parseFloat(userData.get(rowIndex)[destColumnIndex]);
            updateBalance(customerId, sourceAccount, sourceBalance - transferAmount);
            updateBalance(customerId, destAccount, destBalance + transferAmount);
        } catch (NumberFormatException e) {
            // Suppress the error message
        }
    }

    /**
     * Processes a payment by reducing the credit balance for a customer.
     *
     * @param customerId The ID of the customer.
     * @param amount The payment amount.
     */
    public void processPayment(int customerId, float amount) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) return;

        int columnIndex = getBalanceColumnIndex(3); // Credit account
        try {
            float currentBalance = Float.parseFloat(userData.get(rowIndex)[columnIndex]);
            float newBalance = currentBalance - amount;
            updateBalance(customerId, 3, newBalance);
        } catch (NumberFormatException e) {
            // Suppress the error message
        }
    }

    /**
     * Saves the updated user data to the original CSV file.
     */
    public void saveUpdates() {
        saveUpdates(CSV_FILE);
    }

    /**
     * Saves the updated user data to a specified CSV file.
     *
     * @param outputFile The name of the output file.
     */
    public void saveUpdates(String outputFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String[] row : userData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving updates: " + e.getMessage());
        }
    }
}
