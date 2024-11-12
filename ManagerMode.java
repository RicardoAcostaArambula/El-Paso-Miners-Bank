import java.util.HashMap;
import java.util.Scanner;
class ManagerMode implements BankMode {
     /**
     * enterMode is the starting point for entering managerMode which calls then the process of performTransaction
     * 
     * @param users_by_name is a hashmap that contains the customers by name
     * @param username is how the user is identify 
     * @param accounts_by_number is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void enterMode(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number){
        System.out.println("Entering Manager Mode...");
        performTransaction(users_by_name, username, accounts_by_number);
    }
    /**
     * performTransaction allows user to complete transactions as displayed by the menu for the manager
     * @param users_by_name is a hashmap that contains the customers by name
     * @param username is how the user is identify 
     * @param accounts_by_number is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void performTransaction(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number){
        boolean continueTeller = true;
        ManagerOperations managerOperations = new ManagerOperations();
        Log transactionLog = new Log();
        Scanner kb = new Scanner(System.in);
        while (continueTeller) {
            boolean inquiry_chosen = false;
            int inquiry_type;
            do {
                System.out.println("Please select one of the following options: ");
                System.out.println("(1) Inquiry account by name");
                System.out.println("(2) Inquiry account by account number");
                System.out.println("(3) Process transactions from file");
                inquiry_type = kb.nextInt();
                if (1 <= inquiry_type && inquiry_type <= 3) {
                    inquiry_chosen = true;
                } else {
                    System.out.println("Please choose a valid option");
                }
            } while (!inquiry_chosen);

            if (inquiry_type == 1) {
                boolean valid = false;
                String account_holder;
                do {
                    if (kb.hasNextLine()) {
                        kb.nextLine();
                    }
                    System.out.println("Whose account would you like to inquire about? (Enter full name as following: FirstName LastName)");
                    account_holder = kb.nextLine();
                    if (!users_by_name.containsKey(account_holder)) {
                        System.out.println("Please enter a valid name");
                    } else {
                        valid = true;
                    }
                } while (!valid);
                Customer customer = users_by_name.get(account_holder);
                managerOperations.dislay_account_information_by_name(customer);
            } else if (inquiry_type == 2) {
                boolean valid = false;
                int account_type;
                do {
                    System.out.println("What is the account type: ");
                    System.out.println("(1) Checkings");
                    System.out.println("(2) Savings");
                    System.out.println("(3) Credit");
                    account_type = kb.nextInt();
                    if (1 <= account_type && account_type <= 3) {
                        valid = true;
                    } else {
                        System.out.println("Please choose a valid account");
                    }
                } while (!valid);
                valid = false;
                int account_number;
                do {
                    System.out.println("What is the account number?");
                    account_number = kb.nextInt();
                    if (kb.hasNextLine()) {
                        kb.nextLine();
                    }
                    if (!accounts_by_number.containsKey(account_number)) {
                        System.out.println("Please enter a valid account number");
                    } else {
                        valid = true;
                    }
                } while (!valid);
                Customer customer = accounts_by_number.get(account_number);
                managerOperations.dislay_account_information_by_account_number(customer, account_number, account_type);
                transactionLog.logBalanceInquiry(customer, account_type);
            } else if (inquiry_type == 3) {
                System.out.print("The transaction process from the file will start shortly...");
                TransactionReader.transaction_reader("Transactions(1).csv", users_by_name, transactionLog);
            }

            System.out.println("Would you like to exit? (yes/no)");
            String response = kb.nextLine().trim().toLowerCase();
            continueTeller = !response.equals("yes");
            exitMode();
        }
    }
    /** 
     * ExitMode allows user to return to the main manu
     * 
    */
    @Override
    public void exitMode(){
        System.out.println("Exiting Manager Mode...");
    }
}