package com.solvd;

import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import static com.solvd.HttpClientService.httpPatchUser;
import static com.solvd.HttpHelper.sendUserPostRequest;

public class UserPatchTests extends AbstractTest {

    //T003
    @Test
    public void testPatchingUserById() throws IOException, URISyntaxException, InterruptedException {
        User user = sendUserPostRequest(httpClient, getExampleUser());
        Map<String, String> map = new HashMap<>();
        map.put("status", "inactive");
        HttpRequest httpPatch = httpPatchUser(map, "/users/" + user.getId());
        sendAndValidateResponse(httpClient, httpPatch, HttpStatus.SC_OK, "Wrong response code during user patching");
    }



    //T008
    @Test
    public void testPatchingUserByGender() throws URISyntaxException, IOException, InterruptedException {
        Map<String, String> map = new HashMap<>();
        map.put("status", "inactive");
        HttpRequest httpPatch = httpPatchUser(map, "/users?gender=male");
        sendAndValidateResponse(httpClient, httpPatch, HttpStatus.SC_NOT_FOUND, "Wrong response code during patching user by gender");
    }


}
