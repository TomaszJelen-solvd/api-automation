package com.solvd;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.solvd.AbstractTest.assertResponse;
import static com.solvd.HttpServices.httpDeleteUserById;
import static com.solvd.POSTTests.sendUser;

public class DELETETest extends AbstractTest {

    //T004
    @Test
    public void testDeletingUser() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            User user = sendUser(httpClient, new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active"));
            deleteUserById(httpClient, user.getId());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteUserById(CloseableHttpClient httpClient, Long id) throws IOException, ParseException {
        HttpDelete httpDelete = httpDeleteUserById(id);
        assertResponse(httpClient, httpDelete, 204, "Wrong response code during user deletion");
    }
}
