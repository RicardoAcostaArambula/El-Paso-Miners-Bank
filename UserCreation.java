import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class UserCreation {
    private static int lastUserId = 0;
    private static int lastAccountNumber = 0;

    public static void initializeLastIds(HashMap<String, Customer> users_by_name) {
        for (Customer customer : users_by_name.values()) {
            lastUserId = Math.max(lastUserId, customer.get_account_id());
            lastAccountNumber = Math.max(lastAccountNumber, 
                Math.max(customer.get_checking_account_number(), 
                Math.max(customer.get_saving_account_number(), 
                customer.get_credit_account_number())));
        }
    }
    
    private static int generateUniqueUserId() {
        return ++lastUserId;
    }
    
    private static int generateUniqueAccountNumber() {
        return ++lastAccountNumber;
    }
    
    private static float generateCreditLimit(int creditScore) {
        Random random = new Random();

        if (creditScore <= 580) {
            return 100 + random.nextFloat() * (699 - 100);
        } else if (creditScore <= 669) {
            return 700 + random.nextFloat() * (4999 - 700);
        } else if (creditScore <= 739) {
            return 5000 + random.nextFloat() * (7499 - 5000);
        } else if (creditScore <= 799) {
            return 7500 + random.nextFloat() * (15999 - 7500);
        } else {
            return 16000 + random.nextFloat() * (25000 - 16000);
        }
    }

    public static Customer createNewUser(Scanner kb, HashMap<String, Customer> users_by_name, 
                                          HashMap<Integer, Customer> accounts_by_number) {
        // Initialize IDs if not already done
        if (lastUserId == 0) {
            initializeLastIds(users_by_name);
        }
        
        Customer customer = new Customer();
        
        // Generate unique ID
        int userId = generateUniqueUserId();
        customer.set_account_id(userId);
        
        System.out.println("\nEnter user information:");
        
        if (kb.hasNextLine()) {
            kb.nextLine();
        }
        
        // Get personal information
        System.out.print("First Name: ");
        String firstName = kb.nextLine();
        customer.set_name(firstName);
        
        System.out.print("Last Name: ");
        String lastName = kb.nextLine();
        customer.set_last(lastName);
        
        System.out.print("Date of Birth (DD-Month-YY): ");
        String dob = kb.nextLine();
        customer.set_dob(dob);
        
        // Get address information
        System.out.print("Street Address: ");
        String street = kb.nextLine();
        
        System.out.print("City: ");
        String city = kb.nextLine();
        
        System.out.print("State: ");
        String state = kb.nextLine();
        
        System.out.print("ZIP: ");
        String zip = kb.nextLine();
        
        String fullAddress = String.format("%s, %s, %s %s", street, city, state, zip);
        customer.set_address(fullAddress);
        
        System.out.print("Phone Number: (123) 123-1234)");
        String phone = kb.nextLine();
        customer.set_phone_number(phone);
        
        // Get and validate credit score
        int creditScore;
        do {
            System.out.print("Credit Score (300-850): ");
            try {
                creditScore = Integer.parseInt(kb.nextLine());
                if (creditScore >= 300 && creditScore <= 850) {
                    break;
                }
                System.out.println("Invalid credit score. Please enter a score between 300 and 850.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        } while (true);
        
        // Generate and set account numbers
        int checkingAccountNumber = generateUniqueAccountNumber();
        int savingsAccountNumber = generateUniqueAccountNumber();
        int creditAccountNumber = generateUniqueAccountNumber();
        
        customer.set_checking_account_number(checkingAccountNumber);
        customer.set_saving_account_number(savingsAccountNumber);
        customer.set_credit_account_balance(creditAccountNumber);
        
        // Set initial balances
        customer.set_checking_account_balance(0.0f);
        customer.set_saving_account_balance(0.0f);
        
        // Set credit limit and initial balance
        float creditLimit = generateCreditLimit(creditScore);
        customer.set_credit_account_max(creditLimit);
        customer.set_credit_account_balance(0.0f);
        
        // Add to HashMaps
        String key = firstName + " " + lastName;
        users_by_name.put(key, customer);
        accounts_by_number.put(checkingAccountNumber, customer);
        accounts_by_number.put(savingsAccountNumber, customer);
        
        // Display account information
        System.out.println("\nAccount created successfully!");
        System.out.println("User ID: " + userId);
        System.out.println("Checking Account Number: " + checkingAccountNumber);
        System.out.println("Savings Account Number: " + savingsAccountNumber);
        System.out.println("Credit Account Number: " + creditAccountNumber);
        System.out.println("Credit Limit: $" + String.format("%.2f", creditLimit));
        
        // Save user data to CSV after creation
        saveUsersToCSV(users_by_name);
        
        return customer;
    }
    
    public static void saveUsersToCSV(HashMap<String, Customer> users_by_name) {
        try (FileWriter writer = new FileWriter("bank_users.csv", false)) { // 'false' to overwrite
            // Write CSV header
            writer.append("ID,FirstName,LastName,DOB,Address,Phone,CheckingAccountNumber,CheckingBalance,SavingAccountNumber,SavingBalance,CreditAccountNumber,CreditLimit,CreditBalance\n");
            
            // Write each customer's data
            for (Customer customer : users_by_name.values()) {
                writer.append(String.valueOf(customer.get_account_id())).append(",")
                      .append(customer.get_name()).append(",")
                      .append(customer.get_last()).append(",")
                      .append(customer.get_dob()).append(",")
                      .append(customer.get_address()).append(",")
                      .append(customer.get_phone_number()).append(",")
                      .append(String.valueOf(customer.get_checking_account_number())).append(",")
                      .append(String.valueOf(customer.get_checking_account_balance())).append(",")
                      .append(String.valueOf(customer.get_saving_account_number())).append(",")
                      .append(String.valueOf(customer.get_saving_account_balance())).append(",")
                      .append(String.valueOf(customer.get_credit_account_number())).append(",")
                      .append(String.valueOf(customer.get_credit_account_max())).append(",")
                      .append(String.valueOf(customer.get_credit_account_balance())).append("\n");
            }
            writer.flush();
            System.out.println("User data saved successfully to bank_users.csv.");
        } catch (IOException e) {
            System.out.println("Error: could not write to file");
            e.printStackTrace();
        }
    }
}
