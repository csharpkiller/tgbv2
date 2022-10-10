package TelegramBot.SimpleBot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//  рекурсивно проверяет события бота, при получении добавляем в очередь для обработки => MessRec

public class MainBot1 extends TelegramLongPollingBot {
    final int RECONNECT_PAUSE = 10000;
    public final Queue<Update> receiveQueue = new ConcurrentLinkedQueue<>();

    @Override
    public String getBotUsername() {
        return "@spam_vk_bot";
    }

    @Override
    public String getBotToken() {
        return ReadSecretFiles.readBufferReader("secret");
    }

    @Override
    public void onUpdateReceived(Update update) {
        receiveQueue.add(update);
    }

    public void botConnect() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }

}
