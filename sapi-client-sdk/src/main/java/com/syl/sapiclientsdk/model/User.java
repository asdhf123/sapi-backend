package com.syl.sapiclientsdk.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String username;
    public User(){

    }

    public User(String username) {
        this.username = username;
    }
}
