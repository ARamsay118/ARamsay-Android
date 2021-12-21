package com.Aramasy.Android_App.RecipeDetail.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import Aramasy.learn_11_16.R;

public class Time_ServingViewHolder extends RecyclerView.ViewHolder{ //RecyclerView.ViewHolder
	private TextView time;
	private TextView serving;
	public Time_ServingViewHolder(View itemView) {
		super(itemView);  //itemView
		time = (TextView)itemView.findViewById(R.id.id_detail_time);
		serving = (TextView)itemView.findViewById(R.id.id_detail_serving);
	}
	public TextView getTime() {
		return time;
	}
	public TextView getServing() {
		return serving;
	}
}
