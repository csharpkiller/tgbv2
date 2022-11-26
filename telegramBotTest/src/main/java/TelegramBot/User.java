package TelegramBot;

import Main.RunnableSpam;
import VKontakte.VkUserInfo;

import java.util.ArrayList;

// этот класс нужен для messRec, здесь хроним историю общения с пользователем телеграмм бота
public class User {
    private long chatId; // его чат id
    private String lastMessage;
    private boolean needAnswer;
    private ArrayList<VkUserInfo> vkAccountsInfo = new ArrayList<VkUserInfo>();
    private String groupToSpam;
    private String messageToSpam;

    //////////////////////////////////////////////
    private RunnableSpam spamer; // not used
    /////////////////////////////////////////////

    public User(long chatId){
        this.chatId = chatId;
    }

    public User(long chatId, String lastMessage, boolean needAnswer){
        this.chatId = chatId;
        this.lastMessage = lastMessage;
        this.needAnswer = needAnswer;
    }

    /*public void addAccount(String login, String password){
        VkUserInfo account = new VkUserInfo(login, password);
    }*/

    public void addAccount(VkUserInfo account){
        vkAccountsInfo.add(account);
    }

    public void changeInfo(String lastMessage, boolean needAnswer){
        this.lastMessage = lastMessage;
        this.needAnswer = needAnswer;
    }

    public ArrayList<VkUserInfo> getVkAccountsInfo(){
        return vkAccountsInfo;
    }

    public long getChatId(){
        return chatId;
    }

    public String getLastMessage(){
        return lastMessage;
    }

    public boolean needAnswer() { return needAnswer; }

    public void setMessageToSpam(String messageToSpam) {
        this.messageToSpam = messageToSpam;
    }

    public String getMessageToSpam() {
        return messageToSpam;
    }

    public void setGroupToSpam(String groupToSpam) {
        this.groupToSpam = groupToSpam;
    }
    public String getGroupToSpam(){
        return groupToSpam;
    }

    ///////////////////////////////////////////////////// not used
    public void setSpamer(RunnableSpam spamer) {
        this.spamer = spamer;
    }

    public RunnableSpam getSpamer() {
        return spamer;
    }
    /////////////////////////////////////////////////////
}
