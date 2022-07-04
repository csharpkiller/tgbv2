package VKontakte;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class VkAPI {
    private static final controlVersion controlVersion = new controlVersion();

    // >* token
    public static VkAPIResponse getToken(String login, String password) throws IOException, ParseException {
        String url = "https://oauth.vk.com/token?grant_type=password&client_id=2274003&client_secret=hHbZxrka2uZ6jB1inYsH&username=";
        StringBuffer stringBuffer = new StringBuffer(url);
        stringBuffer.append(login);
        stringBuffer.append("&password=");
        stringBuffer.append(password);
        url = stringBuffer.toString();
        if(errodDetection(linkConnect(url))) return createAnswer(linkConnect(url), "error");
        return createAnswer(linkConnect(url), "access_token");
    }
    // *<

    // >* group
    private static VkAPIResponse createAnswer(String jsonString, String keyword) throws ParseException, IOException {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject= (JSONObject) parser.parse(jsonString);

        /*boolean errorDetection = errodDetection(jsonString);
        if(errorDetection) return new VkAPIResponse(getErrorNumber(jsonObject), true);*/

        if(keyword.equals("error")) return new VkAPIResponse<String>(jsonObject.get(keyword).toString(), true);

        String answer = jsonObject.get(keyword).toString();
        return new VkAPIResponse<String>(answer);
    }

    private static String groupLink(String nameGroup, int offset, String access_token) throws IOException, ParseException {
        String link = "https://api.vk.com/method/groups.getMembers?group_id=";
        StringBuffer buffer = new StringBuffer(link);
        String encodedUrl = URLEncoder.encode(nameGroup,"UTF-8");
        buffer.append(nameGroup);
        buffer.append("&v=");
        buffer.append(controlVersion.getVersion());
        buffer.append("&sort=id_desc&offset=");
        buffer.append(offset);
        buffer.append("&count=1&fields=can_write_private_message&access_token=");
        buffer.append(access_token);
        return buffer.toString();
    }

    private static String linkConnect(String url) throws ParseException, IOException {
        Document document = Jsoup.connect(url).ignoreContentType(true).get();
        String jsonString = document.body().text();
        return jsonString;
    }

    public static VkAPIResponse createBaseUsers(String token, String group) throws IOException, ParseException {
        ArrayList<Integer> usersId = new ArrayList<Integer>();
        int offset = 0;
        String url = groupLink(group, offset, token);
        if(errodDetection(linkConnect(url))){
            return createAnswer(linkConnect(url), "error");
        }
        return createBase(token, group, offset, url, usersId);
    }
    private static VkAPIResponse createBase(String token, String group, int offset, String url, ArrayList<Integer> usersId) throws IOException {
        Document document = Jsoup.connect(url).ignoreContentType(true).get();
        String str =document.body().text();

        int index = str.indexOf('[');
        StringBuffer buffer = new StringBuffer(str);
        buffer.delete(0, index + 1);
        String[] strSplt = buffer.toString().split(",");
        for(int i=0; i<strSplt.length; i+= 4){
            StringBuffer buf = new StringBuffer(strSplt[i]);
            buf.delete(0, 6);
            String bufstr = buf.toString();
            int userId = Integer.parseInt(bufstr);
            usersId.add(userId);
        }

        // start recurcy
        StringBuffer getCount = new StringBuffer(str);
        getCount.delete(0, 21);
        String[] splt = getCount.toString().split(",");
        int count = Integer.parseInt(splt[0]);
        if(count == 1000){
            offset += 1000;
            createBase(token, group, offset, url, usersId);
        }
        return new VkAPIResponse<ArrayList<Integer>>(usersId);
    }
    // *<

    // >* message

    public static VkAPIResponse createMessageLink(String domain, Integer userId, String message, String token) throws Exception{
        String url = "https://api.vk.com/method/messages.send?";
        StringBuffer strBuffer = new StringBuffer(url);

        if(domain != null){
            strBuffer.append("domain=");
            strBuffer.append(domain);
        }
        else if(userId != null){
            strBuffer.append("user_id=");
            strBuffer.append(userId);
        }
        strBuffer.append("&message=");
        message = URLEncoder.encode(message,"UTF-8");
        strBuffer.append(message);
        strBuffer.append("&v=");
        strBuffer.append(controlVersion.getVersion());
        strBuffer.append("&access_token=");
        strBuffer.append(token);
        String messageUrl = strBuffer.toString();
        return new VkAPIResponse<String>(messageUrl);
    }

    // *<

    public static boolean errodDetection(String jsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject= (JSONObject) parser.parse(jsonString);
        if(jsonObject.get("error") != null){
            return true;
        }
        return false;
    }

    private static int getErrorNumber(JSONObject jsonObject){
        JSONObject jo = (JSONObject) jsonObject.get("error");
        int errorNumber;
        try {
            errorNumber = Integer.parseInt(jo.get("error_code").toString());
        } catch (NumberFormatException e) {
            System.out.println("ErrorHandler ошибка в парсинге error code");
            errorNumber = 505476;
        }
        return errorNumber;
    }
}
