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
    Menus menu = Menus.getInstance();
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
     * Validates the input amount format and value for financial transactions.
     * 
     * @param input The string input representing the monetary amount
     * @return The validated amount as a float
     * @throws InvalidAmountFormatException if the amount format is invalid or the amount is less than or equal to zero
     */
    private float validateAmount(String input) throws InvalidAmountFormatException {
        String moneyPattern = "^\\d+(\\.\\d{2})?$";
        
        if (!input.matches(moneyPattern)) {
            throw new InvalidAmountFormatException("Invalid amount format. Please enter a number with exactly two decimal places (e.g., 100.00)");
        }
        
        float amount = Float.parseFloat(input);
        if (amount <= 0) {
            throw new InvalidAmountFormatException("Amount must be greater than zero");
        }
        
        return amount;
    }
    /**
     * Repeatedly prompts the user for a valid float input until a valid amount is entered.
     * 
     * @param kb The Scanner object used for input
     * @param prompt The message to display when requesting input
     * @return The valid float amount entered by the user
     * @throws InvalidAmountFormatException if the input format is invalid
     */
    private float getValidFloatInput(Scanner kb, String prompt) throws InvalidAmountFormatException {
        while (true) {
            System.out.println(prompt);
            String input = kb.nextLine();
            try {
                return validateAmount(input);
            } catch (InvalidAmountFormatException e) {
                System.out.println("Error: " + e.getMessage() + "\nPlease try again.");
            }
        }
    }
    /**
     * Repeatedly prompts the user for a valid account selection (1-3) until a valid choice is made.
     * 
     * @param kb The Scanner object used for input
     * @param prompt The message to display when requesting input
     * @return The selected account number (1, 2, or 3)
     */
    private int getValidAccountSelection(Scanner kb, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = kb.nextLine().trim();
            if (input.matches("[1-3]")) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid input. Please enter 1, 2, or 3.");
        }
    }
    /**
     * Gets a valid transaction option from the user using the transaction menu.
     * 
     * @param kb The Scanner object used for input
     * @param menu The Menus object used to display the transaction menu
     * @return The selected transaction option (1-6)
     */
    private int getValidTransactionOption(Scanner kb, Menus menu) {
        while (true) {
            int option = menu.selectTransactionMenu();
            if (option >= 1 && option <= 6) {
                return option;
            }
            System.out.println("Invalid option. Please enter a number between 1 and 6.");
        }
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
        boolean continueBanking = true;
        Customer customer = usersByName.get(userName);

        while(continueBanking) {
            int accountType = menu.getAccountTypeMenu();
            int transactionOption = getValidTransactionOption(kb, menu);

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

                case 2: // Deposit
                    try {
                        float depositAmount = getValidFloatInput(kb, "Enter deposit amount:");
                        userOperations.deposit(customer, accountType, depositAmount);
                        transactionLog.logDeposit(customer, accountType, depositAmount);
                        String depositMsg = String.format("Deposit of $%.2f to %s account",
                                depositAmount,
                                accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit");
                        statementGenerator.recordTransaction(customer, depositMsg);
                        managerStatement.recordTransaction(customer, depositMsg);
                    } catch (InvalidAmountFormatException e) {
                        String errorMsg = "Failed deposit attempt - " + e.getMessage();
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                    }
                    break;

                case 3: // Withdraw
                    try {
                        float withdrawalAmount = getValidFloatInput(kb, "Enter withdraw amount:");
                        userOperations.withdraw(customer, accountType, withdrawalAmount);
                        transactionLog.logWithdrawal(customer, accountType, withdrawalAmount);
                        String withdrawMsg = String.format("Withdrawal of $%.2f from %s account",
                                withdrawalAmount,
                                accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit");
                        statementGenerator.recordTransaction(customer, withdrawMsg);
                        managerStatement.recordTransaction(customer, withdrawMsg);
                    } catch (InvalidAmountFormatException e) {
                        String errorMsg = "Failed withdrawal attempt - " + e.getMessage();
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                    }
                    break;

                case 4: // Transfer between accounts
                    try {
                        int sourceAccount = getValidAccountSelection(kb, "From which account?\n(1) Checking\n(2) Savings\n(3) Credit");
                        int destAccount = getValidAccountSelection(kb, "To which account?\n(1) Checking\n(2) Savings\n(3) Credit");
                        float transferAmount = getValidFloatInput(kb, "Enter transfer amount:");
                        
                        userOperations.transferBetweenAccounts(customer, sourceAccount, destAccount, transferAmount);
                        transactionLog.logTransfer(customer, sourceAccount, destAccount, transferAmount);
                        String transferMsg = String.format("Transfer of $%.2f from %s account to %s account",
                                transferAmount,
                                sourceAccount == 1 ? "Checking" : sourceAccount == 2 ? "Savings" : "Credit",
                                destAccount == 1 ? "Checking" : destAccount == 2 ? "Savings" : "Credit");
                        statementGenerator.recordTransaction(customer, transferMsg);
                        managerStatement.recordTransaction(customer, transferMsg);
                    } catch (InvalidAmountFormatException e) {
                        String errorMsg = "Failed transfer attempt - " + e.getMessage();
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                    }
                    break;

                case 5: // Payment
                    if (accountType != 3) {
                        System.out.println("Error: Payments can only be made from credit account");
                        String errorMsg = "Failed payment attempt - invalid account type";
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                        break;
                    }
                    
                    try {
                        float paymentAmount = getValidFloatInput(kb, "Enter payment amount:");
                        
                        float creditBalance = userOperations.creditAccountBalance(customer);
                        if (creditBalance + paymentAmount > customer.getCreditAccountMax()) {
                            throw new InvalidAmountFormatException("Payment would exceed credit limit");
                        }
                        
                        customer.setCreditAccountBalance(creditBalance + paymentAmount);
                        transactionLog.logPayment(customer, paymentAmount);
                        String paymentMsg = String.format("Payment of $%.2f made to credit account", paymentAmount);
                        statementGenerator.recordTransaction(customer, paymentMsg);
                        managerStatement.recordTransaction(customer, paymentMsg);
                    } catch (InvalidAmountFormatException e) {
                        String errorMsg = "Failed payment attempt - " + e.getMessage();
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                    }
                    break;

                case 6: // Inter-customer transfer
                    try {
                        System.out.println("Enter recipient's name: ");
                        String recipientFullName = kb.nextLine();
                        
                        if (!usersByName.containsKey(recipientFullName)) {
                            throw new InvalidAmountFormatException("Recipient not found: " + recipientFullName);
                        }
                        
                        Customer recipientCustomer = usersByName.get(recipientFullName);
                        int recipientAccountType = getValidAccountSelection(kb, "Select recipient's account type:\n(1) Checking\n(2) Savings\n(3) Credit");
                        float interCustomerTransferAmount = getValidFloatInput(kb, "Enter transfer amount:");
                        
                        if (userOperations.transferBetweenCustomers(customer, recipientCustomer, accountType, recipientAccountType, interCustomerTransferAmount)) {
                            System.out.printf("Successfully transferred $%.2f to customer: %s\n", 
                                interCustomerTransferAmount, 
                                recipientFullName);
                            
                            transactionLog.logInterCustomerTransfer(customer, recipientCustomer, accountType, recipientAccountType, interCustomerTransferAmount);
                            String transferSuccessMsg = String.format(
                                    "Transfer of $%.2f from %s account to %s's %s account",
                                    interCustomerTransferAmount,
                                    accountType == 1 ? "Checking" : accountType == 2 ? "Savings" : "Credit",
                                    recipientFullName,
                                    recipientAccountType == 1 ? "Checking" : recipientAccountType == 2 ? "Savings" : "Credit");
                            statementGenerator.recordTransaction(customer, transferSuccessMsg);
                            managerStatement.recordTransaction(customer, transferSuccessMsg);
                        } else {
                            throw new InvalidAmountFormatException("Transfer failed - insufficient funds or invalid data");
                        }
                    } catch (InvalidAmountFormatException e) {
                        String errorMsg = "Failed transfer attempt - " + e.getMessage();
                        statementGenerator.recordTransaction(customer, errorMsg);
                        managerStatement.recordTransaction(customer, errorMsg);
                    }
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