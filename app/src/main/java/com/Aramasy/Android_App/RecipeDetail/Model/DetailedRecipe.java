package com.Aramasy.Android_App.RecipeDetail.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

//public class Ingredients implements Serializable {
//
//}
public class DetailedRecipe implements Serializable {
    @SerializedName("id")  //position 0
    public int id = 0;

    @SerializedName("image") //position 1
    public String img_url = "";

    @SerializedName("title")    //position 2
    public String title = "";

    @SerializedName("summary")  //position 3
    public String summary = "";

    @SerializedName("diets")        //position 4
    public ArrayList<String> diets = new ArrayList<>();

    @SerializedName("extendedIngredients")      //position 5 further seperation required
    public ArrayList<Ingredient> ingredients_list = new ArrayList<>();


    @SerializedName("readyInMinutes")       //position 6
    public int minutes = 0;

    @SerializedName("servings")             //position 6
    public int servings = 1;

    @SerializedName("analyzedInstructions")     // saved for hololens
    public ArrayList<Steps> step_list = new ArrayList<Steps>();
}
