import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


/**
 * TransacitonReader serves as the processor for the transactions provided in the file
 * 
 * @author Sebastian Nares & ricardo Acosta
 */
class TransactionReader {
    
    private TransactionReader (){
    }
    /**
     * reads and performs all transactions in the file
     * 
     * @param filename is the name of the file that contains the transactions
     * @return None
     */
    public static void transactionReader(String filename, HashMap <String, Customer>  usersByName, Log transactionLog){
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
                String fromFirstName;
                /*From Last Name*/
                String fromLastName;
                /*From Where*/
                String fromWhere = "";
                /*Action */
                String action = "";
                /*To First Name*/
                String toFirstName = "";
                /*To Last Name*/
                String toLastName = "";
                /*To Where*/
                String toWhere = "";
                /*Amount*/
                String amount = "";
                String fromUser;
                float withdrawalAmount;
                if (items.length == 4){
                    fromFirstName = items[0];
                    /*From Last Name*/
                    fromLastName = items[1];
                    /*From Where*/
                    fromWhere = items[2];
                    /*Action */
                    action = items[3];
                } else {
                    /*From First Name */
                    fromFirstName = items[0];
                    /*From Last Name*/
                    fromLastName = items[1];
                    /*From Where*/
                    fromWhere = items[2];
                    /*Action */
                    action = items[3];
                    /*To First Name*/
                    toFirstName = items[4];
                    /*To Last Name*/
                    toLastName = items[5];
                    /*To Where*/
                    toWhere = items[6];
                    /*Amount*/
                    amount = items[7];
                }
                
                
                switch (action){
                    /*Mickey,Mouse,Checking,pays,Donald,Duck,Checking,100 */
                    case "pays":
                        fromUser = fromFirstName + " " + fromLastName;
                        String toUser = toFirstName + " " + toLastName;
                        if (!usersByName.containsKey(fromUser)) {
                            System.out.println("The customer with Name: " + fromFirstName + " and last name: " + fromLastName + " was not found");
                        } else if (!usersByName.containsKey(toUser)){
                            System.out.println("The customer with Name: " + toFirstName + " and last name: " + toLastName + " was not found");
                        } else {
                            /*get the customer object for each person */
                            Customer fromCustomer = usersByName.get(fromUser);
                            Customer toCustomer = usersByName.get(toUser);
                            float interCustomerTransferAmount = Float.parseFloat(amount);
                            if (interCustomerTransferAmount <= 0) {
                                System.out.println("Error: Transfer amount must be greater than zero");
                                break;
                            }
                            /*get account type*/
                            int accountTypeFrom = getAccountType(fromWhere);
                            int accountTypeTo = getAccountType(fromWhere);
                            if (accountTypeFrom == -1 || accountTypeTo==-1){
                                System.out.println("There was an error getting the account type");
                                break;
                            }

                            if (userOperations.transferBetweenCustomers(fromCustomer, toCustomer, accountTypeFrom, accountTypeTo, interCustomerTransferAmount)) {
                                System.out.println("Successfully transferred $" + String.format("%.2f", interCustomerTransferAmount) + 
                                                   " to customer: " + toCustomer.getName() + " " + toCustomer.getLast());
                                /*recording transaction*/
                                transactionLog.logInterCustomerTransfer(fromCustomer, toCustomer, accountTypeFrom, accountTypeTo, interCustomerTransferAmount);
                            } else {
                                System.out.println("Transfer failed. Please check account balances or input data.");
                            }
                        }
                        break;
                    case "transfers":
                        /*Gets customer name*/
                        fromUser = fromFirstName + " " + fromLastName;
                        if (!usersByName.containsKey(fromUser)) {
                            System.out.println("The customer with Name: " + fromFirstName + " and last name: " + fromLastName + " was not found");
                            break;
                        } 
                        Customer customer = usersByName.get(fromUser);
                        int accountTypeFrom = getAccountType(fromWhere);
                        int accountTypeTo = getAccountType(toWhere);
                        float transferAmount = Float.parseFloat(amount);
                        if (accountTypeFrom == -1 || accountTypeTo== -1){
                            System.out.println("There was an error getting the account type");
                            break;
                        } else if (accountTypeFrom == accountTypeTo) {
                            System.out.println("Error: Cannot transfer to the same account");
                            break;
                        }
                        // Get source account balance
                        float sourceBalance = 0;
                        if (accountTypeFrom == 1) {
                            sourceBalance = userOperations.checkingAccountBalance(customer);
                        } else if (accountTypeFrom == 2) {
                            sourceBalance = userOperations.savingAccountBalance(customer);
                        } else if (accountTypeFrom == 3) {
                            sourceBalance = userOperations.creditAccountBalance(customer);
                        }

                        // Check if source has sufficient funds
                        if (sourceBalance < transferAmount) {
                            System.out.println("Error: Insufficient funds in source account");
                            break;
                        }

                        // Perform transfer
                        // Deduct from source
                        if (accountTypeFrom == 1) {
                            customer.setCheckingAccountBalance(sourceBalance - transferAmount);
                        } else if (accountTypeFrom == 2) {
                            customer.setSavingAccountBalance(sourceBalance - transferAmount);
                        } else if (accountTypeFrom == 3) {
                            customer.setCreditAccountBalance(sourceBalance - transferAmount);
                        }
                        
                        // Add to destination
                        if (accountTypeTo == 1) {
                            userOperations.depositToChecking(customer, transferAmount);
                        } else if (accountTypeTo == 2) {
                            userOperations.depositToSaving(customer, transferAmount);
                        } else if (accountTypeTo == 3) {
                            userOperations.depositToCredit(customer, transferAmount);
                        }
                        
                        System.out.println("Successfully transferred $" + transferAmount + " from " +fromWhere + " to " + toWhere + " for account holder: " + fromUser);
                        transactionLog.logTransfer(customer, accountTypeFrom,  accountTypeTo, transferAmount);
                        break;
                    case "withdraws":
                        withdrawalAmount = Float.parseFloat(amount);
                        if (withdrawalAmount <= 0) {
                            System.out.println("Error: Withdraw amount must be greater than zero");
                            break;
                        }
                        
                        /*Gets customer name*/
                        fromUser = fromFirstName + " " + fromLastName;
                        if (!usersByName.containsKey(fromUser)) {
                            System.out.println("The customer with Name: " + fromFirstName + " and last name: " + fromLastName + " was not found");
                            break;
                        } 
                        customer = usersByName.get(fromUser);
                        /*gets account type */
                        accountTypeFrom = getAccountType(fromWhere);

                        /*processing based on account type */
                        if (accountTypeFrom == 1) {
                            float checkingBalance = userOperations.checkingAccountBalance(customer);
                            if (checkingBalance >= withdrawalAmount) {
                                customer.setCheckingAccountBalance(checkingBalance - withdrawalAmount);
                                System.out.println("Successfully withdrew $" + withdrawalAmount + " from checking account");
                            } else {
                                System.out.println("Error: Insufficient funds in checking account");
                            }
                        } else if (accountTypeFrom == 2) {
                            float savingsBalance = userOperations.savingAccountBalance(customer);
                            if (savingsBalance >= withdrawalAmount) {
                                customer.setSavingAccountBalance(savingsBalance - withdrawalAmount);
                                System.out.println("Successfully withdrew $" + withdrawalAmount + " from savings account");
                            } else {
                                System.out.println("Error: Insufficient funds in savings account");
                            }
                        } else {
                            System.out.println("Error: Cannot withdraw from credit account");
                        }
                        transactionLog.logWithdrawal(customer, accountTypeFrom, withdrawalAmount);
                        break;

                    case "deposits":
                        float depositAmount = Float.parseFloat(amount);
                        if (depositAmount <= 0) {
                            System.out.println("Error: Deposit amount must be greater than zero");
                            break;
                        }

                        /*Gets customer name*/
                        toUser = toFirstName + " " + toLastName;
                        if (!usersByName.containsKey(toUser)) {
                            System.out.println("The customer with Name: " + toFirstName + " and last name: " + toLastName + " was not found");
                            break;
                        } 
                        customer = usersByName.get(toUser);
                        int accountType = getAccountType(toWhere);
                        
                        if (accountType == 1) {
                            userOperations.depositToChecking(customer, depositAmount);
                            System.out.println("Successfully deposited $" + depositAmount + " to checking account");
                        } else if (accountType == 2) {
                            userOperations.depositToSaving(customer, depositAmount);
                            System.out.println("Successfully deposited $" + depositAmount + " to savings account");
                        } else {
                            userOperations.depositToCredit(customer, depositAmount);
                            System.out.println("Successfully deposited $" + depositAmount + " to credit account");
                        }
                        transactionLog.logDeposit(customer, accountType, depositAmount);
                        break;
                    case "inquires":
                        /*Gets customer name*/
                        fromUser = fromFirstName + " " + fromLastName;
                        if (!usersByName.containsKey(fromUser)) {
                            System.out.println("The customer with Name: " + fromFirstName + " and last name: " + fromLastName + " was not found");
                            break;
                        } 
                        customer = usersByName.get(fromUser);
                        Float balance;
                        /*implement inquires*/
                        accountType = getAccountType(fromWhere);
                        if (accountType == 1) {
                            balance = userOperations.checkingAccountBalance(customer);
                        } else if (accountType == 2) {
                            balance = userOperations.savingAccountBalance(customer);
                        } else {
                            balance = userOperations.creditAccountBalance(customer);
                        }
                        System.out.println("The account balance is: $" + String.format("%.2f", balance));
                        transactionLog.logBalanceInquiry(customer, accountType);
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

  

    /**
     * Gets the user account type to be worked on
     * 
     * @return accountType is the user selected option
     */
    
    public static int getAccountType(String account){
        int accountType;
        if (account.equalsIgnoreCase("checking")){
            accountType = 1;
        } else if (account.equalsIgnoreCase("savings")){
            accountType = 2;
        } else if (account.equalsIgnoreCase("credit")){
            accountType = 3;
        } else {
            accountType = -1;
        }
        return accountType;
    }
}