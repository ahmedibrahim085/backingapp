package com.ahmed.bakingapp.ui.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeItem;

class RecipesViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = RecipesViewHolder.class.getSimpleName();

    private TextView textView;

    RecipesViewHolder(@NonNull View itemView, View.OnClickListener onItemClickListener) {
        super(itemView);
        itemView.setTag(this);
        itemView.setOnClickListener(onItemClickListener);
        textView = itemView.findViewById(R.id.tv_recipes_names);
    }


    void bindData(RecipeItem recipeItem) {
        textView.setText(recipeItem.getRecipeItemName());
    }
}
