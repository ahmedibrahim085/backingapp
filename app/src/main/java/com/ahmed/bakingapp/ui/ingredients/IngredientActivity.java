package com.ahmed.bakingapp.ui.ingredients;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.ui.UiConstants;
import com.ahmed.bakingapp.utils.AppBars;
import com.ahmed.bakingapp.utils.AppToast;

import java.util.List;
import java.util.Objects;

public class IngredientActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    List<RecipeIngredients> recipeIngredients;
    String recipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        if (getIntent()!=null) {
            if (getIntent().hasExtra(UiConstants.getRecipeIngredient())) {
                recipeIngredients =
                        (List<RecipeIngredients>) Objects.requireNonNull(getIntent().getExtras())
                                .getSerializable(UiConstants.getRecipeIngredient());

                recipeName = getIntent().getExtras().getString(UiConstants.getRecipeName());
                initFragments();
            }
        }else {
            AppToast.showLong(App.getAppContext(),"Something went wrong\ncouldn't show Recipe Detail");
            finish();
        }
    }

    private void initBars(){
        AppBars.setToolBar(this, recipeName);
        AppBars.setActionBar(this, recipeName,true);
    }

    private void initFragments(){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frameLayout_ingredients, IngredientsFragment.newInstance
                        (recipeIngredients))
                .commit();
    }
}
