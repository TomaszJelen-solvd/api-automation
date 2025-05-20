package com.solvd;

import org.apache.hc.core5.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static com.solvd.AbstractTest.sendAndValidateResponse;
import static com.solvd.HttpClientService.*;
import static com.solvd.JsonMappingHelper.getUserFromJson;

public class HttpHelper {

    protected static void deleteUserByIdAndValidateResponse(HttpClient httpClient, Long id) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest httpDelete = httpDeleteUserById(id);
        sendAndValidateResponse(httpClient, httpDelete, HttpStatus.SC_NO_CONTENT, "Wrong response code during user deletion");
    }

    protected static void retrieveUserById(HttpClient httpClient, Long id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpGet = httpGetUserById(id);
        sendAndValidateResponse(httpClient, httpGet, HttpStatus.SC_SUCCESS, "Wrong response code during user retrieving by id");
    }



    protected static User sendUserPostRequest(HttpClient httpClient, User user) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest httpPost = createUserRequest(user);
        String bodyAsString = sendAndValidateResponse(httpClient, httpPost, HttpStatus.SC_CREATED, "Wrong response code during user sending");
        return getUserFromJson(bodyAsString);
    }

}
