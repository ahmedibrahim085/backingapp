package com.ahmed.bakingapp.ui.ingredients;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;

import java.util.List;

 class IngredientsAdapter extends  RecyclerView.Adapter<RecipeIngredientsViewHolder>  {

     private static final String TAG = IngredientsAdapter.class.getSimpleName();

     private List<RecipeIngredients> ingredients;

     IngredientsAdapter(List<RecipeIngredients> recipeIngredientsList) {
         this.ingredients = recipeIngredientsList;

    }

     @NonNull
     @Override
     public RecipeIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                 .list_ingredients_contents, viewGroup, false);
         return new RecipeIngredientsViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull RecipeIngredientsViewHolder recipeIngredientsViewHolder, int position) {
         recipeIngredientsViewHolder.bindData(ingredients.get(position));
     }

     @Override
     public int getItemCount() {
         return ingredients == null ? 0 : ingredients.size();
     }
 }
