package com.example.gitreposmvp.data.model;

import com.google.gson.annotations.SerializedName;


public class Owner {

    private String login;
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }
}