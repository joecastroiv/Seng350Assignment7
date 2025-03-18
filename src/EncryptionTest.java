
public class EncryptionTest {
    public static void main(String[] args) {
        String filePath = "testfile.txt";
        int encryptionKey = 3;
        int maxKey = 6;
        String knownText = "known";
        int testIterations = 1000;

        FileHandler fileHandler = new FileHandler();
        EncryptionService encryptionService = new EncryptionService(testIterations);
        TimingService timingService = new TimingService();
        OutputWriter outputWriter = new OutputWriter();

        String originalMessage = fileHandler.readFile(filePath);
        if (originalMessage == null) return;

        String encryptedMessage = encryptionService.caesarEncrypt(originalMessage);

        boolean result = timingService.runEncryptionDecryptionTest(filePath, encryptionKey,
                encryptionService::caesarEncrypt,
                encryptionService::caesarDecrypt);

        if (result) {
            System.out.println("Encryption and decryption were successful.");
        } else {
            System.out.println("Encryption and decryption failed.");
        }

        double averageBreakTimeMs = timingService.calculateAverageBreakTime(encryptedMessage, maxKey, knownText, testIterations);

        outputWriter.writeOutput(originalMessage, encryptedMessage, averageBreakTimeMs);
    }

}
