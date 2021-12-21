package com.Aramasy.Android_App.RecipeDetail;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Aramasy.Android_App.NetService.Api.RecipeService;
import com.Aramasy.Android_App.RecipeDetail.Model.DetailedRecipe;
import com.Aramasy.Android_App.RecipeDetail.Model.Step;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;

import Aramasy.learn_11_16.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RecipeDetailActivity extends Activity {

    private RecyclerView MyRecycler;
    private DetailAdapter MyAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout sendRecipe;
    private DetailedRecipe data;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);  // 设置各个控件
        sendRecipe = findViewById(R.id.send_recipe);
        data = (DetailedRecipe)getIntent().getSerializableExtra("detailed_recipe");
        IniRecycler(data);

        if (data.step_list == null || data.step_list.size() == 0
        || data.step_list.get(0) == null || data.step_list.get(0).getSteps() == null
        || data.step_list.get(0).getSteps().size() == 0) {
            sendRecipe.setVisibility(View.GONE);
        } else {
            sendRecipe.setVisibility(View.VISIBLE);
            sendRecipe.setOnClickListener(view -> {
                try {
                    sendRecipe(data.title,data.step_list.get(0).step_list);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


    }
    private void IniRecycler(DetailedRecipe data){
        MyRecycler = (RecyclerView) findViewById(R.id.id_detailed_recipe);     // 连接到recyclerView
        mLayoutManager = new LinearLayoutManager(RecipeDetailActivity.this);
        MyRecycler.setLayoutManager(mLayoutManager);
        MyAdapter = new DetailAdapter(RecipeDetailActivity.this, data);
        MyRecycler.setAdapter(MyAdapter);
    }

//    private void sendRecipe(ArrayList<Step> data) {
//        Gson gson = new Gson();
//        String recipeJson = gson.toJson(data);
//        TCPServer.sendRecipe(RecipeDetailActivity.this, recipeJson);
//        Toast.makeText(RecipeDetailActivity.this, "Please open the ARamasy app on Hololens to receive the recipe", Toast.LENGTH_SHORT).show();
//    }

    private void sendRecipe(String recipe_name,ArrayList<Step> data) throws IOException {
        ArrayList<String> steps = new ArrayList<>();
        int max_len = 150 ;
        int period_cur;
        int period_last;
        int period_start;
        String this_step;
        for(Step step : data) {
            this_step = step.getStep();
            period_cur = this_step.indexOf(".",0);
            period_last = 0;
            period_start = 0;
            while(period_cur!=-1 && (period_cur < this_step.length())) {
                while (period_cur != -1 && (period_cur - period_start < max_len)) {
                    period_last = period_cur;
                    period_cur = this_step.indexOf(".", period_cur + 1);
                }
                if (period_cur != -1) {
                    steps.add(this_step.substring(period_start, period_last+ 1));
                    this_step = this_step.substring(period_last + 1, this_step.length());
                    period_start= period_last + 1;
                    period_cur = this_step.indexOf(".",period_last+1);
                }
            }
            if (this_step.length()>0) {
                steps.add(this_step);
            }
        }


        RecipeService.getInstance().postRecipe(recipe_name, steps)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas-> {
                            Toast.makeText(RecipeDetailActivity.this, "Recipe has been sent. Please open your Hololens and start to cook!", Toast.LENGTH_SHORT).show();
                        },
                        throwable -> {
                            Toast.makeText(RecipeDetailActivity.this, "Error! Please check you internet!", Toast.LENGTH_SHORT).show();
                        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void writeRecipeToTxt(ArrayList<Step> data) throws IOException {
        Path file = null;
        this.getDir("folder_recipe", this.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            file = Paths.get(this.getFilesDir().toString()+"/recipe_steps.txt"); //getPath().
        }
        String this_step= "";
        for(int i = 0;i < data.size(); i++) {
            this_step = (data.get(i)).getStep();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.write(file, Collections.singleton(this_step), StandardCharsets.UTF_8,StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            }
        }
        Files.deleteIfExists(file);
    }
}
