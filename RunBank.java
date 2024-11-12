import java.util.HashMap;
import java.util.Scanner;

public class RunBank {
    private BankMode currentMode;
    public void setMode(BankMode mode, HashMap <String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number){
        if (currentMode != null){
            currentMode.exitMode();
        }
        currentMode = mode;
        currentMode.enterMode(users_by_name, username, accounts_by_number);
    }
    public static void main(String[] args) {
        RunBank bankApp = new RunBank();
        boolean continueProgram = true;
        Scanner kb = new Scanner(System.in);
        Menus menu = new Menus();
        TransactionStatement statementGenerator = new TransactionStatement();
        HashMap<String, Customer> users_by_name = new HashMap<>();
        HashMap<Integer, Customer> accounts_by_number = new HashMap<>();
        UserCreation.loadUsersFromCSV(users_by_name, accounts_by_number);
        SetupUsers.setup_users(users_by_name, accounts_by_number);

        while (continueProgram) {
            System.out.println("===============================");
            System.out.println("Welcome to El Paso miners Bank");
            System.out.println("===============================");
            int option = menu.displayModeMenu();
            
            if (option == 1) {
                String username = menu.get_full_name_menu(users_by_name);
                Customer customer = users_by_name.get(username);
                statementGenerator.startSession(customer);  // Start tracking the session
                bankApp.setMode(new UserMode(), users_by_name, username, accounts_by_number);
                
                System.out.println("Would you like to exit the program? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            } else if (option == 2) {
                
                bankApp.setMode(new ManagerMode(), users_by_name, "Manager", accounts_by_number);
                
                System.out.println("Would you like to exit? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            } else if (option == 3) {
                String username = "";
                bankApp.setMode(new AccountCreationMode(), users_by_name, username, accounts_by_number);
                System.out.println("Would you like to exit? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            } else if (option == 4) {
                menu.displayStatementMenu(users_by_name, statementGenerator);
                
                System.out.println("Would you like to exit? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            }
        }
    }
}