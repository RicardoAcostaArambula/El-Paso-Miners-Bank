import java.io.*;
import java.util.*;

public class Log {

    public static boolean verifyUser(String username, int pin) {
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && Integer.parseInt(data[1]) == pin) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static float getAccountBalance(String username, String accountType) {
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[2].equals(accountType)) {
                    return Float.parseFloat(data[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    public static void updateAccountBalance(String username, String accountType, float newBalance) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username) && data[2].equals(accountType)) {
                    line = data[0] + "," + data[1] + "," + data[2] + "," + newBalance;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("customers.txt"))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logTransaction(String username, String transactionType, String accountType, float amount) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            bw.write(username + "," + transactionType + "," + accountType + "," + amount + "," + new Date().toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
