import java.util.Scanner;
public class RunBank {
    public static void main(String[] args){

        boolean browing = true;
        int option, pin, account_type;
        Scanner kb = new Scanner(System.in);
        String exit, username;
        float balance;
        Account account;

        System.out.println("Welcome to El Paso miners Bank");
        do {

            System.out.println("Please Select the One of the following modes:");
            System.out.println("1. Individual Person");
            System.out.println("2. Bank Teller");
            option = kb.nextInt();
            /*Verify user*/
            System.out.print("Enter your name");
            username = kb.nextLine();
            System.out.print("Enter your pin");
            pin = kb.nextInt();
            
            /*We may check if the username is in the text file (mimicking our database) 
            if so we create the object from there*/
            
            if (!verify_user(username, pin)){
                System.out.println("Please, try again");
            } else{
                if (option == 1){
                    /*do we get the account first or after the transaction?*/
                    System.out.println("(1) Checkings");
                    System.out.println("(2) Savings");
                    account_type = kb.nextInt();
                    /* We need to store the account object in the account varaible so that we are able to 
                     * call the methods for checking the balance
                    */
                    /*code below is assuming we have the account and account type*/
                    System.out.println("Select one of the transactions below:");
                    System.out.println("(1) Inquire about balance");

                    /*Assuming the user already selected the account in which the money will go*/
                    System.out.println("(2) Depsit money to an account");
                    System.out.println("(3) Withdraw money from an account");
                    System.out.println("(4) Transfer Money between accounts");
                    System.out.println("(5) Make payment");
                    kb.nextInt();
                    
                    /* switch statmenet with all call to each*/
                    switch (option){
                        case 1: 
                            balance = check_balance(account);
                            System.out.println("The account balance is: " + balance);
                            break;
                        case 2:
                            /*Ask user to which account will it deposit moeny to*/
                            break; 
                        case 3: 
                            break;
                        case 4:
                            break; 
                        case 5: 
                            break; 
                        default:
                            break;
                    }


                } else if (option == 2){    
                    System.out.println("Will be implemented later");
                }
            }
            System.out.println("Do you want to exit? YES/NO");
            exit = kb.next();
            browing = exit.toLowerCase().equals("yes") ? true : false;
        } while (browing);
    }

    /*Verifies user*/
    public boolean verify_user(Customer customer, int pin){
        return customer.get_pin() == pin;
    }
    /*logs transaction information */
    public void log_information(Customer customer, String transaction){

    }
    /*returns checking account balance */
    public float check_balance(CheckingAccount customer_account){
        return customer_account.get_account_balance();
    }
    /*deposit dunts to checkings*/

    /*Are we going to have one method for each account type? 
     * or are we going to have one method, and the account class will be abstract and have the methods and attributes needed for generalization?
     * 
    */
    public boolean deposit_funds(Account account, float amount){
        float current_balance = account.get_account_balance();
        float new_balance = current_balance + amount;
        return account.set_account_balance(new_balance);
    }
}