package com.Aramasy.Android_App.RecipeList.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recipe implements Serializable {

    @SerializedName("id")
    public int id = 0;

    @SerializedName("title")
    public String title = "";

    @SerializedName("image")
    public String img_url = "";

}
