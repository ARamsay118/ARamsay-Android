package com.Aramasy.Android_App.RecipeDetail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import Aramasy.learn_11_16.R;
import androidx.recyclerview.widget.RecyclerView;

import com.Aramasy.Android_App.RecipeDetail.Model.DetailedRecipe;
import com.Aramasy.Android_App.RecipeDetail.Model.Ingredient;
import com.Aramasy.Android_App.RecipeDetail.ViewHolder.ImageViewHolder;
import com.Aramasy.Android_App.RecipeDetail.ViewHolder.IngredientsViewHolder;
import com.Aramasy.Android_App.RecipeDetail.ViewHolder.NutritionViewHolder;
import com.Aramasy.Android_App.RecipeDetail.ViewHolder.SummaryViewHolder;
import com.Aramasy.Android_App.RecipeDetail.ViewHolder.Time_ServingViewHolder;
import com.Aramasy.Android_App.RecipeDetail.ViewHolder.TitleViewHolder;
import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "DetailAdapter";
    private final DetailedRecipe datas;
    private final LayoutInflater inflater;
    private AdapterView.OnItemClickListener mClickListener;
    private final Context this_context;

    private static final int view_type_img = 1;
    private static final int view_type_title = 2;
    private static final int view_type_time_serving = 6;
    private static final int view_type_summary = 3;
    private static final int view_type_nutrition = 4;
    private static final int view_type_ingredients= 5;

    public DetailAdapter(Context context, DetailedRecipe datas) {
        this.datas = datas;
        this_context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override       //这里return 各种viewholder是干啥用的？需要自己define class吗？
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case view_type_img:
                return new ImageViewHolder(inflater.inflate(R.layout.rv_detail_image, parent, false));
            case view_type_title:
                return new TitleViewHolder(inflater.inflate(R.layout.rv_detail_title, parent, false));
            case view_type_summary:
                return new SummaryViewHolder(inflater.inflate(R.layout.rv_detail_summary, parent, false));
            case view_type_nutrition:
                return new NutritionViewHolder(inflater.inflate(R.layout.rv_detail_ingredient, parent, false)).setData(datas);
            case view_type_ingredients:
                return new IngredientsViewHolder(inflater.inflate(R.layout.rv_detail_ingredients, parent, false));
            case view_type_time_serving:
                return new Time_ServingViewHolder(inflater.inflate(R.layout.rv_detail_time_serving, parent, false));
            default:
                return null;
        }
    }

    @Override           //@NonNull
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.e(TAG, "set value to type_view:" + position);
        switch(holder.getItemViewType()){
            case view_type_img:
                URL img_url =null;      // set the images for each recipe
                try {
                    img_url= new URL(datas.img_url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Glide.with(this_context)
                        .load(img_url)
                        .into(((ImageViewHolder)holder).getImage());
                break;
            case view_type_title:
                ((TitleViewHolder)holder).getTitle().setText(datas.title); //datas.get(position).title
                break;
            case view_type_summary:
                String raw = (datas.summary).replace("<b>","");
                raw = raw.replace("</b>","");
                raw = raw.replace("<a>","");
                raw = raw.replace("</a>","");
                int first_href = raw.indexOf("<a href",0);
                int period_cur = raw.indexOf(".",0);
                int period_last = 0;
                while(period_cur < first_href){
                    period_last = period_cur;
                    period_cur = raw.indexOf(".",period_last+1);
                }
                if (first_href!=-1 && period_last!=0) {
                    raw = raw.substring(0, period_last + 1);
                }
                //raw = raw.replaceFirst("[.](\\s[A-Za-z,;'\"\\s])*?<a href.*","");
                ((SummaryViewHolder)holder).getSummary().setText(raw);
                break;
            case view_type_nutrition:
                ((NutritionViewHolder)holder).IniGridView();
                break;
            case view_type_ingredients:
                ArrayList<Ingredient> ingred_list = datas.ingredients_list;
                String ingred_concat = "";
                for(int i = 0;i < ingred_list.size(); i ++){
                    ingred_concat = ingred_concat + (ingred_list.get(i)).getName();
                    if (i<(ingred_list.size()-1)){
                        ingred_concat = ingred_concat +", ";
                    }
                }
                ((IngredientsViewHolder)holder).getIngredients().setText(ingred_concat);
                break;
            case view_type_time_serving:
                String time = "\u2022  Ready in ";
                time = time + datas.minutes;
                time = time + " minutes.";
                ((Time_ServingViewHolder)holder).getTime().setText(time);
                String serving = "\u2022  Serves ";
                serving = serving + datas.servings;
                serving = serving + " people.";
                ((Time_ServingViewHolder)holder).getServing().setText(serving);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.e(TAG, "position in getItemViewType:" + position);
        switch(position){
            case 0:
                return view_type_img;
            case 1:
                return view_type_title;
            case 2:
                return view_type_summary;
            case 3:
                return view_type_nutrition;
            case 4:
                return view_type_ingredients;
            case 5:
                return view_type_time_serving;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }  //datas.size()

}
