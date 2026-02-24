package com.example.gitreposmvp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;




/*public class ApiResponse {

    private List<Repository> items;

    public List<Repository> getItems() {
        return items;
    }
}

 */


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {

    @SerializedName("items")
    private List<Repository> items;

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }
}