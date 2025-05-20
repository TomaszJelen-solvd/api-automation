package com.solvd;

import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import static com.solvd.HttpClientService.*;
import static com.solvd.HttpHelper.retrieveUserById;
import static com.solvd.HttpHelper.sendUserPostRequest;

public class UserGetTests extends AbstractTest {

    //T002
    @Test
    public void testGetUserById() throws IOException, URISyntaxException, InterruptedException {
        User user = sendUserPostRequest(httpClient, getExampleUser());
        retrieveUserById(httpClient, user.getId());

    }

    //T005
    @Test
    public void testReadingAllUsers() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpGet = httpGetAllUsers();
        sendAndValidateResponse(httpClient, httpGet, HttpStatus.SC_SUCCESS, "Wrong response code during all users retrieving");

    }

    //T006
    @Test
    public void testGetUserByGender() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpGet = httpGetUsersByGender("male");
        sendAndValidateResponse(httpClient, httpGet, HttpStatus.SC_SUCCESS, "Wrong response code during users retrieving by gender");
    }

    //T007
    @Test
    public void testGetUserByStatus() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpGet = httpGetUsersByStatus("active");
        sendAndValidateResponse(httpClient, httpGet, HttpStatus.SC_SUCCESS, "Wrong response code during users retrieving by status");
    }

}
