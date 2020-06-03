package com.cx.utils;

import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.yx.util.S;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpU {

    public static <T> Optional<T> send(String url, Class<T> clazz, Map<String, String> args) {
        HttpPost httpPost = new HttpPost(url);

        List<BasicNameValuePair> parames = new ArrayList<>();

        for (String key : args.keySet()) {
            BasicNameValuePair pair = new BasicNameValuePair(key, args.get(key));
            parames.add(pair);
        }

        String result = "";
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parames, "UTF-8"));
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            return Optional.of(S.json.fromJson(result, clazz));
        } catch (JsonSyntaxException jsonE) {
            return (Optional<T>) Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static void sendAsync(String url, Map<String, String> args) {
        HttpPost httpPost = new HttpPost(url);
        List<BasicNameValuePair> parames = new ArrayList<>();

        for (String key : args.keySet()) {
            BasicNameValuePair pair = new BasicNameValuePair(key, args.get(key));
            parames.add(pair);
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parames, "UTF-8"));
            CloseableHttpClient client = HttpClients.createDefault();
            client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
