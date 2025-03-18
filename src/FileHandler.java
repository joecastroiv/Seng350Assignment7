import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class FileHandler {
    public String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return null;
        }
        return content.toString();
    }
}