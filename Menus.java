import java.util.HashMap;
import java.util.Scanner;
public class Menus {
    Scanner kb = new Scanner(System.in);
    public Menus(){

    }
    public int displayModeMenu(){
        
        int option;
        boolean right_option = false;
        do {
            System.out.println("Please Select the one of the following modes:");
            System.out.println("1. Individual Person");
            System.out.println("2. Bank Teller");
            System.out.println("3. Create New Account");
            option = kb.nextInt();
            if (1 <= option && option <=3){
                right_option = true;
            } else {
                System.out.println("Please choose a valid option");
            }
        } while(!right_option);

        return option;
    }
    public String get_full_name_menu(HashMap <String, Customer> users_by_name){
        boolean right_user = false;
        System.out.println("Enter your full name: ");
        if (kb.hasNextLine()) { 
            kb.nextLine();
        }
        String username;
        do {
            username = kb.nextLine();
            if (!users_by_name.containsKey(username)){
                System.out.println("Error: please enter a valid name");
            } else {
                right_user = true;
            } 
        } while(!right_user);
        return username;
    }

    public int get_account_type_menu(){
        boolean valid = false;
        int account_type;
        do {
            System.out.println("Select one of the following accounts:");
            System.out.println("(1) Checkings");
            System.out.println("(2) Savings");
            System.out.println("(3) Credit");
            account_type = kb.nextInt();
            if (1 <= account_type && account_type <=3){
                valid = true;
            } else {
                System.out.println("Please choose a valid account");
            }
        } while(!valid);
        return account_type;
    }
    public int select_transaction_menu(){
        System.out.println("Select one of the transactions below:");
        System.out.println("(1) Inquire about balance");
        System.out.println("(2) Deposit money to the account");
        System.out.println("(3) Withdraw money from the account");
        System.out.println("(4) Transfer money between accounts");
        System.out.println("(5) Make payment");
        System.out.println("(6) Transfer to another customer");
        int transaction_option = kb.nextInt();
        return transaction_option;
    }
    public int displayOptions(String userType){
        int option;
        if ("user".equalsIgnoreCase(userType)){

        } else if ("manager".equalsIgnoreCase((userType))) {

        } else {

        }
        option = 0;
        return option;
    }
    public int get_source_account(){
        return 0;
    }
}