package com.ahmed.bakingapp.ui;

public class UiConstants {
    // Booleans
    private static boolean twoPan;

    // Strings
    final private static String RECIPE_ITEM = "recipeItem";

//    private static List<RecipeSteps> selectedRecipeSteps;

    // ======= ======= ======= Strings ======= ======= =======
    public static String getRecipeItem() {
        return RECIPE_ITEM;
    }

    // ======= ======= ======= Booleans ======= ======= =======

    public static boolean isTwoPan() {
        return twoPan;
    }

    public static void setTwoPan(boolean twoPan) {
        UiConstants.twoPan = twoPan;
    }

//    public static List<RecipeSteps> getSelectedRecipeSteps() {
//        return selectedRecipeSteps;
//    }
//
//    public static void setSelectedRecipeSteps(List<RecipeSteps> selectedRecipeSteps) {
//        UiConstants.selectedRecipeSteps = selectedRecipeSteps;
//    }
}
