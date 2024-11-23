import java.util.Scanner;
public class VerifyUser {
    public boolean isValidPassword(Customer customer, String input){
        String password = customer.get_name();
        return password.equals(input);
    }
    public boolean promptPassword(Customer customer){
        int count = 3;
        Scanner kb = new Scanner(System.in);
        System.out.println("Please enter your password: ");
        while (count > 0){
            String input = kb.nextLine();
            if (isValidPassword(customer, input)) {
                return true;
            } else if (count > 1){
                System.out.println("Wrong password. Please try again: ");
            }
            count-=1;
        }
        System.out.println("You have consume all attempts try again later.");
        return false;
    }
}