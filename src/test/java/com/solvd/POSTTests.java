package com.solvd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.solvd.Constants.TOKEN;
import static com.solvd.Constants.URL;
import static com.solvd.HttpServices.httpPostUser;

public class POSTTests extends AbstractTest {

    //T001
    @Test
    public void testCreatingUser() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //T009
    @Test
    public void testCreatingMalformedUser() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost httpPost = new HttpPost(URL + "/users");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("na", "error"));
            params.add(new BasicNameValuePair("em", "error"));
            params.add(new BasicNameValuePair("gen", "error"));
            params.add(new BasicNameValuePair("stat", "error"));


            httpPost.setEntity(new UrlEncodedFormEntity(params));
            httpPost.addHeader("Authorization", "Bearer " + TOKEN);
            assertResponse(httpClient, httpPost, 422, "Wrong response code during malformed user sending");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //T010
    @Test
    public void testCreatingUserWithoutAuthorization() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost httpPost = new HttpPost(URL + "/users");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", "Invalid token"));
            params.add(new BasicNameValuePair("email", "Invalid token"));
            params.add(new BasicNameValuePair("gender", "Invalid token"));
            params.add(new BasicNameValuePair("status", "Invalid token"));


            httpPost.setEntity(new UrlEncodedFormEntity(params));
            httpPost.addHeader("Authorization", "Bearer 0");
            assertResponse(httpClient, httpPost, 401, "Wrong response code during user sending with wrong token");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    protected static User sendUser(CloseableHttpClient httpClient, User user) throws IOException, ParseException {
        HttpPost httpPost = httpPostUser(user);
        String bodyAsString = assertResponse(httpClient, httpPost, 201, "Wrong response code during user sending");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString, User.class);
    }


}
