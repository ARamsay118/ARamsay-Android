package com.Aramasy.Android_App.NetService.Api;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

	private String name;
	private ArrayList<String> steps;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<String> steps) {
		this.steps = steps;
	}
}
