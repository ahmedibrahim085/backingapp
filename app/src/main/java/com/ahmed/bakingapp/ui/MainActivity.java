package com.ahmed.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.models.RecipeItem;
import com.ahmed.bakingapp.network.Generator;
import com.ahmed.bakingapp.network.RecipeClient;
import com.ahmed.bakingapp.ui.recipeDetails.RecipeDetailsActivity;
import com.ahmed.bakingapp.ui.recipes.RecipesAdapter;
import com.ahmed.bakingapp.utils.AppBars;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailsActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private RecyclerView recyclerView;
    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipes_list);
        // Twopane value is returned programmatically from Android Configuration - See AppClass
        Log.d(TAG, "twoPane - MainActivity - onCreate - is:"+UiConstants.isTwoPan());
        initUI();
        getRecipes();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "THIS IS : "+TAG);
    }

    // ======= ======= ======= Life Cycles  ======= END/FIN ======= =======

    // ======= ======= ======= User Interface  ======= START ======= =======

    private void initUI(){
        AppBars.setAppBars(this,this.getResources().getString(R.string.main_title) , false);
        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.list_recipe);
        assert recyclerView != null;
        // Add layout manager to manage layout
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add adapter to handle data from data source to view
        recipesAdapter = new RecipesAdapter(new ArrayList<RecipeItem>());
        recyclerView.setAdapter(recipesAdapter);
        // to Open the Recipe Details when click on the item in the recycle view
        recipesAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                RecipeItem recipeItem = recipesAdapter.getRecipeItems(position);
                showRecipeDetails(recipeItem);
            }
        });
    }

    // ======= ======= ======= User Interface  ======= END/FIN ======= =======


    // ======= ======= ======= Networking Get Recipe  ======= START ======= =======
    private void getRecipes() {
        RecipeClient recipeClient = Generator.createService(RecipeClient.class);
        Call<List<RecipeItem>> call = recipeClient.getRecipes("baking.json");
        call.enqueue(new Callback<List<RecipeItem>>() {
            // TODO handle NO Internet Connection cases
            @Override
            public void onResponse(@NonNull Call<List<RecipeItem>> call, @NonNull Response<List<RecipeItem>> response) {
                recipesAdapter.updateList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<RecipeItem>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure : " + t.toString());
            }
        });
    }

    // ======= ======= ======= Networking Get Recipe  ======= END/FIN ======= =======

    // ======= ======= ======= Open recipeDetails Activity  ======= START ======= =======
    private void showRecipeDetails(RecipeItem recipeItem){
        Log.d(TAG, "recipeItem clicked is  : "+ recipeItem.getRecipeItemName());
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(UiConstants.getRecipeItem(), recipeItem);
        startActivity(intent);
    }
    // ======= ======= ======= Open recipeDetails Activity  ======= END/FIN ======= =======


    // ======= ======= ======= Local Helper Methods  ======= START ======= =======

    // ======= ======= ======= Local Helper Methods  ======= END/FIN ======= =======

}
