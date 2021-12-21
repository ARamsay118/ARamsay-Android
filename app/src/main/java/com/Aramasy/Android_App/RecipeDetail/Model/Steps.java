package com.Aramasy.Android_App.RecipeDetail.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Steps implements Serializable {
	@SerializedName("steps")
	public ArrayList<Step> step_list = new ArrayList<Step>();
	public ArrayList<Step> getSteps() {
		return step_list;
	}
}
