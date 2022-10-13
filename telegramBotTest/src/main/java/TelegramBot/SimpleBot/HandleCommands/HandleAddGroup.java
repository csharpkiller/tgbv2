package TelegramBot.SimpleBot.HandleCommands;

import TelegramBot.TypeException;
import TelegramBot.VkApiException;
import VKontakte.VkAPI;
import VKontakte.controlVersion;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
// just like HandleAddAcc
public class HandleAddGroup{
    private final controlVersion controlVersion = new controlVersion();
    private String group;
    boolean connected = true;
    private boolean tryRecconect = false;

    public String operate(Message message) throws TelegramApiException {
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
            //connected = !VkAPI.createBaseUsers(controlVersion.getTestToken(), group).isSuccess();
            connected = !VkAPI.tryGetOneUserGroup(group).isSuccess();
        } catch (VkApiException e) {
            if(e.getExcpType() == TypeException.IOException){
                if(!tryRecconect){
                    tryRecconect = true;
                    operate(message);
                }
                else{
                    return "connection error, please try again later";
                }
            }
            else{
                HandlerException handlerException = new HandlerException(e);
                handlerException.solveExcp(e);
            }
        }

        if(connected) return "";
        else return "This is group with private users or wrong domain";
    }
    public String retrunGroup(){
        return group;
    }
}
