import java.util.function.Function;

class TimingService {
    public boolean runEncryptionDecryptionTest(String filePath, int key, Function<String, String> encryptionAlgorithm, Function<String, String> decryptionAlgorithm) {
        FileHandler fileHandler = new FileHandler();
        String originalMessage = fileHandler.readFile(filePath);
        if (originalMessage == null) return false;

        StringBuilder encryptedMessage = new StringBuilder();
        StringBuilder decryptedMessage = new StringBuilder();

        final int iterations = 1000;
        long encryptionTotalTimeNs = 0;
        long decryptionTotalTimeNs = 0;

        for (int i = 0; i < iterations; i++) {
            long encryptionStartTime = System.nanoTime();
            encryptedMessage.setLength(0);
            encryptedMessage.append(encryptionAlgorithm.apply(originalMessage));
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

        boolean isCorrect = originalMessage.equals(decryptedMessage.toString());

        OutputWriter outputWriter = new OutputWriter();
        outputWriter.writeTestResults(encryptedMessage.toString(), decryptedMessage.toString(), originalMessage.split("\\s+").length, encryptionTimeMs, averageEncryptionTimeNs, decryptionTimeMs, averageDecryptionTimeNs, isCorrect);

        return isCorrect;
    }

    public double calculateAverageBreakTime(String encryptedMessage, int maxKey, String knownText, int testIterations) {
        long totalTimeNs = 0;

        for (int i = 0; i < testIterations; i++) {
            long startTime = System.nanoTime();

            for (int key = 1; key <= maxKey; key++) {
                String decryptedAttempt = new EncryptionService(key).caesarDecrypt(encryptedMessage);
                if (decryptedAttempt.contains(knownText)) {
                    break;
                }
            }

            long endTime = System.nanoTime();
            totalTimeNs += (endTime - startTime);
        }

        return (totalTimeNs / testIterations) / 1_000_000.0;
    }
}