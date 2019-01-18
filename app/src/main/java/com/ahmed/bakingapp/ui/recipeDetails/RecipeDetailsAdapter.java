package com.ahmed.bakingapp.ui.recipeDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeSteps;

import java.util.List;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsViewHolder> {

    private List<RecipeSteps> recipeSteps;
    OnRecipeDetailClicked onRecipeDetailClicked;

    public RecipeDetailsAdapter(List<RecipeSteps> recipeStepsList) {
        this.recipeSteps = recipeStepsList;
    }

    @NonNull
    @Override
    public RecipeDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // view holder is abstract

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .list_recipes_details_content, parent, false);
        return new RecipeDetailsViewHolder(view, onRecipeDetailClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsViewHolder recipeDetailsViewHolder, int position) {
        recipeDetailsViewHolder.bindData(recipeSteps.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeSteps == null ? 0 : recipeSteps.size();
    }

    // ========================================================


    public RecipeSteps getRecipeDetailsItems(int position) {
        return recipeSteps.get(position);
    }

    public void updateList(List<RecipeSteps> recipeSteps) {
        if (recipeSteps != null && recipeSteps.size() > 0) {
            recipeSteps.clear();
            recipeSteps.addAll(recipeSteps);
            notifyDataSetChanged();
        }
    }

    public void onRecipeDetailClicked(OnRecipeDetailClicked onClickListener) {
        onRecipeDetailClicked = onClickListener;
    }

    public void onRecipeDetailClicked(View.OnClickListener onClickListener) {
    }
}
