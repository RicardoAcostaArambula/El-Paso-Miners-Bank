import java.util.HashMap;
import java.util.Scanner;

/**
 * The RunBank class serves as the main entry point for the banking application.
 * It facilitates different modes of operation, such as User Mode, Manager Mode, and Account Creation Mode.
 * It handles user input and manages transitions between these modes.
 * 
 * @author Sebastian Nares and Ricardo Acosta
 */
public class RunBank {
    private BankMode currentMode;

     /**
     * Sets the current mode of the application and transitions into the specified mode.
     * Exits the current mode if one is already active.
     *
     * @param mode The new mode to be set.
     * @param usersByName HashMap containing customers mapped by their full name.
     * @param userName The userName of the current customer.
     * @param accountsByNumber HashMap containing customers mapped by their account number.
     */
    public void setMode(BankMode mode, HashMap <String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber){
        if (currentMode != null){
            currentMode.exitMode();
        }
        currentMode = mode;
        currentMode.enterMode(usersByName, userName, accountsByNumber);
    }
    /**
     * The main method where the banking application starts.
     * It initializes the application, loads customer data, and handles user interactions for various banking modes.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        RunBank bankApp = new RunBank();
        boolean continueProgram = true;
        Scanner kb = new Scanner(System.in);
        Menus menu = Menus.getInstance();
        TransactionStatement statementGenerator = new TransactionStatement();
        HashMap<String, Customer> usersByName = new HashMap<>();
        HashMap<Integer, Customer> accountsByNumber = new HashMap<>();
        UserCreation.loadUsersFromCSV(usersByName, accountsByNumber);
        SetupUsers.setUpUsers(usersByName, accountsByNumber);

        while (continueProgram) {
            System.out.println("===============================");
            System.out.println("Welcome to El Paso Miners Bank");
            System.out.println("===============================");
            int option = menu.displayModeMenu();
            
            if (option == 1) {
                String userName = menu.getFullNameMenu(usersByName);
                Customer customer = usersByName.get(userName);
                statementGenerator.startSession(customer); 
                bankApp.setMode(new UserMode(), usersByName, userName, accountsByNumber);
                
                System.out.println("Would you like to exit the program? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            } else if (option == 2) {
                
                bankApp.setMode(new ManagerMode(), usersByName, "Manager", accountsByNumber);
        
                System.out.println("Would you like to exit the program? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            } else if (option == 3) {
                // String userName = "";
                // bankApp.setMode(new AccountCreationMode(), usersByName, userName, accountsByNumber);
                // System.out.println("Would you like to exit the program? (yes/no)");
                // String response = kb.nextLine().trim().toLowerCase();
                // continueProgram = !response.equals("yes");
                
                menu.displayStatementMenu(usersByName, statementGenerator);
                System.out.println("Would you like to exit the program? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            } 
            // else if (option == 4) {
            //     // menu.displayStatementMenu(usersByName, statementGenerator);
                
            //     // System.out.println("Would you like to exit the program? (yes/no)");
            //     // String response = kb.nextLine().trim().toLowerCase();
            //     // continueProgram = !response.equals("yes");
            // }
        }
    }
}