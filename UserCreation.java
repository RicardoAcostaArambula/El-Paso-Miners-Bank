import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class UserCreation {
    private static Log logger;
    private static final String CSV_FILE = "new_bank_users.csv";

        /*Default constructor*/
        public UserCreation(){
            logger = new Log(); 
            
        }
        private static int lastUserId = 0;
        private static int lastAccountNumber = 0;

        private static int generateRandomCreditScore() {
            Random random = new Random();
            return random.nextInt(551) + 300; 
        }

        public static void loadUsersFromCSV(HashMap<String, Customer> users_by_name, 
                                      HashMap<Integer, Customer> accounts_by_number) {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("ID,")) {
                    continue; 
                }
                
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); 
                if (data.length < 13) continue; 
                Customer customer = new Customer();
                customer.set_account_id(Integer.parseInt(data[0]));
                customer.set_name(data[1]);
                customer.set_last(data[2]);
                customer.set_dob(data[3]);
                customer.set_address(data[4]);
                customer.set_phone_number(data[5]);
                customer.set_checking_account_number(Integer.parseInt(data[6]));
                customer.set_checking_account_balance(Float.parseFloat(data[7]));
                customer.set_saving_account_number(Integer.parseInt(data[8]));
                customer.set_saving_account_balance(Float.parseFloat(data[9]));
                customer.set_credit_account_number(Integer.parseInt(data[10]));
                customer.set_credit_account_max(Float.parseFloat(data[11]));
                customer.set_credit_account_balance(Float.parseFloat(data[12]));

                String key = customer.get_name() + " " + customer.get_last();
                users_by_name.put(key, customer);
                accounts_by_number.put(customer.get_checking_account_number(), customer);
                accounts_by_number.put(customer.get_saving_account_number(), customer);
                accounts_by_number.put(customer.get_credit_account_number(), customer);

                // Update last IDs
                lastUserId = Math.max(lastUserId, customer.get_account_id());
                lastAccountNumber = Math.max(lastAccountNumber, 
                    Math.max(customer.get_checking_account_number(),
                    Math.max(customer.get_saving_account_number(),
                    customer.get_credit_account_number())));
            }
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        }
    }
    
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
    
        public Customer createNewUser(Scanner kb, HashMap<String, Customer> users_by_name, 
                                              HashMap<Integer, Customer> accounts_by_number) {
            

            try {
                SetupUsers.setup_users(users_by_name, accounts_by_number);
            } catch (Exception e) {
                System.out.println("Error initializing users: " + e.getMessage());
                return null;
            }                                   
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
            
            System.out.print("Phone Number (without spaces or dashes): ");
            String phone = kb.nextLine();
            customer.set_phone_number(formatPhoneNumber(phone));
            
            int creditScore = generateRandomCreditScore();
            System.out.println("Credit Score: " + creditScore);            
            
            // Generate and set account numbers
            int checkingAccountNumber = generateUniqueAccountNumber();
            int savingsAccountNumber = generateUniqueAccountNumber();
            int creditAccountNumber = generateUniqueAccountNumber();
            
            customer.set_checking_account_number(checkingAccountNumber);
            customer.set_saving_account_number(savingsAccountNumber);
            customer.set_credit_account_number(creditAccountNumber);
            
            // Set initial balances
            customer.set_checking_account_balance(0.0f);
            customer.set_saving_account_balance(0.0f);
            
            // Set credit limit and initial balance
            float creditLimit = generateCreditLimit(creditScore);
            customer.set_credit_account_max(creditLimit);
            customer.set_credit_account_balance(-creditLimit); 
            
            // Add to HashMaps
            String key = firstName + " " + lastName;
            users_by_name.put(key, customer);
            accounts_by_number.put(checkingAccountNumber, customer);
            accounts_by_number.put(savingsAccountNumber, customer);
            accounts_by_number.put(creditAccountNumber, customer);
    
            saveUsersToCSV(users_by_name);
            logger.logNewAccountCreation(customer);

        
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
        try (FileWriter writer = new FileWriter("new_bank_users.csv", false)) {
            // Write CSV header using the original format exactly
            writer.append("Identification Number,First Name,Last Name,Date of Birth,Address,Phone Number,Checking Account Number,Checking Starting Balance,Savings Account Number,Savings Starting Balance,Credit Account Number,Credit Max,Credit Starting Balance\n");
            
            for (Customer customer : users_by_name.values()) {
                writer.append(String.format("%.0f,%s,%s,%s,\"%s\",%s,%.0f,%.2f,%.0f,%.2f,%.0f,%.2f,%.2f\n",
                    (float)customer.get_account_id(),
                    customer.get_name(),
                    customer.get_last(),
                    customer.get_dob(),
                    customer.get_address(),
                    customer.get_phone_number(),
                    (float)customer.get_checking_account_number(),
                    customer.get_checking_account_balance(),
                    (float)customer.get_saving_account_number(),
                    customer.get_saving_account_balance(),
                    (float)customer.get_credit_account_number(),
                    customer.get_credit_account_max(),
                    customer.get_credit_account_balance()
                ));
            }
            
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error: could not write to file");
            e.printStackTrace();
        }
    }
    
    
    private static Date parseDate(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yy"); // Change to match the input format
        try {
            return inputFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return new Date(); // Return current date or handle accordingly
        }
    }
    
    private static String formatPhoneNumber(String phone) {
        // Assume the phone is provided in the format "9159159159" and you want "(915) 915-9159"
        if (phone.length() == 10) { // Ensure it has the expected length
            return String.format("(%s) %s-%s", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6, 10));
        } else {
            return phone; // Return the original phone number if it doesn't match expected format
        }
    }
    
}
