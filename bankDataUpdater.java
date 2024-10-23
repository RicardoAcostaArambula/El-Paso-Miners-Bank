import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class bankDataUpdater {
    private static final String CSV_FILE = "bank_users.csv";
    private List<String[]> userData;

    public bankDataUpdater() {
        userData = new ArrayList<>();
        loadData();
        
    }

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

    

    private int findCustomerRow(int customerId) {
        for (int i = 1; i < userData.size(); i++) { // Start from 1 to skip header
            if (Integer.parseInt(userData.get(i)[0]) == customerId) {
                return i;
            }
        }
        return -1;
    }

    private int getBalanceColumnIndex(int accountType) {
        switch (accountType) {
            case 1: return 9;  // Checking Starting Balance
            case 2: return 11; // Savings Starting Balance
            case 3: return 14; // Credit Starting Balance
            default: throw new IllegalArgumentException("Invalid account type");
        }
    }

    private void updateBalance(int customerId, int accountType, float newBalance) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }

        int columnIndex = getBalanceColumnIndex(accountType);
        String[] row = userData.get(rowIndex);
        row[columnIndex] = String.format("%.2f", newBalance);
    }

    public void processDeposit(int customerId, int accountType, float deposit_amount) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) {
            System.err.println("Customer not found: " + customerId);
            return; // Exit the method if customer not found
        }
    
        int columnIndex = getBalanceColumnIndex(accountType);
        
        try {
    
            // Ensure you're getting the right value (the balance, not the phone number)
            float currentBalance = Float.parseFloat(userData.get(rowIndex)[columnIndex]);
    
            float newBalance = currentBalance + deposit_amount;
    
            // Update the balance in the userData
            updateBalance(customerId, accountType, newBalance);
    
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid balance format for customer ID " + customerId + ": " + userData.get(rowIndex)[columnIndex]);
        }
    }
    

    public void processWithdrawal(int customerId, int accountType, float withdrawal_amount) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) return; // Customer not found, exit the method

        int columnIndex = getBalanceColumnIndex(accountType);
        try {
            float currentBalance = Float.parseFloat(userData.get(rowIndex)[columnIndex]);
            float newBalance = currentBalance - withdrawal_amount;
            updateBalance(customerId, accountType, newBalance);
        } catch (NumberFormatException e) {
            // Suppressing the error message
        }
    }

    public void processTransfer(int customerId, int source_account, int dest_account, float transfer_amount) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) return; // Customer not found, exit the method

        // Withdraw from source account
        int sourceColumnIndex = getBalanceColumnIndex(source_account);
        try {
            float sourceBalance = Float.parseFloat(userData.get(rowIndex)[sourceColumnIndex]);
            updateBalance(customerId, source_account, sourceBalance - transfer_amount);
        } catch (NumberFormatException e) {
            // Suppressing the error message
        }

        // Deposit to destination account
        int destColumnIndex = getBalanceColumnIndex(dest_account);
        try {
            float destBalance = Float.parseFloat(userData.get(rowIndex)[destColumnIndex]);
            updateBalance(customerId, dest_account, destBalance + transfer_amount);
        } catch (NumberFormatException e) {
            // Suppressing the error message
        }
    }

    public void processPayment(int customerId, float amount) {
        int rowIndex = findCustomerRow(customerId);
        if (rowIndex == -1) return; // Customer not found, exit the method

        int columnIndex = getBalanceColumnIndex(3); // Credit account
        try {
            float currentBalance = Float.parseFloat(userData.get(rowIndex)[columnIndex]);
            float newBalance = currentBalance - amount;
            updateBalance(customerId, 3, newBalance);
        } catch (NumberFormatException e) {
            // Suppressing the error message
        }
        
    }

    public void saveUpdates() {
        saveUpdates("bank_users.csv");
    }

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
