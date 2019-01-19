package com.ahmed.bakingapp.ui.recipeDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.utils.AppToast;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();
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
        initiateRecipeIngredient();
        initRecipeDetailsListRecyclerView();
        return view;
    }

    private void initiateRecipeIngredient(){
        tv_recipe_ingredients = getRootView().findViewById(R.id.tv_recipe_ingredients);
        tv_recipe_ingredients.setText("Ingredients");
        tv_recipe_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecipeIngredientDetails(recipeIngredientsList);
                Toast.makeText(getContext(), "Recipe Ingredient clicked :",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showRecipeIngredientDetails(List<RecipeIngredients> recipeIngredients) {
        Log.e(TAG,"recipeIngredients : "+recipeIngredients );
    }

    private void initRecipeDetailsListRecyclerView() {
        recyclerView = getRootView().findViewById(R.id.list_recipe_details_steps);
        assert recyclerView != null;
        // Add layout manager to manage layout
        // Add adapter to handle data from data source to view
        recipeDetailsAdapter = new RecipeDetailsAdapter(recipeStepsList);
        // to Open the Recipe Details when click on the item in the recycle view
        recipeDetailsAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                RecipeSteps recipeStep = recipeDetailsAdapter.getRecipeStepDetailsItems(position);
                AppToast.showLong(getContext(), "you selected Recipe Detail position :"+position);
                showRecipeStepDetails(recipeStep);
            }
        });
        recyclerView.setAdapter(recipeDetailsAdapter);
    }

    private void showRecipeStepDetails(RecipeSteps recipeStep) {
        Log.e(TAG,"recipeStep : "+recipeStep );
    }


}
