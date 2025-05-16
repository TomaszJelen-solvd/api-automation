package com.solvd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.solvd.HttpServices.*;
import static com.solvd.POSTTests.sendUser;

public class GETTests extends AbstractTest {

    //T002
    @Test
    public void testReadingUserById() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active"));
            retrieveUserById(httpClient, user.getId());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //T005
    @Test
    public void testReadingAllUsers() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet =  httpGetAllUsers();
            assertResponse(httpClient, httpGet, 200, "Wrong response code during all users retrieving");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //T006
    @Test
    public void testReadingUsersByGender() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet =  httpGetUsersByGender("male");
            assertResponse(httpClient, httpGet, 200, "Wrong response code during users retrieving by gender");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //T007
    @Test
    public void testReadingUsersByStatus() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet =  httpGetUsersByStatus("active");
            assertResponse(httpClient, httpGet, 200, "Wrong response code during users retrieving by status");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void retrieveUserById(CloseableHttpClient httpClient, Long id) throws IOException, ParseException {
        HttpGet httpGet = httpGetUserById(id);
        assertResponse(httpClient, httpGet, 200, "Wrong response code during user retrieving");
    }

}
