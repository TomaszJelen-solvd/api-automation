package com.solvd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static com.solvd.HttpServices.httpPostUser;

public class UserPostTests extends AbstractTest {

    //T001
    @Test
    public void testCreatingUser() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active"));
    }

    //T009
    @Test
    public void testCreatingMalformedUser() throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpPost = httpPostUser(new MalformedUser("error", "error", "error", "error"));
        validateResponse(httpClient, httpPost, HttpStatus.SC_UNPROCESSABLE_CONTENT, "Wrong response code during malformed user sending");
    }

    //T010
    @Test
    public void testCreatingUserWithoutAuthorization() throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpPost = HttpServices.httpPostUser(new User("Invalid token", "Invalid token", "Invalid token", "Invalid token"), String.valueOf(0));
        validateResponse(httpClient, httpPost, HttpStatus.SC_UNAUTHORIZED, "Wrong response code during user sending with wrong token");
    }

    protected static User sendUser(HttpClient httpClient, User user) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest httpPost = httpPostUser(user);
        String bodyAsString = validateResponse(httpClient, httpPost, HttpStatus.SC_CREATED, "Wrong response code during user sending");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(bodyAsString, User.class);
    }
}