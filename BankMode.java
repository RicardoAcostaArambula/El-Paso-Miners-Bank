import java.util.HashMap;
interface BankMode {
    void enterMode(HashMap<String, Customer> users_by_name);
    void performTransaction(HashMap<String, Customer> users_by_name);
    void exitMode();
}