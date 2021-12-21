package com.Aramasy.Android_App.RecipeDetail.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import Aramasy.learn_11_16.R;
import androidx.recyclerview.widget.RecyclerView;

public class ImageViewHolder extends RecyclerView.ViewHolder{
	private ImageView image;
	public ImageViewHolder(View itemView) {
		super(itemView);  //itemView
		image = (ImageView)itemView.findViewById(R.id.id_detail_img);
	}
	public ImageView getImage() {
		return image;
	}
}
