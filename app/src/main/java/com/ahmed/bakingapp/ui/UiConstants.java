package com.ahmed.bakingapp.ui;

class UiConstants {

    // ======= ======= ======= Strings ======= ======= =======
    final private static String RECCIPE_ITEM = "recipeItem";

    static String getReccipeItem() {
        return RECCIPE_ITEM;
    }

    // ======= ======= ======= Booleans ======= ======= =======

    private static boolean mTwoPan;

    public static boolean ismTwoPan() {
        return mTwoPan;
    }

    public static void setmTwoPan(boolean mTwoPan) {
        UiConstants.mTwoPan = mTwoPan;
    }
}
