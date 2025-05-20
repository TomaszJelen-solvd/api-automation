package com.solvd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AbstractTest {

    protected HttpClient httpClient;

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        httpClient = HttpClient.newHttpClient();
    }

    protected static User getExampleUser() {
        return new User("test", "test" + System.currentTimeMillis() + "@test.test", "male", "active");
    }

    protected static String sendAndValidateResponse(HttpClient httpClient, HttpRequest httpRequest, int expectedCode, String message) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        logger.info(String.valueOf(response.statusCode()));
        Assert.assertEquals(response.statusCode(), expectedCode, message);
        if (response.body() == null) {
            return null;
        }
        String bodyAsString = response.body();
        logger.info(bodyAsString);
        return bodyAsString;
    }
}
