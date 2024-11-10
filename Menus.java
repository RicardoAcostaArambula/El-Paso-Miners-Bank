import java.util.HashMap;
import java.util.Scanner;
public class Menus {
    Scanner kb = new Scanner(System.in);
    public Menus(){

    }
    public int displayModeMenu(){
        
        int option;
        boolean right_option = false;
        do {
            System.out.println("Please Select the one of the following modes:");
            System.out.println("1. Individual Person");
            System.out.println("2. Bank Teller");
            System.out.println("3. Create New Account");
            option = kb.nextInt();
            if (1 <= option && option <=3){
                right_option = true;
            } else {
                System.out.println("Please choose a valid option");
            }
        } while(!right_option);

        return option;
    }
    public String get_full_name_menu(HashMap <String, Customer> users_by_name){
        boolean right_user = false;
        System.out.println("Enter your full name: ");
        if (kb.hasNextLine()) { 
            kb.nextLine();
        }
        String username;
        do {
     