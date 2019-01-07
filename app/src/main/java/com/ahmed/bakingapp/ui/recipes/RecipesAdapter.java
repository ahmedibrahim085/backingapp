package com.ahmed.bakingapp.ui.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeItem;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesViewHolder> {

    private List<RecipeItem> recipeItems;
    private View.OnClickListener onItemClickListener;


    public RecipesAdapter(List<RecipeItem> recipeItems) {
        this.recipeItems = recipeItems;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // view holder is abstract

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .list_recipes_content, parent, false);
        return new RecipesViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder recipesViewHolder, int position) {
        recipesViewHolder.bindData(recipeItems.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeItems.size();
    }

    public RecipeItem getRecipeItems(int position) {
        return recipeItems.get(position);
    }

    public void updateList(List<RecipeItem> recipeItem) {
        if (recipeItem !=null && recipeItem.size()>0) {
            recipeItems.clear();
            recipeItems.addAll(recipeItem);
            notifyDataSetChanged();
        }
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }
}
