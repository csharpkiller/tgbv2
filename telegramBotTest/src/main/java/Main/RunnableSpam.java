package Main;

import TelegramBot.SimpleBot.MainBot1;
import VKontakte.VkAPIResponse;
import VKontakte.VkUserInfo;
import VKontakte.VkAPI;
import VKontakte.controlVersion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RunnableSpam implements Runnable{
    private final controlVersion controlVersion = new controlVersion();
    private volatile boolean mFinish = false;
    private ArrayList<VkUserInfo> accounts;
    private String group;
    private String spamMessage;
    private MainBot1 bot;
    public Thread t;

    public RunnableSpam(ArrayList<VkUserInfo> accounts, String group, String spamMessage){
        this.accounts = accounts;
        this.group = group;
        this.spamMessage = spamMessage;
        t = new Thread(this, "potok");
    }

    public void finish(){
        mFinish=true;
    }

    @Override
    public void run() {
        do{
            if(!mFinish){
                try {
                    start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else return;

            try {
                Thread.sleep(5);
            }
            catch (InterruptedException e) {}
        }
        while (true);
    }

    private void start() throws Exception {
        VkAPIResponse<ArrayList<Integer>> response = VkAPI.createBaseUsers(controlVersion.getTestToken(), group);
        ArrayList<Integer> groupUsers = new ArrayList<Integer>();
        if(response.isSuccess()){
            groupUsers = response.getData();
        }
        for (VkUserInfo acc: accounts
             ) {
            String token ="";
            VkAPIResponse<String> response1 = VkAPI.getToken(acc.getLogin(), acc.getPassword());
            if(response.isSuccess()) token = response1.getData();

            for (int userId: groupUsers
                 ) {
                VkAPIResponse<String> response2 = VkAPI.createMessageLink(null, userId, spamMessage, token);
                String url = "";
                if(response2.isSuccess()) url = response2.getData();
                Document doc = Jsoup.connect(url).ignoreContentType(true).get();
                String jsonString = doc.body().text();


                System.out.println(jsonString);
                System.out.println("ССылка на сообщение: " + url);

                TimeUnit.SECONDS.sleep(10000000);
            }
        }
        finish(); // close thread
    }


}
