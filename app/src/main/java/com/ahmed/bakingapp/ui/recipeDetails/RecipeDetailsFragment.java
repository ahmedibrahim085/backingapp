package com.ahmed.bakingapp.ui.recipeDetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.models.RecipeSteps;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();
    OnRecipeDetailClicked onRecipeDetailClicked;
    List<RecipeSteps> recipeStepsList;
    List<RecipeIngredients>recipeIngredientsList;
    RecipeDetailsAdapter recipeDetailsAdapter;
    private RecyclerView recyclerView;
    private View rootView;
    private TextView tv_recipe_ingredients;


    public static RecipeDetailsFragment newInstance(List<RecipeSteps> recipeSteps,
                                                    List<RecipeIngredients> recipeIngredients) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipeSteps", (Serializable) recipeSteps);
        args.putSerializable("recipeIngredients", (Serializable) recipeIngredients);
        fragment.setArguments(args);
        return fragment;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeStepsList = (List<RecipeSteps>) getArguments().getSerializable("recipeSteps");
            recipeIngredientsList = (List<RecipeIngredients>) getArguments().getSerializable("recipeIngredients");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipedetails_master_list,
                container, false);
        setRootView(view);
        tv_recipe_ingredients = getRootView().findViewById(R.id.tv_recipe_ingredients);
        tv_recipe_ingredients.setText("Ingredients");
        initRecipeDetailsListRecyclerView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onRecipeDetailClicked = (OnRecipeDetailClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnItemClick Listener");
        }
    }

    private void initRecipeDetailsListRecyclerView() {
        recyclerView = getRootView().findViewById(R.id.list_recipe_details_steps);
        assert recyclerView != null;
        // Add layout manager to manage layout
        // Add adapter to handle data from data source to view
        recipeDetailsAdapter = new RecipeDetailsAdapter(recipeStepsList);
        recyclerView.setAdapter(recipeDetailsAdapter);
    }
}
