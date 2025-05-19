package com.solvd;

import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static com.solvd.HttpServices.*;
import static com.solvd.UserPostTests.sendUser;

public class UserGetTests extends AbstractTest {

    //T002
    @Test
    public void testGetUserById() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active"));
        retrieveUserById(httpClient, user.getId());

    }

    //T005
    @Test
    public void NEWtestReadingAllUsers() throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpGet = httpGetAllUsers();
        validateResponse(httpClient, httpGet, HttpStatus.SC_SUCCESS, "Wrong response code during all users retrieving");

    }

    //T006
    @Test
    public void NEWtestGetUserByGender() throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpGet = httpGetUsersByGender("male");
        validateResponse(httpClient, httpGet, HttpStatus.SC_SUCCESS, "Wrong response code during users retrieving by gender");
    }

    //T007
    @Test
    public void NEWtestGetUserByStatus() throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpGet = httpGetUsersByStatus("active");
        validateResponse(httpClient, httpGet, HttpStatus.SC_SUCCESS, "Wrong response code during users retrieving by status");
    }

    private void retrieveUserById(HttpClient httpClient, Long id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpGet = httpGetUserById(id);
        validateResponse(httpClient, httpGet, HttpStatus.SC_SUCCESS, "Wrong response code during user retrieving");
    }
}
