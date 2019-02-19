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
import com.ahmed.bakingapp.ui.ingredients.IngredientActivity;
import com.ahmed.bakingapp.ui.ingredients.IngredientsFragment;
import com.ahmed.bakingapp.ui.steps.StepsActivity;
import com.ahmed.bakingapp.ui.steps.StepsNavigationFragment;
import com.ahmed.bakingapp.utils.Constants;
import com.ahmed.bakingapp.utils.DividerItemDecoration;
import com.ahmed.bakingapp.utils.VideoPlayer;

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
    private static RecipeSteps recipeStepsInfo;
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
        args.putSerializable(Constants.getRecipeSteps(), (Serializable) recipeSteps);
        args.putSerializable(Constants.getRecipeIngredient(), (Serializable) recipeIngredients);
        args.putString(Constants.getRecipeName(), recipeItemName);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState != null ) {
            setRecipeDetailsInfo(savedInstanceState);
        } else {
            if ( getArguments() != null ) {
                setRecipeDetailsInfo(getArguments());
            }
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
                if ( Constants.isTwoPan() ) {
                    showIngredientsHideInstructions();
                }
                showRecipeIngredientDetails();
            }
        });
        setRecyclerView(view);
        initializeLayouts();
        initializeFragments();

        // in TwoPane = true this will cause the Ingredients Fragment to show Automatically
        // without the user clicking it.
        if ( Constants.isTwoPan() ) {
            showRecipeIngredientDetails();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if ( Constants.isTwoPan() ) {
            VideoPlayer.releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if ( Constants.isTwoPan() ) {
            VideoPlayer.releasePlayer();
        }
    }
    // ======= ======= ======= SaveInstanceState ======= START ======= =======

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.getRecipeSteps(), (Serializable) recipeStepsList);
        outState.putSerializable(Constants.getRecipeIngredient(), (Serializable) recipeIngredientsList);
        outState.putString(Constants.getRecipeName(), recipeName);
        if ( frameLayoutIngredients != null ) { // solve a crash on not mTwoPane
            if ( frameLayoutIngredients.getVisibility() == View.VISIBLE ) {
                outState.putBoolean(Constants.getRecipeIngredientShow(), true);
            } else {
                outState.putBoolean(Constants.getRecipeIngredientShow(), false);
            }
        }
        if ( VideoPlayer.getmExoPlayer() != null ) {
            // ExoPLayer Window Index
            outState.putInt(Constants.getPlayerWindowIndex(),
                    VideoPlayer.getmExoPlayer().getCurrentWindowIndex());
            // ExoPLayer Current Position
            outState.putLong(Constants.getPlayerCurrentPosition(),
                    VideoPlayer.getmExoPlayer().getCurrentPosition());
            // ExoPLayer boolean Play When Ready
            outState.putBoolean(Constants.getPlayerWhenReady(),
                    VideoPlayer.getmExoPlayer().getPlayWhenReady());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        initializeLayouts();
        initializeFragments();
        if ( savedInstanceState != null ) {
            setRecipeDetailsInfo(savedInstanceState);
            recipeStepsList = (List<RecipeSteps>) savedInstanceState.getSerializable(Constants.getRecipeSteps());
            recipeIngredientsList = (List<RecipeIngredients>) savedInstanceState.getSerializable(Constants.getRecipeIngredient());
            recipeName = savedInstanceState.getString(Constants.getRecipeName());
            if ( !savedInstanceState.getBoolean(Constants.getRecipeIngredientShow()) ) {
                showInstructionsHideIngredients();
            } else {
                showIngredientsHideInstructions();
            }
        }
    }


    private void showIngredientsHideInstructions() {
        VideoPlayer.stopPlayer();
        if ( frameLayoutIngredients != null ) {
            if ( frameLayoutIngredients.getVisibility() == View.GONE ) {
                frameLayoutIngredients.setVisibility(View.VISIBLE);
            }
        }
        if ( constraintLayout_recipeStepDetails != null ) {
            if ( constraintLayout_recipeStepDetails.getVisibility() == View.VISIBLE ) {
                constraintLayout_recipeStepDetails.setVisibility(View.GONE);
            }
        }
    }

    private void showInstructionsHideIngredients() {
        if ( frameLayoutIngredients != null ) {
            if ( frameLayoutIngredients.getVisibility() == View.VISIBLE ) {
                frameLayoutIngredients.setVisibility(View.GONE);
            }
        }
        if ( constraintLayout_recipeStepDetails != null ) {
            if ( constraintLayout_recipeStepDetails.getVisibility() == View.GONE ) {
                constraintLayout_recipeStepDetails.setVisibility(View.VISIBLE);
            }
        }
    }

    private void hideIngredientsListLayout() {
        if ( frameLayoutIngredients != null ) {
            if ( frameLayoutIngredients.getVisibility() == View.VISIBLE ) {
                frameLayoutIngredients.setVisibility(View.GONE);
            }
        }
    }

    // ======= ======= ======= ======= ======= ======= ======= ======= ======= ======= =======
    // ======= ======= ======= Handle User Experience ======= ======= =======
    // ======= ======= ======= ======= ======= ======= ======= ======= ======= ======= =======

    // ======= ======= ======= SetUp UI ======= START ======= =======

    private void setRecipeDetailsInfo(@NonNull Bundle bundle) {
        recipeStepsList = (List<RecipeSteps>) bundle.getSerializable(Constants.getRecipeSteps());
        recipeIngredientsList = (List<RecipeIngredients>) bundle.getSerializable(Constants.getRecipeIngredient());
        recipeName = bundle.getString(Constants.getRecipeName());
    }

    private void initializeLayouts() {
        if ( frameLayoutIngredients == null ) {
            frameLayoutIngredients = (FrameLayout) getActivity().findViewById(R.id.frameLayout_ingredients);
        }
        if ( constraintLayout_recipeStepDetails == null ) {
            constraintLayout_recipeStepDetails =
                    (ConstraintLayout) getActivity().findViewById(R.id.constraintLayout_steps);
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
        if ( recyclerView == null ) {
            recyclerView = view.findViewById(R.id.list_recipe_details_steps);
        }
        // Add adapter to handle data from data source to view
        if ( recipeDetailsAdapter == null ) {
            recipeDetailsAdapter = new RecipeDetailsAdapter(recipeStepsList);
        }
        // to Open the Recipe Details when click on the item in the recycle view
        recipeDetailsAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                setRecipeStepInfo(position);
                if ( Constants.isTwoPan() ) {
                    if ( VideoPlayer.getmExoPlayer() != null ) {
                        VideoPlayer.getmExoPlayer().stop();
                    }
                    showInstructionsHideIngredients();
                    showRecipeStepsNavigationFragment();
                } else {
                    showRecipeStepActivity(recipeStepsList);
                }
            }
        });
        recyclerView.setAdapter(recipeDetailsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    }

    private void setRecipeStepInfo(int position) {
        setRecipeStepsInfo(recipeStepsList.get(position));
        Constants.setRecipeSingleStepDescription(getRecipeStepsInfo().getStepsDescription());
        Constants.setCurrentStepId(getRecipeStepsInfo().getStepsId());
        if ( !recipeStepsInfo.getStepsVideoURL().isEmpty() ) {
            Constants.setRecipeSingleStepVideo(recipeStepsInfo.getStepsVideoURL());
        }
        if ( !recipeStepsInfo.getStepsThumbnailURL().isEmpty() ) {
            Constants.setRecipeSingleStepVideo(recipeStepsInfo.getStepsThumbnailURL());
        }
    }
    // ======= ======= ======= SetUp UI ======= END/FIN ======= =======


    // ======= LeftSide ======= ======= ======= ======= ======= ======= LeftSide ======= =======
    // ======= LeftSide ======= ======= Show Recipe Step ======= START ======= =======
    // ======= LeftSide ======= ======= ======= ======= ======= ======= LeftSide ======= =======

    private void showRecipeStepActivity(List<RecipeSteps> recipeStep) {
        Intent intent = new Intent(getActivity(), StepsActivity.class);
        intent.putExtra(Constants.getRecipeSteps(), (Serializable) recipeStep);
//        intent.putExtra(Constants.getRecipeStepsNumber(), recipeStep.size());
        intent.putExtra(Constants.getRecipeItem(), (Serializable) getRecipeStepsInfo());
        intent.putExtra(Constants.getRecipeName(), recipeName);
        startActivity(intent);
    }

    // ======= LeftSide ======= ======= ======= ======= ======= ======= LeftSide ======= =======
    // ======= LeftSide ======= ======= Show Recipe Step ======= END/FIN ======= =======
    // ======= LeftSide ======= ======= ======= ======= ======= ======= LeftSide ======= =======

    // =======  ======= ======= ======= Fragments Controllers ======= Start ======= =======
    void goToPreviousFragments(RecipeSteps recipeStep) {
        Log.e(TAG, "Previous Recipe info : " + recipeStep.toString());
        if ( Constants.isTwoPan() ) {
            hideIngredientsListLayout();
        }
        // DO something to update Recipe Fragment to the previous one
        setRecipeStepInfo(recipeStep.getStepsId());
        replaceRecipeNavigationFragments();
    }

    void goToNextFragments(RecipeSteps recipeStep) {
        Log.e(TAG, "Next Recipe info : " + recipeStep.toString());
        if ( Constants.isTwoPan() ) {
            hideIngredientsListLayout();
        }
        // DO something to update Recipe Fragment to the previous one
        setRecipeStepInfo(recipeStep.getStepsId());
        replaceRecipeNavigationFragments();
    }
    // =======  ======= ======= ======= Fragments Controllers ======= End/FIN ======= =======

    // ======= RightSide ======= ======= ======= ======= ======= ======= ======= RightSide =======
    // ======= ======= Handle Recipe Instructions ALL Fragments ======= Start ======= =======
    // ======= RightSide ======= ======= ======= ======= ======= ======= ======= RightSide =======

    // ======= ======= ======= 1- Show Recipe Ingredients ======= START ======= =======
    private void showRecipeIngredientDetails() {
        Log.e(TAG,
                "showRecipeIngredientDetails - \n Constants.isTwoPan() - value is: " + Constants.isTwoPan());
        if ( !Constants.isTwoPan() ) {
            showRecipeIngredientDetailsActivity(recipeIngredientsList);
        } else {
            showRecipeIngredientDetailsFragment(recipeIngredientsList);
        }
    }

    private void showRecipeIngredientDetailsActivity(List<RecipeIngredients> recipeIngredients) {
        Log.e(TAG,
                "showRecipeIngredientDetailsActivity - recipeIngredients : " + recipeIngredients);
        Intent intent = new Intent(getActivity(), IngredientActivity.class);
        intent.putExtra(Constants.getRecipeIngredient(), (Serializable) recipeIngredients);
        intent.putExtra(Constants.getRecipeName(), recipeName);
        startActivity(intent);
    }

    private void showRecipeIngredientDetailsFragment(List<RecipeIngredients> recipeIngredients) {
        if ( !ingredientsFragment.isAdded() ) {
            fragmentManagerRecipeDetails.beginTransaction()
                    .replace(frameLayoutIngredients.getId(), ingredientsFragment)
                    .commit();
        }
    }

    // ======= ======= ======= 1- Show Recipe Ingredients ======= END/FIN ======= =======


    // ======= ======= ======= 4- Show Recipe Navigation Fragment ======= START ======= =======

    private void showRecipeStepsNavigationFragment() {
        if ( stepsNavigationFragment == null ) {
            stepsNavigationFragment =
                    StepsNavigationFragment.newInstance(Constants.getNumberOfSteps());
        }
        if ( !stepsNavigationFragment.isAdded() ) {
            replaceRecipeNavigationFragments();
        } else {
            stepsNavigationFragment.updateNavigationFragmentView();
        }
    }

    private void replaceRecipeNavigationFragments() {
        if ( stepsNavigationFragment == null ) {
            stepsNavigationFragment =
                    StepsNavigationFragment.newInstance(Constants.getNumberOfSteps());
        }
        // replace Steps Navigation Fragment
        if ( !stepsNavigationFragment.isAdded() ) {
            fragmentManagerRecipeDetails.beginTransaction()
                    .replace(R.id.frameLayout_recipe_steps_navigation, stepsNavigationFragment)
                    .commit();
        }
    }
    // ======= ======= ======= 4- Show Recipe Navigation Fragment ======= END/FIN ======= =======


    public static RecipeSteps getRecipeStepsInfo() {
        return RecipeDetailsFragment.recipeStepsInfo;
    }

    private static void setRecipeStepsInfo(RecipeSteps recipeStepsInfo) {
        RecipeDetailsFragment.recipeStepsInfo = recipeStepsInfo;
    }
}
