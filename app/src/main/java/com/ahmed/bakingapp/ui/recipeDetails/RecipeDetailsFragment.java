package com.ahmed.bakingapp.ui.recipeDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.ui.UiConstants;
import com.ahmed.bakingapp.ui.ingredients.IngredientActivity;
import com.ahmed.bakingapp.ui.ingredients.IngredientsFragment;
import com.ahmed.bakingapp.ui.steps.StepsNavigationFragment;
import com.ahmed.bakingapp.utils.AppToast;
import com.ahmed.bakingapp.utils.DividerItemDecoration;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    // Fragments
    FragmentManager fragmentManagerRecipeDetails;
    StepsNavigationFragment stepsNavigationFragment;
    IngredientsFragment ingredientsFragment;
    // Lists
    List<RecipeSteps> recipeStepsList;
    List<RecipeIngredients> recipeIngredientsList;
//    ArrayList oneRecipeStepInstructions;
    // Objects
    private RecipeSteps recipeStepsInfo;
    // Layouts
    private FrameLayout frameLayoutIngredients;
    private ConstraintLayout constraintLayout_recipeStepDetails;
    // Layouts - Components
    private TextView tv_recipe_ingredients;
    //Strings
    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();
    private String recipeName;
//    public String recipeStepDescription;
    // Views
    private RecyclerView recyclerView;
    private RecipeDetailsAdapter recipeDetailsAdapter;

    // ======= ======= ======= ======= ======= ======= ======= ======= ======= ======= =======
    // ======= ======= ======= Handle Fragment Life Cycle ======= ======= =======
    // ======= ======= ======= ======= ======= ======= ======= ======= ======= ======= =======

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
        tv_recipe_ingredients.setText(App.getAppContext().getResources().getString(R.string.tv_ingredients));
        tv_recipe_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( frameLayoutIngredients.getVisibility()==View.GONE ){
                    frameLayoutIngredients.setVisibility(View.VISIBLE);
                }
                UiConstants.setCurrentStepId(0);
                showRecipeIngredientDetails();
                stepsNavigationFragment.updateNavigationFragmentView();
            }
        });
        setRecyclerView(view);
        initializeLayouts();
        initializeFragments();

        // in TwoPane = true this will cause the Ingredients Fragment to show Automatically
        // without the user clicking it.
        if ( UiConstants.isTwoPan() ) {
            showRecipeIngredientDetails();
            showRecipeStepsNavigationFragment();
        }
    }

    // ======= ======= ======= SaveInstanceState ======= START ======= =======

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(UiConstants.getRecipeSteps(), (Serializable) recipeStepsList);
        outState.putSerializable(UiConstants.getRecipeIngredient(), (Serializable) recipeIngredientsList);
        outState.putString(UiConstants.getRecipeName(), recipeName);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if ( savedInstanceState !=null ){
            setRecipeDetailsInfo(savedInstanceState);
            initializeLayouts();
            initializeFragments();
        }
    }

    // ======= ======= ======= ======= ======= ======= ======= ======= ======= ======= =======
    // ======= ======= ======= Handle User Experience ======= ======= =======
    // ======= ======= ======= ======= ======= ======= ======= ======= ======= ======= =======

    // ======= ======= ======= SetUp UI ======= START ======= =======

    private void setRecipeDetailsInfo(@NonNull Bundle bundle) {
        recipeStepsList = (List<RecipeSteps>) bundle.getSerializable(UiConstants.getRecipeSteps());
        recipeIngredientsList = (List<RecipeIngredients>) bundle.getSerializable(UiConstants.getRecipeIngredient());
        recipeName = bundle.getString(UiConstants.getRecipeName());
/*
        setAllRecipeStepsDescription();
*/
    }
