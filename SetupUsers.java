import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
class SetupUsers {
    public SetupUsers(){

    }
    /**
     * Sets up users from a CSV file and populates the users HashMap.
     * @param users_by_name the HashMap to be populated with Customer objects
     * @param accounts_by_number the HashMap to be pupulated with Customer objects
     */
    public static void setup_users(HashMap<String, Customer> users_by_name, HashMap<Integer, Customer> accounts_by_number) {
        try {
            File file = new File("bank_users.csv");
            Scanner read = new Scanner(file);
            String line = read.nextLine(); // Skip first header
            
            while (read.hasNextLine()) {
                line = read.nextLine();
                // Skip empty lines or header lines
                if (line.trim().isEmpty() || line.startsWith("ID,")) {
                    continue;
                }
                
                // Process the line
                line = remove_commas_inside_quotations(line);
                String[] items = line.split(",");
                
                // Clean up the fields - remove quotes and restore special characters
                for (int i = 0; i < items.length; i++) {
                    items[i] = items[i].replace("\"", "").replace("|", ",").trim();
                }
                
                // Validate that we have all required fields
                if (items.length < 13) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }
    
                try {
                    // Only parse numeric fields - skip phone number field
                    int id = Integer.parseInt(items[0]);
                    String name = items[1];
                    String last = items[2];
                    String dob = items[3];
                    String address = items[4];
                    String phone_number = items[5]; // Don't parse this as a number
                    int checking_account_number = Integer.parseInt(items[6]);
                    float checking_account_balance = Float.parseFloat(items[7]);
                    int saving_account_number = Integer.parseInt(items[8]);
                    float saving_account_balance = Float.parseFloat(items[9]);
                    int credit_account_number = Integer.parseInt(items[10]);
                    float credit_account_max = Float.parseFloat(items[11]);
                    float credit_account_balance = Float.parseFloat(items[12]);
    
                    // Create customer object and set fields
                    Customer customer = new Customer();
                    customer.set_account_id(id);
                    customer.set_name(name);
                    customer.set_last(last);
                    customer.set_dob(dob);
                    customer.set_address(address);
                    customer.set_phone_number(phone_number); // Store as string
                    customer.set_checking_account_number(checking_account_number);
                    customer.set_checking_account_balance(checking_account_balance);
                    customer.set_saving_account_number(saving_account_number);
                    customer.set_saving_account_balance(saving_account_balance);
                    customer.set_credit_account_number(credit_account_number);
                    customer.set_credit_account_max(credit_account_max);
                    customer.set_credit_account_balance(credit_account_balance);
                    
                    String key = name + " " + last;
                    users_by_name.put(key, customer);
                    accounts_by_number.put(checking_account_number, customer);
                    accounts_by_number.put(saving_account_number, customer);
                    accounts_by_number.put(credit_account_number, customer);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numeric data in line: " + line);
                    System.out.println("Specific error: " + e.getMessage());
                    continue;
                }
            }
            read.close();
        } catch (FileNotFoundException error) {
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
    public static String remove_commas_inside_quotations(String line) {
        StringBuilder new_line = new StringBuilder();
        boolean inside_quotes = false;
        for (int i = 0; i < line.length(); i++) {
            char current_char = line.charAt(i);
            if (current_char == '"') {
                inside_quotes = !inside_quotes;
                // Keep the quotes to maintain field boundaries
                new_line.append(current_char);
            } else if (current_char == ',' && inside_quotes) {
                // Replace commas inside quotes with a special character
                new_line.append('|');
            } else {
                new_line.append(current_char);
            }
        }
        return new_line.toString();
    }
}