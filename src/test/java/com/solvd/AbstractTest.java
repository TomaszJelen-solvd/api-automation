package com.solvd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AbstractTest {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    protected static String validateResponse(HttpClient httpClient, HttpRequest httpRequest, int expectedCode, String message) throws IOException, InterruptedException {
        HttpResponse response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        logger.info(String.valueOf(response.statusCode()));
        Assert.assertEquals(response.statusCode(), expectedCode, message);
        if (response.body() == null) {
            return null;
        }
        String bodyAsString = response.body().toString();
        logger.info(bodyAsString);
        return bodyAsString;
    }
}
