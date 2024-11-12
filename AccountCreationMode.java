import java.util.HashMap;
import java.util.Scanner;

/**
 * This class implements the BankMode interface which allows the program user to create accounts
 * 
 * @author Sebastian Nares & Ricardo Acosta
 */

class AccountCreationMode implements BankMode {
    /**
     * enterMode is the starting point for entering AccountCreationMode which calls then the process of performTransaction
     * 
     * @param users_by_name is a hashmap that contains the customers by name
     * @param username is how the user is identify 
     * @param accounts_by_number is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void enterMode(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number){
        System.out.println("Entering Account Creation Mode...");
        performTransaction(users_by_name, username, accounts_by_number);
    }

    /**
     * performTransaction allows user to create a new user
     * @param users_by_name is a hashmap that contains the customers by name
     * @param username is how the user is identify 
     * @param accounts_by_number is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void performTransaction(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number){
        Scanner kb = new Scanner(System.in);
        UserCreation userCreation = new UserCreation();
        Customer customer = userCreation.createNewUser(kb, users_by_name, accounts_by_number);
        if (customer != null) {
            System.out.println("Account creation completed. You may now login as an Individual Person.");
        } else {
            System.out.println("Account creation failed.");
        }
        exitMode();
    }

    /** 
     * ExitMode allows user to return to the main manu
     * 
    */
    @Override
    public void exitMode(){
        System.out.println("Exiting Account Creation Mode...");
    }
}