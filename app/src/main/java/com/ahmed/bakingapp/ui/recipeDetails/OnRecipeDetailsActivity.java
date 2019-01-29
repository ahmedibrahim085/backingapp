package com.ahmed.bakingapp.ui.recipeDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.models.RecipeItem;
import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.ui.MainActivity;
import com.ahmed.bakingapp.ui.UiConstants;
import com.ahmed.bakingapp.ui.listeners.OnRecipeNavigationClickListener;
import com.ahmed.bakingapp.utils.AppBars;
import com.ahmed.bakingapp.utils.AppToast;

import java.util.List;
import java.util.Objects;

public class OnRecipeDetailsActivity extends AppCompatActivity implements OnRecipeNavigationClickListener {

    FragmentManager fragmentManager;
    RecipeItem recipeItem;
    List<RecipeSteps> recipeStepsList;
    List<RecipeIngredients> recipeIngredientsList;
    private String recipeName;
    private static final String TAG = OnRecipeDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if (getIntent()!=null) {
            if (getIntent().hasExtra(UiConstants.getRecipeItem())) {
                setRecipeDetailsInfo((RecipeItem)
                        Objects.requireNonNull(getIntent().getExtras())
                                .getSerializable(UiConstants.getRecipeItem()));
                AppBars.setAppBars(this,recipeName , true);
                initFragments();
            }
        }else {
            AppToast.showLong(App.getAppContext(),getString(R.string.error_show_recipe));
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(UiConstants.getRecipeItem(), recipeItem);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setRecipeDetailsInfo((RecipeItem)
                Objects.requireNonNull(savedInstanceState
                        .getSerializable(UiConstants.getRecipeItem())));
        initFragments();
    }

    private void setRecipeDetailsInfo(RecipeItem recipeItemDetailedInfo){
        recipeItem = recipeItemDetailedInfo;
        recipeStepsList = Objects.requireNonNull(recipeItem).getRecipeItemSteps();
        recipeIngredientsList = recipeItem.getRecipeItemIngredients();
        recipeName= recipeItem.getRecipeItemName();
    }
    private void initFragments(){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_master_recipe_details_list, RecipeDetailsFragment.newInstance
                        (recipeStepsList,recipeIngredientsList, recipeName))
                .commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if ( UiConstants.getCurrentStepId() >= recipeStepsList.size() ) {
            AppToast.showLong(this,"This is the last Recipe Step");
        }else{
            // Do Fragment Update
        }
    }
}
