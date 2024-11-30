import java.util.Scanner;

public class VerifyUser {
    public boolean isValidPassword(Customer customer, String input) {
        String fullName = customer.getName() + " " + customer.getLast();
        return fullName.equals(input);
    }

    public boolean promptPassword(Customer customer) {
        int count = 3;
        Scanner kb = new Scanner(System.in);
        System.out.println("Please enter your password (your full name): ");
        while (count > 0) {
            String input = kb.nextLine();
            if (isValidPassword(customer, input)) {
                return true;
            } else if (count > 1) {
                System.out.println("Wrong password. Please try again (enter your full name): ");
            }
            count -= 1;
        }
        System.out.println("You have consumed all attempts. Try again later.");
        return false;
    }
}