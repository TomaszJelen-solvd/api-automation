package com.solvd;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static com.solvd.GraphQlHelper.*;
import static com.solvd.JsonMappingHelper.getJsonBodyAsMap;
import static com.solvd.JsonMappingHelper.getObject;

public class GraphQlTests extends AbstractTest {

    //T011
    @Test
    public void testGraphQlCreateUser() throws IOException, URISyntaxException, InterruptedException {
        User exampleUser = getExampleUser();
        User createdUser = createUser(httpClient, exampleUser);
        Assert.assertEquals(createdUser, exampleUser, "Mismatch in created user");
    }

    //T012
    @Test
    public void testGraphQlGetUser() throws IOException, URISyntaxException, InterruptedException {
        User exampleUser = getExampleUser();
        User createdUser = createUser(httpClient, exampleUser);
        Assert.assertEquals(createdUser, exampleUser, "Mismatch in created user");
        Long id = createdUser.getId();
        User retrievedUser = readUserById(httpClient, id);
        Assert.assertEquals(retrievedUser.getId(), id, "Mismatch in retrieved user id");
    }

    //T013
    @Test
    public void testGraphQlUpdateUserStatus() throws IOException, URISyntaxException, InterruptedException {
        User exampleUser = getExampleUser();
        User createdUser = createUser(httpClient, exampleUser);
        Assert.assertEquals(createdUser, exampleUser, "Mismatch in created user");
        String newStatus = "inactive";
        User retrievedUser = updateUserStatus(httpClient, createdUser.getId(), newStatus);
        Assert.assertEquals(retrievedUser.getStatus(), newStatus, "Mismatch in updated user status");

    }

    //T014
    @Test
    public void testGraphQlDeleteUser() throws IOException, URISyntaxException, InterruptedException {
        User exampleUser = getExampleUser();
        User createdUser = createUser(httpClient, exampleUser);
        Assert.assertEquals(createdUser, exampleUser, "Mismatch in created user");
        Long id = createdUser.getId();
        User retrievedUser = removeUserById(httpClient, id);
        Assert.assertEquals(retrievedUser.getId(), id, "Mismatch in deleted user id");
    }

    //T015
    @Test
    public void testGraphQlSearchNonexistentUser() throws IOException, URISyntaxException, InterruptedException {
        User exampleUser = getExampleUser();
        User createdUser = createUser(httpClient, exampleUser);
        Assert.assertEquals(createdUser, exampleUser, "Mismatch in created user");
        Long id = createdUser.getId();
        User retrievedUser = removeUserById(httpClient, id);
        Assert.assertEquals(retrievedUser.getId(), id, "Mismatch in deleted user id");
        String bodyAsString = readNonexistantUserByIdAndRetrieveResponseBody(id, httpClient);
        Map<String, Object> bodyAsMap = getJsonBodyAsMap(bodyAsString);
        logger.info(bodyAsMap.toString());
        Assert.assertTrue(bodyAsMap.containsKey("errors"));
        Assert.assertNull(getObject(bodyAsMap));
    }




}
