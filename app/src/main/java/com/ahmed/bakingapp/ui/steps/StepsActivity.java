package com.ahmed.bakingapp.ui.steps;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.ui.UiConstants;
import com.ahmed.bakingapp.ui.listeners.RecipeNavigationClickListener;
import com.ahmed.bakingapp.utils.AppToast;

public class StepsActivity extends AppCompatActivity implements RecipeNavigationClickListener {

    private static final String TAG = StepsActivity.class.getSimpleName();

    FragmentManager stepsFragmentManager;

    private int numberOfRecipeSteps;
    StepsNavigationFragment stepsNavigationFragment;
    private RecipeSteps recipeStepsInfo;
    private int numberOfSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "StepsActivity - onCreate");
        setContentView(R.layout.activity_steps);
        stepsFragmentManager = getSupportFragmentManager();
        if (getIntent() != null)  {
            recipeStepsInfo = (RecipeSteps) getIntent().getSerializableExtra(UiConstants.getRecipeSteps());
            numberOfSteps = getIntent().getIntExtra(UiConstants.getRecipeStepsNumber(),0);
        }else{
            AppToast.showLong(App.getAppContext(),getString(R.string.error_show_steps));
            finish();
        }

        if (savedInstanceState == null) {
            commitRecipeStepsFragments();
        }
    }


    private void commitRecipeStepsFragments() {
        // commit Steps Navigation Fragment
        stepsFragmentManager.beginTransaction()
                .add(R.id.frameLayout_recipe_steps_navigation,
                        StepsNavigationFragment.newInstance(numberOfSteps))
                .commit();
    }

    private void replaceRecipeStepsFragments() {
        // commit Steps Navigation Fragment
        stepsFragmentManager.beginTransaction()
                .replace(R.id.frameLayout_recipe_steps_navigation,
                        StepsNavigationFragment.newInstance(numberOfSteps))
                .commit();
    }


    @Override
    public void onPreviousRecipeSelected(int position) {
        if ( UiConstants.getCurrentStepId() <= 1 ) {
            AppToast.showLong(this,"This is the First Recipe Step");
        }else{
            // DO something to update Recipe Fragment to the previous one
        }
    }

    @Override
    public void onNextRecipeSelected(int position) {
        if ( UiConstants.getCurrentStepId() >= numberOfRecipeSteps ) {
            AppToast.showLong(this,"This is the last Recipe Step");
        }else{
            // Do Fragment Update
        }
    }

    // =======
}