/*    private void setAllRecipeStepsDescription(){
        if ( oneRecipeStepInstructions == null) {
            oneRecipeStepInstructions = new ArrayList();
        }else{
            oneRecipeStepInstructions.clear();
        }
        for (int i = 0; i< this.recipeStepsList.size(); i++){
            oneRecipeStepInstructions.add(this.recipeStepsList.get(i).getStepsDescription());
        }
    }*/

    private void initializeLayouts() {
        if ( frameLayoutIngredients == null ) {
            frameLayoutIngredients = (FrameLayout) getActivity().findViewById(R.id.frameLayout_ingredients);
        }
        if ( constraintLayout_recipeStepDetails == null ){
            constraintLayout_recipeStepDetails =
                    (ConstraintLayout)getActivity().findViewById(R.id.constraintLayout_steps);
        }
    }
    private void initializeFragments() {
        if ( fragmentManagerRecipeDetails == null ) {
            fragmentManagerRecipeDetails = getActivity().getSupportFragmentManager();
        }
        if ( ingredientsFragment == null ) {
            ingredientsFragment = IngredientsFragment.newInstance(recipeIngredientsList);
        }
        if ( stepsNavigationFragment == null ) {
            stepsNavigationFragment = StepsNavigationFragment.newInstance(recipeStepsList.size());
        }
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.list_recipe_details_steps);
        // Add adapter to handle data from data source to view
        recipeDetailsAdapter = new RecipeDetailsAdapter(recipeStepsList);
        // to Open the Recipe Details when click on the item in the recycle view
        recipeDetailsAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                recipeStepsInfo = recipeDetailsAdapter.getRecipeStepDetailsItems(position);
                UiConstants.setRecipeSingleStepDescription(recipeStepsInfo.getStepsDescription());
                UiConstants.setCurrentStepId(recipeStepsInfo.getStepsId()+1);
                if ( frameLayoutIngredients.getVisibility()==View.VISIBLE ){
                    frameLayoutIngredients.setVisibility(View.GONE);
                }
                showRecipeStepsNavigationFragment();
            }
        });
        recyclerView.setAdapter(recipeDetailsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    }
    // ======= ======= ======= SetUp UI ======= END/FIN ======= =======


    // ======= LeftSide ======= ======= ======= ======= ======= ======= LeftSide ======= =======
    // ======= LeftSide ======= ======= Show Recipe Step ======= START ======= =======
    // ======= LeftSide ======= ======= ======= ======= ======= ======= LeftSide ======= =======

/*    void showRecipeStepDetails(RecipeSteps recipeStep) {
        Log.e(TAG, "recipeStep : " + recipeStep);
        AppToast.showShort(getActivity(), recipeStep.getStepsShortDescription());
        recipeStepDescription = recipeStep.getStepsDescription();
        if ( !UiConstants.isTwoPan() ) {
            showRecipeStepActivity(recipeStep);
        } else {
            showRecipeStepFragment(recipeStep);
        }
    }*/

/*    private void showRecipeStepActivity(RecipeSteps recipeStep) {
        Intent intent = new Intent(getActivity(), StepsActivity.class);
        intent.putExtra(UiConstants.getRecipeSteps(), recipeStep);
        intent.putExtra(UiConstants.getRecipeStepsNumber(), recipeStepsList.size());
        intent.putExtra(UiConstants.getRecipeName(), recipeName);
        startActivity(intent);
    }*/

