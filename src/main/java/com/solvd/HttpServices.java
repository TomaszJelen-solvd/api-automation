package com.solvd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import static com.solvd.Constants.*;

public class HttpServices {

    public static HttpRequest httpPostUser(User user) throws JsonProcessingException, URISyntaxException {
        return httpPostUser(user, TOKEN);
    }

    public static HttpRequest httpPostUser(User user, String token) throws JsonProcessingException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        String userBody = objectMapper.writeValueAsString(user);
        return httpJsonPost(userBody, token, "/users");
    }

    public static HttpRequest httpJsonPost(String jsonBody, String suffix) throws JsonProcessingException, URISyntaxException {
        return httpJsonPost(jsonBody, TOKEN, suffix);
    }

    public static HttpRequest httpJsonPost(String jsonBody, String token, String suffix) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(URL + suffix))
                .headers("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
    }

    public static HttpRequest httpGetUserById(Long id) throws URISyntaxException {
        return customHttpGet("/users/" + id);
    }

    public static HttpRequest customHttpGet(String suffix) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(URL + suffix))
                .headers("Authorization", "Bearer " + TOKEN)
                .GET()
                .build();
    }

    public static HttpRequest httpDeleteUserById(Long id) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(URL + "/users/" + id))
                .headers("Authorization", "Bearer " + TOKEN)
                .DELETE()
                .build();
    }

    public static HttpRequest httpGetAllUsers() throws URISyntaxException {
        return customHttpGet("/users");
    }

    public static HttpRequest httpGetUsersByGender(String gender) throws URISyntaxException {
        return customHttpGet("/users?gender=" + gender);
    }

    public static HttpRequest httpGetUsersByStatus(String status) throws URISyntaxException {
        return customHttpGet("/users?status=" + status);
    }
}
