package com.solvd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Map;

import static com.solvd.GraphQlServices.*;

public class GraphQlTests extends AbstractTest {

    //T011
    @Test
    public void testGraphQlCreateUser() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        User user = new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "inactive");
        sendUser(httpClient, user);
    }

    //T012
    @Test
    public void testGraphQlGetUser() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "inactive"));
        retrieveUserById(httpClient, user.getId());
    }

    //T013
    @Test
    public void testGraphQlUpdateUserStatus() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "inactive"));
        updateUserStatus(httpClient, user.getId(), "active");
    }

    //T014
    @Test
    public void testGraphQlDeleteUser() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "inactive"));
        removeUser(httpClient, user.getId());
    }

    //T015
    @Test
    public void testGraphQlSearchNonexistentUser() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "inactive"));
        Long id = user.getId();
        removeUser(httpClient, id);
        HttpRequest httpRequest = httpGraphQLOperationPost(readUser(id));
        String bodyAsString = validateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> bodyAsMap = objectMapper.readValue(bodyAsString, new TypeReference<>() {
        });
        logger.info(bodyAsMap.toString());
        Assert.assertTrue(bodyAsMap.containsKey("errors"));
        Assert.assertNull(objectMapper.convertValue(((Map<String, Object>) bodyAsMap.get("data")).get("user"), User.class));
    }

    private static User sendUser(HttpClient httpClient, User user) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(createUser(user));
        String bodyAsString = validateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");
        User createdUser = getModifiedUser(bodyAsString, "createUser");
        Assert.assertEquals(createdUser, user, "Mismatch in created user");
        return createdUser;
    }

    private void retrieveUserById(HttpClient httpClient, Long id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(readUser(id));
        String bodyAsString = validateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");
        User retrievedUser = getModifiedUser(bodyAsString, "query");
        Assert.assertEquals(retrievedUser.getId(), id, "Mismatch in retrieved user id");
    }

    private void updateUserStatus(HttpClient httpClient, Long id, String status) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(GraphQlServices.updateUserStatus(id, status));
        String bodyAsString = validateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");

        User retrievedUser = getModifiedUser(bodyAsString, "updateUser");
        Assert.assertEquals(retrievedUser.getStatus(), status, "Mismatch in updated user status");
    }

    private void removeUser(HttpClient httpClient, Long id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = httpGraphQLOperationPost(deleteUser(id));
        String bodyAsString = validateResponse(httpClient, httpRequest, HttpStatus.SC_SUCCESS, "Wrong http response code during GraphQl operation sending");
        User retrievedUser = getModifiedUser(bodyAsString, "deleteUser");
        Assert.assertEquals(retrievedUser.getId(), id, "Mismatch in deleted user id");
    }

    private static User getModifiedUser(String bodyAsString, String action) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> bodyAsMap = objectMapper.readValue(bodyAsString, new TypeReference<>() {
        });
        logger.info(bodyAsMap.toString());
        User createdUser;
        if (action.equals("query")) {
            createdUser = objectMapper.convertValue(((Map<String, Object>) bodyAsMap.get("data")).get("user"), User.class);
        } else {
            createdUser = objectMapper.convertValue(((Map<String, Object>) ((Map<String, Object>) bodyAsMap.get("data")).get(action)).get("user"), User.class);
        }
        return createdUser;
    }
}
