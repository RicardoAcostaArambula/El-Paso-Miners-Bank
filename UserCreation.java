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
/**
 * The {@code UserCreation} class is responsible for handling the creation of new users, managing 
 * user account information, generating unique account numbers and user IDs, and saving the data to a CSV file.
 * It interacts with user input to gather necessary information and updates the user's records in a set of HashMaps.
 */
public class UserCreation {
    private static Log logger;
    private static final String CSV_FILE = "new_bank_users.csv";
        /**
         * Default constructor for the {@code UserCreation} class.
         * Initializes the logger.
         */
        /*Default constructor*/
        public UserCreation(){
            logger = new Log(); 
            
        }
        private static int lastUserId = 0;
        private static int lastAccountNumber = 0;
        
        /**
         * Generates a random credit score between 300 and 850.
         * 
         * @return A random integer between 300 and 850 representing the credit score.
         */
        private static int generateRandomCreditScore() {
            Random random = new Random();
            return random.nextInt(551) + 300; 
        }

         /**
         * Loads user data from a CSV file and updates the provided HashMaps with the user information.
         * 
         * @param usersByName A HashMap to store users by their full name.
         * @param accountsByNumber A HashMap to store users by their account numbers.
         */
        public static void loadUsersFromCSV(HashMap<String, Customer> usersByName, 
                                      HashMap<Integer, Customer> accountsByNumber) {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (data.length < 13) continue;

                try {
                    Customer customer = new Customer();
                    // Parse numeric values properly
                    customer.setAccountId(Integer.parseInt(data[0].trim()));
                    customer.setName(data[1].trim());
                    customer.setLast(data[2].trim());
                    customer.setDob(data[3].trim());
                    customer.setAddress(data[4].replace("\"", "").trim());
                    customer.setPhoneNumber(data[5].trim());
                    customer.setCheckingAccountNumber(Integer.parseInt(data[6].trim()));
                    customer.setCheckingAccountBalance(Float.parseFloat(data[7].trim()));
                    customer.setSavingAccountNumber(Integer.parseInt(data[8].trim()));
                    customer.setSavingAccountBalance(Float.parseFloat(data[9].trim()));
                    customer.setCreditAccountNumber(Integer.parseInt(data[10].trim()));
                    customer.setCreditAccountMax(Float.parseFloat(data[11].trim()));
                    customer.setCreditAccountBalance(Float.parseFloat(data[12].trim()));

                    String key = customer.getName() + " " + customer.getLast();
                    usersByName.put(key, customer);
                    accountsByNumber.put(customer.getCheckingAccountNumber(), customer);
                    accountsByNumber.put(customer.getSavingAccountNumber(), customer);
                    accountsByNumber.put(customer.getCreditAccountNumber(), customer);

                    // Update last IDs
                    lastUserId = Math.max(lastUserId, customer.getAccountId());
                    lastAccountNumber = Math.max(lastAccountNumber, 
                        Math.max(customer.getCheckingAccountNumber(),
                        Math.max(customer.getSavingAccountNumber(),
                        customer.getCreditAccountNumber())));
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numeric values for customer record: " + line);
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        }
    }
        /**
         * Initializes the last user ID and last account number by iterating through all users.
         * 
         * @param usersByName A HashMap that contains user information.
         */
        public static void initializeLastIds(HashMap<String, Customer> usersByName) {
            for (Customer customer : usersByName.values()) {
                lastUserId = Math.max(lastUserId, customer.getAccountId());
                lastAccountNumber = Math.max(lastAccountNumber, 
                    Math.max(customer.getCheckingAccountNumber(), 
                    Math.max(customer.getSavingAccountNumber(), 
                    customer.getCreditAccountNumber())));
            }
        }
        /**
         * Generates a unique user ID by incrementing the last user ID.
         * 
         * @return A unique user ID.
         */
        private static int generateUniqueUserId() {
            return ++lastUserId;
        }
        /**
         * Generates a unique account number by incrementing the last account number.
         * 
         * @return A unique account number.
         */
        private static int generateUniqueAccountNumber() {
            return ++lastAccountNumber;
        }
        /**
         * Generates a credit limit based on the provided credit score.
         * 
         * @param creditScore The credit score used to determine the credit limit.
         * @return The generated credit limit based on the credit score.
        */
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
        /**
         * Creates a new user account by interacting with the user to gather necessary information.
         * It also generates unique user and account IDs, credit scores, and credit limits.
         * 
         * @param kb A Scanner object used to read user input.
         * @param usersByName A HashMap to store users by their full name.
         * @param accountsByNumber A HashMap to store users by their account numbers.
         * @return The created {@code Customer} object.
         */
        public Customer createNewUser(Scanner kb, HashMap<String, Customer> usersByName, 
                                              HashMap<Integer, Customer> accountsByNumber) {
            

            try {
                SetupUsers.setUpUsers(usersByName, accountsByNumber);
            } catch (Exception e) {
                System.out.println("Error initializing users: " + e.getMessage());
                return null;
            }                                   
            // Initialize IDs if not already done
            if (lastUserId == 0) {
                initializeLastIds(usersByName);
            }
            
            Customer customer = new Customer();
            
            // Generate unique ID
            int userId = generateUniqueUserId();
            customer.setAccountId(userId);
            
            System.out.println("\nEnter user information:");
            
            // Get personal information
            String firstName;
            while (true) {
                try {
                    System.out.print("First Name: ");
                    firstName = kb.nextLine();
                    customer.setName(firstName);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("First name must contain only letters. Please try again.");
                }
            }
            
            // Get Last Name 
            String lastName;
            while (true) {
                try {
                    System.out.print("Last Name: ");
                    lastName = kb.nextLine();
                    customer.setLast(lastName);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Last name can only contain letters, spaces, or hyphens. Please try again.");
                }
            }
            
            System.out.print("Date of Birth (DD-Month-YY): ");
            String dob = kb.nextLine();
            customer.setDob(dob);
            
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
            customer.setAddress(fullAddress);
            
            System.out.print("Phone Number (without spaces or dashes): ");
            String phone = kb.nextLine();
            customer.setPhoneNumber(formatPhoneNumber(phone));
            
            int creditScore = generateRandomCreditScore();
            System.out.println("Credit Score: " + creditScore);            
            
            // Generate and set account numbers
            int checkingAccountNumber = generateUniqueAccountNumber();
            int savingsAccountNumber = generateUniqueAccountNumber();
            int creditAccountNumber = generateUniqueAccountNumber();
            
            customer.setCheckingAccountNumber(checkingAccountNumber);
            customer.setSavingAccountNumber(savingsAccountNumber);
            customer.setCreditAccountNumber(creditAccountNumber);
            
            // Set initial balances
            customer.setCheckingAccountBalance(0.0f);
            customer.setSavingAccountBalance(0.0f);
            
            // Set credit limit and initial balance
            float creditLimit = generateCreditLimit(creditScore);
            customer.setCreditAccountMax(creditLimit);
            customer.setCreditAccountBalance(-creditLimit);
    
            // Add to HashMaps
            String key = firstName + " " + lastName;
            usersByName.put(key, customer);
            accountsByNumber.put(checkingAccountNumber, customer);
            accountsByNumber.put(savingsAccountNumber, customer);
            accountsByNumber.put(creditAccountNumber, customer);
    
            saveUsersToCSV(usersByName);
            logger.logNewAccountCreation(customer);

        
        // Display account information
        System.out.println("\nAccount created successfully!");
        System.out.println("User ID: " + userId);
        System.out.println("Checking Account Number: " + checkingAccountNumber);
        System.out.println("Savings Account Number: " + savingsAccountNumber);
        System.out.println("Credit Account Number: " + creditAccountNumber);
        System.out.println("Credit Limit: $" + String.format("%.2f", creditLimit));
        
        // Save user data to CSV after creation
        saveUsersToCSV(usersByName);
        
        return customer;
    }
    /**
     * Saves new user's to the new CSV file.
     * @param usersByName A hashmap to store users by their full name.
     */
    public static void saveUsersToCSV(HashMap<String, Customer> usersByName) {
        try (FileWriter writer = new FileWriter(CSV_FILE, false)) {
            // Write CSV header
            writer.append("Identification Number,First Name,Last Name,Date of Birth,Address,Phone Number,Checking Account Number,Checking Starting Balance,Savings Account Number,Savings Starting Balance,Credit Account Number,Credit Max,Credit Starting Balance\n");
            
            for (Customer customer : usersByName.values()) {
                // Format numbers as integers where appropriate and ensure proper decimal formatting for balances
                String line = String.format("%d,%s,%s,%s,\"%s\",%s,%d,%.2f,%d,%.2f,%d,%.2f,%.2f\n",
                    customer.getAccountId(),
                    customer.getName(),
                    customer.getLast(),
                    customer.getDob(),
                    customer.getAddress(),
                    customer.getPhoneNumber(),
                    customer.getCheckingAccountNumber(),
                    customer.getCheckingAccountBalance(),
                    customer.getSavingAccountNumber(),
                    customer.getSavingAccountBalance(),
                    customer.getCreditAccountNumber(),
                    customer.getCreditAccountMax(),
                    customer.getCreditAccountBalance()
                );
                writer.append(line);
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error: could not write to file");
            e.printStackTrace();
        }
    }
    
    /**
     * Parses a string representing a date in the format "dd-MMM-yy" into a {@code Date} object.
     * If the string cannot be parsed, it returns the current date.
     * 
     * @param dateStr The string representing the date to be parsed. The format should be "dd-MMM-yy".
     * @return A {@code Date} object representing the parsed date, or the current date if parsing fails.
     */
    private static Date parseDate(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yy"); // Change to match the input format
        try {
            return inputFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return new Date(); // Return current date or handle accordingly
        }
    }
    /**
     * Formats a phone number into the standard format "(XXX) XXX-XXXX".
     * Assumes the input phone number is a 10-digit string (e.g., "9159159159") and formats it accordingly.
     * If the input does not have 10 digits, it returns the original phone number as is.
     * 
     * @param phone The 10-digit phone number to be formatted.
     * @return The formatted phone number in the format "(XXX) XXX-XXXX", or the original phone number if invalid.
     */
    
    private static String formatPhoneNumber(String phone) {
        // Assume the phone is provided in the format "9159159159" and you want "(915) 915-9159"
        if (phone.length() == 10) { // Ensure it has the expected length
            return String.format("(%s) %s-%s", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6, 10));
        } else {
            return phone; // Return the original phone number if it doesn't match expected format
        }
    }
    
}
