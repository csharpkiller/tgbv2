package TelegramBot.SimpleBot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// main class создаем сущности и запускаем бота и ресивер => mainBot1

public class Application {
    //private MainBot1 spamBot = new MainBot1();

    public static void main(String[] args) throws Exception {
        MainBot1 spamBot = new MainBot1();
        spamBot.botConnect();
        MessRec reciver = new MessRec(spamBot);
        reciver.run();
    }
}
