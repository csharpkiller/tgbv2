package TelegramBot.SimpleBot;

import Main.RunnableSpam;
import TelegramBot.SimpleBot.Buttons.CheckButtons;
import TelegramBot.SimpleBot.HandleCommands.HandleAddAcc;
import TelegramBot.SimpleBot.HandleCommands.HandleAddGroup;
import TelegramBot.SimpleBot.HandleCommands.HandleCheck;
import TelegramBot.SimpleBot.HandleCommands.HandleMainCommandSpam;
import TelegramBot.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class MessRec {
    private final MainBot1 bot;
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    private ArrayList<User> users = new ArrayList<User>();
    private Map<Long, RunnableSpam> map = new HashMap<Long, RunnableSpam>();

    public MessRec(MainBot1 spamBot) {
        bot=spamBot;
    }

    public void run() throws Exception {
        while (true) {
            for (Update update = bot.receiveQueue.poll(); update != null; update = bot.receiveQueue.poll()) {
                if(update.hasCallbackQuery()){
                    handleCallback(update);
                }
                if(update.hasMessage()){
                    messageHandler(update);
                }
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void handleCallback(Update update) throws TelegramApiException {
        Message message = update.getCallbackQuery().getMessage();
        int userIndex = findUser(message.getChatId());
        if(userIndex < 0){
            users.add(new User(message.getChatId()));
            userIndex = users.size() - 1;
        }
        User user = users.get(userIndex);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);

        HandleCheck handleCheck = new HandleCheck(bot, user, sendMessage);
        switch (update.getCallbackQuery().getData()){
            case "checkAccounts":
                handleCheck.checkAccounts();
                break;
            case "checkGroup":
                handleCheck.checkGroup();
                break;
            case "checkMessage":
                handleCheck.checkMessage();
                break;
            case "checkAll":
                handleCheck.checkAccounts();
                handleCheck.checkGroup();
                handleCheck.checkMessage();
                break;
        }
    }

    private void messageHandler(Update update) throws Exception {
        Message message = update.getMessage();

        int userIndex = findUser(message.getChatId());
        if(userIndex < 0){
            users.add(new User(message.getChatId()));
            userIndex = users.size() - 1;
        }
        User user = users.get(userIndex);

        boolean messageIsCommand = messageIsCommand(message);
        boolean needAnswer = user.needAnswer();
        if(messageIsCommand) handleCommand(message, user);
        else if(needAnswer) handleAnswer(message, user);
        else handleMessage(message);
    }

    private void handleCommand(Message message, User user) throws Exception {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);

        Optional<MessageEntity> commandEntry = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
        String command = message.getText().substring(commandEntry.get().getOffset(), commandEntry.get().getLength());
        user.changeInfo(command, true);
        HandleCheck handleCheck = new HandleCheck(bot, user, sendMessage);
        switch (command){
            case "/check":
                user.changeInfo(command, false);
                sendMessage.setText("What do you want to check?");
                CheckButtons checkButtons = new CheckButtons();
                InlineKeyboardMarkup inlineKeyboardMarkup = checkButtons.addButton();
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                bot.execute(sendMessage);
                break;
            case "/test":
                sendMessage.setText("testing bot with queue");
                user.changeInfo(command, false);
                bot.execute(sendMessage);
                break;
            case "/add_account":
                sendMessage.setText("Enter your VKontakte username and password.\n" +
                        "Use the format:\n" +
                        "login password");
                user.changeInfo(command, true);
                bot.execute(sendMessage);
                break;
            case "/check_my_accounts":
                user.changeInfo(command, false);
                handleCheck.checkAccounts();
                break;
            case "/add_spam_group":
                user.changeInfo(command, true);
                sendMessage.setText("Enter group domain name \n Example: for groups address https://vk.com/basicprogramming1920 domain name is basicprogramming1920 \n Use the format: \n name");
                bot.execute(sendMessage);
                break;
            case "/check_spam_group":
                user.changeInfo(command, false);
                handleCheck.checkGroup();
                break;
            case "/add_message_to_spam":
                user.changeInfo(command, true);
                sendMessage.setText("Enter ur message. This message will be sent to the members of the specified group.");
                bot.execute(sendMessage);
                break;
            case "/check_message_to_spam":
                user.changeInfo(command, false);
                handleCheck.checkMessage();
                break;
            case "/start":
                user.changeInfo(command, false);
                HandleMainCommandSpam handleMainCommandSpam1 = new HandleMainCommandSpam();
                String result1 = handleMainCommandSpam1.operate(user);
                if(result1.equals("")){
                    RunnableSpam spam = new RunnableSpam(user.getVkAccountsInfo(), user.getGroupToSpam(),user.getMessageToSpam());
                    long userid = user.getChatId();
                    map.put(userid, spam);
                    map.get(userid).t.start();
                    /*user.setSpamer(new RunnableSpam(user.getVkAccountsInfo(), user.getGroupToSpam(),user.getMessageToSpam()));
                    user.getSpamer().t.start();*/
                    sendMessage.setText("Spam started, to end enter /stop");
                    bot.execute(sendMessage);
                }
                else{
                    sendMessage.setText(result1);
                    bot.execute(sendMessage);
                }
                break;
            case "/stop":
                user.changeInfo(command, false);
                /*user.getSpamer().finish();
                user.getSpamer().t.stop();
                user.setSpamer(null)*/;
                map.get(user.getChatId()).t.stop();
                map.remove(user.getChatId());
                sendMessage.setText("spam is over");
                bot.execute(sendMessage);
                break;
            case "/info":
                String info = "some info :)";
                sendMessage.setText(info);
                bot.execute(sendMessage);
                break;
            default:{
                sendMessage.setText("don't have this command, check /info");
                user.changeInfo(command, false);
                bot.execute(sendMessage);
            }
        }
    }

    private void handleAnswer(Message message, User user) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);

        switch (user.getLastMessage()){
            case "/add_account":
                if(message.getText().equals("back")){
                    user.changeInfo("back", false);
                    sendMessage.setText("ok we return back");
                    bot.execute(sendMessage);
                    return;
                }

                HandleAddAcc handleAddAcc = new HandleAddAcc();
                String res = handleAddAcc.operate(message);
                if(res.equals("")){
                    user.addAccount(handleAddAcc.getVkUserInfo());
                    sendMessage.setText("Done!");
                    user.changeInfo("", false);
                    bot.execute(sendMessage);
                }
                else {
                    sendMessage.setText(res);
                    user.changeInfo("", false);
                    bot.execute(sendMessage);
                }
                break;
            case "/add_spam_group":
                HandleAddGroup handleAddGroup = new HandleAddGroup();
                String result = handleAddGroup.operate(message);
                if (result.equals("")) {
                    user.setGroupToSpam(handleAddGroup.retrunGroup());
                    sendMessage.setText("Done!");
                    user.changeInfo("", false);
                    bot.execute(sendMessage);
                }
                else {
                    sendMessage.setText(result);
                    user.changeInfo("", false);
                    bot.execute(sendMessage);
                }
                break;
            case "/add_message_to_spam":
                user.setMessageToSpam(message.getText());
                sendMessage.setText("Done!");
                user.changeInfo("", false);
                bot.execute(sendMessage);
                break;
            default:{
                user.changeInfo("", false);
            }
        }
    }

    private void handleMessage(Message message) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setText("cool guy wrote: " + message.getText());
        bot.execute(sendMessage);
    }

    private boolean messageIsCommand(Message message){
        if(message.hasText() && message.hasEntities()){
            Optional<MessageEntity> commandEntry = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            return (commandEntry.isPresent());
        }
        return false;
    }

    private int findUser(long chatId){
        int result = -1;
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getChatId()==chatId){
                result = i;
                break;
            }
        }
        return result;
    }

/*    private void addNewUser(long chatId, String lastMessage, boolean needAnswer){
        User user =new User(chatId, lastMessage, needAnswer);
        users.add(user);
    }*/
}
