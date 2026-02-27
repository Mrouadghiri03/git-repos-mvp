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
    // data : fournir les données au presenter sans que celui nait besoin de connaitre dou il vient

    //model : les class java et DTOs et les donnes qui circulent dans lapp

    //Api response : les items de retour de retrofit
    @SerializedName("items")//faire le pont entre Json et objets java
    private List<Repository> items;

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }
}