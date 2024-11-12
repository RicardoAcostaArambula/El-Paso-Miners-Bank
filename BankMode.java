import java.util.HashMap;
/**
 * BankMode is an interface for the different modes that the program user can select
 * 
 * @author Sebastian Nares & Ricardo Acosta
 */
interface BankMode {
    void enterMode(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number);
    void performTransaction(HashMap<String, Customer> users_by_name, String username, HashMap<Integer, Customer> accounts_by_number);
    void exitMode();
}