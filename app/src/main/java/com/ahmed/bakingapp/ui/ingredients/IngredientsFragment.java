package com.ahmed.bakingapp.ui.ingredients;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.utils.AppBars;
import com.ahmed.bakingapp.utils.Constants;
import com.ahmed.bakingapp.utils.DividerItemDecoration;

import java.io.Serializable;
import java.util.List;

public class IngredientsFragment extends Fragment {

    private static final String TAG = IngredientsFragment.class.getSimpleName();
    List<RecipeIngredients>recipeIngredientsList;
    IngredientsAdapter ingredientsAdapter;
    private RecyclerView recyclerViewIngredient;

    public static IngredientsFragment newInstance(List<RecipeIngredients> recipeIngredients) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipeIngredients", (Serializable) recipeIngredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            recipeIngredientsList = (List<RecipeIngredients>) getArguments().getSerializable("recipeIngredients");
            if ( !Constants.isTwoPan() ) {
                AppBars.setActionBar((AppCompatActivity) getActivity(),
                        Constants.getRecipeTitle() + " - Ingredients", false);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_ingredients_items,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewIngredient = view.findViewById(R.id.recyclerViewIngredientItems);
        // Add adapter to handle data from data source to view
        ingredientsAdapter = new IngredientsAdapter(recipeIngredientsList);
        // to Open the Recipe Details when click on the item in the recycle view
        recyclerViewIngredient.setAdapter(ingredientsAdapter);
        recyclerViewIngredient.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    }
}
