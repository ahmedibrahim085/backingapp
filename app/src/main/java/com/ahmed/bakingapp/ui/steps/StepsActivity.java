package com.ahmed.bakingapp.ui.steps;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.ui.UiConstants;
import com.ahmed.bakingapp.ui.listeners.OnRecipeNavigationClickListener;
import com.ahmed.bakingapp.utils.AppToast;

import java.util.List;

public class StepsActivity extends AppCompatActivity implements OnRecipeNavigationClickListener {

    private static final String TAG = StepsActivity.class.getSimpleName();

    FragmentManager stepsFragmentManager;

    private int numberOfRecipeSteps;
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
            recipeStepsList =  (List<RecipeSteps>) getIntent().getSerializableExtra(UiConstants.getRecipeSteps());
            if ( recipeStepsList.size()<=UiConstants.getCurrentStepId()) {
                UiConstants.setCurrentStepId(recipeStepsList.size()-1);
            }
            recipeStepsInfo = recipeStepsList.get(UiConstants.getCurrentStepId());
            UiConstants.setCurrentStepId(recipeStepsInfo.getStepsId());
            UiConstants.setNumberOfSteps(recipeStepsList.size());
        }else{
            AppToast.showLong(App.getAppContext(),getString(R.string.error_show_steps));
            finish();
        }
     ;
        if (savedInstanceState == null) {
            commitRecipeNavigationFragments();
        }
    }


    private void commitRecipeNavigationFragments() {
        // commit Steps Navigation Fragment
        stepsNavigationFragment =
                StepsNavigationFragment.newInstance(UiConstants.getNumberOfSteps());
        stepsFragmentManager.beginTransaction()
                .add(R.id.frameLayout_recipe_steps_navigation,
                        stepsNavigationFragment).commit();
    }


    public void onPreviousRecipeSelected(int position) {
        if ( position < 1 ) {
            AppToast.showLong(this,"This is the First Recipe Step");
        }else{
            recipeStepsInfo = recipeStepsList.get(position);
            UiConstants.setRecipeSingleStepDescription(recipeStepsInfo.getStepsDescription());
            UiConstants.setCurrentStepId(recipeStepsInfo.getStepsId());
            stepsNavigationFragment.updateNavigationFragmentView();

        }
    }

    @Override
    public void onNextRecipeSelected(int position) {
        if ( position >= UiConstants.getNumberOfSteps() ) {
            AppToast.showLong(this,"This is the last Recipe Step");
        }else{
            recipeStepsInfo = recipeStepsList.get(position);
            UiConstants.setRecipeSingleStepDescription(recipeStepsInfo.getStepsDescription());
            UiConstants.setCurrentStepId(recipeStepsInfo.getStepsId());
            stepsNavigationFragment.updateNavigationFragmentView();
        }
    }

    // =======
}
