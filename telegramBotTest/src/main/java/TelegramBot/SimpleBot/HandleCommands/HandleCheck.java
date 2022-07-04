package TelegramBot.SimpleBot.HandleCommands;

import TelegramBot.SimpleBot.MainBot1;
import TelegramBot.User;
import VKontakte.VkUserInfo;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class HandleCheck {
    private MainBot1 bot;
    private User user;
    private SendMessage sendMessage;

    public HandleCheck(MainBot1 bot, User user, SendMessage sendMessage){
        this.bot = bot;
        this.user = user;
        this.sendMessage = sendMessage;
    }

    public void checkMessage() throws TelegramApiException {
        if(user.getMessageToSpam()==null){
            sendMessage.setText("No have message to spam");
            bot.execute(sendMessage);
        }
        else {
            sendMessage.setText("Message: " + user.getMessageToSpam());
            bot.execute(sendMessage);
        }
    }

    public void checkGroup() throws TelegramApiException {
        if(user.getGroupToSpam()==null){
            sendMessage.setText("No have group to spam");
            bot.execute(sendMessage);
        }
        else {
            String url = "Group to spam: ";
            StringBuffer stringBuffer1 = new StringBuffer(url);
            stringBuffer1.append("https://vk.com/");
            stringBuffer1.append(user.getGroupToSpam());
            sendMessage.setText(stringBuffer1.toString());
            bot.execute(sendMessage);
        }
    }

    public void checkAccounts() throws TelegramApiException {
        StringBuffer stringBuffer = new StringBuffer("Verified accounts: \n");
        ArrayList<VkUserInfo> vkUserInfos = user.getVkAccountsInfo();
        for (VkUserInfo e: vkUserInfos
        ) {
            stringBuffer.append(e.getLogin());
            stringBuffer.append("\n");
        }
        sendMessage.setText(stringBuffer.toString());
        bot.execute(sendMessage);
    }
}
