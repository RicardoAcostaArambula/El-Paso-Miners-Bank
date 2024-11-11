import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
class TransactionReader {
    
    private TransactionReader (){
    }
    /**
     * reads and performs all transactions in the file
     * 
     * @param filename is the name of the file that contains the transactions
     * @return None
     */
    public static void transaction_reader(String filename, HashMap <String, Customer>  users_by_name, Log transactionLog){
        UserOperations userOperations = new UserOperations();
        System.out.println("Processing transacitons from file... from another class:)");
        try {
            File file = new File(filename);
            Scanner read = new Scanner(file);
            String line = read.nextLine();
            
            while (read.hasNextLine()){
                line = read.nextLine();
                String[] items = line.split(",");
                /*From First Name */
                String from_first_name;
                /*From Last Name*/
                String from_last_name;
                /*From Where*/
                String from_where = "";
                /*Action */
                String action = "";
                /*To First Name*/
                String to_first_name = "";
                /*To Last Name*/
                String to_last_name = "";
                /*To Where*/
                String to_where = "";
                /*Amount*/
                String amount = "";
                String from_user;
                float withdrawal_amount;
                if (items.length == 4){
                    from_first_name = items[0];
                    /*From Last Name*/
                    from_last_name = items[1];
                    /*From Where*/
                    from_where = items[2];
                    /*Action */
                    action = items[3];
                } else {
                    /*From First Name */
                    from_first_name = items[0];
                    /*From Last Name*/
                    from_last_name = items[1];
                    /*From Where*/
                    from_where = items[2];
                    /*Action */
                    action = items[3];
                    /*To First Name*/
                    to_first_name = items[4];
                    /*To Last Name*/
                    to_last_name = items[5];
                    /*To Where*/
                    to_where = items[6];
                    /*Amount*/
                    amount = items[7];
                }
                
                
                switch (action){
                    /*Mickey,Mouse,Checking,pays,Donald,Duck,Checking,100 */
                    case "pays":
                        from_user = from_first_name + " " + from_last_name;
                        String to_user = to_first_name + " " + to_last_name;
                        if (!users_by_name.containsKey(from_user)) {
                            System.out.println("The customer with Name: " + from_first_name + " and last name: " + from_last_name + " was not found");
                        } else if (!users_by_name.containsKey(to_user)){
                            System.out.println("The customer with Name: " + to_first_name + " and last name: " + to_last_name + " was not found");
                        } else {
                            /*get the customer object for each person */
                            Customer from_customer = users_by_name.get(from_user);
                            Customer to_customer = users_by_name.get(to_user);
                            float interCustomerTransferAmount = Float.parseFloat(amount);
                            if (interCustomerTransferAmount <= 0) {
                                System.out.println("Error: Transfer amount must be greater than zero");
                                break;
                            }
                            /*get account type*/
                            int account_type_from = get_account_type(from_where);
                            int account_type_to = get_account_type(from_where);
                            if (account_type_from == -1 || account_type_to==-1){
                                System.out.println("There was an error getting the account type");
                                break;
                            }

                            if (userOperations.transferBetweenCustomers(from_customer, to_customer, account_type_from, account_type_to, interCustomerTransferAmount)) {
                                System.out.println("Successfully transferred $" + String.format("%.2f", interCustomerTransferAmount) + 
                                                   " to customer: " + to_customer.get_name() + " " + to_customer.get_last());
                                /*recording transaction*/
                                transactionLog.logInterCustomerTransfer(from_customer, to_customer, account_type_from, account_type_to, interCustomerTransferAmount);
                            } else {
                                System.out.println("Transfer failed. Please check account balances or input data.");
                            }
                        }
                        break;
                    case "transfers":
                        /*Gets customer name*/
                        from_user = from_first_name + " " + from_last_name;
                        if (!users_by_name.containsKey(from_user)) {
                            System.out.println("The customer with Name: " + from_first_name + " and last name: " + from_last_name + " was not found");
                            break;
                        } 
                        Customer customer = users_by_name.get(from_user);
                        int account_type_from = get_account_type(from_where);
                        int account_type_to = get_account_type(to_where);
                        float transfer_amount = Float.parseFloat(amount);
                        if (account_type_from == -1 || account_type_to== -1){
                            System.out.println("There was an error getting the account type");
                            break;
                        } else if (account_type_from == account_type_to) {
                            System.out.println("Error: Cannot transfer to the same account");
                            break;
                        }
                        // Get source account balance
                        float source_balance = 0;
                        if (account_type_from == 1) {
                            source_balance = userOperations.checking_account_balance(customer);
                        } else if (account_type_from == 2) {
                            source_balance = userOperations.saving_account_balance(customer);
                        } else if (account_type_from == 3) {
                            source_balance = userOperations.credit_account_balance(customer);
                        }

                        // Check if source has sufficient funds
                        if (source_balance < transfer_amount) {
                            System.out.println("Error: Insufficient funds in source account");
                            break;
                        }

                        // Perform transfer
                        // Deduct from source
                        if (account_type_from == 1) {
                            customer.set_checking_account_balance(source_balance - transfer_amount);
                        } else if (account_type_from == 2) {
                            customer.set_saving_account_balance(source_balance - transfer_amount);
                        } else if (account_type_from == 3) {
                            customer.set_credit_account_balance(source_balance - transfer_amount);
                        }
                        
                        // Add to destination
                        if (account_type_to == 1) {
                            userOperations.deposit_to_checking(customer, transfer_amount);
                        } else if (account_type_to == 2) {
                            userOperations.deposit_to_saving(customer, transfer_amount);
                        } else if (account_type_to == 3) {
                            userOperations.deposit_to_credit(customer, transfer_amount);
                        }
                        
                        System.out.println("Successfully transferred $" + transfer_amount + " from " +from_where + " to " + to_where + " for account holder: " + from_user);
                        transactionLog.logTransfer(customer, account_type_from,  account_type_to, transfer_amount);
                        break;
                    case "withdraws":
                        withdrawal_amount = Float.parseFloat(amount);
                        if (withdrawal_amount <= 0) {
                            System.out.println("Error: Withdraw amount must be greater than zero");
                            break;
                        }
                        
                        /*Gets customer name*/
                        from_user = from_first_name + " " + from_last_name;
                        if (!users_by_name.containsKey(from_user)) {
                            System.out.println("The customer with Name: " + from_first_name + " and last name: " + from_last_name + " was not found");
                            break;
                        } 
                        customer = users_by_name.get(from_user);
                        /*gets account type */
                        account_type_from = get_account_type(from_where);

                        /*processing based on account type */
                        if (account_type_from == 1) {
                            float checking_balance = userOperations.checking_account_balance(customer);
                            if (checking_balance >= withdrawal_amount) {
                                customer.set_checking_account_balance(checking_balance - withdrawal_amount);
                                System.out.println("Successfully withdrew $" + withdrawal_amount + " from checking account");
                            } else {
                                System.out.println("Error: Insufficient funds in checking account");
                            }
                        } else if (account_type_from == 2) {
                            float savings_balance = userOperations.saving_account_balance(customer);
                            if (savings_balance >= withdrawal_amount) {
                                customer.set_saving_account_balance(savings_balance - withdrawal_amount);
                                System.out.println("Successfully withdrew $" + withdrawal_amount + " from savings account");
                            } else {
                                System.out.println("Error: Insufficient funds in savings account");
                            }
                        } else {
                            System.out.println("Error: Cannot withdraw from credit account");
                        }
                        transactionLog.logWithdrawal(customer, account_type_from, withdrawal_amount);
                        break;

                    case "deposits":
                        float deposit_amount = Float.parseFloat(amount);
                        if (deposit_amount <= 0) {
                            System.out.println("Error: Deposit amount must be greater than zero");
                            break;
                        }

                        /*Gets customer name*/
                        to_user = to_first_name + " " + to_last_name;
                        if (!users_by_name.containsKey(to_user)) {
                            System.out.println("The customer with Name: " + to_first_name + " and last name: " + to_last_name + " was not found");
                            break;
                        } 
                        customer = users_by_name.get(to_user);
                        int account_type = get_account_type(to_where);
                        
                        if (account_type == 1) {
                            userOperations.deposit_to_checking(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to checking account");
                        } else if (account_type == 2) {
                            userOperations.deposit_to_saving(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to savings account");
                        } else {
                            userOperations.deposit_to_credit(customer, deposit_amount);
                            System.out.println("Successfully deposited $" + deposit_amount + " to credit account");
                        }
                        transactionLog.logDeposit(customer, account_type, deposit_amount);
                        break;
                    case "inquires":
                        /*Gets customer name*/
                        from_user = from_first_name + " " + from_last_name;
                        if (!users_by_name.containsKey(from_user)) {
                            System.out.println("The customer with Name: " + from_first_name + " and last name: " + from_last_name + " was not found");
                            break;
                        } 
                        customer = users_by_name.get(from_user);
                        Float balance;
                        /*implement inquires*/
                        account_type = get_account_type(from_where);
                        if (account_type == 1) {
                            balance = userOperations.checking_account_balance(customer);
                        } else if (account_type == 2) {
                            balance = userOperations.saving_account_balance(customer);
                        } else {
                            balance = userOperations.credit_account_balance(customer);
                        }
                        System.out.println("The account balance is: $" + String.format("%.2f", balance));
                        transactionLog.logBalanceInquiry(customer, account_type);
                        break;
                    default:
                        System.out.println("there was an error selecting the action");
                        break;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " +e.getMessage());
        }
        System.out.println("Processed all transacitons from file...");
    }
    public static int get_account_type(String account){
        int account_type;
        if (account.equalsIgnoreCase("checking")){
            account_type = 1;
        } else if (account.equalsIgnoreCase("savings")){
            account_type = 2;
        } else if (account.equalsIgnoreCase("credit")){
            account_type = 3;
        } else {
            account_type = -1;
        }
        return account_type;
    }
}