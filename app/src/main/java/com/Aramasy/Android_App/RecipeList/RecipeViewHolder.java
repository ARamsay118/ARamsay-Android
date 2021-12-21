package com.Aramasy.Android_App.RecipeList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Aramasy.learn_11_16.R;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private ImageView image;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.id_list_recipe);
        image = (ImageView)itemView.findViewById(R.id.recipe_pic);
    }

    public TextView getTitle() {
        return title;
    }
    public ImageView getImage() {
        return image;
    }
}