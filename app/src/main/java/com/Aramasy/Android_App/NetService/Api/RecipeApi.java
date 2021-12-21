package com.Aramasy.Android_App.NetService.Api;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RecipeApi {
    //获取所有菜谱
    @GET("/recipe/all_recipes")
    Observable<Response<List<Recipe>>> getAllRecipes(
            @Query("start") int start
    );

    //上传菜谱
    @POST("recipe/")
    @FormUrlEncoded
    Observable<Response<String>> postRecipe(
            @Field("recipe_name") String name,
            @Field("steps") ArrayList<String> steps
    );

}
