import java.util.List;
public class Customer extends Person {
    /*Attributes*/
    private String customer_id;
    private List<Account> accounts;
    /*constructors*/
    /*default constructor */
    public Customer(){
        
    }
    public Customer(String customer_id){
        this.customer_id = customer_id;
    }
    public Customer(String customer_id, List<Account> accounts){
        this.customer_id = customer_id;
        this.accounts = accounts;
    }
    /*setters*/
    public void set_account_id(String customer_id){
        this.customer_id = customer_id;
    }
    public void set_accounts(List<Account> accounts){
        this.accounts = accounts ;
    }
    /*getters*/
    public String set_account_id(){
        return this.customer_id;
    }
    public List<Account>  set_accounts(){
        return this.accounts;
    }
}