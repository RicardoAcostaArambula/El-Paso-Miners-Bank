import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
/**
 * The RunBank class simulates a banking system that allows users to perform various 
 * transactions such as deposits, withdrawals, and transfers across different account types.
 *
 * @author Sebastian Nares, Ricardo Acosta 
 */
public class RunBank {
    /**
     * The main method that initiates the banking application.
     * 
     ** <p>This method handles the user interface for selecting modes of 
     * operation (Individual Person or Bank Teller) and authenticates 
     * the user based on their name and Customer ID. It allows users 
     * to perform the following banking transactions: Inquiring balance, depositing, 
     * withdrawing, and transferring funds. </p>
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args){
        boolean browing = true;
        boolean right_user = false;
        boolean right_option = false;
        int option, id, account_type;
        Scanner kb = new Scanner(System.in);
        String exit, username;
        float balance;
        Log transactionLog = new Log();
        HashMap <Integer, Customer> users = new HashMap<>();
        setup_users(users);
        System.out.println("Welcome to El Paso miners Bank");
        do {
            System.out.println("Please Select the One of the following modes:");
            System.out.println("1. Individual Person");
            System.out.println("2. Bank Teller");
            option = kb.nextInt();
            if (1 <= option && option <=3){
                right_option = true;
            } else {
                System.out.println("Please choose a valid option");
            }
        } while(!right_option);
        
        
        if (option==1){
            do {
                System.out.println("Enter your name: ");
                username = kb.next();
                System.out.println("Enter your Customer ID: ");
                id = kb.nextInt();
                /*Check that user exist in the dictonary */
                if (!(users.containsKey(id)) || !(users.get(id).get_name().equals(username))){
                    System.out.println("Error: please enter a valid number and/or name");
                } else {
                    right_user = true;
                } 
            } while(!right_user);
            do {
                /*Getting the customer*/
                Customer customer = users.get(id);
                boolean valid = false;
                /*checking the account time */
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
                /* We need to store the account object in the account varaible so that we are able to 
                    * call the methods for checking the balance
                */
                /*code below is assuming we have the account and account type*/
                System.out.println("Select one of the transactions below:");
                System.out.println("(1) Inquire about balance");
                /*Assuming the user already selected the account in which the money will go*/
                System.out.println("(2) Deposit money to the account");
                System.out.println("(3) Withdraw money from the account");
                System.out.println("(4) Transfer money between accounts");
                System.out.println("(5) Make payment");
                int transaction_option = kb.nextInt();
                
                /* switch statmenet with all call to each*/
                switch (transaction_option) {
                    case 1: 
                        if (account_type == 1) {
                            balance = checking_account_balance(customer);
                        } else if (account_type == 2) {
                            balance = saving_account_balance(customer);
                        } else {
                            balance = credit_account_balance(customer);
                        }
                        System.out.println("The account balance is: $" + String.format("%.2f", balance));
                        transactionLog.logBalanceInquiry(customer, account_type);
                        break;
                
                    case 2:
                        System.out.println("Enter deposit amount:");
                        float deposit_amount = kb.nextFloat();
                        if (deposit_amount <= 0) {
                            System.out.println("Error: Deposit amount must be positive");
                            break;
                        }
                        
                        if (account_type == 1) {
                            deposit_to_checking(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to checking account");
                        } else if (account_type == 2) {
                            deposit_to_saving(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to savings account");
                        } else {
                            deposit_to_credit(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to credit account");
                        }
                        transactionLog.logDeposit(customer, account_type, deposit_amount);
                        break;
                
                    case 3:
                        System.out.println("Enter withdrawal amount:");
                        float withdrawal_amount = kb.nextFloat();
                        if (withdrawal_amount <= 0) {
                            System.out.println("Error: Withdrawal amount must be positive");
                            break;
                        }
                        
                        if (account_type == 1) {
                            float checking_balance = checking_account_balance(customer);
                            if (checking_balance >= withdrawal_amount) {
                                customer.set_checking_account_balance(checking_balance - withdrawal_amount);
                                System.out.println("Successfully withdrew $" + withdrawal_amount + " from checking account");
                            } else {
                                System.out.println("Error: Insufficient funds in checking account");
                            }
                        } else if (account_type == 2) {
                            float savings_balance = saving_account_balance(customer);
                            if (savings_balance >= withdrawal_amount) {
                                customer.set_saving_account_balance(savings_balance - withdrawal_amount);
                                System.out.println("Successfully withdrew $" + withdrawal_amount + " from savings account");
                            } else {
                                System.out.println("Error: Insufficient funds in savings account");
                            }
                        } else {
                            System.out.println("Error: Cannot withdraw from credit account");
                        }
                        transactionLog.logWithdrawal( customer, account_type, withdrawal_amount);
                        break;
                
                    case 4:
                        System.out.println("From which account?");
                        System.out.println("(1) Checking");
                        System.out.println("(2) Savings");
                        System.out.println("(3) Credit");
                        int source_account = kb.nextInt();
                        
                        System.out.println("To which account?");
                        System.out.println("(1) Checking");
                        System.out.println("(2) Savings");
                        System.out.println("(3) Credit");
                        int dest_account = kb.nextInt();
                        
                        if (source_account == dest_account) {
                            System.out.println("Error: Cannot transfer to the same account");
                            break;
                        }
                        
                        System.out.println("Enter transfer amount:");
                        float transfer_amount = kb.nextFloat();
                        if (transfer_amount <= 0) {
                            System.out.println("Error: Transfer amount must be positive");
                            break;
                        }
                        
                        // Get source account balance
                        float source_balance = 0;
                        if (source_account == 1) {
                            source_balance = checking_account_balance(customer);
                        } else if (source_account == 2) {
                            source_balance = saving_account_balance(customer);
                        } else if (source_account == 3) {
                            source_balance = credit_account_balance(customer);
                        }
                        
                        // Check if source has sufficient funds
                        if (source_balance < transfer_amount) {
                            System.out.println("Error: Insufficient funds in source account");
                            break;
                        }
                        
                        // Perform transfer
                        // Deduct from source
                        if (source_account == 1) {
                            customer.set_checking_account_balance(source_balance - transfer_amount);
                        } else if (source_account == 2) {
                            customer.set_saving_account_balance(source_balance - transfer_amount);
                        } else if (source_account == 3) {
                            customer.set_credit_account_balance(source_balance - transfer_amount);
                        }
                        
                        // Add to destination
                        if (dest_account == 1) {
                            deposit_to_checking(customer, transfer_amount);
                        } else if (dest_account == 2) {
                            deposit_to_saving(customer, transfer_amount);
                        } else if (dest_account == 3) {
                            deposit_to_credit(customer, transfer_amount);
                        }
                        
                        System.out.println("Successfully transferred $" + transfer_amount);
                        transactionLog.logTransfer(customer, source_account,  dest_account, transfer_amount);
                        break;
                
                    case 5:
                        if (account_type != 3) {
                            System.out.println("Error: Payments can only be made from credit account");
                            break;
                        }
                        
                        System.out.println("Enter payment amount:");
                        float payment_amount = kb.nextFloat();
                        if (payment_amount <= 0) {
                            System.out.println("Error: Payment amount must be positive");
                            break;
                        }
                        
                        float credit_balance = credit_account_balance(customer);
                        if (credit_balance + payment_amount > customer.get_credit_account_max()) {
                            System.out.println("Error: Payment would exceed credit limit");
                            break;
                        }
                        
                        customer.set_credit_account_balance(credit_balance + payment_amount);
                        System.out.println("Successfully made payment of $" + payment_amount);
                        transactionLog.logPayment(customer, payment_amount);
                        break;
                        
                    default:
                        System.out.println("Invalid option selected");
                        break;
                }
                System.out.println("Write: EXIT to exit");
                exit = kb.next();
                browing = exit.equals("EXIT") ? false : true;
            } while(browing);
        } else if (option == 2){    
            do {
                System.out.println("Please enter the user ID: ");
                id = kb.nextInt();

                Customer customer = users.get(id);

                System.out.println("Enter the account type: ");
                System.out.println("(1) Checking");
                System.out.println("(2) Saving");
                System.out.println("(3) Credit");
                account_type = kb.nextInt();

                if (1<= account_type && account_type <=2){
                    if (account_type == 1) {
                        balance = checking_account_balance(customer);
                    } else if (account_type == 2) {
                        balance = saving_account_balance(customer);
                    } else {
                        balance = credit_account_balance(customer);
                    }
                    System.out.println("The account balance is: $" + String.format("%.2f", balance));
                    transactionLog.logBalanceInquiry(customer, account_type);
                } else {
                    System.out.println("Please choose a valid option");
                }
                System.out.println("Write: EXIT to exit");
                exit = kb.next();
                browing = exit.equals("EXIT") ? false : true;
            } while(browing);
            




        }
    }
      /**
     * Logs transaction information for a customer.
     *
     * @param customer the customer whose transaction information is being logged
     * @param transaction a description of the transaction
     */
    /*logs transaction information */
    public void log_information(Customer customer, String transaction){

    }
    /**
     * Returns the checking account balance for a customer.
     *
     * @param customer the customer whose checking account balance is requested
     * @return the checking account balance
     */
    /*returns checking account balance */
    public static float checking_account_balance(Customer customer){
       return customer.get_checking_account_balance();
    }
      /**
     * Returns the savings account balance for a customer.
     *
     * @param customer the customer whose savings account balance is requested
     * @return the savings account balance
     */

    public static float saving_account_balance(Customer customer){
        return customer.get_saving_account_balance();
    }
    /**
     * Returns the credit account balance for a customer.
     *
     * @param customer the customer whose credit account balance is requested
     * @return the credit account balance
     */

    public static float credit_account_balance(Customer customer){
        return customer.get_credit_account_balance();
    }
    /**
     * Deposits funds into a customer's checking account.
     *
     * @param customer the customer whose checking account will be credited
     * @param amount the amount to be deposited
     */
    /*Deposit checkings */
    public static void deposit_to_checking(Customer customer, float amount){
        float current_balance = checking_account_balance(customer);
        float new_balance = current_balance + amount;
        customer.set_checking_account_balance(new_balance);
    }
    /**
     * Deposits funds into a customer's savings account.
     *
     * @param customer the customer whose savings account will be credited
     * @param amount the amount to be deposited
     */
    /*Deposit savings */
    public static void deposit_to_saving(Customer customer, float amount){
        float current_balance = saving_account_balance(customer);
        float new_balance = current_balance + amount;
        customer.set_saving_account_balance(new_balance);
    }
    
     /**
     * Deposits funds into a customer's credit account.
     *
     * @param customer the customer whose credit account will be credited
     * @param amount the amount to be deposited
     */
    /*Deposit credit */
    public static void deposit_to_credit(Customer customer, float amount){
        float current_balance = credit_account_balance(customer);
        float new_balance = current_balance + amount;
        customer.set_credit_account_balance(new_balance);
    }

    /*Are we going to have one method for each account type? 
     * or are we going to have one method, and the account class will be abstract and have the methods and attributes needed for generalization?
     * 
     
    */

    /**
     * Deposit funds into the selected account 
    * @param account The account into which the funds will be deposited.
    * @param amount The amount of money to deposit into the account.
    * @return true if the deposit was successful; false otherwise.
    */
    
    public static boolean deposit_funds(Account account, float amount){
        return false;
    }
    /*builds up the dictinary with the users with the id as primary key*/
    /*
        ,Credit Account Number,Credit Max,Credit Starting Balance
     */

     /**
     * Sets up users from a CSV file and populates the users HashMap.
     *
     * @param users the HashMap to be populated with Customer objects
     */
    public static void setup_users(HashMap <Integer, Customer>  users){
        try {
            File file = new File("bank_users.csv");
            Scanner read = new Scanner(file);
            String line = read.nextLine();
            while (read.hasNextLine()){
                line = read.nextLine();
                line = remove_commas_inside_quotations(line);
                String[] items = line.split(",");
                int id = Integer.parseInt(items[0]);
                String name = items[1];
                String last = items[2];
                String dob = items[3];
                String address = items[4];
                String phone_number = items[5];
                int checking_account_number = Integer.parseInt(items[6]);
                float checking_account_balance = Float.parseFloat((items[7]));
                int saving_account_number = Integer.parseInt(items[8]);
                float saving_account_balance = Float.parseFloat((items[9]));
                int credit_account_number = Integer.parseInt(items[10]);
                float credit_account_max= Float.parseFloat((items[11]));
                float credit_account_balance= Float.parseFloat((items[12]));
                /*Create object*/
                Customer customer = new Customer();
                customer.set_account_id(id);
                customer.set_name(name);
                customer.set_last(last);
                customer.set_dob(dob);
                customer.set_address(address);
                customer.set_phone_number(phone_number);
                customer.set_checking_account_number(checking_account_number);
                customer.set_checking_account_balance(checking_account_balance);
                customer.set_saving_account_number(saving_account_number);
                customer.set_saving_account_balance(saving_account_balance);
                customer.set_credit_account_balance(credit_account_number);
                customer.set_credit_account_max(credit_account_max);
                customer.set_credit_account_balance(credit_account_balance);
                users.put(id, customer);
                /*add it to dic */
            }
        } catch (FileNotFoundException error){
            System.out.println("Error: could not find file");
            error.printStackTrace();
        }
     
        /**
     * Removes commas inside quotations from a given line.
     *
     * @param line the line from which to remove commas inside quotations
     * @return the modified line with commas removed
     */
    }
    public static String remove_commas_inside_quotations(String line){
        StringBuilder new_line = new StringBuilder();
        boolean inside_quotes = false;
        for (int i = 0; i < line.length(); i++){
            char current_char = line.charAt(i);
            if (current_char == '"'){
                inside_quotes = !inside_quotes;
            } else if(current_char == ',' && inside_quotes){
                continue;
            } else {
                new_line.append(current_char);
            }
        }
        return new_line.toString();
    }
}