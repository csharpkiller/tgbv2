package TelegramBot.SimpleBot.HandleCommands;

import TelegramBot.User;
import VKontakte.VkUserInfo;

import java.util.ArrayList;

public class HandleMainCommandSpam {

    /*public void start(User user) throws Exception {
        ArrayList<VkUserInfo> userInfo = user.getVkAccountsInfo();
        VkUserInfo vkUserInfo = userInfo.get(0);
        String login = vkUserInfo.getLogin();
        String password = vkUserInfo.getLogin();
        SpamMachine spamMachine = new SpamMachine(login, password, user.getGroupToSpam(), user.getMessageToSpam());
        spamMachine.start();
    }*/

    public String operate(User user){
        ArrayList<String> whatNeed = new ArrayList<String>();
        boolean alright = true;
        if(user.getMessageToSpam() == null){
            whatNeed.add("need Spam Message");
            alright = false;
        }
        if(user.getGroupToSpam() == null){
            whatNeed.add("need Group to spamp");
            alright = false;
        }
        if(user.getVkAccountsInfo().size() == 0){
            whatNeed.add("need VK account");
            alright = false;
        }

        if(alright) return "";
        else {
            String returnMessage = "Error. Not enough information: \n";
            StringBuffer stringBuffer = new StringBuffer(returnMessage);
            for (String e: whatNeed
                 ) {
                stringBuffer.append(e);
                stringBuffer.append("\n");
            }
            return stringBuffer.toString();
        }
    }
}
