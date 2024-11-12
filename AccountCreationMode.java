import java.util.HashMap;
import java.util.Scanner;
class AccountCreationMode implements BankMode {
    @Override
    public void enterMode(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number){
        System.out.println("Entering Account Creation Mode...");
        performTransaction(users_by_name, username, accounts_by_number);
    }
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
    @Override
    public void exitMode(){
        System.out.println("Exiting Account Creation Mode...");
    }
}