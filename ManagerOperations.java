class ManagerOperations implements Operations {
    public ManagerOperations(){
    }
     /**
     * Returns the checking account balance for a customer.
     *
     * @param customer the customer whose checking account balance is requested
     * @return the checking account balance
     */
    /*returns checking account balance */
    @Override
    public float checking_account_balance(Customer customer){
        return customer.get_checking_account_balance();
    }
    /**
     * Returns the savings account balance for a customer.
     *
     * @param customer the customer whose savings account balance is requested
     * @return the savings account balance
     */
    @Override
    public float saving_account_balance(Customer customer){
        return customer.get_saving_account_balance();
    }

    /**
     * Returns the credit account balance for a customer.
     *
     * @param customer the customer whose credit account balance is requested
     * @return the credit account balance
     */

     @Override
    public float credit_account_balance(Customer customer){
        return customer.get_credit_account_balance();
    }

    /**
     * Deposits funds into a customer's checking account.
     *
     * @param customer the customer whose checking account will be credited
     * @param amount the amount to be deposited
     */
    @Override
    public void deposit_to_checking(Customer customer, float amount){
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
    @Override
    public void deposit_to_saving(Customer customer, float amount){
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
    @Override
    public void deposit_to_credit(Customer customer, float amount){
        float current_balance = credit_account_balance(customer);
        float new_balance = current_balance + amount;
        customer.set_credit_account_balance(new_balance);
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
            sourceBalance = checking_account_balance(sourceCustomer);
        } else if (sourceAccountType == 2) {
            sourceBalance = saving_account_balance(sourceCustomer);
        } else if (sourceAccountType == 3) {
            sourceBalance = credit_account_balance(sourceCustomer);
        }
        
        // Check if source has sufficient funds
        if (sourceBalance < amount) {
            System.out.println("Error: Insufficient funds in source account");
            return false;
        }
        
        // Deduct from source account
        if (sourceAccountType == 1) {
            sourceCustomer.set_checking_account_balance(sourceBalance - amount);
        } else if (sourceAccountType == 2) {
            sourceCustomer.set_saving_account_balance(sourceBalance - amount);
        } else if (sourceAccountType == 3) {
            sourceCustomer.set_credit_account_balance(sourceBalance - amount);
        }
        
        // Add to destination account
        if (destAccountType == 1) {
            deposit_to_checking(destCustomer, amount);
        } else if (destAccountType == 2) {
            deposit_to_saving(destCustomer, amount);
        } else if (destAccountType == 3) {
            deposit_to_credit(destCustomer, amount);
        }
        
        return true;
    }
 