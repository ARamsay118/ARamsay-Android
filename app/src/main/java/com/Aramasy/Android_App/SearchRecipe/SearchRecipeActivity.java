package com.Aramasy.Android_App.SearchRecipe;

import Aramasy.learn_11_16.R;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Aramasy.Android_App.RecipeList.Model.Recipe;
import com.Aramasy.Android_App.RecipeList.RecipeListActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.loading.rotate.RotateLoading;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main); //hduweiq
//    }
//}
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import org.json.JSONObject;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main); //hduweiq
//    }
//}
public class SearchRecipeActivity extends AppCompatActivity {         // try get the recipes
    private TextView txt_search;
    private EditText input_ingredient1;
    private EditText input_ingredient2;
    private EditText input_ingredient3;
    private RotateLoading rotateLoading;
    private ImageView loading_bg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input_ingredient1 = findViewById(R.id.ingredient1);
        input_ingredient2 = findViewById(R.id.ingredient2);
        input_ingredient3 = findViewById(R.id.ingredient3);
        rotateLoading = findViewById(R.id.rotate_loading);
        loading_bg = findViewById(R.id.loading_bg);


        // 设置Item之间间隔样式
        //mRecyclerView.addItemDecoration(mDividerItemDecoration);

        txt_search = findViewById(R.id.id_search);     // 连接到activity_main中
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient1 = (input_ingredient1.getText()).toString();
                String ingredient2 = (input_ingredient2.getText()).toString();
                String ingredient3 = (input_ingredient3.getText()).toString();
                if ((ingredient1 != null && ingredient1.length() > 0) || (ingredient2 != null && ingredient2.length() > 0) || (ingredient3 != null && ingredient3.length() > 0)) {

                    get_list_recipe(ingredient1, ingredient2, ingredient3, 10);
                } else {
                    Toast.makeText(SearchRecipeActivity.this, "ingredients should not be empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });
//            txt_main.setText(recipe_txt_sub);
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            rotateLoading.start();
            loading_bg.setVisibility(View.VISIBLE);
            input_ingredient1.setFocusableInTouchMode(false);
            input_ingredient2.setFocusableInTouchMode(false);
            input_ingredient3.setFocusableInTouchMode(false);
            txt_search.setClickable(false);
        } else {
            rotateLoading.stop();
            loading_bg.setVisibility(View.GONE);
            input_ingredient1.setFocusableInTouchMode(true);
            input_ingredient2.setFocusableInTouchMode(true);
            input_ingredient3.setFocusableInTouchMode(true);
            txt_search.setClickable(true);
        }
    }



    private void get_list_recipe(String ingred1, String ingred2, String ingred3, int num_recipe) {
        List<String> recipe_list = new ArrayList<>();
        recipe_list.add("recipe one");
        recipe_list.add("recipe two");
        recipe_list.add("recipe three");

        setLoading(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String URL_base = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=";
//                String apikey1="9e9e2a214b1f4cce8136938f755affb6";
//                String apikey2="8cd419bfe56e41b5b9e1837db2b2c64b";
//                String apikey3="dcc6e872dfe64a52be1b8d51ca25dc73";
//                String apikey4="4d545747389a44d99c6ae79f0cad093f";
                ArrayList<String> apikeys = new ArrayList<>();
                apikeys.add("4d545747389a44d99c6ae79f0cad093f");
                apikeys.add("9e9e2a214b1f4cce8136938f755affb6");
                apikeys.add("8cd419bfe56e41b5b9e1837db2b2c64b");
                apikeys.add("dcc6e872dfe64a52be1b8d51ca25dc73");
                boolean success = false;
                String msg = "Default:Start of get_recipe";
                for(int i = 0;(i < apikeys.size())&&(!success); i++) {
                    String URL_recipe = URL_base + apikeys.get(i) + "&ingredients=" + ingred1 + ",+" + ingred2 + ",+" + ingred3 + "&number=" + (String.valueOf(num_recipe));
                    try {
                        URL url = new URL(URL_recipe);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        //设置请求方式,请求超时信息
                        conn.setRequestMethod("GET");
                        conn.setReadTimeout(50000);
                        conn.setConnectTimeout(50000);
                        //开启连接
                        conn.connect();
                        InputStream inputStream = null;
                        BufferedReader reader = null;
                        //如果应答码为200的时候，表示成功的请求带了，这里的HttpURLConnection.HTTP_OK就是200
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            //获得连接的输入流
                            inputStream = conn.getInputStream();
                            //转换成一个加强型的buffered流
                            reader = new BufferedReader(new InputStreamReader(inputStream));
                            //把读到的内容赋值给result
                            String result = reader.readLine();
                            Log.d("Debug", "Result: " + result);
                            msg = result;
                            if (msg != null){
                                success = true;
                            }
                        } else {
                            msg = "Fail to connect";
                        }
                        //关闭流和连接
                        reader.close();
                        inputStream.close();
                        conn.disconnect();
                    }catch (Exception e) {
                        e.printStackTrace();
                        msg = e.toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setLoading(false);
                            }
                        });
                    }
                }
                String finalMsg = msg;
                //JSONObject finaljson = json_test;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setLoading(false);
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<Collection<Recipe>>(){}.getType();
                        ArrayList<Recipe> data = gson.fromJson(finalMsg, collectionType);
                        Intent intent = new Intent();
                        intent.setClass(SearchRecipeActivity.this, RecipeListActivity.class);
                        intent.putExtra("data", (Serializable)data);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }

}


