package com.ahmed.bakingapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.ahmed.bakingapp.utils.Constants;
import com.ahmed.bakingapp.utils.SharedPrefs;

public  class App extends Application {

    private static Context appContext;
    private static String UNIQUE_APPLICATION_PREFS_ID;

    private static boolean debuggable;
    private static final String TAG = App.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        setAppContext(this.getApplicationContext());
//        setAppDatabase(AppDatabase.getsInstance(getAppContext()));
        setUniqueApplicationPrefsId(getAppContext().getPackageName()+"PREFS");
        initiateAppSharedPrefs(getAppContext());
        if (BuildConfig.DEBUG){
            setDebuggable(true);
        }else {
            setDebuggable(false);
        }
        Constants.setTwoPan(isW900DpSupported());
    }

    private void initiateAppSharedPrefs(Context context){
        SharedPrefs.InitiateAppSharedPrefs(context);
        SharedPrefs.getInstance();
    }

    public static Context getAppContext() {
        return appContext;
    }

    private void setAppContext(Context appContext) {
        App.appContext = appContext;
    }

    public static String getUniqueApplicationPrefsId() {
        return UNIQUE_APPLICATION_PREFS_ID;
    }

    private static void setUniqueApplicationPrefsId(String uniqueApplicationPrefsId) {
        UNIQUE_APPLICATION_PREFS_ID = uniqueApplicationPrefsId;
    }

    public static boolean isDebuggable() {
        return debuggable;
    }

    public static void setDebuggable(boolean debuggable) {
        App.debuggable = debuggable;
    }

    public static boolean isW900DpSupported() {
        Configuration configuration = getAppContext().getResources().getConfiguration();
        return configuration.smallestScreenWidthDp >= 900;
    }

}
