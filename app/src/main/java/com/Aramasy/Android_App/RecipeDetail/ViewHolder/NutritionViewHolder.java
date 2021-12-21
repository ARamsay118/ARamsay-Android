package com.Aramasy.Android_App.RecipeDetail.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.Aramasy.Android_App.RecipeDetail.Model.DetailedRecipe;

import java.util.ArrayList;

import Aramasy.learn_11_16.R;
import androidx.recyclerview.widget.RecyclerView;

public class NutritionViewHolder extends RecyclerView.ViewHolder{ //RecyclerView.ViewHolder
	private GridView nutritionGridView;
	private NutritionGridAdapter nutritionGridAdapter;
	private DetailedRecipe datas;

	public NutritionViewHolder(View itemView) {
		super(itemView);  //itemView
		nutritionGridView = itemView.findViewById(R.id.id_detail_GridView);
	}

	public NutritionViewHolder setData(DetailedRecipe datas) {
		this.datas = datas;
		return this;
	}

	public void IniGridView() {
		nutritionGridAdapter = new NutritionGridAdapter(datas.diets, nutritionGridView.getContext());
		nutritionGridView.setAdapter(nutritionGridAdapter);
	}

	class NutritionGridAdapter extends BaseAdapter {

		private final ArrayList<String> diets;
		private final Context context;
		public NutritionGridAdapter(ArrayList<String> diets, Context context) {
			this.diets = diets;
			if (diets.size()==0){
				diets.add("None");
			}
			nutritionGridView.setVerticalSpacing(5);
			ViewGroup.LayoutParams params = nutritionGridView.getLayoutParams();
			params.height = ((diets.size()+1)/2)*140+90;
			nutritionGridView.setLayoutParams(params);

			this.context = context;
		}

		@Override
		public int getCount() {
			return diets.size();
		}

		@Override
		public Object getItem(int position) {
			return diets.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			NutritionItemViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.rv_detail_words_like_tag, null);
				holder = new NutritionItemViewHolder();

				holder.nutrition = convertView
						.findViewById(R.id.id_detail_words_like_tag);
				// 打标签
				convertView.setTag(holder);

			} else {
				// 从标签中获取数据
				holder = (NutritionItemViewHolder) convertView.getTag();
			}
			holder.nutrition.setText(diets.get(position));

			return convertView;
		}
	}

	class NutritionItemViewHolder {
		TextView nutrition;
	}



}