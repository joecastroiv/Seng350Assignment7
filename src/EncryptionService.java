class EncryptionService {
    private int key;

    public EncryptionService(int key) {
        this.key = key;
    }

    public String caesarEncrypt(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append((char) (c + key));
        }
        return result.toString();
    }

    public String caesarDecrypt(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append((char) (c - key));
        }
        return result.toString();
    }
}