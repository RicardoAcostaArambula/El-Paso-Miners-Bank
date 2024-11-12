import java.util.HashMap;
import java.util.Scanner;

public class RunBank {
    public static void main(String[] args) {
        boolean continueProgram = true;
        Scanner kb = new Scanner(System.in);
        UserOperations userOperations = new UserOperations();
        ManagerOperations managerOperations = new ManagerOperations();
        Log transactionLog = new Log();
        Menus menu = new Menus();
        UserCreation userCreation = new UserCreation();
        TransactionStatement statementGenerator = new TransactionStatement();
        HashMap<String, Customer> users_by_name = new HashMap<>();
        HashMap<Integer, Customer> accounts_by_number = new HashMap<>();
        UserCreation.loadUsersFromCSV(users_by_name, accounts_by_number);
        SetupUsers.setup_users(users_by_name, accounts_by_number);

        while (continueProgram) {
            System.out.println("===============================");
            System.out.println("Welcome to El Paso miners Bank");
            System.out.println("===============================");
            int option = menu.displayModeMenu();
            
            if (option == 1) {
                String username = menu.get_full_name_menu(users_by_name);
                Customer customer = users_by_name.get(username);
                statementGenerator.startSession(customer);  // Start tracking the session
                
                boolean continueBanking = true;
                while (continueBanking) {
                    int account_type = menu.get_account_type_menu();
                    int transaction_option = menu.select_transaction_menu();
                    
                    switch (transaction_option) {
                        case 1:
                            userOperations.check_balance(customer, account_type);
                            transactionLog.logBalanceInquiry(customer, account_type);
                            statementGenerator.recordTransaction(customer, "Balance inquiry for " +
                                (account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit") + " account");
                            break;

                        case 2:
                            System.out.println("Enter deposit amount:");
                            float deposit_amount = kb.nextFloat();
                            kb.nextLine();
                            userOperations.deposit(customer, account_type, deposit_amount);
                            transactionLog.logDeposit(customer, account_type, deposit_amount);
                            statementGenerator.recordTransaction(customer, String.format("Deposit of $%.2f to %s account",
                                deposit_amount,
                                account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit"));
                            break;

                        case 3:
                            System.out.println("Enter withdraw amount:");
                            float withdrawal_amount = kb.nextFloat();
                            kb.nextLine();
                            userOperations.withdraw(customer, account_type, withdrawal_amount);
                            transactionLog.logWithdrawal(customer, account_type, withdrawal_amount);
                            statementGenerator.recordTransaction(customer, String.format("Withdrawal of $%.2f from %s account",
                                withdrawal_amount,
                                account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit"));
                            break;

                        case 4:
                            System.out.println("From which account?");
                            System.out.println("(1) Checking");
                            System.out.println("(2) Savings");
                            System.out.println("(3) Credit");
                            int source_account = kb.nextInt();

                            System.out.println("To which account?");
                            System.out.println("(1) Checking");
                            System.out.println("(2) Savings");
                            System.out.println("(3) Credit");
                            int dest_account = kb.nextInt();

                            System.out.println("Enter transfer amount:");
                            float transfer_amount = kb.nextFloat();
                            kb.nextLine();
                            userOperations.transfer_between_accounts(customer, source_account, dest_account, transfer_amount);
                            transactionLog.logTransfer(customer, source_account, dest_account, transfer_amount);
                            statementGenerator.recordTransaction(customer, String.format("Transfer of $%.2f from %s account to %s account",
                                transfer_amount,
                                source_account == 1 ? "Checking" : source_account == 2 ? "Savings" : "Credit",
                                dest_account == 1 ? "Checking" : dest_account == 2 ? "Savings" : "Credit"));
                            break;

                        case 5:
                            if (account_type != 3) {
                                System.out.println("Error: Payments can only be made from credit account");
                                statementGenerator.recordTransaction(customer, "Failed payment attempt - invalid account type");
                                break;
                            }

                            System.out.println("Enter payment amount:");
                            float payment_amount = kb.nextFloat();
                            kb.nextLine();
                            if (payment_amount <= 0) {
                                System.out.println("Error: Payment amount must be greater than zero");
                                statementGenerator.recordTransaction(customer, "Failed payment attempt - invalid amount");
                                break;
                            }

                            float credit_balance = userOperations.credit_account_balance(customer);
                            if (credit_balance + payment_amount > 0) {
                                System.out.println("Error: Payment would exceed credit limit");
                                statementGenerator.recordTransaction(customer, "Failed payment attempt - would exceed credit limit");
                                break;
                            }

                            customer.set_credit_account_balance(credit_balance + payment_amount);
                            System.out.println("Successfully made payment of $" + payment_amount);
                            transactionLog.logPayment(customer, payment_amount);
                            statementGenerator.recordTransaction(customer, String.format("Payment of $%.2f made to credit account",
                                payment_amount));
                            break;

                        case 6:
                            if (kb.hasNextLine()) {
                                kb.nextLine();
                            }
                            System.out.println("Enter recipient's name: ");
                            String recipient_full_name = kb.nextLine();

                            if (!users_by_name.containsKey(recipient_full_name)) {
                                System.out.println("Error: Recipient not found");
                                statementGenerator.recordTransaction(customer, "Failed transfer attempt - recipient not found: " + recipient_full_name);
                                break;
                            }

                            Customer recipientCustomer = users_by_name.get(recipient_full_name);

                            System.out.println("Select recipient's account type:");
                            System.out.println("(1) Checking");
                            System.out.println("(2) Savings");
                            System.out.println("(3) Credit");
                            int recipientAccountType = kb.nextInt();
                            kb.nextLine();

                            if (recipientAccountType < 1 || recipientAccountType > 3) {
                                System.out.println("Error: Invalid account type");
                                statementGenerator.recordTransaction(customer, "Failed transfer attempt - invalid recipient account type");
                                break;
                            }

                            System.out.println("Enter transfer amount:");
                            float interCustomerTransferAmount = kb.nextFloat();
                            kb.nextLine();

                            if (interCustomerTransferAmount <= 0) {
                                System.out.println("Error: Transfer amount must be greater than zero");
                                statementGenerator.recordTransaction(customer, "Failed transfer attempt - invalid amount");
                                break;
                            }

                            if (userOperations.transferBetweenCustomers(customer, recipientCustomer, account_type,
                                    recipientAccountType, interCustomerTransferAmount)) {
                                System.out.println("Successfully transferred $" + String.format("%.2f", interCustomerTransferAmount) +
                                        " to customer: " + recipientCustomer.get_name() + " " + recipientCustomer.get_last());
                                transactionLog.logInterCustomerTransfer(customer, recipientCustomer, account_type,
                                        recipientAccountType, interCustomerTransferAmount);
                                statementGenerator.recordTransaction(customer, String.format(
                                    "Transfer of $%.2f from %s account to %s %s's %s account",
                                    interCustomerTransferAmount,
                                    account_type == 1 ? "Checking" : account_type == 2 ? "Savings" : "Credit",
                                    recipientCustomer.get_name(),
                                    recipientCustomer.get_last(),
                                    recipientAccountType == 1 ? "Checking" : recipientAccountType == 2 ? "Savings" : "Credit"));
                            } else {
                                System.out.println("Transfer failed. Please check account balances or input data.");
                                statementGenerator.recordTransaction(customer, "Failed transfer attempt - insufficient funds or invalid data");
                            }
                            break;

                        default:
                            System.out.println("Invalid option selected");
                            statementGenerator.recordTransaction(customer, "Invalid transaction option selected: " + transaction_option);
                            break;
                    }

                    System.out.println("Would you like to exit? (yes/no)");
                    String response = kb.nextLine().trim().toLowerCase();
                    continueBanking = !response.equals("yes");
                }

                System.out.println("Would you like to exit the program? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");

            } else if (option == 2) {
                boolean continueTeller = true;
                while (continueTeller) {
                    boolean inquiry_chosen = false;
                    int inquiry_type;
                    do {
                        System.out.println("Please select one of the following options: ");
                        System.out.println("(1) Inquiry account by name");
                        System.out.println("(2) Inquiry account by account number");
                        System.out.println("(3) Process transactions from file");
                        inquiry_type = kb.nextInt();
                        if (kb.hasNextLine()) {
                            kb.nextLine();
                        }
                        if (1 <= inquiry_type && inquiry_type <= 3) {
                            inquiry_chosen = true;
                        } else {
                            System.out.println("Please choose a valid option");
                        }
                    } while (!inquiry_chosen);

                    if (inquiry_type == 1) {
                        boolean valid = false;
                        String account_holder;
                        do {
                            if (kb.hasNextLine()) {
                                kb.nextLine();
                            }
                            System.out.println("Whose account would you like to inquire about? (Enter full name as following: FirstName LastName)");
                            account_holder = kb.nextLine();
                            if (!users_by_name.containsKey(account_holder)) {
                                System.out.println("Please enter a valid name");
                            } else {
                                valid = true;
                            }
                        } while (!valid);
                        Customer customer = users_by_name.get(account_holder);
                        managerOperations.dislay_account_information_by_name(customer);
                    } else if (inquiry_type == 2) {
                        boolean valid = false;
                        int account_type;
                        do {
                            System.out.println("What is the account type: ");
                            System.out.println("(1) Checkings");
                            System.out.println("(2) Savings");
                            System.out.println("(3) Credit");
                            account_type = kb.nextInt();
                            if (1 <= account_type && account_type <= 3) {
                                valid = true;
                            } else {
                                System.out.println("Please choose a valid account");
                            }
                        } while (!valid);
                        valid = false;
                        int account_number;
                        do {
                            System.out.println("What is the account number?");
                            account_number = kb.nextInt();
                            if (kb.hasNextLine()) {
                                kb.nextLine();
                            }
                            if (!accounts_by_number.containsKey(account_number)) {
                                System.out.println("Please enter a valid account number");
                            } else {
                                valid = true;
                            }
                        } while (!valid);
                        Customer customer = accounts_by_number.get(account_number);
                        managerOperations.dislay_account_information_by_account_number(customer, account_number, account_type);
                        transactionLog.logBalanceInquiry(customer, account_type);
                    } else if (inquiry_type == 3) {
                        System.out.print("The transaction process from the file will start shortly...");
                        TransactionReader.transaction_reader("Transactions(1).csv", users_by_name, transactionLog);
                    }

                    System.out.println("Would you like to exit? (yes/no)");
                    String response = kb.nextLine().trim().toLowerCase();
                    continueTeller = !response.equals("yes");
                }
            } else if (option == 3) {
                Customer customer = userCreation.createNewUser(kb, users_by_name, accounts_by_number);
                if (customer != null) {
                    System.out.println("Account creation completed. You may now login as an Individual Person.");
                } else {
                    System.out.println("Account creation failed.");
                }
                System.out.println("Would you like to exit? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            } else if (option == 4) {
                menu.displayStatementMenu(users_by_name, statementGenerator);
                
                System.out.println("Would you like to exit? (yes/no)");
                String response = kb.nextLine().trim().toLowerCase();
                continueProgram = !response.equals("yes");
            }
        }
    }
}