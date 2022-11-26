/*
package Archive;


import VKontakte.Exceptions.Error14_need_Capctha;
import VKontakte.Exceptions.ErrorDetection;
import VKontakte.Exceptions.ErrorHandler;
import VKontakte.MessageLink.CreateAddFriendLink;
import VKontakte.MessageLink.CreateMessageLink;
import VKontakte.MessageLink.GetGroupUsers;
import VKontakte.MessageLink.GetToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SpamMachine {
    private String access_token;
    private ArrayList<Integer> groupUsers;
    private String message;
    private static final String VERSION = "5.81";

    public SpamMachine(String login, String password, String group, String message) throws Exception {
        GetToken getToken = new GetToken(login, password);
        access_token = getToken.getToken();
        GetGroupUsers getGroupUsers= new GetGroupUsers(group, access_token,VERSION);
        groupUsers = getGroupUsers.createBaseUsers();
        this.message = message;
        //start();
    }

    public void start() throws Exception {
        for (int e: groupUsers
        ) {
            CreateMessageLink link = new CreateMessageLink(message, e, access_token);

            String url = link.createURL();
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            String jsonString = doc.body().text();


            ErrorDetection errorDetection = new ErrorDetection(jsonString);
            if(errorDetection.Detection()){
                ErrorHandler errorHandler = new ErrorHandler(errorDetection.getErrorNumber(), errorDetection.getParseJsObj(), link);
            }

            System.out.println(jsonString);
            System.out.println("ССылка на сообщение: " + url);

            TimeUnit.SECONDS.sleep(5);
        }
    }

    */
/*public void test() throws Exception {
        while (true){
            CreateMessageLink link = new CreateMessageLink(message, 189989950, access_token);
            String url = link.createURL();
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            String jsonString = doc.body().text();

            ErrorDetection errorDetection = new ErrorDetection(jsonString);
            if(errorDetection.getErrorNumber() == 14){
                Error14_need_Capctha error14_need_capctha = new Error14_need_Capctha(errorDetection.getJsonObject());
                try {
                    Desktop.getDesktop().browse(new URL(error14_need_capctha.getCaptchaUrl()).toURI());
                } catch (Exception d) {
                    d.printStackTrace();
                }
                System.out.println("Введите символы с картинки");
                Scanner in = new Scanner(System.in);
                String captcha = in.next();
                in.close();
                Jsoup.connect(link.addCapcha(error14_need_capctha.getCaptchaSid(), captcha)).ignoreContentType(true).get();

            }
            System.out.println(jsonString);
            System.out.println("ССылка на сообщение: " + url);

            TimeUnit.SECONDS.sleep(5);

        }
    }*//*


    public void addFriends() throws IOException, InterruptedException {
        for (int e: groupUsers
        ) {
            CreateAddFriendLink link = new CreateAddFriendLink(e, access_token, VERSION);
            Document doc = Jsoup.connect(link.createLink()).ignoreContentType(true).get();
            String str = doc.body().text();
            System.out.println(str);
            TimeUnit.SECONDS.sleep(3);
        }
    }

}
*/
