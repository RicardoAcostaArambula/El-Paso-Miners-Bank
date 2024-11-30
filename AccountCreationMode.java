import java.util.HashMap;
import java.util.Scanner;

/**
 * This class implements the BankMode interface which allows the program user to create accounts
 * 
 * @author Sebastian Nares, Ricardo Acosta
 */

class AccountCreationMode implements BankMode {
    /**
     * enterMode is the starting point for entering AccountCreationMode which calls then the process of performTransaction
     * 
     * @param usersByName is a hashmap that contains the customers by name
     * @param userName is how the user is identify 
     * @param accountsByNumber is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void enterMode(HashMap<String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber){
        System.out.println("Entering Account Creation Mode...");
        performTransaction(usersByName, userName, accountsByNumber);
    }

    /**
     * performTransaction allows user to create a new user
     * @param usersByName is a hashmap that contains the customers by name
     * @param userName is how the user is identify 
     * @param accountsByNumber is a hashmap that contains the customers by account number
     * @return None 
     */
    @Override
    public void performTransaction(HashMap<String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber){
        Scanner kb = new Scanner(System.in);
        UserCreation userCreation = new UserCreation();
        Customer customer = userCreation.createNewUser(kb, usersByName, accountsByNumber);
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