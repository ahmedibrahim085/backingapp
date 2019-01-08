package com.ahmed.bakingapp.ui.RecipeDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeItem;
import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.ui.MainActivity;
import com.ahmed.bakingapp.ui.UiConstants;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements OnRecipeDetailClicked{

    FragmentManager fragmentManager;
    RecipeItem recipeItem;
    List<RecipeSteps> recipeSteps;
    RecipeDetailsFragment recipeDetailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if (getIntent()!=null) {
            if (getIntent().hasExtra(UiConstants.getRecipeItem())) {
                recipeItem = (RecipeItem) getIntent().getExtras().getSerializable(UiConstants.getRecipeItem());
                recipeSteps = recipeItem.getRecipeItemSteps();
                UiConstants.setSelectedRecipeSteps(recipeSteps);

                initBars();
                initFragments();
                Toast.makeText(this,"you clicked : "+recipeItem.getRecipeItemName() , Toast
                        .LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(App.getAppContext(),"Something went wrong\ncouldn't show Recipe Detail",
                    Toast
                    .LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initBars(){
        // Toolbar
        this.setTitle(recipeItem.getRecipeItemName());
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initFragments(){
        fragmentManager = getSupportFragmentManager();
        recipeDetailsFragment = new RecipeDetailsFragment();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_master_recipe_details_list, recipeDetailsFragment)
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
    public void onRecipeDetailItemSelected(int position) {

    }

    @Override
    public void onClick(View v) {

    }
}