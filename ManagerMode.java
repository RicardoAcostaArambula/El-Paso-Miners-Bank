import java.util.HashMap;
import java.util.Scanner;
class ManagerMode implements BankMode {
     /**
     * enterMode is the starting point for entering managerMode which calls then the process of performTransaction
     * 
     * @param usersByName is a hashmap that contains the customers by name
     * @param userName is how the user is identify 
     * @param accountsByNumber is a hashmap that contains the customers by account number
     * @return None 
     * 
     * @author Sebastian Nares, Ricardo Acosta
     */
    @Override
    public void enterMode(HashMap<String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber){
        System.out.println("Entering Manager Mode...");
        performTransaction(usersByName, userName, accountsByNumber);
    }
    /**
     * performTransaction allows user to complete transactions as displayed by the menu for the manager
     * @param usersByName is a hashmap that contains the customers by name
     * @param userName is how the user is identify 
     * @param accountsByNumber is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void performTransaction(HashMap<String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber){
        boolean continueTeller = true;
        ManagerOperations managerOperations = new ManagerOperations();
        ManagerTransactionStatement managerStatement = new ManagerTransactionStatement();
        Log transactionLog = new Log();
        Scanner kb = new Scanner(System.in);
        while (continueTeller) {
            boolean inquiryChosen = false;
            int inquiryType;
            do {
                System.out.println("Please select one of the following options: ");
                System.out.println("(1) Inquiry account by name");
                System.out.println("(2) Inquiry account by account number");
                System.out.println("(3) Process transactions from file");
                System.out.println("(4) Process manager transaction statement");
                System.out.println("(5) Create New Account");
                inquiryType = kb.nextInt();
                kb.nextLine();
                
                if (1 <= inquiryType && inquiryType <= 5) {
                    inquiryChosen = true;
                } else {
                    System.out.println("Please choose a valid option");
                }
            } while (!inquiryChosen);

            if (inquiryType == 1) {
                boolean valid = false;
                String accountHolder;
                do {
                    
                    System.out.println("Whose account would you like to inquire about? (Enter full name as following: FirstName LastName)");
                    accountHolder = kb.nextLine();
                    if (!usersByName.containsKey(accountHolder)) {
                        System.out.println("Please enter a valid name");
                    } else {
                        valid = true;
                    }
                } while (!valid);
                
                Customer customer = usersByName.get(accountHolder);
                managerOperations.dislayAccountInformationByName(customer);
                managerStatement.recordTransaction(customer, String.format("Manager performed account inquiry for %s %s", 
                customer.getName(), customer.getLast()));
            } else if (inquiryType == 2) {
                boolean valid = false;
                int accountType;
                do {
                    System.out.println("What is the account type: ");
                    System.out.println("(1) Checkings");
                    System.out.println("(2) Savings");
                    System.out.println("(3) Credit");
                    accountType = kb.nextInt();
                    if (1 <= accountType && accountType <= 3) {
                        valid = true;
                    } else {
                        System.out.println("Please choose a valid account");
                    }
                } while (!valid);
                valid = false;
                int accountNumber;
                do {
                    System.out.println("What is the account number?");
                    accountNumber = kb.nextInt();
                    if (kb.hasNextLine()) {
                        kb.nextLine();
                    }
                    if (!accountsByNumber.containsKey(accountNumber)) {
                        System.out.println("Please enter a valid account number");
                    } else {
                        valid = true;
                    }
                } while (!valid);
                Customer customer = accountsByNumber.get(accountNumber);
                managerOperations.dislayAccountInformationByAccountNumber(customer, accountNumber, accountType);
                transactionLog.logBalanceInquiry(customer, accountType);
                managerStatement.recordTransaction(customer, String.format("Manager performed account inquiry for %s %s", 
                customer.getName(), customer.getLast()));
            } else if (inquiryType == 3) {
                System.out.print("The transaction process from the file will start shortly...");
                TransactionReader.transactionReader("Transactions(1).csv", usersByName, transactionLog);
            } else if (inquiryType == 4) {
                boolean valid = false;
                String accountHolder;
                do {
                    if (kb.hasNextLine()) {
                        kb.nextLine();
                    }
                    System.out.println("For which customer would you like to generate a transaction statement? (Enter full name as following: FirstName LastName)");
                    accountHolder = kb.nextLine();
                    if (!usersByName.containsKey(accountHolder)) {
                        System.out.println("Please enter a valid name");
                    } else {
                        valid = true;
                    }
                } while (!valid);
                
                Customer customer = usersByName.get(accountHolder);
                managerStatement.generateTransactionStatement(customer);
                managerStatement.recordTransaction(customer, String.format("Manager generated transaction statement for %s %s", 
                customer.getName(), customer.getLast()));
            } else if (inquiryType == 5){
                RunBank bankApp = new RunBank();
                bankApp.setMode(new AccountCreationMode(), usersByName, userName, accountsByNumber);
            }
            System.out.println("Would you like to exit? (yes/no)");
            String response = kb.nextLine().trim().toLowerCase();
            continueTeller = !response.equals("yes");
        }
        exitMode();
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