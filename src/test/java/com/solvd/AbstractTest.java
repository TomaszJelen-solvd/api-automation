package com.solvd;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;

public class AbstractTest {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    protected static String assertResponse(CloseableHttpClient httpClient, HttpUriRequestBase httpRequest, int expectedCode, String message) throws IOException, ParseException {
        CloseableHttpResponse response = httpClient.execute(httpRequest);

//        System.out.println(response.getCode());
        logger.info(String.valueOf(response.getCode()));
        Assert.assertEquals(response.getCode(), expectedCode, message);
        if (response.getEntity() == null) {
            return null;
        }
        String bodyAsString = EntityUtils.toString(response.getEntity());
//        System.out.println(bodyAsString);
        logger.info(bodyAsString);
        return bodyAsString;
    }
}
