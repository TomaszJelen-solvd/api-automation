package com.solvd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Map;

import static com.solvd.JsonMappingHelper.getJsonBody;

public class HttpClientService {
    private static final String TOKEN = PropertiesLoader.getToken();

    private static final String URL = PropertiesLoader.getUrl();

    public static HttpRequest createUserRequest(User user) throws JsonProcessingException, URISyntaxException {
        return createUserRequest(user, TOKEN);
    }

    public static HttpRequest createUserRequest(User user, String token) throws JsonProcessingException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        String userBody = objectMapper.writeValueAsString(user);
        return httpJsonPost(userBody, token, "/users");
    }

    public static HttpRequest httpJsonPost(String jsonBody, String suffix) throws URISyntaxException {
        return httpJsonPost(jsonBody, TOKEN, suffix);
    }

    public static HttpRequest httpJsonPost(String jsonBody, String token, String suffix) throws URISyntaxException {
        return getHttpRequestBuilder()
                .uri(new URI(URL + suffix))
                .headers("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
    }

    private static HttpRequest.Builder getHttpRequestBuilder() {
        return HttpRequest.newBuilder();
    }

    public static HttpRequest httpGetUserById(Long id) throws URISyntaxException {
        return createGetRequest("/users/" + id);
    }

    public static HttpRequest createGetRequest(String suffix) throws URISyntaxException {
        return getHttpRequestBuilder()
                .uri(new URI(URL + suffix))
                .headers("Authorization", "Bearer " + TOKEN)
                .GET()
                .build();
    }

    public static HttpRequest httpDeleteUserById(Long id) throws URISyntaxException {
        return getHttpRequestBuilder()
                .uri(new URI(URL + "/users/" + id))
                .headers("Authorization", "Bearer " + TOKEN)
                .DELETE()
                .build();
    }

    public static HttpRequest httpPatchUser(Map<String, String> map, String suffix) throws URISyntaxException, JsonProcessingException {
        return getHttpRequestBuilder()
                .uri(new URI(URL + suffix))
                .headers("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(getJsonBody(map)))
                .build();
    }

    public static HttpRequest httpGetAllUsers() throws URISyntaxException {
        return createGetRequest("/users");
    }

    public static HttpRequest httpGetUsersByGender(String gender) throws URISyntaxException {
        return createGetRequest("/users?gender=" + gender);
    }

    public static HttpRequest httpGetUsersByStatus(String status) throws URISyntaxException {
        return createGetRequest("/users?status=" + status);
    }
}
