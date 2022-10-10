package VKontakte;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

// объект для хроениня информации пользователя: vk login password

public class VkUserInfo {
    private String login;
    private String password;
    private String token;
    private boolean confirmed;

    public VkUserInfo(String login, String password){
        this.login = login;
        this.password = password;
    }
    public VkUserInfo(String login, String password, String token){
        this.login = login;
        this.password = password;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() { return password; }
}
