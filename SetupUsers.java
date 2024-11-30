import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
class SetupUsers {
    public SetupUsers(){

    }
    /**
     * Sets up users from a CSV file and populates the users HashMap.
     * @param usersByName the HashMap to be populated with Customer objects
     * @param accountsByNumber the HashMap to be pupulated with Customer objects
     */
    public static void setUpUsers(HashMap<String, Customer> usersByName, HashMap<Integer, Customer> accountsByNumber) {
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
                line = removeCommasInsideQuotations(line);
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
                    String phoneNumber = items[5]; // Don't parse this as a number
                    int checkingAccountNumber = Integer.parseInt(items[6]);
                    float checkingAccountBalance = Float.parseFloat(items[7]);
                    int savingAccountNumber = Integer.parseInt(items[8]);
                    float savingAccountBalance = Float.parseFloat(items[9]);
                    int creditAccountNumber = Integer.parseInt(items[10]);
                    float creditAccountMax = Float.parseFloat(items[11]);
                    float creditAccountBalance = Float.parseFloat(items[12]);
    
                    // Create customer object and set fields
                    Customer customer = new Customer();
                    customer.setAccountId(id);
                    customer.setName(name);
                    customer.setLast(last);
                    customer.setDob(dob);
                    customer.setAddress(address);
                    customer.setPhoneNumber(phoneNumber); // Store as string
                    customer.setCheckingAccountNumber(checkingAccountNumber);
                    customer.setCheckingAccountBalance(checkingAccountBalance);
                    customer.setSavingAccountNumber(savingAccountNumber);
                    customer.setSavingAccountBalance(savingAccountBalance);
                    customer.setCreditAccountNumber(creditAccountNumber);
                    customer.setCreditAccountMax(creditAccountMax);
                    customer.setCreditAccountBalance(creditAccountBalance);
                    
                    String key = name + " " + last;
                    usersByName.put(key, customer);
                    accountsByNumber.put(checkingAccountNumber, customer);
                    accountsByNumber.put(savingAccountNumber, customer);
                    accountsByNumber.put(creditAccountNumber, customer);
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
    public static String removeCommasInsideQuotations(String line) {
        StringBuilder newLine = new StringBuilder();
        boolean insideQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (currentChar == '"') {
                insideQuotes = !insideQuotes;
                // Keep the quotes to maintain field boundaries
                newLine.append(currentChar);
            } else if (currentChar == ',' && insideQuotes) {
                // Replace commas inside quotes with a special character
                newLine.append('|');
            } else {
                newLine.append(currentChar);
            }
        }
        return newLine.toString();
    }
}