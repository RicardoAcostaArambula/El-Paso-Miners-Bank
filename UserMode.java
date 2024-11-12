import java.util.HashMap;
import java.util.Scanner;
class UserMode implements BankMode {
    /**
     * enterMode is the starting point for entering UserMode which calls then the process of performTransaction
     * 
     * @param users_by_name is a hashmap that contains the customers by name
     * @param username is how the user is identify 
     * @param accounts_by_number is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void enterMode(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number){
        System.out.println("Entering User Mode...");
        // Start the manager transaction session for this user
        Customer customer = users_by_name.get(username);
        ManagerTransactionStatement managerStatement = new ManagerTransactionStatement();
        managerStatement.startSession(customer);
        performTransaction(users_by_name, username, accounts_by_number);
    }

    /**
     * performTransaction allows user to complete transactions as displayed by the manu for the user
     * @param users_by_name is a hashmap that contains the customers by name
     * @param username is how the user is identify 
     * @param accounts_by_number is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void performTransaction(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number){
        TransactionStatement statementGenerator = new TransactionStatement();
        Scanner kb = new Scanner(System.in);
        Log transactionLog = new Log();
        UserOperations userOperations = new UserOperations();
        ManagerTransactionStatement managerStatement = new ManagerTransactionStatement();
        ManagerOperations managerOperations = new ManagerOperations();
        Menus menu = new Menus();
        boolean continueBanking = true;
        Customer customer = users_by_name.get(username);

        while(continueBanking) {
            int account_type = menu.get_account_type_menu();
            int transaction_option =  menu.select_transaction_menu();
            switch (transaction_option) {
                case 1: 
                    userOperations.check_balance(customer, account_type);
                    transactionLog.logBalanceInquiry(customer, account_type);
                    statementGenerator.recordTransaction(customer, "Balance inquiry for " +
                                (account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit") + " account");
                    managerStatement.recordTransaction(customer, String.format("Balance inquiry for %s account - Current balance: $%.2f",
                            account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit",
                            account_type == 1 ? customer.get_checking_account_balance() :
                            account_type == 2 ? customer.get_saving_account_balance() :
                            customer.get_credit_account_balance()));
                    break;
                case 2:
                    System.out.println("Enter deposit amount:");
                    float deposit_amount = kb.nextFloat();
                    kb.nextLine();
                    userOperations.deposit(customer, account_type, deposit_amount);
                    transactionLog.logDeposit(customer, account_type, deposit_amount);
                    statementGenerator.recordTransaction(customer, String.format("Deposit of $%.2f to %s account",
                                deposit_amount,
                                account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit"));
                    managerStatement.recordTransaction(customer, String.format("Deposit of $%.2f to %s account",
                                deposit_amount,
                                account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit"));
                    break;
                case 3:
                    System.out.println("Enter withdraw amount:");
                    float withdrawal_amount = kb.nextFloat();
                    userOperations.withdraw(customer, account_type, withdrawal_amount);
                    transactionLog.logWithdrawal(customer, account_type, withdrawal_amount);
                    statementGenerator.recordTransaction(customer, String.format("Withdrawal of $%.2f from %s account",
                                withdrawal_amount,
                                account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit"));
                    managerStatement.recordTransaction(customer, String.format("Withdrawal of $%.2f from %s account",
                                withdrawal_amount,
                                account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit"));
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

                    System.out.println("Enter transfer amount:");
                    float transfer_amount = kb.nextFloat();
                    
                    userOperations.transfer_between_accounts(customer, source_account, dest_account, transfer_amount);
                    transactionLog.logTransfer(customer, source_account, dest_account, transfer_amount);
                    String transferMsg = String.format("Transfer of $%.2f from %s account to %s account",
                                transfer_amount,
                                source_account == 1 ? "Checking" : source_account == 2 ? "Savings" : "Credit",
                                dest_account == 1 ? "Checking" : dest_account == 2 ? "Savings" : "Credit");
                    statementGenerator.recordTransaction(customer, transferMsg);
                    managerStatement.recordTransaction(customer, transferMsg);
                    break;
                case 5:
                    if (account_type != 3) {
                        System.out.println("Error: Payments can only be made from credit account");
                        String errorMsg = "Failed payment attempt - invalid account type";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    System.out.println("Enter payment amount:");
                    float payment_amount = kb.nextFloat();
                    if (payment_amount <= 0) {
                        System.out.println("Error: Payment amount must be greater than zero");
                        String errorMsg = "Failed payment attempt - invalid amount";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    float credit_balance = userOperations.credit_account_balance(customer);
                    if (credit_balance + payment_amount > customer.get_credit_account_max()) {
                        System.out.println("Error: Payment would exceed credit limit");
                        String errorMsg = "Failed payment attempt - would exceed credit limit";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    customer.set_credit_account_balance(credit_balance + payment_amount);
                    System.out.println("Successfully made payment of $" + payment_amount);
                    transactionLog.logPayment(customer, payment_amount);
                    String paymentMsg = String.format("Payment of $%.2f made to credit account", payment_amount);
                    statementGenerator.recordTransaction(customer, paymentMsg);
                    managerStatement.recordTransaction(customer, paymentMsg);
                    break;
                case 6:
                    System.out.println("Enter recipient's name: ");
                    String recipient_full_name = kb.nextLine();
                    
                    if (!users_by_name.containsKey(recipient_full_name)) {
                        System.out.println("Error: Recipient not found");
                        String errorMsg = "Failed transfer attempt - recipient not found: " + recipient_full_name;
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
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
                        String errorMsg = "Failed transfer attempt - invalid recipient account type";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    System.out.println("Enter transfer amount:");
                    float interCustomerTransferAmount = kb.nextFloat();
                    
                    if (interCustomerTransferAmount <= 0) {
                        System.out.println("Error: Transfer amount must be greater than zero");
                        String errorMsg = "Failed transfer attempt - invalid amount";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    if (userOperations.transferBetweenCustomers(customer, recipientCustomer, account_type, recipientAccountType, interCustomerTransferAmount)) {
                        System.out.println("Successfully transferred $" + String.format("%.2f", interCustomerTransferAmount) + 
                                            " to customer: " + recipientCustomer.get_name() + " " + recipientCustomer.get_last());
                        transactionLog.logInterCustomerTransfer(customer, recipientCustomer, account_type, recipientAccountType, interCustomerTransferAmount);
                        String transferSuccessMsg = String.format(
                                    "Transfer of $%.2f from %s account to %s %s's %s account",
                                    interCustomerTransferAmount,
                                    account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit",
                                    recipientCustomer.get_name(),
                                    recipientCustomer.get_last(),
                                    recipientAccountType == 1 ? "Checking" : recipientAccountType == 2 ? "Savings" : "Credit");
                        statementGenerator.recordTransaction(customer, transferSuccessMsg);
                        managerStatement.recordTransaction(customer, transferSuccessMsg);
                    } else {
                        System.out.println("Transfer failed. Please check account balances or input data.");
                        String errorMsg = "Failed transfer attempt - insufficient funds or invalid data";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                    }
                    break;
                default:
                    System.out.println("Invalid option selected");
                    String errorMsg = "Invalid transaction option selected: " + transaction_option;
                    statementGenerator.recordTransaction(customer, errorMsg);
                    managerStatement.recordTransaction(customer, errorMsg);
                    break;
            }
            System.out.println("Would you like to exit? (yes/no)");
            String response = kb.nextLine().trim().toLowerCase();
            continueBanking = !response.equals("yes");
        }
        exitMode();
    }

    /** 
     * ExitMode allows user to return to the main menu
     */
    @Override
    public void exitMode(){
        System.out.println("Exiting User Mode...");
    }
}