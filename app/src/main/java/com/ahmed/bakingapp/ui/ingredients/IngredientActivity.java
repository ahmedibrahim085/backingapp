package com.ahmed.bakingapp.ui.ingredients;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeIngredients;
import com.ahmed.bakingapp.utils.AppToast;
import com.ahmed.bakingapp.utils.Constants;

import java.util.List;
import java.util.Objects;

public class IngredientActivity extends AppCompatActivity {
    private static final String TAG = IngredientActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    List<RecipeIngredients> recipeIngredients;
    String recipeName = App.getAppContext().getResources().getString(R.string.app_name);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "This is IngredientActivity onCreate");
        setContentView(R.layout.activity_ingredient);
        if (getIntent()!=null) {
            if ( getIntent().hasExtra(Constants.getRecipeIngredient()) ) {
                recipeIngredients =
                        (List<RecipeIngredients>) Objects.requireNonNull(getIntent().getExtras())
                                .getSerializable(Constants.getRecipeIngredient());

                recipeName = getIntent().getExtras().getString(Constants.getRecipeName());
                initIngredientsFragments();
            }
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

}
