import java.util.HashMap;
/**
 * BankMode is an interface for the different modes that the program user can select
 * 
 * @author Sebastian Nares & Ricardo Acosta
 */
interface BankMode {
    void enterMode(HashMap<String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber);
    void performTransaction(HashMap<String, Customer> usersByName, String userName, HashMap<Integer, Customer> accountsByNumber);
    void exitMode();
}