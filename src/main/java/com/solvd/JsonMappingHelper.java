package com.solvd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonMappingHelper {

    protected static String getJsonBody(Map<String, String> map) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(map);
    }

    protected static User getUserFromJson(String bodyAsString) throws JsonProcessingException {
        return new ObjectMapper().readValue(bodyAsString, User.class);
    }

    protected static User getObject(Map<String, Object> bodyAsMap) {
        return new ObjectMapper().convertValue(((Map<String, Object>) bodyAsMap.get("data")).get("user"), User.class);
    }

    protected static Map<String, Object> getJsonBodyAsMap(String bodyAsString) throws JsonProcessingException {
        return new ObjectMapper().readValue(bodyAsString, new TypeReference<>() {
        });
    }


    protected static User getUserFromGraphQlJson(String action, Map<String, Object> bodyAsMap) {
        return new ObjectMapper().convertValue(((Map<String, Object>) ((Map<String, Object>) bodyAsMap.get("data")).get(action)).get("user"), User.class);
    }

    protected static User getUserFromGraphQlJson(Map<String, Object> bodyAsMap) {
        return new ObjectMapper().convertValue(((Map<String, Object>) bodyAsMap.get("data")).get("user"), User.class);
    }
}
