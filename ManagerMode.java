class ManagerMode implements BankMode {
    @Override
    public void enterMode(){
        System.out.println("Entering Manager Mode...");
        /*We may ask for user where */
    }
    @Override
    public void performTransaction(){
        
    }
    @Override
    public void exitMode(){
        System.out.println("Exiting Manager Mode...");
    }
}