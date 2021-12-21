package com.Aramasy.Android_App.RecipeList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import Aramasy.learn_11_16.R;
import androidx.recyclerview.widget.RecyclerView;

import com.Aramasy.Android_App.RecipeDetail.Model.DetailedRecipe;
import com.Aramasy.Android_App.RecipeDetail.RecipeDetailActivity;
import com.Aramasy.Android_App.RecipeList.Model.Recipe;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private static final String TAG = "RecipeListAdapter";
    private List<Recipe> datas;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener mClickListener;
    private Context this_context;
    private RotateLoading rotateLoading;
    private ImageView loading_bg;


    public RecipeListAdapter(Context context, List<Recipe> datas,RotateLoading rl,ImageView lb) {
        this.datas = datas;
        this_context=context;
        inflater = LayoutInflater.from(context);
        rotateLoading = rl;
        loading_bg = lb;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "create a new item");
        return new RecipeViewHolder(inflater.inflate(R.layout.rv_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {   //ignore this bug.It's totally fine.
        Log.e(TAG, "set value to item:" + position);
        holder.getTitle().setText(datas.get(position).title);
        URL img_url =null;      // set the images for each recipe
        try {
            img_url= new URL(datas.get(position).img_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Glide.with(this_context)
                .load(img_url)
                .into(holder.getImage());


        holder.getImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 点击事件
                String ID = String.valueOf(datas.get(position).id);
                search_detailed_recipe(ID);
            }
        });
        holder.getTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 点击事件
                String ID = String.valueOf(datas.get(position).id);
                search_detailed_recipe(ID);
            }
        });
    }


    private void setLoading(boolean isLoading) {
        if (isLoading) {
            rotateLoading.start();
            loading_bg.setVisibility(View.VISIBLE);
        } else {
            rotateLoading.stop();
            loading_bg.setVisibility(View.GONE);
        }
    }

    public void search_detailed_recipe(String recipe_id){
        ((RecipeListActivity)this_context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setLoading(true);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                String base = "https://api.spoonacular.com/recipes/";//640352/information?apiKey=9e9e2a214b1f4cce8136938f755affb6";
                ArrayList<String> apikeys = new ArrayList<>();
                apikeys.add("4d545747389a44d99c6ae79f0cad093f");
                apikeys.add("9e9e2a214b1f4cce8136938f755affb6");
                apikeys.add("8cd419bfe56e41b5b9e1837db2b2c64b");
                apikeys.add("dcc6e872dfe64a52be1b8d51ca25dc73");
//                String apikey1="9e9e2a214b1f4cce8136938f755affb6";
//                String apikey2="8cd419bfe56e41b5b9e1837db2b2c64b";
//                String apikey3="dcc6e872dfe64a52be1b8d51ca25dc73";
//                String apikey4="4d545747389a44d99c6ae79f0cad093f";
                boolean success = false;
                String msg = "Default:Start of get_recipe";
                for(int i = 0;(i < apikeys.size())&&(!success); i++)  {
                    String URL_recipe = base + recipe_id + "/information?apiKey=" + apikeys.get(i);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        msg = e.toString();
                        ((RecipeListActivity)this_context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setLoading(false);
                            }
                        });
                    }
                }
                String finalMsg = msg;
                ((RecipeListActivity)this_context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        setLoading(false);
                        try {
                            //Type collectionType = new TypeToken<Collection<DetailedRecipe>>(){}.getType();
                            DetailedRecipe data = gson.fromJson(finalMsg, DetailedRecipe.class);
                            Intent intent = new Intent();
                            intent.setClass(this_context, RecipeDetailActivity.class);
                            intent.putExtra("detailed_recipe", data);
                            this_context.startActivity(intent);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }

                    }
                });
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

}
