class UserOperations implements Operations {
    public UserOperations(){

    }
     /**
     * Returns the checking account balance for a customer.
     *
     * @param customer the customer whose checking account balance is requested
     * @return the checking account balance
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
        System.out.println("Successfully deposited $" + amount + " to checking account");
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
        System.out.println("Successfully deposited $" + amount + " to savings account");
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
        System.out.println("Successfully deposited $" + amount + " to credit account");
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
        System.out.println("Successfully transferred $" + amount);
        return true;
    }
    public void checkBalance(Customer customer, int accountType){
        float balance;
        if (accountType == 1) {
            balance = checkingAccountBalance(customer);
        } else if (accountType == 2) {
            balance = savingAccountBalance(customer);
        } else {
            balance = creditAccountBalance(customer);
        }
        System.out.println("The account balance is: $" + String.format("%.2f", balance));
    }
    public void deposit(Customer customer, int accountType, float amount){
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be greater than zero");
            return;
        }
        if (accountType == 1) {
            depositToChecking(customer, amount);
        } else if (accountType == 2) {
            depositToSaving(customer, amount);
        } else {
            depositToCredit(customer, amount);
        }
    }
    public void withdraw(Customer customer, int accountType, float amount){
        if (amount <= 0) {
            System.out.println("Error: Withdraw amount must be greater than zero");
            return;
        }

        if (accountType == 1) {
            float checkingBalance = checkingAccountBalance(customer);
            if (checkingBalance >= amount) {
                customer.setCheckingAccountBalance(checkingBalance - amount);
                System.out.println("Successfully withdrew $" + amount + " from checking account");
            } else {
                System.out.println("Error: Insufficient funds in checking account");
            }
        } else if (accountType == 2) {
            float savingsBalance = savingAccountBalance(customer);
            if (savingsBalance >= amount) {
                customer.setSavingAccountBalance(savingsBalance - amount);
                System.out.println("Successfully withdrew $" + amount + " from savings account");
            } else {
                System.out.println("Error: Insufficient funds in savings account");
            }
        } else {
            System.out.println("Error: Cannot withdraw from credit account");
        }
    }
    public void transferBetweenAccounts(Customer customer, int sourceAccount, int destAccount, float transferAmount){
        if (sourceAccount == destAccount) {
            System.out.println("Error: Cannot transfer to the same account");
            return;
        }
        float sourceBalance = 0;
        if (sourceAccount == 1) {
            sourceBalance = checkingAccountBalance(customer);
        } else if (sourceAccount == 2) {
            sourceBalance = savingAccountBalance(customer);
        } else if (sourceAccount == 3) {
            sourceBalance = creditAccountBalance(customer);
        }
        
        if (sourceBalance < transferAmount) {
            System.out.println("Error: Insufficient funds in source account");
            return;
        }
        
        if (sourceAccount == 1) {
            customer.setCheckingAccountBalance(sourceBalance - transferAmount);
        } else if (sourceAccount == 2) {
            customer.setSavingAccountBalance(sourceBalance - transferAmount);
        } else if (sourceAccount == 3) {
            customer.setCreditAccountBalance(sourceBalance - transferAmount);
        }
        
        if (destAccount == 1) {
            depositToChecking(customer, transferAmount);
        } else if (destAccount == 2) {
            depositToSaving(customer, transferAmount);
        } else if (destAccount == 3) {
            depositToCredit(customer, transferAmount);
        }
    }
}