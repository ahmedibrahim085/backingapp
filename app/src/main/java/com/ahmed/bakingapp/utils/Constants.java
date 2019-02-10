package com.ahmed.bakingapp.utils;

public class Constants {
    // Booleans
    private static boolean twoPan;

    // Integers
    private static int currentStepId;
    private static int numberOfSteps;

    // Strings - Constants
    final private static String RECIPE_ITEM = "RECIPE_ITEM";
    final private static String RECIPE_INGREDIENT = "RECIPE_INGREDIENT";
    final private static String RECIPE_INGREDIENT_SHOW = "RECIPE_INGREDIENT_SHOW";
    final private static String RECIPE_NAME = "RECIPE_NAME";
    final private static String RECIPE_STEPS = "RECIPE_STEPS";
    final private static String RECIPE_STEPS_NUMBER = "RECIPE_STEPS_NUMBER";
    final private static String RECIPE_VIDEO = "RECIPE_VIDEO";



    // Strings - Variable
    private static  String recipeSingleStepDescription;
    private static String recipeSingleStepVideo = "";
    private static String recipeTitle;

    // XoPlayer
    final private static String PLAYER_WINDOW_INDEX = "PLAYER_WINDOW_INDEX";
    final private static String PLAYER_CURRENT_POSITION = "PLAYER_CURRENT_POSITION";
    final private static String PLAYER_WHEN_READY = "PLAYER_WHEN_READY";

    // ======= ======= ======= Strings ======= Constants ======= =======
    public static String getRecipeItem() {
        return RECIPE_ITEM;
    }

    public static String getRecipeIngredient() {
        return RECIPE_INGREDIENT;
    }

    public static String getRecipeIngredientShow() {
        return RECIPE_INGREDIENT_SHOW;
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

    public static String getRecipeVideo() {
        return RECIPE_VIDEO;
    }

    // ======= ======= ======= Strings ======= Variable ======= =======
    public static String getRecipeSingleStepDescription() {
        return recipeSingleStepDescription;
    }

    public static void setRecipeSingleStepDescription(String recipeSingleStepDescription) {
        Constants.recipeSingleStepDescription = recipeSingleStepDescription;
    }

    public static String getRecipeSingleStepVideo() {
        return recipeSingleStepVideo;
    }

    public static void setRecipeSingleStepVideo(String recipeSingleStepVideo) {
        Constants.recipeSingleStepVideo = recipeSingleStepVideo;
    }

    public static String getRecipeTitle() {
        return recipeTitle;
    }

    public static void setRecipeTitle(String recipeTitle) {
        Constants.recipeTitle = recipeTitle;
    }

    // ======= ======= ======= Integers ======= ======= =======
    public static int getCurrentStepId() {
        return Constants.currentStepId;
    }

    public static void setCurrentStepId(int currentStepId) {
        Constants.currentStepId = currentStepId;
    }

    public static int getNumberOfSteps() {
        return numberOfSteps;
    }

    public static void setNumberOfSteps(int numberOfSteps) {
        Constants.numberOfSteps = numberOfSteps;
    }

    // ======= ======= ======= Booleans ======= ======= =======

    public static boolean isTwoPan() {
        return twoPan;
    }

    public static void setTwoPan(boolean twoPan) {
        Constants.twoPan = twoPan;
    }

    public static String getPlayerWindowIndex() {
        return PLAYER_WINDOW_INDEX;
    }

    public static String getPlayerCurrentPosition() {
        return PLAYER_CURRENT_POSITION;
    }

    public static String getPlayerWhenReady() {
        return PLAYER_WHEN_READY;
    }
}
