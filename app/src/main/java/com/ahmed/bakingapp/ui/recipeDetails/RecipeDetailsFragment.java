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
import com.ahmed.bakingapp.utils.AppToast;
import com.ahmed.bakingapp.utils.DividerItemDecoration;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();
    FragmentManager fragmentManagerIngredients;

    List<RecipeSteps> recipeStepsList;
    List<RecipeIngredients>recipeIngredientsList;
    RecipeDetailsAdapter recipeDetailsAdapter;
    private RecyclerView recyclerView;
    private TextView tv_recipe_ingredients;

    private String recipeName;


    public static RecipeDetailsFragment newInstance(List<RecipeSteps> recipeSteps,
                                                    List<RecipeIngredients> recipeIngredients, String recipeItemName) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipeSteps", (Serializable) recipeSteps);
        args.putSerializable("recipeIngredients", (Serializable) recipeIngredients);
        args.putString(UiConstants.getRecipeName(), recipeItemName);

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeStepsList = (List<RecipeSteps>) getArguments().getSerializable("recipeSteps");
            recipeIngredientsList = (List<RecipeIngredients>) getArguments().getSerializable("recipeIngredients");
            recipeName = getArguments().getString(UiConstants.getRecipeName());
        }
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
        Log.e(TAG, "UiConstants.isTwoPan() - value is: "+UiConstants.isTwoPan());
        tv_recipe_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UiConstants.isTwoPan()) {
                    showRecipeIngredientDetailsActivity(recipeIngredientsList);
                }else {
                    showRecipeIngredientDetailsFragment(recipeIngredientsList);
                }
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

        if (UiConstants.isTwoPan()) {
            showRecipeIngredientDetailsFragment(recipeIngredientsList);
        }
    }

    private void showRecipeIngredientDetailsActivity(List<RecipeIngredients> recipeIngredients) {
        Log.e(TAG,"recipeIngredients : "+recipeIngredients );
            Intent intent = new Intent(getActivity(), IngredientActivity.class);
            intent.putExtra(UiConstants.getRecipeIngredient(), (Serializable) recipeIngredients);
            intent.putExtra(UiConstants.getRecipeName(), recipeName);
            startActivity(intent);
    }

    private void showRecipeStepDetails(RecipeSteps recipeStep) {
        Log.e(TAG,"recipeStep : "+recipeStep );
        AppToast.showShort(getActivity(), recipeStep.getStepsShortDescription());

    }

    private void showRecipeIngredientDetailsFragment(List<RecipeIngredients> recipeIngredients){
        AppToast.showShort(getActivity(), recipeName + " Ingredients");
        fragmentManagerIngredients = getActivity().getSupportFragmentManager();
        fragmentManagerIngredients.beginTransaction()
                .add(R.id.frameLayout_ingredients, IngredientsFragment.newInstance
                        (recipeIngredients))
                .commit();
    }


}
