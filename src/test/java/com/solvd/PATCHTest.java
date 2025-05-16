package com.solvd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.solvd.Constants.TOKEN;
import static com.solvd.Constants.URL;
import static com.solvd.HttpServices.httpPostUser;
import static com.solvd.POSTTests.sendUser;

public class PATCHTest extends AbstractTest {

    //T003
    @Test
    public void testPatchingUserById() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active"));
            HttpPatch httpPatch = new HttpPatch(URL + "/users/" + user.getId());

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("status", "inactive"));

            httpPatch.setEntity(new UrlEncodedFormEntity(params));
            httpPatch.addHeader("Authorization", "Bearer " + TOKEN);
            assertResponse(httpClient, httpPatch, 200, "Wrong response code during user patching");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //T008
    @Test
    public void testPatchingUserByGender() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPatch httpPatch = new HttpPatch(URL + "/users?gender=male");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("status", "inactive"));

            httpPatch.setEntity(new UrlEncodedFormEntity(params));
            httpPatch.addHeader("Authorization", "Bearer " + TOKEN);
            assertResponse(httpClient, httpPatch, 404, "Wrong response code during patching user by gender");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
