import java.util.List;
public class Bank {
    /*Attributes*/
    private List<Account> accounts;
    public Bank(List<Account> accounts){
        if (accounts == null || accounts.isEmpty()){
            throw new IllegalArgumentException("There must be at least one Account");
        }   
        this.accounts = accounts;

    }
} 