/*    private void showRecipeStepFragment(RecipeSteps recipeStep) {
        AppToast.showShort(getActivity(), "recipeStep id: = " + recipeStep.getStepsId());
        if ( fragmentManagerRecipeDetails == null ) {
            fragmentManagerRecipeDetails = getActivity().getSupportFragmentManager();
        }
        // todo should be replaced by the correct Recycle steps Fragment
        fragmentManagerRecipeDetails.beginTransaction()
                .replace(R.id.frameLayout_recipe_steps_instruction, StepsNavigationFragment.newInstance
                        (recipeStepsList.size()))
                .commit();
    }*/

    // ======= LeftSide ======= ======= ======= ======= ======= ======= LeftSide ======= =======
    // ======= LeftSide ======= ======= Show Recipe Step ======= END/FIN ======= =======
    // ======= LeftSide ======= ======= ======= ======= ======= ======= LeftSide ======= =======

    // =======  ======= ======= ======= Fragments Controllers ======= Start ======= =======
    void goToPreviousFragments(RecipeSteps recipeStep){
        Log.e(TAG, "Previous Recipe info : "+recipeStep.toString());
        if ( recipeStep.getStepsId() <= 0 ) {
            AppToast.showLong(getActivity(),"This is the First Recipe Step");
                frameLayoutIngredients.setVisibility(View.VISIBLE);
            UiConstants.setCurrentStepId(0);
            showRecipeIngredientDetails();
        }else{
            if ( frameLayoutIngredients.getVisibility() == View.VISIBLE ) {
                frameLayoutIngredients.setVisibility(View.GONE);
            }
            // DO something to update Recipe Fragment to the previous one
            UiConstants.setRecipeSingleStepDescription(recipeStep.getStepsDescription());
            UiConstants.setCurrentStepId(recipeStep.getStepsId());
            replaceRecipeNavigationFragments();
        }


    }

    void goToNextFragments(RecipeSteps recipeStep){
        if ( recipeStep.getStepsId() == recipeStepsList.size() ) {
            AppToast.showLong(getActivity(),"This is the last Recipe Step id= "+recipeStep.getStepsId());
            UiConstants.setCurrentStepId(recipeStep.getStepsId());
        }else{
            Log.e(TAG, "Next Recipe info : "+recipeStep.toString());
            if ( frameLayoutIngredients.getVisibility() == View.VISIBLE ) {
                frameLayoutIngredients.setVisibility(View.GONE);
            }
            UiConstants.setRecipeSingleStepDescription(recipeStep.getStepsDescription());
            replaceRecipeNavigationFragments();
        }
    }
    // =======  ======= ======= ======= Fragments Controllers ======= End/FIN ======= =======

    // ======= RightSide ======= ======= ======= ======= ======= ======= ======= RightSide =======
    // ======= ======= Handle Recipe Instructions ALL Fragments ======= Start ======= =======
    // ======= RightSide ======= ======= ======= ======= ======= ======= ======= RightSide =======

    // ======= ======= ======= 1- Show Recipe Ingredients ======= START ======= =======
    private void showRecipeIngredientDetails() {
        Log.e(TAG,
                "showRecipeIngredientDetails - \n UiConstants.isTwoPan() - value is: " + UiConstants.isTwoPan());
        if ( !UiConstants.isTwoPan() ) {
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
        fragmentManagerRecipeDetails.beginTransaction()
                .replace(frameLayoutIngredients.getId(), ingredientsFragment)
                .commit();
    }

    // ======= ======= ======= 1- Show Recipe Ingredients ======= END/FIN ======= =======




    // ======= ======= ======= 4- Show Recipe Navigation Fragment ======= START ======= =======

    private void showRecipeStepsNavigationFragment() {
        if ( stepsNavigationFragment == null ) {
            stepsNavigationFragment =
                    StepsNavigationFragment.newInstance(UiConstants.getNumberOfSteps());
        }
        if ( !stepsNavigationFragment.isAdded() ) {
            fragmentManagerRecipeDetails.beginTransaction()
                    .add(R.id.frameLayout_recipe_steps_navigation, stepsNavigationFragment)
                    .commit();
        } else {
            stepsNavigationFragment.updateNavigationFragmentView();
            replaceRecipeNavigationFragments();
        }
    }

    private void replaceRecipeNavigationFragments() {
        if ( stepsNavigationFragment == null ) {
            stepsNavigationFragment =
                    StepsNavigationFragment.newInstance(UiConstants.getNumberOfSteps());
        }
        // replace Steps Navigation Fragment
            fragmentManagerRecipeDetails.beginTransaction()
                    .replace(R.id.frameLayout_recipe_steps_navigation, stepsNavigationFragment)
                    .commit();
    }
    // ======= ======= ======= 4- Show Recipe Navigation Fragment ======= END/FIN ======= =======

}
