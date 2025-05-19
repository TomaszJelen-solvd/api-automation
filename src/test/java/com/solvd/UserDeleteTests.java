package com.solvd;

import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static com.solvd.HttpServices.httpDeleteUserById;
import static com.solvd.UserPostTests.sendUser;

public class UserDeleteTests extends AbstractTest {

    //T004
    @Test
    public void testDeletingUser() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active"));
        deleteUserById(httpClient, user.getId());
    }

    private void deleteUserById(HttpClient httpClient, Long id) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest httpDelete = httpDeleteUserById(id);
        validateResponse(httpClient, httpDelete, HttpStatus.SC_NO_CONTENT, "Wrong response code during user deletion");
    }
}
