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
import com.ahmed.bakingapp.utils.AppBars;
import com.ahmed.bakingapp.utils.AppToast;

import java.util.List;
import java.util.Objects;

public class RecipeDetailsActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    RecipeItem recipeItem;
    List<RecipeSteps> recipeStepsList;
    List<RecipeIngredients> recipeIngredientsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if (getIntent()!=null) {
            if (getIntent().hasExtra(UiConstants.getRecipeItem())) {
                recipeItem = (RecipeItem) Objects.requireNonNull(getIntent().getExtras()).getSerializable(UiConstants.getRecipeItem());
                recipeStepsList = Objects.requireNonNull(recipeItem).getRecipeItemSteps();
                recipeIngredientsList = recipeItem.getRecipeItemIngredients();
                initFragments();
                initBars();

            }
        }else {
            AppToast.showLong(App.getAppContext(),"Something went wrong\ncouldn't show Recipe Detail");
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initBars(){
        AppBars.setActionBar(this,recipeItem.getRecipeItemName(), true);
    }
    private void initFragments(){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_master_recipe_details_list, RecipeDetailsFragment.newInstance
                        (recipeStepsList,recipeIngredientsList, recipeItem.getRecipeItemName() ))
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

}
