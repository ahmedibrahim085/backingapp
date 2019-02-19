package com.ahmed.bakingapp.utils;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ahmed.bakingapp.R;

public class AppBars {

    private static final String TAG = AppBars.class.getSimpleName();

    public static void setAppBars(AppCompatActivity activity, String toolBarTitle,
                                  Boolean actionBarEnabled){
        setActionBar(activity,toolBarTitle,actionBarEnabled);
        setToolBar(activity, toolBarTitle);
    }

    private static void setActionBar(AppCompatActivity activity, String toolBarTitle,
                                     Boolean actionBarEnabled){
        // Show the Up button in the action bar.
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(toolBarTitle);
            actionBar.setDisplayHomeAsUpEnabled(actionBarEnabled);
        }
    }

    private static void setToolBar(AppCompatActivity activity, String toolBarTitle){
        Log.e(TAG, "toolBarTitle is : "+toolBarTitle);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if (toolbar!=null) {
            toolbar.setTitle(toolBarTitle);
            activity.setSupportActionBar(toolbar);
        }
    }
}
