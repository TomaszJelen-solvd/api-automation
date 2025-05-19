package com.solvd;

import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import static com.solvd.Constants.*;
import static com.solvd.Constants.UPDATE_USER_STATUS;
import static com.solvd.HttpServices.httpJsonPost;

public class GraphQlServices {

    protected  static String createUser(User user) {
        return String.format(CREATE_USER, user.getName(), user.getEmail(), user.getGender(), user.getStatus());
    }

    protected static String readUser(Long id) {
        return String.format(READ_USER, id);
    }

    protected static String deleteUser(Long id) {
        return String.format(DELETE_USER, id);
    }

    protected static String updateUserStatus(Long id, String status) {
        return String.format(UPDATE_USER_STATUS, id, status);
    }

    public static HttpRequest httpGraphQLOperationPost(String operation) throws URISyntaxException {
        String jsonBody = String.format(JSON_GRAPHQL, operation);
        return httpJsonPost(jsonBody, TOKEN, "/graphql");
    }
}
