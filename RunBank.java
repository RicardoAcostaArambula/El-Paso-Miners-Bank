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
        int option, account_type;
        Scanner kb = new Scanner(System.in);
        String exit, username;
        float balance;
        Log transactionLog = new Log();
        HashMap <String, Customer> users_by_name = new HashMap<>();
        HashMap <Integer, Customer> accounts_by_number = new HashMap<>();
        setup_users(users_by_name, accounts_by_number);
        TransactionReader.transaction_reader("Transactions(1).csv", users_by_name, transactionLog);
        System.out.println("Welcome to El Paso miners Bank");
        do {
            System.out.println("Please Select the One of the following modes:");
            System.out.println("1. Individual Person");
            System.out.println("2. Bank Teller");
            System.out.println("3. Create New Account");
            option = kb.nextInt();
            if (1 <= option && option <=3){
                right_option = true;
            } else {
                System.out.println("Please choose a valid option");
            }
        } while(!right_option);
        
        
        if (option==1){
            System.out.println("Enter your full name: ");
            if (kb.hasNextLine()) { 
                kb.nextLine();
            }
            do {
                username = kb.nextLine();
                /*Check that user exist in the dictonary */
                if (!users_by_name.containsKey(username)){
                    System.out.println("Error: please enter a valid name");
                } else {
                    right_user = true;
                } 
            } while(!right_user);
            do {
                /*Getting the customer*/
                Customer customer = users_by_name.get(username);
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
                System.out.println("(6) Transfer to another customer");
                int transaction_option = kb.nextInt();
                
                /* switch statmenet with all call to each*/
                switch (transaction_option) {
                    case 1: 
                        if (account_type == 1) {
                            balance = Opearations.checking_account_balance(customer);
                        } else if (account_type == 2) {
                            balance = Opearations.saving_account_balance(customer);
                        } else {
                            balance = Opearations.credit_account_balance(customer);
                        }
                        System.out.println("The account balance is: $" + String.format("%.2f", balance));
                        transactionLog.logBalanceInquiry(customer, account_type);
                        break;
                
                    case 2:
                        System.out.println("Enter deposit amount:");
                        float deposit_amount = kb.nextFloat();
                        if (deposit_amount <= 0) {
                            System.out.println("Error: Deposit amount must be greater than zero");
                            break;
                        }
                        
                        if (account_type == 1) {
                            Opearations.deposit_to_checking(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to checking account");
                        } else if (account_type == 2) {
                            Opearations.deposit_to_saving(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to savings account");
                        } else {
                            Opearations.deposit_to_credit(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to credit account");
                        }
                        transactionLog.logDeposit(customer, account_type, deposit_amount);
                        break;
                
                    case 3:
                        System.out.println("Enter withdraw amount:");
                        float withdrawal_amount = kb.nextFloat();
                        if (withdrawal_amount <= 0) {
                            System.out.println("Error: Withdraw amount must be greater than zero");
                            break;
                        }
                        
                        if (account_type == 1) {
                            float checking_balance = Operations.checking_account_balance(customer);
                            if (checking_balance >= withdrawal_amount) {
                                customer.set_checking_account_balance(checking_balance - withdrawal_amount);
                                System.out.println("Successfully withdrew $" + withdrawal_amount + " from checking account");
                            } else {
                                System.out.println("Error: Insufficient funds in checking account");
                            }
                        } else if (account_type == 2) {
                            float savings_balance = Opearations.saving_account_balance(customer);
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
                        
                        // Get source account balance
                        float source_balance = 0;
                        if (source_account == 1) {
                            source_balance = Operations.checking_account_balance(customer);
                        } else if (source_account == 2) {
                            source_balance = Operations.saving_account_balance(customer);
                        } else if (source_account == 3) {
                            source_balance = Operations.credit_account_balance(customer);
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
                            Operations.deposit_to_checking(customer, transfer_amount);
                        } else if (dest_account == 2) {
                            Operations.deposit_to_saving(customer, transfer_amount);
                        } else if (dest_account == 3) {
                            Operations.deposit_to_credit(customer, transfer_amount);
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
                            System.out.println("Error: Payment amount must be greater than zero");
                            break;
                        }
                        
                        float credit_balance = Operations.credit_account_balance(customer);
                        if (credit_balance + payment_amount > customer.get_credit_account_max()) {
                            System.out.println("Error: Payment would exceed credit limit");
                            break;
                        }
                        
                        customer.set_credit_account_balance(credit_balance + payment_amount);
                        System.out.println("Successfully made payment of $" + payment_amount);
                        transactionLog.logPayment(customer, payment_amount);
                        break;
                        
                    case 6:
                        if (kb.hasNextLine()) { 
                            kb.nextLine();
                        }
                        System.out.println("Enter recipient's name: ");
                        String recipient_full_name = kb.nextLine();
                        
                        if (!users_by_name.containsKey(recipient_full_name)) {
                            System.out.println("Error: Recipient not found");
                            break;
                        }
                        
                        Customer recipientCustomer = users_by_name.get(recipient_full_name);
                        
                        System.out.println("Select recipient's account type:");
                        System.out.println("(1) Checking");
                        System.out.println("(2) Savings");
                        System.out.println("(3) Credit");
                        int recipientAccountType = kb.nextInt();
                        
                        if (recipientAccountType < 1 || recipientAccountType > 3) {
                            System.out.println("Error: Invalid account type");
                            break;
                        }
                        
                        System.out.println("Enter transfer amount:");
                        float interCustomerTransferAmount = kb.nextFloat();
                        
                        if (interCustomerTransferAmount <= 0) {
                            System.out.println("Error: Transfer amount must be greater than zero");
                            break;
                        }
                        
                        if (Operations.transferBetweenCustomers(customer, recipientCustomer, account_type, recipientAccountType, interCustomerTransferAmount)) {
                            System.out.println("Successfully transferred $" + String.format("%.2f", interCustomerTransferAmount) + 
                                               " to customer: " + recipientCustomer.get_name() + " " + recipientCustomer.get_last());
                            transactionLog.logInterCustomerTransfer(customer, recipientCustomer, account_type, recipientAccountType, interCustomerTransferAmount);
                        } else {
                            System.out.println("Transfer failed. Please check account balances or input data.");
                        }
                        
                        break;
                        
                    default:
                        System.out.println("Invalid option selected");
                        break;
                }
                if (kb.hasNextLine()) { 
                    kb.nextLine();
                }
                System.out.println("Type EXIT to exit");
                exit = kb.nextLine().trim(); 
                browing = exit.equalsIgnoreCase("exit") ? false : true;
            } while(browing);
        } else if (option == 2){    
            do {
                String account_holder;
                boolean inquiry_chosen = false;
                int inquiry_type;
                int account_number;
                do {
                    System.out.println("Please select one of the two options: ");
                    System.out.println("(1) Inquiry account by name");
                    System.out.println("(2) Inquiry account by account number");
                    inquiry_type = kb.nextInt();
                    if (1 <= inquiry_type && inquiry_type <= 2){
                        inquiry_chosen = true;
                    } else {
                        System.out.println("Please choose a valid option");
                    }
                } while (!inquiry_chosen);
                
                if (inquiry_type == 1){
                    boolean valid = false;
                    do {
                        if (kb.hasNextLine()) { 
                            kb.nextLine();
                        }
                        System.out.println("Whose account would you like to inquire about? (Enter full name as following: FirstName LastName)");
                        account_holder = kb.nextLine();
                        if (!users_by_name.containsKey(account_holder)){
                            System.out.println("Please enter a valid name");
                        } else {
                            valid = true;
                        }
                    } while (!valid);


                    Customer customer = users_by_name.get(account_holder);
                    dislay_account_information_by_name(customer);

                } else {
                    boolean valid = false;
                    /*checking the account time */
                    do {
                        System.out.println("What is the account type: ");
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
                    valid = false;
                    do {
                        System.out.println("What is the account number?");
                        account_number = kb.nextInt();
                        if (kb.hasNextLine()) { 
                            kb.nextLine();
                        }
                        if (!accounts_by_number.containsKey(account_number)){
                            System.out.println("Please enter a valid account number");
                        } else {
                            valid = true;
                        }
                    } while (!valid);

                    
                    Customer customer = accounts_by_number.get(account_number);
                    dislay_account_information_by_account_number(customer, account_number, account_type);
                    transactionLog.logBalanceInquiry(customer, account_type);
                }

                System.out.println("Type EXIT to exit");
                exit = kb.nextLine().trim(); // Read the whole line for exit command
                browing = exit.equalsIgnoreCase("exit") ? false : true;
            } while(browing);
            
        } if (option == 3) {            
            // Create a new user
            Customer customer = UserCreation.createNewUser(kb, users_by_name, accounts_by_number);
            

            if (customer != null) {
                System.out.println("Account creation completed. You may now login as an Individual Person.");
            } else {
                System.out.println("Account creation failed.");
            }
            
        }

    }
    /**
     * Displays account information by account number
     * 
     * @param customer is the target user for the information
     * @param account_type is an int that represents the account type to dislay the information
     * @param account_number is an int that represents the account number to display the information
     */

    public static void dislay_account_information_by_account_number(Customer customer, int account_number, int account_type){
        String name = customer.get_name();
        String account;
        String last = customer.get_last();
        int id = customer.get_account_id();
        float balance;
        if (account_type == 1){
            balance = customer.get_checking_account_balance();
            account = "Checking";
        } else if (account_type == 2){
            balance = customer.get_saving_account_balance();
            account = "Saving";
        } else {
            balance = customer.get_credit_account_balance();
            account = "Credit";
        }
        System.out.println("Account holder: " + name + " " + last + " with ID: " + id);
        System.out.println("The Account of type: " + account + ", with number: " + account_number);
        System.out.println("Balance: " + balance);
    }
     /**
     * Displays account information by name
     * 
     * @param customer is the target user for the information
     * 
     */

    public static void dislay_account_information_by_name(Customer customer){
        String name = customer.get_name();
        String last = customer.get_last();
        int id = customer.get_account_id();
        int account_number_saving, account_number_checking, account_number_credit;
        float balance_checking, balance_saving, balance_credit;

        account_number_saving = customer.get_checking_account_number();
        balance_checking = customer.get_checking_account_balance();

        account_number_checking = customer.get_saving_account_number();
        balance_saving = customer.get_saving_account_balance();

        account_number_credit = customer.get_credit_account_number();
        balance_credit = customer.get_credit_account_balance();

        System.out.println("Account holder: " + name + " " + last + " with ID: " + id);

        System.out.println("The Account of type: Checkings | number: " + account_number_checking + " | Balance: " + balance_checking);

        System.out.println("The Account of type: Savings | number: " + account_number_saving + " | Balance: " + balance_saving);

        System.out.println("The Account of type: Credit | number: " + account_number_credit + " | Balance: " + balance_credit);
        
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
     * @param users_by_name the HashMap to be populated with Customer objects
     * @param accounts_by_number the HashMap to be pupulated with Customer objects
     */
    public static void setup_users(HashMap <String, Customer>  users_by_name, HashMap <Integer, Customer> accounts_by_number){
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
                String key = name + " " + last;
                users_by_name.put(key, customer);
                accounts_by_number.put(checking_account_number, customer);
                accounts_by_number.put(saving_account_number, customer);
            }
        } catch (FileNotFoundException error){
            System.out.println("Error: could not find file");
            error.printStackTrace();
        }
    }
    /**
     * Removes commas inside quotations from a given line.
     *
     * @param line the line from which to remove commas inside quotations
     * @return the modified line with commas removed
     */
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