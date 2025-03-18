import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

public class EncryptionTest {
		
    // Function to automate encryption, decryption, and comparison
    public static boolean runEncryptionDecryptionTest(String filePath, int key, Function<String, String> encryptionAlgorithm, Function<String, String> decryptionAlgorithm) {
        StringBuilder originalMessage = new StringBuilder();
        StringBuilder encryptedMessage = new StringBuilder();
        StringBuilder decryptedMessage = new StringBuilder();

        // Read file content (I/O time not measured)
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                originalMessage.append(scanner.nextLine()).append("\n");
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return false;
        }

        // Run the encryption multiple times to amplify timing
        final int iterations = 1000;
        long encryptionTotalTimeNs = 0;
        long decryptionTotalTimeNs = 0;

        for (int i = 0; i < iterations; i++) {
            long encryptionStartTime = System.nanoTime();
            encryptedMessage.setLength(0);
            encryptedMessage.append(encryptionAlgorithm.apply(originalMessage.toString()));
            long encryptionEndTime = System.nanoTime();
            encryptionTotalTimeNs += (encryptionEndTime - encryptionStartTime);

            long decryptionStartTime = System.nanoTime();
            decryptedMessage.setLength(0);
            decryptedMessage.append(decryptionAlgorithm.apply(encryptedMessage.toString()));
            long decryptionEndTime = System.nanoTime();
            decryptionTotalTimeNs += (decryptionEndTime - decryptionStartTime);
        }

        long averageEncryptionTimeNs = encryptionTotalTimeNs / iterations;
        long averageDecryptionTimeNs = decryptionTotalTimeNs / iterations;

        double encryptionTimeMs = averageEncryptionTimeNs / 1_000_000.0;
        double decryptionTimeMs = averageDecryptionTimeNs / 1_000_000.0;

        boolean isCorrect = originalMessage.toString().equals(decryptedMessage.toString());

        // Write all output to output.txt
        try (FileWriter outputWriter = new FileWriter("output.txt", true)) {
            // Writing the encrypted message
            outputWriter.write("Encrypted message:\n");
            outputWriter.write(encryptedMessage.toString() + "\n\n");

            // Writing the decrypted message
            outputWriter.write("Decrypted message:\n");
            outputWriter.write(decryptedMessage.toString() + "\n\n");

            // Writing total words in the file
            int totalWords = originalMessage.toString().split("\\s+").length;
            outputWriter.write("Total words in the file: " + totalWords + "\n\n");

            // Writing total time to encrypt and decrypt
            outputWriter.write(String.format("Average time to encrypt: %.3f ms (%d ns)\n", encryptionTimeMs, averageEncryptionTimeNs));
            outputWriter.write(String.format("Average time to decrypt: %.3f ms (%d ns)\n", decryptionTimeMs, averageDecryptionTimeNs));

            outputWriter.write(isCorrect ? "Encryption and decryption were successful.\n\n" : "Encryption and decryption failed.\n\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing output to file.");
            e.printStackTrace();
        }

        return isCorrect;
    }

    // Method to calculate average time to break the cipher
    public static double calculateAverageBreakTime(String encryptedMessage, int maxKey, String knownText, int testIterations) {
        long totalTimeNs = 0;

        for (int i = 0; i < testIterations; i++) {
            long startTime = System.nanoTime();

            for (int key = 1; key <= maxKey; key++) {
                String decryptedAttempt = caesarDecrypt(encryptedMessage, key);
                if (decryptedAttempt.contains(knownText)) {
                    break;
                }
            }

            long endTime = System.nanoTime();
            totalTimeNs += (endTime - startTime);
        }

        return (totalTimeNs / testIterations) / 1_000_000.0; // Convert average time to ms
    }

    // Example Caesar cipher encryption algorithm (shift characters by key)
    public static String caesarEncrypt(String input, int key) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append((char) (c + key));
        }
        return result.toString();
    }

    // Example Caesar cipher decryption algorithm (reverse the shift)
    public static String caesarDecrypt(String input, int key) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append((char) (c - key));
        }
        return result.toString();
    }

    // Main method for testing
    public static void main(String[] args) {
        String filePath = "testfile.txt"; // Path to test file
        int encryptionKey = 3; // Known key for encryption
        int maxKey = 6; // Maximum possible key for brute-force attempt
        String knownText = "known"; // Known word for verification
        int testIterations = 1000; // Number of test runs to calculate average time

        StringBuilder originalMessage = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                originalMessage.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return;
        }

        String encryptedMessage = caesarEncrypt(originalMessage.toString(), encryptionKey);

        boolean result = runEncryptionDecryptionTest(filePath, encryptionKey,
                message -> caesarEncrypt(message, encryptionKey),
                message -> caesarDecrypt(message, encryptionKey));
        
     // Use the result to indicate if encryption and decryption were successful
        if (result) {
            System.out.println("Encryption and decryption were successful.");
        } else {
            System.out.println("Encryption and decryption failed.");
        }

        double averageBreakTimeMs = calculateAverageBreakTime(encryptedMessage, maxKey, knownText, testIterations);

        // Write breaking cipher results to output.txt
        try (FileWriter outputWriter = new FileWriter("output.txt", true)) {
            outputWriter.write("Original Message:\n" + originalMessage + "\n\n");
            outputWriter.write("Encrypted Message:\n" + encryptedMessage + "\n\n");
            outputWriter.write(String.format("Average time to break the cipher: %.3f ms\n", averageBreakTimeMs));
            System.out.println("Output saved to output.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing output to file.");
            e.printStackTrace();
        }
    }
}
