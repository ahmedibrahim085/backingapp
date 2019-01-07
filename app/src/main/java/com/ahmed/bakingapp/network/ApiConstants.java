package com.ahmed.bakingapp.network;

class ApiConstants {

    final private static String RECIPES_BASE_URL =  "https://d17h27t6h515a5.cloudfront" +
            ".net/topher/2017/May/59121517_baking/";

    static String getRecipesBaseUrl() {
        return RECIPES_BASE_URL;
    }
}
