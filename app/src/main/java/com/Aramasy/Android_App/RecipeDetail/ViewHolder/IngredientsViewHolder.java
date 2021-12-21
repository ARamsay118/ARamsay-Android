package com.Aramasy.Android_App.RecipeDetail.ViewHolder;

import android.view.View;
import android.widget.TextView;

import Aramasy.learn_11_16.R;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsViewHolder extends RecyclerView.ViewHolder{ //RecyclerView.ViewHolder
	private TextView ingredients;
	public IngredientsViewHolder(View itemView) {
		super(itemView);  //itemView
		ingredients = (TextView)itemView.findViewById(R.id.id_detail_ingredients);
	}
	public TextView getIngredients() {
		return ingredients;
	}
}