package com.Aramasy.Android_App.RecipeDetail.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {
	@SerializedName("name")
	public String name = "";
	public String getName() {
		return name;
	}
}
