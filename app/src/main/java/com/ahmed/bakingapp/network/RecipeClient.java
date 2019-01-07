package com.ahmed.bakingapp.network;

import com.ahmed.bakingapp.models.RecipeItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RecipeClient {

    @GET()
    Call<List<RecipeItem>> getRecipes(
            @Url String url
    );
}
