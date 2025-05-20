package com.solvd;

import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import static com.solvd.HttpClientService.createUserRequest;
import static com.solvd.HttpHelper.sendUserPostRequest;

public class UserPostTests extends AbstractTest {

    //T001
    @Test
    public void testCreatingUser() throws IOException, URISyntaxException, InterruptedException {
        sendUserPostRequest(httpClient, getExampleUser());
    }

    //T009
    @Test
    public void testCreatingMalformedUser() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest httpPost = createUserRequest(new MalformedUser("error", "error", "error", "error"));
        sendAndValidateResponse(httpClient, httpPost, HttpStatus.SC_UNPROCESSABLE_CONTENT, "Wrong response code during malformed user sending");
    }

    //T010
    @Test
    public void testCreatingUserWithoutAuthorization() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpPost = HttpClientService.createUserRequest(new User("Invalid token", "Invalid token", "Invalid token", "Invalid token"), String.valueOf(0));
        sendAndValidateResponse(httpClient, httpPost, HttpStatus.SC_UNAUTHORIZED, "Wrong response code during user sending with wrong token");
    }


}