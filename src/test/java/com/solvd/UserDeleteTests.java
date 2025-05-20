package com.solvd;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.solvd.HttpHelper.deleteUserByIdAndValidateResponse;
import static com.solvd.HttpHelper.sendUserPostRequest;

public class UserDeleteTests extends AbstractTest {

    //T004
    @Test
    public void testDeletingUser() throws IOException, URISyntaxException, InterruptedException {
        User user = sendUserPostRequest(httpClient, getExampleUser());
        deleteUserByIdAndValidateResponse(httpClient, user.getId());
    }

}
