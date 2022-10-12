package TelegramBot.SimpleBot.HandleCommands;

import TelegramBot.VkApiException;

public class HandlerException {
    VkApiException exception;
    public HandlerException(VkApiException exception){
        this.exception = exception;
    }

    public void solveExcp(){}
}
