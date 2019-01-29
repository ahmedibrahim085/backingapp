package com.ahmed.bakingapp.ui.recipeDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.ui.UiConstants;
import com.ahmed.bakingapp.ui.ingredients.IngredientActivity;
import com.ahmed.bakingapp.ui.ingredients.IngredientsFragment;
import com.ahmed.bakingapp.ui.steps.StepsActivity;
import com.ahmed.bakingapp.ui.steps.StepsNavigationFragment;
import com.ahmed.bakingapp.utils.AppToast;
import com.ahmed.bakingapp.utils.DividerItemDecoration;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();
    FragmentManager fragmentManagerIngredients;

    List<RecipeSteps> recipeStepsList;
    List<RecipeIngredients> recipeIngredientsList;
    RecipeDetailsAdapter recipeDetailsAdapter;
    private RecyclerView recyclerView;
    private TextView tv_recipe_ingredients;

    private String recipeName;
    private RecipeSteps recipeStepsInfo;




    public static RecipeDetailsFragment newInstance(List<RecipeSteps> recipeSteps,
                                                    List<RecipeIngredients> recipeIngredients, String recipeItemName) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(UiConstants.getRecipeSteps(), (Serializable) recipeSteps);
        args.putSerializable(UiConstants.getRecipeIngredient(), (Serializable) recipeIngredients);
        args.putString(UiConstants.getRecipeName(), recipeItemName);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState == null ) {
            if ( getArguments() != null ) {
                setRecipeDetailsInfo(getArguments());
            }
        } else {
            setRecipeDetailsInfo(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(UiConstants.getRecipeSteps(), (Serializable) recipeStepsList);
        outState.putSerializable(UiConstants.getRecipeIngredient(), (Serializable) recipeIngredientsList);
        outState.putString(UiConstants.getRecipeName(), recipeName);
    }

    private void setRecipeDetailsInfo(@NonNull Bundle bundle) {
        recipeStepsList = (List<RecipeSteps>) bundle.getSerializable(UiConstants.getRecipeSteps());
        recipeIngredientsList = (List<RecipeIngredients>) bundle.getSerializable(UiConstants.getRecipeIngredient());
        recipeName = bundle.getString(UiConstants.getRecipeName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipedetails_master_list,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_recipe_ingredients = view.findViewById(R.id.tv_recipe_ingredients);
        tv_recipe_ingredients.setText("Ingredients");
        tv_recipe_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecipeIngredientDetails();
            }
        });
        recyclerView = view.findViewById(R.id.list_recipe_details_steps);
        // Add adapter to handle data from data source to view
        recipeDetailsAdapter = new RecipeDetailsAdapter(recipeStepsList);
        // to Open the Recipe Details when click on the item in the recycle view
        recipeDetailsAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                RecipeSteps recipeStep = recipeDetailsAdapter.getRecipeStepDetailsItems(position);
                showRecipeStepDetails(recipeStep);
            }
        });
        recyclerView.setAdapter(recipeDetailsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        // in TwoPane = true this will cause the Ingredients Fragment to show Automatically
        // without the user clicking it.
        if ( UiConstants.isTwoPan() ) {
            showRecipeIngredientDetails();
        }
    }

    // ======= ======= ======= Show Recipe Ingredients ======= START ======= =======
    private void showRecipeIngredientDetails() {
        Log.e(TAG,
                "showRecipeIngredientDetails - \n UiConstants.isTwoPan() - value is: " + UiConstants.isTwoPan());
        if (!UiConstants.isTwoPan() ) {
            showRecipeIngredientDetailsActivity(recipeIngredientsList);
        } else {
            showRecipeIngredientDetailsFragment(recipeIngredientsList);
        }
    }

    private void showRecipeIngredientDetailsActivity(List<RecipeIngredients> recipeIngredients) {
        Log.e(TAG,
                "showRecipeIngredientDetailsActivity - recipeIngredients : " + recipeIngredients);
        Intent intent = new Intent(getActivity(), IngredientActivity.class);
        intent.putExtra(UiConstants.getRecipeIngredient(), (Serializable) recipeIngredients);
        intent.putExtra(UiConstants.getRecipeName(), recipeName);
        startActivity(intent);
    }

    private void showRecipeIngredientDetailsFragment(List<RecipeIngredients> recipeIngredients) {
        AppToast.showShort(getActivity(), recipeName + " Ingredients");
        fragmentManagerIngredients = getActivity().getSupportFragmentManager();
        fragmentManagerIngredients.beginTransaction()
                .replace(R.id.frameLayout_ingredients, IngredientsFragment.newInstance
                        (recipeIngredients))
                .commit();
    }
    // ======= ======= ======= Show Recipe Ingredients ======= END/FIN ======= =======

    // ======= ======= ======= Show Recipe Step ======= START ======= =======
    private void showRecipeStepDetails(RecipeSteps recipeStep) {
        Log.e(TAG, "recipeStep : " + recipeStep);
        AppToast.showShort(getActivity(), recipeStep.getStepsShortDescription());
        // the +1 is because list starts counting from 0
       UiConstants.setCurrentStepId(recipeStep.getStepsId()+1);
        if ( !UiConstants.isTwoPan() ) {
            showRecipeStepActivity(recipeStep);
        } else {
            showRecipeStepFragment(recipeStep);
        }
    }

    private void showRecipeStepActivity(RecipeSteps recipeStep) {
        Intent intent = new Intent(getActivity(), StepsActivity.class);
        intent.putExtra(UiConstants.getRecipeSteps(), recipeStep);
        intent.putExtra(UiConstants.getRecipeStepsNumber(), recipeStepsList.size());
        intent.putExtra(UiConstants.getRecipeName(), recipeName);
        startActivity(intent);
    }

    private void showRecipeStepFragment(RecipeSteps recipeStep) {
        AppToast.showShort(getActivity(),  "recipeStep id: = "+recipeStep.getStepsId());
        fragmentManagerIngredients = getActivity().getSupportFragmentManager();
        fragmentManagerIngredients.beginTransaction()
                .replace(R.id.frameLayout_ingredients, StepsNavigationFragment.newInstance
                        (recipeStepsList.size()))
                .commit();
    }

    // =======


    // ======= ======= ======= Show Recipe Step ======= END/FIN ======= =======
}
