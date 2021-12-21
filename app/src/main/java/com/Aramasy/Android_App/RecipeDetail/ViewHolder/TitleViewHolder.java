package com.Aramasy.Android_App.RecipeDetail.ViewHolder;

import android.view.View;
import android.widget.TextView;

import Aramasy.learn_11_16.R;
import androidx.recyclerview.widget.RecyclerView;

public class TitleViewHolder extends RecyclerView.ViewHolder{ //RecyclerView.ViewHolder
	private TextView title;
	public TitleViewHolder(View itemView) {
		super(itemView);  //itemView
		title = (TextView)itemView.findViewById(R.id.id_detail_title);
	}
	public TextView getTitle() {
		return title;
	}
}
