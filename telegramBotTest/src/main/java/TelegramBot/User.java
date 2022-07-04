package TelegramBot;

import Main.RunnableSpam;
import VKontakte.VkUserInfo;

import java.util.ArrayList;

public class User {
    private long chatId;
    private String lastMessage;
    private boolean needAnswer;
    private ArrayList<VkUserInfo> vkAccountsInfo = new ArrayList<VkUserInfo>();
    private String groupToSpam;
    private String messageToSpam;

    //////////////////////////////////////////////
    private RunnableSpam spamer;
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

    /////////////////////////////////////////////////////
    public void setSpamer(RunnableSpam spamer) {
        this.spamer = spamer;
    }

    public RunnableSpam getSpamer() {
        return spamer;
    }
    /////////////////////////////////////////////////////
}
