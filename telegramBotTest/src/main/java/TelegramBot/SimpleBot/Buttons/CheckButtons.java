package TelegramBot.SimpleBot.Buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

// кнопочки телеграм бота по которым можно клацать

public class CheckButtons {
    public InlineKeyboardMarkup addButton(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton checkMessage = new InlineKeyboardButton();
        InlineKeyboardButton checkGroup = new InlineKeyboardButton();
        InlineKeyboardButton checkAccounts = new InlineKeyboardButton();
        InlineKeyboardButton checkAllToSpam = new InlineKeyboardButton();

        checkAccounts.setText("check accounts");
        checkGroup.setText("check spam group");
        checkMessage.setText("check spam message");
        checkAllToSpam.setText("check all to spam");

        checkAccounts.setCallbackData("checkAccounts");
        checkGroup.setCallbackData("checkGroup");
        checkMessage.setCallbackData("checkMessage");
        checkAllToSpam.setCallbackData("checkAll");

        List<InlineKeyboardButton> line1 = new ArrayList<>();
        line1.add(checkAccounts);
        line1.add(checkGroup);
        line1.add(checkMessage);

        List<InlineKeyboardButton> line2 = new ArrayList<>();
        line2.add(checkAllToSpam);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(line1);
        rowList.add(line2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
