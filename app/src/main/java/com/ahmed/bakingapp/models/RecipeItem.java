package com.ahmed.bakingapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RecipeItem implements Serializable {

    private static final String TAG = RecipeItem.class.getSimpleName();
    // Recipe JSON Constants
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";

    // Recipe Main Values
    @SerializedName(RECIPE_ID)
    private Integer recipeItemId;
    @SerializedName(RECIPE_NAME)
    private String recipeItemName;
    @SerializedName(RECIPE_INGREDIENTS)
    private List<RecipeIngredients> recipeItemIngredients;
    @SerializedName(RECIPE_STEPS)
    private List<RecipeSteps> recipeItemSteps;
    @SerializedName(RECIPE_SERVINGS)
    private Integer recipeItemServings;
    @SerializedName(RECIPE_IMAGE)
    private String recipeItemImage;

    public Integer getRecipeItemId() {
        return recipeItemId;
    }

    public void setRecipeItemId(Integer recipeItemId) {
        this.recipeItemId = recipeItemId;
    }

    public String getRecipeItemName() {
        return recipeItemName;
    }

    public void setRecipeItemName(String recipeItemName) {
        this.recipeItemName = recipeItemName;
    }

    public List<RecipeIngredients> getRecipeItemIngredients() {
        return recipeItemIngredients;
    }

    public void setRecipeItemIngredients(List<RecipeIngredients> recipeItemIngredients) {
        this.recipeItemIngredients = recipeItemIngredients;
    }

    public List<RecipeSteps> getRecipeItemSteps() {
        return recipeItemSteps;
    }

    public void setRecipeItemSteps(List<RecipeSteps> recipeItemSteps) {
        this.recipeItemSteps = recipeItemSteps;
    }

    public Integer getRecipeItemServings() {
        return recipeItemServings;
    }

    public void setRecipeItemServings(Integer recipeItemServings) {
        this.recipeItemServings = recipeItemServings;
    }

    public String getRecipeItemImage() {
        return recipeItemImage;
    }

    public void setRecipeItemImage(String recipeItemImage) {
        this.recipeItemImage = recipeItemImage;
    }

    @Override
    public String toString() {
        String comma = ",";
        String equal = "=";
        char backSlash = '\'';
        return "RecipeItem{" +
                RECIPE_ID + equal +recipeItemId+
                comma+RECIPE_NAME+equal+recipeItemName+backSlash+
                comma+RECIPE_INGREDIENTS+equal+recipeItemIngredients+backSlash+
                comma+RECIPE_STEPS+equal+recipeItemSteps+backSlash+
                comma+RECIPE_SERVINGS+equal+recipeItemServings+backSlash+
                comma+RECIPE_IMAGE+equal+recipeItemImage+backSlash+
                '}';
    }

/*    @Override
    public String toString() {
        return "RecipeItem{" +
                "id=" + recipeItemId +
                ", name='" + recipeItemName + '\'' +
                ", ingredients=" + recipeItemIngredients +
                ", steps=" + recipeItemSteps +
                ", servings=" + recipeItemServings +
                ", image='" + recipeItemImage + '\'' +
                '}';
    }*/
}