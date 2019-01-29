package com.ahmed.bakingapp.ui.ingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.ui.UiConstants;
import com.ahmed.bakingapp.ui.recipeDetails.RecipeDetailsActivity;
import com.ahmed.bakingapp.utils.AppBars;
import com.ahmed.bakingapp.utils.AppToast;

import java.util.List;
import java.util.Objects;

public class IngredientActivity extends AppCompatActivity {
    private static final String TAG = IngredientActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    List<RecipeIngredients> recipeIngredients;
    String recipeName = App.getAppContext().getResources().getString(R.string.app_name);
    String ingredientsTitle = " - ingredients";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "This is IngredientActivity onCreate");
        setContentView(R.layout.activity_ingredient);
        if (getIntent()!=null) {
            if (getIntent().hasExtra(UiConstants.getRecipeIngredient())) {
                recipeIngredients =
                        (List<RecipeIngredients>) Objects.requireNonNull(getIntent().getExtras())
                                .getSerializable(UiConstants.getRecipeIngredient());

                recipeName = getIntent().getExtras().getString(UiConstants.getRecipeName());
                initIngredientsFragments();

            }
            AppBars.setAppBars(this,recipeName+ingredientsTitle , true);
        }else {
            AppToast.showLong(App.getAppContext(),"Something went wrong\ncouldn't show Recipe Detail");
            finish();
        }
    }

    private void initIngredientsFragments(){
        Log.e(TAG, "initIngredientsFragments ");
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frameLayout_ingredients, IngredientsFragment.newInstance
                        (recipeIngredients))
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
            navigateUpTo(new Intent(this, RecipeDetailsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
