import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
public class RunBank {
    public static void main(String[] args){
        boolean browing = true;
        int option, id, account_type;
        Scanner kb = new Scanner(System.in);
        String exit, username;
        float balance;
        HashMap <Integer, Customer> users = new HashMap<>();
        setup_users(users);
        System.out.println("Welcome to El Paso miners Bank");
        do {
            System.out.println("Please Select the One of the following modes:");
            System.out.println("1. Individual Person");
            System.out.println("2. Bank Teller");
            option = kb.nextInt();
            /*Verify user*/
            System.out.println("Enter your name: ");
            username = kb.nextLine();
            System.out.println("Enter your Customer ID: ");
            id = kb.nextInt();
            /*Check that user exist in the dictonary */
            if (!(users.containsKey(id)) || !(users.get(id).get_name().equals(username))){
                System.out.println("Error: please enter a valid number and/or name");
            } else {
                if (option == 1){
                    /*Getting the customer*/
                    Customer customer = users.get(id);
                    boolean valid = false;
                    /*checking the account time */
                    do {
                        System.out.println("Select one of the following accoutns:");
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
                            if (account_type == 1){
                                balance = checking_account_balance(customer);
                            } else if (account_type == 2) {
                                balance = saving_account_balance(customer);
                            } else {
                                balance = credit_account_balance(customer);
                            }
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
    /*logs transaction information */
    public void log_information(Customer customer, String transaction){

    }
    /*returns checking account balance */
    public static float checking_account_balance(Customer customer){
        return customer.get_checking_account_balance();
    }

    public static float saving_account_balance(Customer customer){
        return customer.get_saving_account_balance();
    }

    public static float credit_account_balance(Customer customer){
        return customer.get_credit_account_balance();
    }
    /*deposit funds to checkings*/

    /*Are we going to have one method for each account type? 
     * or are we going to have one method, and the account class will be abstract and have the methods and attributes needed for generalization?
     * 
    */
    public static boolean deposit_funds(Account account, float amount){
        return false;
    }
    /*builds up the dictinary with the users with the id as primary key*/
    /*
        ,Credit Account Number,Credit Max,Credit Starting Balance
     */
    public static void setup_users(HashMap <Integer, Customer>  users){
        try {
            File file = new File("bank_users.csv");
            Scanner read = new Scanner(file);
            String line = read.nextLine();
            while (read.hasNextLine()){
                line = read.nextLine();
                line = remove_commas_inside_quotations(line);
                String[] items = line.split(",");
                int id = Integer.parseInt(items[0]);
                String name = items[1];
                String last = items[2];
                String dob = items[3];
                String address = items[4];
                String phone_number = items[5];
                int checking_account_number = Integer.parseInt(items[6]);
                float checking_account_balance = Float.parseFloat((items[7]));
                int saving_account_number = Integer.parseInt(items[8]);
                float saving_account_balance = Float.parseFloat((items[9]));
                int credit_account_number = Integer.parseInt(items[10]);
                float credit_account_max= Float.parseFloat((items[11]));
                float credit_account_balance= Float.parseFloat((items[12]));
                /*Create object*/
                Customer customer = new Customer();
                customer.set_account_id(id);
                customer.set_name(name);
                customer.set_last(last);
                customer.set_dob(dob);
                customer.set_address(address);
                customer.set_phone_number(phone_number);
                customer.set_checking_account_number(checking_account_number);
                customer.set_checking_account_balance(checking_account_balance);
                customer.set_saving_account_number(saving_account_number);
                customer.set_saving_account_balance(saving_account_balance);
                customer.set_credit_account_balance(credit_account_number);
                customer.set_credit_account_max(credit_account_max);
                customer.set_credit_account_balance(credit_account_balance);
                users.put(id, customer);
                /*add it to dic */
            }
        } catch (FileNotFoundException error){
            System.out.println("Error: could not find file");
            error.printStackTrace();
        }
    }
    public static String remove_commas_inside_quotations(String line){
        StringBuilder new_line = new StringBuilder();
        boolean inside_quotes = false;
        for (int i = 0; i < line.length(); i++){
            char current_char = line.charAt(i);
            if (current_char == '"'){
                inside_quotes = !inside_quotes;
            } else if(current_char == ',' && inside_quotes){
                continue;
            } else {
                new_line.append(current_char);
            }
        }
        return new_line.toString();
    }
}