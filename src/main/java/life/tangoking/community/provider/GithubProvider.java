package life.tangoking.community.provider;

import com.alibaba.fastjson.JSON;
import life.tangoking.community.dto.AccessTokenDTO;
import life.tangoking.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * GitHub 提供者
 */
@Component
public class GithubProvider {

    // 通过模拟Post请求，向Github发送请求 获取AccessToken
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String bodyStr = response.body().string();
            String accessToken = bodyStr.split("&")[0].split("=")[1];
            return accessToken;
        } catch (IOException e) {
        }
        return null;
    }

    // 通过模拟Post的方法返回的AccessToken信息
    // 模拟Get方法，去获得User信息
    public GithubUser getUser(String accseeToken) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accseeToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String userInfo =  response.body().string();
            GithubUser githubUser = JSON.parseObject(userInfo, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
