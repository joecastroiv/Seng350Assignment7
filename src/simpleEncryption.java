import java.util.Scanner;

//Refactored SimpleEncryption class with smaller, focused methods
public class simpleEncryption {
 private static final int MAX_KEY = 6;

 public static void main(String[] args) {
     Scanner in = new Scanner(System.in);

     String userMessage = getUserMessage(in);
     int userKey = getUserKey(in);

     String encryptedMessage = encryptMessage(userMessage, userKey);
     System.out.println("Encrypted message: " + encryptedMessage);
 }

 private static String getUserMessage(Scanner in) {
     System.out.print("Please enter something to be encrypted or the encrypted message: ");
     return in.nextLine();
 }

 private static int getUserKey(Scanner in) {
     System.out.print("Please enter an integer value between 1 and 6 for encryption key: ");
     int userKey = in.nextInt();

     while (userKey > MAX_KEY) {
         System.out.print("Your key must be between 1 and 6.\nPlease enter an integer value between 1 and 6: ");
         userKey = in.nextInt();
     }

     return userKey;
 }

 private static String encryptMessage(String message, int key) {
     char[] chars = message.toCharArray();
     StringBuilder encryptedMessage = new StringBuilder();

     for (char c : chars) {
         encryptedMessage.append((char) (c + key));
     }

     return encryptedMessage.toString();
 }
}
