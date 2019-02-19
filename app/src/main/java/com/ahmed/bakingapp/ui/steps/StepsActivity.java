package com.ahmed.bakingapp.ui.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.ui.listeners.OnRecipeNavigationClickListener;
import com.ahmed.bakingapp.utils.AppBars;
import com.ahmed.bakingapp.utils.AppToast;
import com.ahmed.bakingapp.utils.Constants;

import java.util.List;

public class StepsActivity extends AppCompatActivity implements OnRecipeNavigationClickListener {

    private static final String TAG = StepsActivity.class.getSimpleName();

    FragmentManager stepsFragmentManager;

    List<RecipeSteps> recipeStepsList;
    StepsNavigationFragment stepsNavigationFragment;
    private RecipeSteps recipeStepsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "StepsActivity - onCreate");
        setContentView(R.layout.activity_steps);
        stepsFragmentManager = getSupportFragmentManager();
        if (getIntent() != null)  {
            recipeStepsList = (List<RecipeSteps>) getIntent().getSerializableExtra(Constants.getRecipeSteps());
            Constants.setNumberOfSteps(recipeStepsList.size());
            recipeStepsInfo = (RecipeSteps) getIntent().getSerializableExtra(Constants.getRecipeItem());
            setRecipeStepInfo(recipeStepsInfo.getStepsId());
            if ( !Constants.isTwoPan() ) {
                updateAppBar();
            }
        }else{
            AppToast.showLong(App.getAppContext(),getString(R.string.error_show_steps));
            finish();
        }
        if (savedInstanceState == null) {
            commitRecipeNavigationFragments();
        }
    }


    private void commitRecipeNavigationFragments() {
        // commit Steps Navigation Fragment
        if ( stepsNavigationFragment == null ) {
            stepsNavigationFragment =
                    StepsNavigationFragment.newInstance(Constants.getNumberOfSteps());
        }
        if ( !stepsNavigationFragment.isAdded() ) {
            stepsFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout_recipe_steps_navigation,
                            stepsNavigationFragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onPreviousRecipeSelected(int position) {
        if ( position < 0 ) {
            AppToast.showLong(this,"This is the First Recipe Step");
        }else{
            setRecipeStepInfo(position);
            stepsNavigationFragment.updateNavigationFragmentView();
        }
    }

    @Override
    public void onNextRecipeSelected(int position) {
        if ( position > Constants.getNumberOfSteps() ) {
            AppToast.showLong(this,"This is the last Recipe Step");
        }else{
            setRecipeStepInfo(position);
            stepsNavigationFragment.updateNavigationFragmentView();
        }
    }

    // =======

    private void setRecipeStepInfo(int position) {
        recipeStepsInfo = recipeStepsList.get(position);
        Constants.setRecipeSingleStepDescription(recipeStepsInfo.getStepsDescription());
        Constants.setCurrentStepId(recipeStepsInfo.getStepsId());
        if ( !recipeStepsInfo.getStepsVideoURL().isEmpty() ) {
            Constants.setRecipeSingleStepVideo(recipeStepsInfo.getStepsVideoURL());
        }
        if ( !recipeStepsInfo.getStepsThumbnailURL().isEmpty() ) {
            Constants.setRecipeSingleStepVideo(recipeStepsInfo.getStepsThumbnailURL());
        }
        updateAppBar();
    }

    private void updateAppBar() {
        AppBars.setAppBars((AppCompatActivity) this,
                Constants.getRecipeTitle()
                        + " - "
                        + recipeStepsInfo.getStepsShortDescription()
                , true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == android.R.id.home ) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecipeIngredients.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
