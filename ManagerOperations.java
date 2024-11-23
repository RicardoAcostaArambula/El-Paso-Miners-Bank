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
    /**
     * Displays account information by name
     * 
     * @param customer is the target user for the information
     * 
     */
    public void dislay_account_information_by_name(Customer customer){
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
     * Displays account information by account number
     * 
     * @param customer is the target user for the information
     * @param account_type is an int that represents the account type to dislay the information
     * @param account_number is an int that represents the account number to display the information
     */

     public void dislay_account_information_by_account_number(Customer customer, int account_number, int account_type){
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
    public void createAccount(){
        
    }
}