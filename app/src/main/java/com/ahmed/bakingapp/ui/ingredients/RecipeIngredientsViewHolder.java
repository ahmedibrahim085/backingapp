package com.ahmed.bakingapp.ui.ingredients;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;

class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = RecipeIngredientsViewHolder.class.getSimpleName();

    // quantity
    TextView tv_ingredient_quantity;
    TextView tv_ingredient_quantity_value;
    // measure
    TextView tv_ingredient_measure;
    TextView tv_ingredient_measure_value;
    // ingredient
    TextView tv_ingredient_ingredient;
    TextView tv_ingredient_ingredient_value;


    public RecipeIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        // quantity
        tv_ingredient_quantity = itemView.findViewById(R.id.tv_ingredient_quantity);
        tv_ingredient_quantity_value = itemView.findViewById(R.id.tv_ingredient_quantity_value);
        // measure
        tv_ingredient_measure = itemView.findViewById(R.id.tv_ingredient_measure);
        tv_ingredient_measure_value = itemView.findViewById(R.id.tv_ingredient_measure_value);
        // ingredient
        tv_ingredient_ingredient = itemView.findViewById(R.id.tv_ingredient_ingredient);
        tv_ingredient_ingredient_value = itemView.findViewById(R.id.tv_ingredient_ingredient_value);
    }

    void bindData(RecipeIngredients recipeIngredients) {
        // quantity
        tv_ingredient_quantity_value.setText(String.valueOf(recipeIngredients.getIngredientsQuantity()));
        // measure
        tv_ingredient_measure_value.setText(recipeIngredients.getIngredientsMeasure());
        // ingredient
        tv_ingredient_ingredient_value.setText(recipeIngredients.getIngredientsIngredient());
    }
}
