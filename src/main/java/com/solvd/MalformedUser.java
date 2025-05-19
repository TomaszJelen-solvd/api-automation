package com.solvd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MalformedUser extends User {

    public MalformedUser(String name, String email, String gender, String status) {
        super(name, email, gender, status);
    }

    @JsonProperty("malformed_name")
    @Override
    public String getName() {
        return super.getName();
    }

    @JsonProperty("malformed_email")
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @JsonProperty("malformed_gender")
    @Override
    public String getGender() {
        return super.getGender();
    }

    @JsonProperty("malformed_status")
    @Override
    public String getStatus() {
        return super.getStatus();
    }
}
