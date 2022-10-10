package TelegramBot.SimpleBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
// 5??
public class ReadSecretFiles {
    public static String readBufferReader(String fileName){
        String value = null;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            value = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
