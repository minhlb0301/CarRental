/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.utilities;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Minh
 */
public class ReCaptcha {

    private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
//    private static final String SITE_KEY = "6Ld0kX4aAAAAAPTnou1FJc-jbmrUntYX4ZIr886_";
    private static final String SECRET_KEY = "6Ld0kX4aAAAAADQF_AOCvKNzt0p_Fowee_77yp74";

    public static boolean isValid(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }
        try {
            URL verifyURL = new URL(SITE_VERIFY_URL);
            HttpsURLConnection urlConn = (HttpsURLConnection) verifyURL.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConn.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParam = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;
            urlConn.setDoOutput(true);
            OutputStream os = urlConn.getOutputStream();
            os.write(postParam.getBytes());
            os.flush();
            os.close();
            int responseCode = urlConn.getResponseCode();
            InputStream is = urlConn.getInputStream();
            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            boolean success = jsonObject.getBoolean("success");
            return success;
        } catch (Exception e) {
            return false;
        }
    }

}
