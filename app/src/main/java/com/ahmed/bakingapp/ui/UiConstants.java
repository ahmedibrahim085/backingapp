package com.ahmed.bakingapp.ui;

public class UiConstants {
    // Booleans
    private static boolean twoPan;

    // Integers
    private static int currentStepId;
    private static int numberOfSteps;




    // Strings
    final private static String RECIPE_ITEM = "recipeItem";
    final private static String RECIPE_INGREDIENT = "recipeIngredient";
    final private static String RECIPE_NAME = "recipeName";
    final private static String RECIPE_STEPS = "recipeSteps";
    final private static String RECIPE_STEPS_NUMBER = "recipeStepsNumber";



//    private static List<RecipeSteps> selectedRecipeSteps;

    // ======= ======= ======= Strings ======= ======= =======
    public static String getRecipeItem() {
        return RECIPE_ITEM;
    }

    public static String getRecipeIngredient() {
        return RECIPE_INGREDIENT;
    }

    public static String getRecipeName() {
        return RECIPE_NAME;
    }

    public static String getRecipeSteps() {
        return RECIPE_STEPS;
    }

    public static String getRecipeStepsNumber() {
        return RECIPE_STEPS_NUMBER;
    }

    // ======= ======= ======= Integers ======= ======= =======
    public static int getCurrentStepId() {
        return UiConstants.currentStepId;
    }

    public static void setCurrentStepId(int currentStepId) {
        UiConstants.currentStepId = currentStepId;
    }

    public static int getNumberOfSteps() {
        return numberOfSteps;
    }

    public static void setNumberOfSteps(int numberOfSteps) {
        UiConstants.numberOfSteps = numberOfSteps;
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
