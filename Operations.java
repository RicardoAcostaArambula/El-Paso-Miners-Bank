/**Operations is an interface that will serve as the base for all operations for users and managers 
 * 
 * @author Sebastian Nares and Ricardo Acosta
*/
public interface Operations {
    public float checkingAccountBalance(Customer customer);
    public float savingAccountBalance(Customer customer);
    public float creditAccountBalance(Customer customer);
    public void depositToChecking(Customer customer, float amount);
    public void depositToSaving(Customer customer, float amount);
    public void depositToCredit(Customer customer, float amount);
    public boolean transferBetweenCustomers(Customer sourceCustomer, Customer destCustomer, int sourceAccountType, int destAccountType, float amount);
}