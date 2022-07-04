package TelegramBot.SimpleBot.HandleCommands;

import VKontakte.VkAPI;
import VKontakte.controlVersion;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class HandleAddGroup{
    private final controlVersion controlVersion = new controlVersion();
    private String group;
    boolean connected = true;

    public String operate(Message message){
        String group;
       try{
           group = message.getText().split(" ")[0];
           this.group = group;
       }
       catch (ArrayIndexOutOfBoundsException e){
           return "Error!\n" +
                   "Incorrectly entered data.\n" +
                   "Use the format: groupDomain";
       }
        try {
            connected = !VkAPI.createBaseUsers(controlVersion.getTestToken(), group).isSuccess();
        } catch (IOException e) {
            return "This is group with private users or wrong domain";
        } catch (ParseException e) {
            return "This is group with private users or wrong domain";
        }

        if(connected) return "";
        else return "This is group with private users or wrong domain";
    }
    public String retrunGroup(){
        return group;
    }
}
