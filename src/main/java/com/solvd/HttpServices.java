package com.solvd;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static com.solvd.Constants.TOKEN;
import static com.solvd.Constants.URL;

public class HttpServices {
    public static HttpPost httpPostUser(User user) {

        HttpPost httpPost = new HttpPost(URL + "/users");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", user.getName()));
        params.add(new BasicNameValuePair("email", user.getEmail()));
        params.add(new BasicNameValuePair("gender", user.getGender()));
        params.add(new BasicNameValuePair("status", user.getStatus()));


        httpPost.setEntity(new UrlEncodedFormEntity(params));
        httpPost.addHeader("Authorization", "Bearer " + TOKEN);
        return httpPost;
    }

    public static HttpGet httpGetUserById(Long id) {
        HttpGet httpGet = new HttpGet(URL + "/users/" + id);
        httpGet.addHeader("Authorization", "Bearer " + TOKEN);
        return httpGet;
    }

    public static HttpDelete httpDeleteUserById(Long id) {
        HttpDelete httpDelete = new HttpDelete(URL + "/users/" + id);
        httpDelete.addHeader("Authorization", "Bearer " + TOKEN);
        return httpDelete;
    }

    public static HttpGet httpGetAllUsers() {
        HttpGet httpGet = new HttpGet(URL + "/users");
        httpGet.addHeader("Authorization", "Bearer " + TOKEN);
        return httpGet;
    }

    public static HttpGet httpGetUsersByGender(String gender) {
        HttpGet httpGet = new HttpGet(URL + "/users?gender=" + gender);
        httpGet.addHeader("Authorization", "Bearer " + TOKEN);
        return httpGet;
    }

    public static HttpGet httpGetUsersByStatus(String status) {
        HttpGet httpGet = new HttpGet(URL + "/users?status=" + status);
        httpGet.addHeader("Authorization", "Bearer " + TOKEN);
        return httpGet;
    }
}
