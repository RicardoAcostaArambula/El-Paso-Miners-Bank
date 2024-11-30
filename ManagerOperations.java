class ManagerOperations implements Operations {
    public ManagerOperations(){
    }
     /**
     * Returns the checking account balance for a customer.
     *
     * @param customer the customer whose checking account balance is requested
     * @return the checking account balance
     * @author Sebastian Nares, Ricardo Acosta
     */
    /*returns checking account balance */
    @Override
    public float checkingAccountBalance(Customer customer){
        return customer.getCheckingAccountBalance();
    }
    /**
     * Returns the savings account balance for a customer.
     *
     * @param customer the customer whose savings account balance is requested
     * @return the savings account balance
     */
    @Override
    public float savingAccountBalance(Customer customer){
        return customer.getSavingAccountBalance();
    }

    /**
     * Returns the credit account balance for a customer.
     *
     * @param customer the customer whose credit account balance is requested
     * @return the credit account balance
     */

     @Override
    public float creditAccountBalance(Customer customer){
        return customer.getCreditAccountBalance();
    }

    /**
     * Deposits funds into a customer's checking account.
     *
     * @param customer the customer whose checking account will be credited
     * @param amount the amount to be deposited
     */
    @Override
    public void depositToChecking(Customer customer, float amount){
        float currentBalance = checkingAccountBalance(customer);
        float newBalance = currentBalance + amount;
        customer.setCheckingAccountBalance(newBalance);
    }

    /**
     * Deposits funds into a customer's savings account.
     *
     * @param customer the customer whose savings account will be credited
     * @param amount the amount to be deposited
     */
    @Override
    public void depositToSaving(Customer customer, float amount){
        float currentBalance = savingAccountBalance(customer);
        float newBalance = currentBalance + amount;
        customer.setSavingAccountBalance(newBalance);
    }

    /**
     * Deposits funds into a customer's credit account.
     *
     * @param customer the customer whose credit account will be credited
     * @param amount the amount to be deposited
     */
    @Override
    public void depositToCredit(Customer customer, float amount){
        float currentBalance = creditAccountBalance(customer);
        float newBalance = currentBalance + amount;
        customer.setCreditAccountBalance(newBalance);
    }
     /**
     * Transfers funds between two customers' accounts.
     *
     * @param sourceCustomer the customer sending the money
     * @param destCustomer the customer receiving the money
     * @param sourceAccountType the type of account to transfer from (1=Checking, 2=Savings, 3=Credit)
     * @param destAccountType the type of account to transfer to (1=Checking, 2=Savings, 3=Credit)
     * @param amount the amount to transfer
     * @return true if transfer was successful, false otherwise
     */
    @Override
    public boolean transferBetweenCustomers(Customer sourceCustomer, Customer destCustomer, 
        int sourceAccountType, int destAccountType, float amount) {
        
        // Get source account balance
        float sourceBalance = 0;
        if (sourceAccountType == 1) {
            sourceBalance = checkingAccountBalance(sourceCustomer);
        } else if (sourceAccountType == 2) {
            sourceBalance = savingAccountBalance(sourceCustomer);
        } else if (sourceAccountType == 3) {
            sourceBalance = creditAccountBalance(sourceCustomer);
        }
        
        // Check if source has sufficient funds
        if (sourceBalance < amount) {
            System.out.println("Error: Insufficient funds in source account");
            return false;
        }
        
        // Deduct from source account
        if (sourceAccountType == 1) {
            sourceCustomer.setCheckingAccountBalance(sourceBalance - amount);
        } else if (sourceAccountType == 2) {
            sourceCustomer.setSavingAccountBalance(sourceBalance - amount);
        } else if (sourceAccountType == 3) {
            sourceCustomer.setCreditAccountBalance(sourceBalance - amount);
        }
        
        // Add to destination account
        if (destAccountType == 1) {
            depositToChecking(destCustomer, amount);
        } else if (destAccountType == 2) {
            depositToSaving(destCustomer, amount);
        } else if (destAccountType == 3) {
            depositToCredit(destCustomer, amount);
        }
        
        return true;
    }
    /**
     * Displays account information by name
     * 
     * @param customer is the target user for the information
     * 
     */
    public void dislayAccountInformationByName(Customer customer){
        String name = customer.getName();
        String last = customer.getLast();
        int id = customer.getAccountId();
        int accountNumberSaving, accountNumberChecking, accountNumberCredit;
        float balanceChecking, balanceSaving, balanceCredit;

        accountNumberSaving = customer.getCheckingAccountNumber();
        balanceChecking = customer.getCheckingAccountBalance();

        accountNumberChecking = customer.getSavingAccountNumber();
        balanceSaving = customer.getSavingAccountBalance();

        accountNumberCredit = customer.getCreditAccountNumber();
        balanceCredit = customer.getCreditAccountBalance();

        System.out.println("Account holder: " + name + " " + last + " with ID: " + id);

        System.out.println("The Account of type: Checkings | number: " + accountNumberChecking + " | Balance: " + balanceChecking);

        System.out.println("The Account of type: Savings | number: " + accountNumberSaving + " | Balance: " + balanceSaving);

        System.out.println("The Account of type: Credit | number: " + accountNumberCredit + " | Balance: " + balanceCredit);
    }

    /**
     * Displays account information by account number
     * 
     * @param customer is the target user for the information
     * @param accountType is an int that represents the account type to dislay the information
     * @param accountNumber is an int that represents the account number to display the information
     */

     public void dislayAccountInformationByAccountNumber(Customer customer, int accountNumber, int accountType){
        String name = customer.getName();
        String account;
        String last = customer.getLast();
        int id = customer.getAccountId();
        float balance;
        if (accountType == 1){
            balance = customer.getCheckingAccountBalance();
            account = "Checking";
        } else if (accountType == 2){
            balance = customer.getSavingAccountBalance();
            account = "Saving";
        } else {
            balance = customer.getCreditAccountBalance();
            account = "Credit";
        }
        System.out.println("Account holder: " + name + " " + last + " with ID: " + id);
        System.out.println("The Account of type: " + account + ", with number: " + accountNumber);
        System.out.println("Balance: " + balance);
    }
    public void createAccount(){
        
    }
}