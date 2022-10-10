package VKontakte;

// обновить версию для запроса
// test token для тестовых запросов и получения людей из группы

import TelegramBot.SimpleBot.ReadSecretFiles;

// need to be static
public class controlVersion {
    private String version ="5.81";
    private String testToken = ReadSecretFiles.readBufferReader("secret2");

    public String getTestToken() {
        return testToken;
    }

    public String getVersion() {
        return version;
    }
}
