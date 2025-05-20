package com.solvd;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.hc.core5.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Map;

import static com.solvd.AbstractTest.logger;
import static com.solvd.AbstractTest.sendAndValidateResponse;
import static com.solvd.GraphQlServices.*;
import static com.solvd.JsonMappingHelper.getJsonBodyAsMap;
import static com.solvd.JsonMappingHelper.getUserFromGraphQlJson;

public class GraphQlHelper {

    protected static String readNonexistantUserByIdAndRetrieveResponseBody(Long id, HttpClient httpClient) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(readUser(id));
        return sendAndValidateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");
    }

    protected static User createUser(HttpClient httpClient, User user) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(GraphQlServices.createUser(user));
        String bodyAsString = sendAndValidateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");
        return extractUserFromResponse(bodyAsString, "createUser");
    }

    protected static User readUserById(HttpClient httpClient, Long id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(readUser(id));
        String bodyAsString = sendAndValidateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");
        return extractUserFromResponse(bodyAsString, "query");
    }

    protected static User updateUserStatus(HttpClient httpClient, Long id, String status) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(GraphQlServices.updateUserStatus(id, status));
        String bodyAsString = sendAndValidateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");

        return extractUserFromResponse(bodyAsString, "updateUser");
    }

    protected static User removeUserById(HttpClient httpClient, Long id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(deleteUser(id));
        String bodyAsString = sendAndValidateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");
        return extractUserFromResponse(bodyAsString, "deleteUser");
    }

    protected static User extractUserFromResponse(String bodyAsString, String action) throws JsonProcessingException {
        Map<String, Object> bodyAsMap = getJsonBodyAsMap(bodyAsString);
        logger.info(bodyAsMap.toString());
        User createdUser;
        if (action.equals("query")) {
            createdUser = getUserFromGraphQlJson(bodyAsMap);
        } else {
            createdUser = getUserFromGraphQlJson(action, bodyAsMap);
        }
        return createdUser;
    }

}
