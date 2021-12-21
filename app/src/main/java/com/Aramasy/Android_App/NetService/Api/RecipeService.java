package com.Aramasy.Android_App.NetService.Api;


import com.Aramasy.Android_App.NetService.HttpResultFunc;
import com.Aramasy.Android_App.NetService.ServerResultFunc;
import com.Aramasy.Android_App.NetService.ServiceManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RecipeService {
    private static RecipeService instance;
    public static synchronized RecipeService getInstance(){
        if(instance==null)
            instance=new RecipeService();
        return instance;
    }

    private final RecipeApi recipeApi= ServiceManager.getInstance().create(RecipeApi.class);

    public Observable<List<Recipe>> getAllRecipes(int start){
        return recipeApi.getAllRecipes(start)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> postRecipe(String name, ArrayList<String> steps){
        return recipeApi.postRecipe(name, steps)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
}
