package com.ahmed.bakingapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RecipeIngredients implements Serializable {

    private static final String TAG = RecipeIngredients.class.getSimpleName();
    // Recipe Ingredients JSON UiConstants
    private static final String INGREDIENTS_QUANTITY = "quantity";
    private static final String INGREDIENTS_MEASURE = "measure";
    private static final String INGREDIENTS_INGREDIENT = "ingredient";

    // Recipe Ingredients Values
    @SerializedName(INGREDIENTS_QUANTITY)
    private Double ingredientsQuantity;

    @SerializedName(INGREDIENTS_MEASURE)
    private String ingredientsMeasure;

    @SerializedName(INGREDIENTS_INGREDIENT)
    private String ingredientsIngredient;

    public Double getIngredientsQuantity() {
        return ingredientsQuantity;
    }

    public void setIngredientsQuantity(Double ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }

    public String getIngredientsMeasure() {
        return ingredientsMeasure;
    }

    public void setIngredientsMeasure(String ingredientsMeasure) {
        this.ingredientsMeasure = ingredientsMeasure;
    }

    public String getIngredientsIngredient() {
        return ingredientsIngredient;
    }

    public void setIngredientsIngredient(String ingredientsIngredient) {
        this.ingredientsIngredient = ingredientsIngredient;
    }

    @Override
    public String toString() {
        String comma = ",";
        String equal = "=";
        char backSlash = '\'';
        return "RecipeIngredients{" +
                INGREDIENTS_QUANTITY + equal +ingredientsQuantity+
                comma+INGREDIENTS_MEASURE+equal+ingredientsMeasure+backSlash+
                comma+INGREDIENTS_INGREDIENT+equal+ingredientsIngredient+backSlash+
                '}';
    }
}
