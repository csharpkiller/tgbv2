package TelegramBot.SimpleBot.HandleCommands;

import TelegramBot.VkApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HandlerException {
    VkApiException exception;
    public HandlerException(VkApiException exception){
        this.exception = exception;
    }

    public void solveExcp(VkApiException exception) throws TelegramApiException {
        throw  new TelegramApiException(exception.getException());
    }
}
