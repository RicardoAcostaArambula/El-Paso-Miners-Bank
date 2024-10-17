public class RunBank {
    public static void main(String[] args){

    }

    /*Verifies user*/
    public boolean verify_user(Customer customer, int pin){
        return customer.get_pin() == pin;
    }
    /*logs transaction information */
    public void log_information(Customer customer, String transaction){

    }
    /*returns checking account balance */
    public float check_balance_checkings(CheckingAccount customer_account){
        return customer_account.get_account_balance();
    }
    /*returns savings account balance */
    public float check_balance_savings(SavingAccount customer_account){
        return customer_account.get_account_balance();
    }


}