package TelegramBot.SimpleBot.HandleCommands;

import VKontakte.VkAPI;
import VKontakte.VkAPIResponse;
import VKontakte.VkUserInfo;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
/**
 * класс проверяет все ли норм с логином и паролем что нам отправили, в правильном ли формате: login password
 * и отсылает клиенту оч интересную информацию если была ошибка в парсинге или еще где - то)
 * **/
public class HandleAddAcc {
    private String login;
    private String password;
    private String token;

    public String operate(Message message) {
        String textMessage = message.getText();
        String[] splt = textMessage.split(" ");
        String login;
        String password;
        try {
            login = splt[0];
            password = splt[1];
            this.login = login;
            this.password = password;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return "Error!\n" +
                    "Incorrectly entered data.\n" +
                    "Use the format: login password";
        }
        try {
            VkAPIResponse response = VkAPI.getToken(login, password);
            String token = "";
            if(response.isSuccess()) token = (String)response.getData();
            this.token = token;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        if(token.length() <= 1) return "Error! Wrong login or password / cant log in";
        return "";
    }

    public VkUserInfo getVkUserInfo(){
        return new VkUserInfo(login,password,token);
    }

}
