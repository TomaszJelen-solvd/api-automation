package com.solvd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static Properties properties;

    static {
        loadProperties();
    }

    public static void loadProperties() {
        properties = new Properties();
        try (InputStream inStream = Main.class.getResourceAsStream("/application.properties")) {
            properties.load(inStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application properties");
        }
    }

    public static String getToken() {
        return properties.getProperty("token");
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }
}
