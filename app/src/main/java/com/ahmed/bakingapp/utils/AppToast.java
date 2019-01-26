package com.ahmed.bakingapp.utils;

import android.content.Context;
import android.widget.Toast;

public class AppToast {

    public static void showLong(Context context, String MessageToShow){
        Toast.makeText(context, MessageToShow, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context context, String MessageToShow){
        Toast.makeText(context, MessageToShow, Toast.LENGTH_SHORT).show();
    }
}
