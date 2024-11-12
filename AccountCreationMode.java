class AccountCreationMode implements BankMode {
    @Override
    public void enterMode(){
        System.out.println("Entering Account Creation Mode...");
        /*We may ask for user where */
    }
    @Override
    public void performTransaction(){
        
    }
    @Override
    public void exitMode(){
        System.out.println("Exiting Account Creation Mode...");
    }
}