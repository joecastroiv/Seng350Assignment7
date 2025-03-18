import java.io.FileWriter;
import java.io.IOException;

class OutputWriter {
    public void writeOutput(String originalMessage, String encryptedMessage, double averageBreakTimeMs) {
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

    public void writeTestResults(String encryptedMessage, String decryptedMessage, int totalWords, double encryptionTimeMs, long averageEncryptionTimeNs, double decryptionTimeMs, long averageDecryptionTimeNs, boolean isCorrect) {
        try (FileWriter outputWriter = new FileWriter("output.txt", true)) {
            outputWriter.write("Encrypted message:\n");
            outputWriter.write(encryptedMessage + "\n\n");

            outputWriter.write("Decrypted message:\n");
            outputWriter.write(decryptedMessage + "\n\n");

            outputWriter.write("Total words in the file: " + totalWords + "\n\n");

            outputWriter.write(String.format("Average time to encrypt: %.3f ms (%d ns)\n", encryptionTimeMs, averageEncryptionTimeNs));
            outputWriter.write(String.format("Average time to decrypt: %.3f ms (%d ns)\n", decryptionTimeMs, averageDecryptionTimeNs));

            outputWriter.write(isCorrect ? "Encryption and decryption were successful.\n\n" : "Encryption and decryption failed.\n\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing output to file.");
            e.printStackTrace();
        }
    }
}