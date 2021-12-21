package com.Aramasy.Android_App.RecipeDetail.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Step implements Serializable {
	@SerializedName("step")
	public String step = "";
	public String getStep() {
		return step;
	}
}
