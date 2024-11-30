import java.util.HashMap;
import java.util.Scanner;
class UserMode implements BankMode {
    /**
     * enterMode is the starting point for entering UserMode which calls then the process of performTransaction if log in was successfull 
     * 
     * @param usersByName is a hashmap that contains the customers by name
     * @param userName is how the user is identify 
     * @param accountsByNumber is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void enterMode(HashMap<String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber){
        // Start the manager transaction session for this user
        Customer customer = usersByName.get(userName);
        VerifyUser verifyUser = new VerifyUser();
        ManagerTransactionStatement managerStatement = new ManagerTransactionStatement();
        managerStatement.startSession(customer);
        // performTransaction(usersByName, userName, accountsByNumber);
        System.out.println("===============================");
        System.out.println("Welcome back " + customer.getName()+ "!");
        System.out.println("===============================");
        if (!verifyUser.promptPassword(customer)) 
            exitMode();
        else    
            performTransaction(usersByName, userName, accountsByNumber);
    }

    /**
     * performTransaction allows user to complete transactions as displayed by the manu for the user
     * @param usersByName is a hashmap that contains the customers by name
     * @param userName is how the user is identify 
     * @param accountsByNumber is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void performTransaction(HashMap<String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber){
        TransactionStatement statementGenerator = new TransactionStatement();
        Scanner kb = new Scanner(System.in);
        Log transactionLog = new Log();
        UserOperations userOperations = new UserOperations();
        ManagerTransactionStatement managerStatement = new ManagerTransactionStatement();
        ManagerOperations managerOperations = new ManagerOperations();
        Menus menu = new Menus();
        boolean continueBanking = true;
        Customer customer = usersByName.get(userName);

        while(continueBanking) {
            int accountType = menu.getAccountTypeMenu();
            int transactionOption =  menu.selectTransactionMenu();
            switch (transactionOption) {
                case 1: 
                    userOperations.checkBalance(customer, accountType);
                    transactionLog.logBalanceInquiry(customer, accountType);
                    statementGenerator.recordTransaction(customer, "Balance inquiry for " +
                                (accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit") + " account");
                    managerStatement.recordTransaction(customer, String.format("Balance inquiry for %s account - Current balance: $%.2f",
                            accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit",
                            accountType == 1 ? customer.getCheckingAccountBalance() :
                            accountType == 2 ? customer.getSavingAccountBalance() :
                            customer.getCreditAccountBalance()));
                    break;
                case 2:
                    System.out.println("Enter deposit amount:");
                    float depositAmount = kb.nextFloat();
                    kb.nextLine();
                    userOperations.deposit(customer, accountType, depositAmount);
                    transactionLog.logDeposit(customer, accountType, depositAmount);
                    statementGenerator.recordTransaction(customer, String.format("Deposit of $%.2f to %s account",
                                depositAmount,
                                accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit"));
                    managerStatement.recordTransaction(customer, String.format("Deposit of $%.2f to %s account",
                                depositAmount,
                                accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit"));
                    break;
                case 3:
                    System.out.println("Enter withdraw amount:");
                    float withdrawalAmount = kb.nextFloat();
                    userOperations.withdraw(customer, accountType, withdrawalAmount);
                    transactionLog.logWithdrawal(customer, accountType, withdrawalAmount);
                    statementGenerator.recordTransaction(customer, String.format("Withdrawal of $%.2f from %s account",
                                withdrawalAmount,
                                accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit"));
                    managerStatement.recordTransaction(customer, String.format("Withdrawal of $%.2f from %s account",
                                withdrawalAmount,
                                accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit"));
                    break;
                case 4:
                    System.out.println("From which account?");
                    System.out.println("(1) Checking");
                    System.out.println("(2) Savings");
                    System.out.println("(3) Credit");
                    int sourceAccount = kb.nextInt();
                    
                    System.out.println("To which account?");
                    System.out.println("(1) Checking");
                    System.out.println("(2) Savings");
                    System.out.println("(3) Credit");
                    int destAccount = kb.nextInt();

                    System.out.println("Enter transfer amount:");
                    float transferAmount = kb.nextFloat();
                    
                    userOperations.transferBetweenAccounts(customer, sourceAccount, destAccount, transferAmount);
                    transactionLog.logTransfer(customer, sourceAccount, destAccount, transferAmount);
                    String transferMsg = String.format("Transfer of $%.2f from %s account to %s account",
                                transferAmount,
                                sourceAccount == 1 ? "Checking" : sourceAccount == 2 ? "Savings" : "Credit",
                                destAccount == 1 ? "Checking" : destAccount == 2 ? "Savings" : "Credit");
                    statementGenerator.recordTransaction(customer, transferMsg);
                    managerStatement.recordTransaction(customer, transferMsg);
                    break;
                case 5:
                    if (accountType != 3) {
                        System.out.println("Error: Payments can only be made from credit account");
                        String errorMsg = "Failed payment attempt - invalid account type";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    System.out.println("Enter payment amount:");
                    float paymentAmount = kb.nextFloat();
                    if (paymentAmount <= 0) {
                        System.out.println("Error: Payment amount must be greater than zero");
                        String errorMsg = "Failed payment attempt - invalid amount";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    float creditBalance = userOperations.creditAccountBalance(customer);
                    if (creditBalance + paymentAmount > customer.getCreditAccountMax()) {
                        System.out.println("Error: Payment would exceed credit limit");
                        String errorMsg = "Failed payment attempt - would exceed credit limit";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    customer.setCreditAccountBalance(creditBalance + paymentAmount);
                    System.out.println("Successfully made payment of $" + paymentAmount);
                    transactionLog.logPayment(customer, paymentAmount);
                    String paymentMsg = String.format("Payment of $%.2f made to credit account", paymentAmount);
                    statementGenerator.recordTransaction(customer, paymentMsg);
                    managerStatement.recordTransaction(customer, paymentMsg);
                    break;
                case 6:
                    System.out.println("Enter recipient's name: ");
                    String recipientFullName = kb.nextLine();
                    
                    if (!usersByName.containsKey(recipientFullName)) {
                        System.out.println("Error: Recipient not found");
                        String errorMsg = "Failed transfer attempt - recipient not found: " + recipientFullName;
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    Customer recipientCustomer = usersByName.get(recipientFullName);
                    
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
                    
                    if (userOperations.transferBetweenCustomers(customer, recipientCustomer, accountType, recipientAccountType, interCustomerTransferAmount)) {
                        System.out.println("Successfully transferred $" + String.format("%.2f", interCustomerTransferAmount) + 
                                            " to customer: " + recipientCustomer.getName() + " " + recipientCustomer.getLast());
                        transactionLog.logInterCustomerTransfer(customer, recipientCustomer, accountType, recipientAccountType, interCustomerTransferAmount);
                        String transferSuccessMsg = String.format(
                                    "Transfer of $%.2f from %s account to %s %s's %s account",
                                    interCustomerTransferAmount,
                                    accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit",
                                    recipientCustomer.getName(),
                                    recipientCustomer.getLast(),
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
                    String errorMsg = "Invalid transaction option selected: " + transactionOption;
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