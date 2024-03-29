package com.ahmed.bakingapp.ui.recipeDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeSteps;

class RecipeDetailsViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = RecipeDetailsViewHolder.class.getSimpleName();
    private TextView tv_recipe_steps;


    RecipeDetailsViewHolder(@NonNull View itemView, View.OnClickListener onItemClickListener) {
        super(itemView);
        itemView.setTag(this);
        itemView.setOnClickListener(onItemClickListener);
        tv_recipe_steps = itemView.findViewById(R.id.tv_recipe_steps);
    }

    void bindData(RecipeSteps recipeSteps) {
        tv_recipe_steps.setText(recipeSteps.getStepsShortDescription());
    }
}
