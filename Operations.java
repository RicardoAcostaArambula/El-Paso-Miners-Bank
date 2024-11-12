/**Operations is an interface that will serve as the base for all operations for users and managers 
 * 
 * @author Sebastian Nares & Ricardo Acosta
*/
public interface Operations {
    public float checking_account_balance(Customer customer);
    public float saving_account_balance(Customer customer);
    public float credit_account_balance(Customer customer);
    public void deposit_to_checking(Customer customer, float amount);
    public void deposit_to_saving(Customer customer, float amount);
    public void deposit_to_credit(Customer customer, float amount);
    public boolean transferBetweenCustomers(Customer sourceCustomer, Customer destCustomer, int sourceAccountType, int destAccountType, float amount);
}