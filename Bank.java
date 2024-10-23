import java.util.List;
/**
 * The {@code Bank} class represents a collection of bank accounts.
 * It is designed to manage a list of {@code Account} objects, ensuring that the bank
 * has at least one account during its creation.
 * 
 * <p>The class enforces the presence of at least one account by throwing an exception
 * if the provided list of accounts is null or empty.</p>
 * 
 * @authors Sebastian Nares, Ricardo Acosta 
 */

public class Bank {
    /*Attributes*/
    private List<Account> accounts;
    /**
     * Constructs a {@code Bank} instance with the specified list of accounts.
     * 
     * @param accounts the list of accounts to be managed by the bank
     * @throws IllegalArgumentException if the provided list is null or empty
     */
    public Bank(List<Account> accounts){
        if (accounts == null || accounts.isEmpty()){
            throw new IllegalArgumentException("There must be at least one Account");
        }   
        this.accounts = accounts;

    }
} 