package com.Aramasy.Android_App.RecipeList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.Aramasy.Android_App.RecipeList.Model.Recipe;
import com.victor.loading.rotate.RotateLoading;

import Aramasy.learn_11_16.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {
    private RecyclerView view_recipe;
    //private RecyclerView.Adapter mAdapter;
    private RecipeListAdapter mMyAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> data = new ArrayList<>();
    private RotateLoading rotateLoading;
    private ImageView loading_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        IniRecycler((List<Recipe>)getIntent().getSerializableExtra("data"));
    }
    private void IniRecycler(List<Recipe> datas){
        view_recipe = (RecyclerView) findViewById(R.id.id_list_search);     // 连接到recyclerView
        mLayoutManager = new LinearLayoutManager(RecipeListActivity.this);
        view_recipe.setLayoutManager(mLayoutManager);       //layout 不知道是不是纵向
        rotateLoading = findViewById(R.id.rotate_loading_list);
        loading_bg = findViewById(R.id.loading_bg_list);
        mMyAdapter = new RecipeListAdapter(RecipeListActivity.this, datas,rotateLoading,loading_bg);
        view_recipe.setAdapter(mMyAdapter);
    }
}
