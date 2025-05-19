package com.solvd;

public class Constants {

    public static final String TOKEN = PropertiesLoader.getProperties().getProperty("token");

    public static final String URL = PropertiesLoader.getProperties().getProperty("url");

    ////////////GraphQL Constants////////////
    public static final String RETURN_FIELDS = "{id,name,email,gender,status}";

    public static final String CREATE_USER = "mutation{createUser(input:{name:\\\"%s\\\",email:\\\"%s\\\",gender:\\\"%s\\\",status:\\\"%s\\\"}){user" + RETURN_FIELDS + "}}";

    public static final String DELETE_USER = "mutation{deleteUser(input:{id:%d}){user" + RETURN_FIELDS + "}}";

    public static final String UPDATE_USER_STATUS = "mutation{updateUser(input:{id:%d,status:\\\"%s\\\"}){user" + RETURN_FIELDS + "}}";

    public static final String READ_USER = "query{user(id:%d)" + RETURN_FIELDS + "}";

    public static final String JSON_GRAPHQL = "{\"query\": \"%s\"}";
}
