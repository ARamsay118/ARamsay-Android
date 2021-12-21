package com.Aramasy.Android_App.RecipeDetail.ViewHolder;

import android.view.View;
import android.widget.TextView;

import Aramasy.learn_11_16.R;
import androidx.recyclerview.widget.RecyclerView;

public class SummaryViewHolder extends RecyclerView.ViewHolder{ //RecyclerView.ViewHolder
	private TextView summary;
	public SummaryViewHolder(View itemView) {
		super(itemView);  //itemView
		summary = (TextView)itemView.findViewById(R.id.id_detail_summary);
	}
	public TextView getSummary() {
		return summary;
	}
}