package com.ahmed.bakingapp.ui.RecipeDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ahmed.bakingapp.models.RecipeSteps;

public class RecipeDetailsViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = RecipeDetailsViewHolder.class.getSimpleName();
    private TextView textView;

    public RecipeDetailsViewHolder(@NonNull View itemView, OnRecipeDetailClicked onRecipeDetailClicked) {
        super(itemView);
    }

/*    public RecipesViewHolder(@NonNull View itemView, View.OnClickListener onItemClickListener) {
        super(itemView);
        itemView.setTag(this);
        itemView.setOnClickListener(onItemClickListener);
        textView = itemView.findViewById(R.id.tv_recipes_names);
    }*/

    public void bindData(RecipeSteps recipeSteps) {
        //        textView.setText(recipeItem.getRecipeItemName());
    }
}
