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
        System.out.println("Successfully deposited $" + amount + " to checking account");
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
        System.out.println("Successfully deposited $" + amount + " to savings account");
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
        System.out.println("Successfully transferred $" + amount);
        return true;
    }
    public void check_balance(Customer customer, int account_type){
        float balance;
        if (account_type == 1) {
            balance = checking_account_balance(customer);
        } else if (account_type == 2) {
            balance = saving_account_balance(customer);
        } else {
            balance = credit_account_balance(customer);
        }
        System.out.println("The account balance is: $" + String.format("%.2f", balance));
    }
    public void deposit(Customer customer, int account_type, float amount){
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be greater than zero");
            return;
        }
        if (account_type == 1) {
            deposit_to_checking(customer, amount);
        } else if (account_type == 2) {
            deposit_to_saving(customer, amount);
        } else {
            deposit_to_credit(customer, amount);
        }
    }
    public void withdraw(Customer customer, int account_type, float amount){
        if (amount <= 0) {
            System.out.println("Error: Withdraw amount must be greater than zero");
            return;
        }

        if (account_type == 1) {
            float checking_balance = checking_account_balance(customer);
            if (checking_balance >= amount) {
                customer.set_checking_account_balance(checking_balance - amount);
                System.out.println("Successfully withdrew $" + amount + " from checking account");
            } else {
                System.out.println("Error: Insufficient funds in checking account");
            }
        } else if (account_type == 2) {
            float savings_balance = saving_account_balance(customer);
            if (savings_balance >= amount) {
                customer.set_saving_account_balance(savings_balance - amount);
                System.out.println("Successfully withdrew $" + amount + " from savings account");
            } else {
                System.out.println("Error: Insufficient funds in savings account");
            }
        } else {
            System.out.println("Error: Cannot withdraw from credit account");
        }
    }
    public void transfer_between_accounts(Customer customer, int source_account, int dest_account, float transfer_amount){
        if (source_account == dest_account) {
            System.out.println("Error: Cannot transfer to the same account");
            return;
        }
        float source_balance = 0;
        if (source_account == 1) {
            source_balance = checking_account_balance(customer);
        } else if (source_account == 2) {
            source_balance = saving_account_balance(customer);
        } else if (source_account == 3) {
            source_balance = credit_account_balance(customer);
        }
        
        if (source_balance < transfer_amount) {
            System.out.println("Error: Insufficient funds in source account");
            return;
        }
        
        if (source_account == 1) {
            customer.set_checking_account_balance(source_balance - transfer_amount);
        } else if (source_account == 2) {
            customer.set_saving_account_balance(source_balance - transfer_amount);
        } else if (source_account == 3) {
            customer.set_credit_account_balance(source_balance - transfer_amount);
        }
        
        if (dest_account == 1) {
            deposit_to_checking(customer, transfer_amount);
        } else if (dest_account == 2) {
            deposit_to_saving(customer, transfer_amount);
        } else if (dest_account == 3) {
            deposit_to_credit(customer, transfer_amount);
        }
    }
}