package com.ahmed.bakingapp.network;

import com.ahmed.bakingapp.App;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Generator {

    private static Retrofit.Builder retroDefaultBuilder;

    private static Retrofit retrofit;

    // For Logging Purpose
    private static HttpLoggingInterceptor loggingInterceptor;
    private static OkHttpClient.Builder okHttpClientBuilder;

    private static Retrofit.Builder getRetroDefaultBuilder() {
        if (retroDefaultBuilder == null) {
            retroDefaultBuilder = new Retrofit.Builder();
            retroDefaultBuilder.addConverterFactory(GsonConverterFactory.create());
            retroDefaultBuilder.baseUrl(ApiConstants.getRecipesBaseUrl());
            // adding OKhttp Logging
            if (App.isDebuggable()) {
                if (getOkHttpClientBuilder().interceptors().contains(getLoggingInterceptor())) {
                    retroDefaultBuilder.client(getOkHttpClientBuilder().build());
                }
            }
        }
        return retroDefaultBuilder;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null){
            retrofit = getRetroDefaultBuilder().build();
        }
        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return getRetrofit().create(serviceClass);
    }

    // ======= ======= ======= Network Logging ======= ======= =======

    private static HttpLoggingInterceptor getLoggingInterceptor() {
        if (loggingInterceptor == null){
            loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor
                    .Level.BODY);
        }
        return loggingInterceptor;
    }

    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        if (okHttpClientBuilder == null){
            okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.addInterceptor(getLoggingInterceptor());
        }
        return okHttpClientBuilder;
    }
}
