/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.surfcode;

/**
 *
 * @author dennis
 */
import static co.ke.lib.logs.Logging.logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Functions {

    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void main(String[] args){
        Functions obj = new Functions();
        String url = "https://www.truecaller.com/search/ke/723111920";

        try {
            System.out.println("Testing 1 - Send Http GET request");
            obj.sendGet(url);

           // System.out.println("Testing 2 - Send Http POST request");
           // obj.sendPost(url);
        } finally {
            try {
                obj.close();
            } catch (IOException ex) {
                Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void close() throws IOException {
        httpClient.close();
    }

    private String sendGet(String url){

        HttpGet request = new HttpGet(url);

        // add request headers
        request.addHeader("custom-key", "mkyong");
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try ( CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                return result;
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
        
        return null;
    }

    private String sendPost(String url){
        try {            
            HttpPost post = new HttpPost("https://httpbin.org/post");
            
            // add request parameter, form parameters
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("username", "abc"));
            urlParameters.add(new BasicNameValuePair("password", "123"));
            urlParameters.add(new BasicNameValuePair("custom", "secret"));
            
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            
            try ( CloseableHttpClient httpClient = HttpClients.createDefault();
                    CloseableHttpResponse response = httpClient.execute(post)) {
                String result = EntityUtils.toString(response.getEntity());
                System.out.println(result);
                return result;
            }
        }   catch (Exception ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return null;
    }

}
