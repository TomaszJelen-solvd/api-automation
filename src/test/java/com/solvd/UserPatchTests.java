package com.solvd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import static com.solvd.Constants.TOKEN;
import static com.solvd.Constants.URL;
import static com.solvd.UserPostTests.sendUser;

public class UserPatchTests extends AbstractTest {

    //T003
    @Test
    public void testPatchingUserById() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active"));
        Map<String, String> map = new HashMap<>();
        map.put("status", "inactive");
        HttpRequest httpPatch = HttpRequest.newBuilder()
                .uri(new URI(URL + "/users/" + user.getId()))
                .headers("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(map)))
                .build();
        validateResponse(httpClient, httpPatch, HttpStatus.SC_OK, "Wrong response code during user patching");
    }

    //T008
    @Test
    public void testPatchingUserByGender() throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        Map<String, String> map = new HashMap<>();
        map.put("status", "inactive");
        HttpRequest httpPatch = HttpRequest.newBuilder()
                .uri(new URI(URL + "/users?gender=male"))
                .headers("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(map)))
                .build();
        validateResponse(httpClient, httpPatch, HttpStatus.SC_NOT_FOUND, "Wrong response code during patching user by gender");
    }
}
