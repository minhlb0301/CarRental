/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.utilities;

import org.apache.http.client.fluent.Request;

/**
 *
 * @author Minh
 */
public class NetUtils {
    public static String getResult(String url) throws Exception{
        return Request.Get(url).setHeader("Accept-Charset", "utf-8").execute().returnContent().asString();
    }
}
