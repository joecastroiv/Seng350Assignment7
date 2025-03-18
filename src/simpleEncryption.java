import java.util.Scanner;

public class simpleEncryption {

	public static void main(String[] args) {
		
		// Create a Scanner object attached to the keyboard
        Scanner in = new Scanner (System.in);
         
        // Plain text message to be encrypted from user
        System.out.print("Please enter something to be encrypted or the encrypted message: ");
        String userMessage = in.nextLine();
 
        /* Integer encryption or description key from user.
         * User must provide the opposite mathematical operation of the original key for description.
         * For example, if encryption was done using key 6, then use -6 to decrypt.
        */
        System.out.print("Please enter an integer value between 1 and 6 for encryption key: ");
        int userKey = in.nextInt();
         
        // User error check for key
        while (userKey > 6) {
            System.out.print("Your key must be between 1 and 6.\nPlease enter an integer value between 1 and 6: ");
            userKey = in.nextInt();
        }
         
        // Modulus from user (THIS IS NOT IN USE)
        //System.out.print("What is the secret secondary key? ");
        //int secondKey = in.nextInt();
                 
        // Encryption or decryption processing
        String message = userMessage;
        int key = userKey;
        char [] chars = message.toCharArray();
        for(char i : chars) {
            i += key; 
            System.out.print(i);
        }
        
	}

}
