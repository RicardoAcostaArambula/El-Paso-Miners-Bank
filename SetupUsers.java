import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
class SetupUsers {
    private SetupUsers(){

    }
    /**
     * Sets up users from a CSV file and populates the users HashMap.
     * @param users_by_name the HashMap to be populated with Customer objects
     * @param accounts_by_number the HashMap to be pupulated with Customer objects
     */
    public static void setup_users(HashMap <String, Customer>  users_by_name, HashMap <Integer, Customer> accounts_by_number){
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
                String key = name + " " + last;
                users_by_name.put(key, customer);
                accounts_by_number.put(checking_account_number, customer);
                accounts_by_number.put(saving_account_number, customer);
            }
        } catch (FileNotFoundException error){
            System.out.println("Error: could not find file");
            error.printStackTrace();
        }
    }
    /**
     * Removes commas inside quotations from a given line.
     *
     * @param line the line from which to remove commas inside quotations
     * @return the modified line with commas removed
     */
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