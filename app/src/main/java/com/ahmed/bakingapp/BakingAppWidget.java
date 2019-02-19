package com.ahmed.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.ahmed.bakingapp.models.RecipeSteps;
import com.ahmed.bakingapp.utils.Constants;
import com.ahmed.bakingapp.utils.ObjectConverter;
import com.ahmed.bakingapp.utils.SharedPrefs;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    private static final String TAG = BakingAppWidget.class.getSimpleName();

    private static final String NextRecipeClickTag = "OnNextRecipeClickTag";
    private static final String PreviousRecipeClickTag = "PreviousRecipeClickTag";
    private static final String updateWidget = "updateWidget";
    private static int stepCounter = 0;
    private static List<RecipeSteps> recipeSteps;
    private static RemoteViews views;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.e(TAG, "onUpdate");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.e(TAG, "updateAppWidget");
        if ( views == null ) {
            setWidgetView(context);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static PendingIntent getNextRecipeStepIntent(Context context, String action) {
        Intent intent = new Intent(context, BakingAppWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private static PendingIntent getPreviousRecipeStepIntent(Context context, String action) {
        Intent intent = new Intent(context, BakingAppWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private static PendingIntent getWidgetUpdateStepIntent(Context context, String action) {
        Intent intent = new Intent(context, BakingAppWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive");
        if ( views == null ) {
            setWidgetView(context);
        }
        if ( intent.getAction() != null ) {
            Log.e(TAG, "onReceive - intent : " + intent.getAction());
            if ( intent.getAction().equals(updateWidget) ) {
                if ( recipeSteps == null & (SharedPrefs.getRecipeSteps() != null && !SharedPrefs.getRecipeSteps().isEmpty()) ) {
                    String convertedRecipeSteps = SharedPrefs.getRecipeSteps();
                    recipeSteps = ObjectConverter.stringToObjectS(convertedRecipeSteps);
                }
            }
            switch (intent.getAction()) {
                case NextRecipeClickTag:
                    if ( recipeSteps != null ) {
                        if ( stepCounter < recipeSteps.size() ) {
                            stepCounter++;
                        } else if ( stepCounter >= recipeSteps.size() ) {
                            stepCounter = recipeSteps.size();
                        }
                        setWidgetView(context);
                    } else {
                        stepCounter = 0;
                    }
                    break;
                case PreviousRecipeClickTag:
                    if ( recipeSteps != null ) {
                        if ( stepCounter > 0 ) {
                            stepCounter--;
                        }
                        setWidgetView(context);
                    } else {
                        stepCounter = 0;
                    }
                case updateWidget:
                    stepCounter = 0;
                    setWidgetView(context);
                    break;
            }
            AppWidgetManager.getInstance(context).updateAppWidget(
                    new ComponentName(context, BakingAppWidget.class), views);
        } else {
            Log.e(TAG,
                    "onReceive - intent.getAction() is NULL  ");
        }
    }

    private static void setWidgetView(Context context) {
        String recipeName = SharedPrefs.getRecipeName();
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.backing_widget_steps_navigation);
        // Step Title - Always Visible
        views.setTextViewText(R.id.apw_tv_recipeTitle, recipeName);

        if ( recipeName.equals(Constants.getWidgetRecipeName()) ) {
            // All the widget view components will be hidden for the first Run
            showOrHideWidgetViews(View.GONE);
        } else {
            String convertedRecipeSteps = SharedPrefs.getRecipeSteps();
            recipeSteps = ObjectConverter.stringToObjectS(convertedRecipeSteps);
            showOrHideWidgetViews(View.VISIBLE);
            // Step Instruction
            views.setTextViewText(R.id.apw_tv_oneRecipeStepInstruction,
                    recipeSteps.get(stepCounter).getStepsDescription());
            // Next Recipe
            views.setOnClickPendingIntent(R.id.apw_img_next_recipe, getNextRecipeStepIntent(context, NextRecipeClickTag));
            // Number of steps apw_tv_numberOfSteps
            String stepbyStepCounter =
                    String.format(context.getResources().getString(R.string.recipe_step_counter),
                            stepCounter, recipeSteps.size() - 1);
            views.setTextViewText(R.id.apw_tv_numberOfSteps, String.valueOf(stepbyStepCounter));
            // previous Recipe
            views.setOnClickPendingIntent(R.id.apw_img_previous_recipe, getPreviousRecipeStepIntent(context,
                    PreviousRecipeClickTag));

        }
    }

    private static void showOrHideWidgetViews(int visibility) {
        // Step Instruction
        views.setViewVisibility(R.id.apw_tv_oneRecipeStepInstruction, visibility);
        // Next Recipe
        views.setViewVisibility(R.id.apw_img_next_recipe, visibility);
        // Number of steps apw_tv_numberOfSteps
        views.setViewVisibility(R.id.apw_tv_numberOfSteps, visibility);
        // previous Recipe
        views.setViewVisibility(R.id.apw_img_previous_recipe, visibility);
    }


    public static void updateWidgetInfo(Context context) {
        if ( views == null ) {
            setWidgetView(context);
        }
        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, BakingAppWidget.class), views);
    }
}

