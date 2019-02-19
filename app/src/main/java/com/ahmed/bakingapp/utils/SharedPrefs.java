package com.ahmed.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ahmed.bakingapp.App;
import com.ahmed.bakingapp.models.RecipeSteps;

import java.util.List;

// In this class I am trying to have a unified access across the app for the shared preference to
// avoid having multiple instances from it.
public class SharedPrefs {

    private static SharedPreferences mSharedPreferences;
    private static List<RecipeSteps> recipeStepsList;

    public static void InitiateAppSharedPrefs(Context context) {
        if ( mSharedPreferences == null ) {
            mSharedPreferences = context.getSharedPreferences(context.getPackageName() + "_PREFS",
                    Context.MODE_PRIVATE);
        }
    }


    public static synchronized SharedPreferences getInstance() {
        if ( getsSharedPreferences() == null ) {
            setsSharedPreferences(
                    App.getAppContext().getSharedPreferences(App.getUniqueApplicationPrefsId(),
                            Context.MODE_PRIVATE));
        }
        return getsSharedPreferences();
    }

    private static SharedPreferences getsSharedPreferences() {
        return mSharedPreferences;
    }

    private static void setsSharedPreferences(SharedPreferences mSharedPreferences) {
        SharedPrefs.mSharedPreferences = mSharedPreferences;
    }

    public static String getRecipeSteps() {
        return getsSharedPreferences().getString(Constants.getRecipeSteps(), "");
    }

    public static void setRecipeSteps(String recipeSteps) {
        getsSharedPreferences().edit().putString(Constants.getRecipeSteps(), recipeSteps).apply();
    }

    public static String getRecipeName() {
        return getsSharedPreferences().getString(Constants.getRecipeName(), Constants.getWidgetRecipeName());
    }

    public static void setRecipeName(String recipeName) {
        getsSharedPreferences().edit().putString(Constants.getRecipeName(), recipeName).apply();
    }

}
