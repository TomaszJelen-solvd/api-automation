package com.solvd;

public class GraphQlTemplates {

    public static final String RETURN_FIELDS = "{id,name,email,gender,status}";

    public static final String JSON_GRAPHQL_TEMPLATE = "{\"query\": \"%s\"}";

    public static final String CREATE_USER_TEMPLATE = "mutation{" +
            "createUser(input:{" +
                "name:\\\"%s\\\"," +
                "email:\\\"%s\\\"," +
                "gender:\\\"%s\\\"," +
                "status:\\\"%s\\\"})" +
            "{user" + RETURN_FIELDS + "}" +
        "}";


    public static final String DELETE_USER = "mutation{" +
            "deleteUser(input:{" +
                "id:%d})" +
            "{user" + RETURN_FIELDS + "}" +
        "}";

    public static final String UPDATE_USER_STATUS = "mutation{" +
            "updateUser(input:{" +
                "id:%d," +
                "status:\\\"%s\\\"})" +
            "{user" + RETURN_FIELDS + "}" +
        "}";

    public static final String READ_USER = "query{" +
        "user(id:%d)" + RETURN_FIELDS + "}";

}
