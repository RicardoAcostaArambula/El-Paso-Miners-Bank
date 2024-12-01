import java.util.HashMap;
import java.util.Scanner;
/**
 * It is a class that serves to display the different menus and take user selection for each of the menus
 * 
 * @author Sebastian Nares and Ricardo Acosta
 */
public class Menus {
    Scanner kb = new Scanner(System.in);
    HashMap <String, Customer> usersByName = new HashMap<>();
    HashMap <Integer, Customer> accountsByNumber = new HashMap<>();
    private static Menus menu;
    private Menus(){}
    public static synchronized Menus getInstance(){
        if (menu == null){
            menu = new Menus();
        }
        return menu;
    }

    /**
     * dispalyModeMenu allows user to select between four modes
     * 
     * @return option is the users selected option
     */
    public int displayModeMenu(){
        
        int option;
        boolean rightOption = false;
        do {
            System.out.println("Please Select the one of the following modes:");
            System.out.println("1. Customer");
            System.out.println("2. Bank Manager");
            System.out.println("3. Create New Account");
            option = kb.nextInt();
            if (kb.hasNextLine()) {
                kb.nextLine();
            }
            if (1 <= option && option <=3){
                rightOption = true;
            } else {
                System.out.println("Please choose a valid option");
            }
        } while(!rightOption);
        return option;
    }
    /**
     * prompts the user to enter the full name from the user and verifies if it is a valid user
     * 
     * @param usersByName A HashMap that contains user information
     * @return userName is the user full name 
     */
    public String getFullNameMenu(HashMap <String, Customer> usersByName){
        boolean rightUser = false;
        System.out.println("Enter your full name: ");
        String userName;
        do {
            userName = kb.nextLine();
            if (!usersByName.containsKey(userName)){
                System.out.println("Error: please enter a valid name");
            } else {
                rightUser = true;
            } 
        } while(!rightUser);
        return userName;
    }
    /**
     * Gets the user account type to be worked on
     * 
     * @return account_type is the user selected option
     */
    public int getAccountTypeMenu(){
        boolean valid = false;
        int accountType;
        do {
            System.out.println("Select one of the following accounts:");
            System.out.println("(1) Checkings");
            System.out.println("(2) Savings");
            System.out.println("(3) Credit");
            accountType = kb.nextInt();
            if (1 <= accountType && accountType <=3){
                valid = true;
            } else {
                System.out.println("Please choose a valid account");
            }
        } while(!valid);
        if (kb.hasNextLine()){
            kb.nextLine();
        }
        return accountType;
    }

    /**
     * Gets the user transaction type to be done
     * 
     * @return transactionOption is the user selected option
     */
    public int selectTransactionMenu(){
        System.out.println("Select one of the transactions below:");
        System.out.println("(1) Inquire about balance");
        System.out.println("(2) Deposit money to the account");
        System.out.println("(3) Withdraw money from the account");
        System.out.println("(4) Transfer money between accounts");
        System.out.println("(5) Make payment");
        System.out.println("(6) Transfer to another customer");
        int transactionOption = kb.nextInt();
        return transactionOption;
    }
    /**
     * This method is yet to be implemented
     * @return Gets source account.
     */
    public int getSourceAccount(){
        return 0;
    }
    /**
     * Ask and generates statement for user
     * @param usersByName A HashMap that contains user information.
     * @param statementGenerator Generates Statement.
     * 
     */
    public void displayStatementMenu(HashMap<String, Customer> usersByName, TransactionStatement statementGenerator) {
        System.out.println("=== Generate Account Statement ===");
        
        // Get customer name
        System.out.println("Enter customer's full name (FirstName LastName):");
        String customerName = kb.nextLine().trim();
        
        if (usersByName.containsKey(customerName)) {
            Customer customer = usersByName.get(customerName);
            statementGenerator.generateTransactionStatement(customer);
        } else {
            System.out.println("Error: Customer not found. Please try again.");
        }
    }
}