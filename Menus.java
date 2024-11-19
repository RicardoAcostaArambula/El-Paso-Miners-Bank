import java.util.HashMap;
import java.util.Scanner;
/**
 * It is a class that serves to display the different menus and take user selection for each of the menus
 * 
 * @author Sebastian Nares and Ricardo Acosta
 */
public class Menus {
    Scanner kb = new Scanner(System.in);
    HashMap <String, Customer> users_by_name = new HashMap<>();
    HashMap <Integer, Customer> accounts_by_number = new HashMap<>();
    public Menus(){

    }
    /**
     * dispalyModeMenu allows user to select between four modes
     * 
     * @return option is the users selected option
     */
    public int displayModeMenu(){
        
        int option;
        boolean right_option = false;
        do {
            System.out.println("Please Select the one of the following modes:");
            System.out.println("1. Individual Person");
            System.out.println("2. Bank Teller");
            System.out.println("3. Create New Account");
            System.out.println("4. Generate account statement");
            option = kb.nextInt();
            if (kb.hasNextLine()) {
                kb.nextLine();
            }
            if (1 <= option && option <=4){
                right_option = true;
            } else {
                System.out.println("Please choose a valid option");
            }
        } while(!right_option);
        return option;
    }
    /**
     * prompts the user to enter the full name from the user and verifies if it is a valid user
     * 
     * @param users_by_name A HashMap that contains user information
     * @return username is the user full name 
     */
    public String get_full_name_menu(HashMap <String, Customer> users_by_name){
        boolean right_user = false;
        System.out.println("Enter your full name: ");
        String username;
        do {
            username = kb.nextLine();
            if (!users_by_name.containsKey(username)){
                System.out.println("Error: please enter a valid name");
            } else {
                right_user = true;
            } 
        } while(!right_user);
        return username;
    }
    /**
     * Gets the user account type to be worked on
     * 
     * @return account_type is the user selected option
     */
    public int get_account_type_menu(){
        boolean valid = false;
        int account_type;
        do {
            System.out.println("Select one of the following accounts:");
            System.out.println("(1) Checkings");
            System.out.println("(2) Savings");
            System.out.println("(3) Credit");
            account_type = kb.nextInt();
            if (1 <= account_type && account_type <=3){
                valid = true;
            } else {
                System.out.println("Please choose a valid account");
            }
        } while(!valid);
        if (kb.hasNextLine()){
            kb.nextLine();
        }
        return account_type;
    }

    /**
     * Gets the user transaction type to be done
     * 
     * @return transaction_option is the user selected option
     */
    public int select_transaction_menu(){
        System.out.println("Select one of the transactions below:");
        System.out.println("(1) Inquire about balance");
        System.out.println("(2) Deposit money to the account");
        System.out.println("(3) Withdraw money from the account");
        System.out.println("(4) Transfer money between accounts");
        System.out.println("(5) Make payment");
        System.out.println("(6) Transfer to another customer");
        int transaction_option = kb.nextInt();
        return transaction_option;
    }
    /**
     * This method is yet to be implemented
     * @return Gets source account.
     */
    public int get_source_account(){
        return 0;
    }
    /**
     * Ask and generates statement for user
     * @param users_by_name A HashMap that contains user information.
     * @param statementGenerator Generates Statement.
     * 
     */
    public void displayStatementMenu(HashMap<String, Customer> users_by_name, TransactionStatement statementGenerator) {
        System.out.println("=== Generate Account Statement ===");
        
        // Get customer name
        System.out.println("Enter customer's full name (FirstName LastName):");
        String customerName = kb.nextLine().trim();
        
        if (users_by_name.containsKey(customerName)) {
            Customer customer = users_by_name.get(customerName);
            statementGenerator.generateTransactionStatement(customer);
        } else {
            System.out.println("Error: Customer not found. Please try again.");
        }
    }
}