/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.utilities;

import com.google.gson.Gson;
import java.io.Serializable;
import minhlb.dtos.User;

/**
 *
 * @author Minh
 */
public class APIWrapper implements Serializable {
//    private static final String APP_ID = "119786970088421";

    private static final String APP_ID = "341241570632859";
//    private static final String APP_SECRET = "43c3d338b5d26e01dd8aa091828b88d1";
    private static final String APP_SECRET = "d6730d5e47ffea64b5242e70a0996c31";
    private static final String REDIRECT_URL = "http://localhost:8084/J3.L.P0015/FacebookController";
    private String accessToken;
    private Gson gson;

    public APIWrapper() {
        gson = new Gson();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public String getAccessToken(String code) throws Exception {
        String accessTokenLink = "https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s";
        accessTokenLink = String.format(accessTokenLink, APP_ID, APP_SECRET, REDIRECT_URL, code);
        String result = NetUtils.getResult(accessTokenLink);
//        String token = result.substring(result.indexOf("=") + 1, result.indexOf("&"));
        String token = result.substring(result.indexOf(":") + 2, result.indexOf(",") - 1);
        return token;
    }

    public User getUserInfo() throws Exception {
        String infoUrl = "https://graph.facebook.com/me?access_token=%s";
        infoUrl = String.format(infoUrl, this.accessToken);
        String result = NetUtils.getResult(infoUrl);
        User user = gson.fromJson(result, User.class);
        return user;
    }
}
